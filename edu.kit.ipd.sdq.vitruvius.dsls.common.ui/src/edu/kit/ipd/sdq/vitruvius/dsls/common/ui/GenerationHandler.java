package edu.kit.ipd.sdq.vitruvius.dsls.common.ui;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator.MappingLanguageGenerator;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseFileSystemAccess;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.PrependPathFSA;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.IResponseEnvironmentGenerator;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseEnvironmentGeneratorFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile;

public class GenerationHandler extends AbstractHandler {
	private static class LanguageScope {
		@Inject
		public IResourceSetProvider resourceSetProvider;

		@Inject
		@Named(Constants.FILE_EXTENSIONS)
		public String fileExtension;
	}

	private static class ResponseScope extends LanguageScope {
		@Inject
		public ResponseEnvironmentGeneratorFactory responseEnvironmentGeneratorFactory;
	}

	private static class MappingScope extends LanguageScope {
		@Inject
		public MappingLanguageGenerator mappingLanguageGenerator;
	}

	private static class MIRResourceCollectionVisitor implements IResourceVisitor {
		private final ResourceSet mappingResourceSet;
		private final ResourceSet responseResourceSet;

		private final Collection<Resource> mappingResources;
		private final Collection<Resource> responseResources;

		private final MappingScope mappingScope;
		private final ResponseScope responseScope;

		public MIRResourceCollectionVisitor(IProject project, MappingScope mappingScope, ResponseScope responseScope) {
			this.mappingScope = mappingScope;
			this.responseScope = responseScope;

			mappingResourceSet = mappingScope.resourceSetProvider.get(project);
			responseResourceSet = responseScope.resourceSetProvider.get(project);

			mappingResources = new HashSet<>();
			responseResources = new HashSet<>();
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
				System.out.println(uri.toString());
				if (resource.getFileExtension().equals(responseScope.fileExtension)) {
					responseResources.add(responseResourceSet.getResource(uri, true));
				} else if (resource.getFileExtension().equals(mappingScope.fileExtension)) {
					mappingResources.add(mappingResourceSet.getResource(uri, true));
				}
			}

			return true;
		}

		public Collection<Resource> getMappingResources() {
			return mappingResources;
		}

		public Collection<Resource> getResponseResources() {
			return responseResources;
		}
	}

	public final MappingScope mappingScope = new MappingScope();
	public final ResponseScope responseScope = new ResponseScope();

	public static void acceptForEachSourceClassPathEntry(IJavaProject javaProject, IResourceVisitor resourceVisitor) {
		final IProject project = javaProject.getProject();
		final IPath rawLocation = project.getFullPath();
		try {
			for (IClasspathEntry classPathEntry : javaProject.getRawClasspath()) {
				if (classPathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					final IPath relativePath = classPathEntry.getPath().makeRelativeTo(rawLocation);
					final IFolder folder = project.getFolder(relativePath);
					folder.accept(resourceVisitor);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		MIRCommonActivator.getDefault().mappingInjector.injectMembers(mappingScope);
		MIRCommonActivator.getDefault().responseInjector.injectMembers(responseScope);

		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			final Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof IJavaProject) {
				final IJavaProject javaProject = (IJavaProject) firstElement;
				final IProject project = javaProject.getProject();
				final IFileSystemAccess fsa = new EclipseFileSystemAccess(javaProject);
				final IFileSystemAccess srcGenFSA = new PrependPathFSA(fsa, "src-gen");

				MIRResourceCollectionVisitor resourceVisitor = new MIRResourceCollectionVisitor(project, mappingScope,
						responseScope);
				acceptForEachSourceClassPathEntry(javaProject, resourceVisitor);

				final IResponseEnvironmentGenerator responseEnvironmentGenerator = responseScope.responseEnvironmentGeneratorFactory
						.createResponseEnvironmentGenerator();
				
				for (Resource mappingResource : resourceVisitor.getMappingResources()) {
					final Collection<Response> generatedResponses = mappingScope.mappingLanguageGenerator.doGenerate(mappingResource, srcGenFSA);
					responseEnvironmentGenerator.addResponses(generatedResponses);
				}

				for (Resource responseResource : resourceVisitor.getResponseResources()) {
					responseEnvironmentGenerator
							.addResponses(((ResponseFile) responseResource.getContents().get(0)).getResponses());
				}

				responseEnvironmentGenerator.generateEnvironment(srcGenFSA);

				System.out.println();
			}
		}
		return null;
	}

}
