package edu.kit.ipd.sdq.vitruvius.dsls.mapping.ui.launcher;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.MappingLanguageStandaloneSetup;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator.MappingLanguageGenerator;

public class MappingLanguageLaunchConfigurationDelegate extends LaunchConfigurationDelegate {
	@Inject
	private MappingLanguageGenerator generator;
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		String[] mappingFiles = configuration
				.getAttribute(MappingLanguageLaunchConfigurationTabGroup.FILES_TO_GENERATE, "").split("\n");
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		MappingLanguageStandaloneSetup setup = new MappingLanguageStandaloneSetup();
		Injector injector = setup.createInjectorAndDoEMFRegistration();
		injector.injectMembers(this);
		
		for (String mappingFile : mappingFiles) {
			URI uri = URI.createURI(mappingFile, true);
			if (uri.isPlatform()) {
				System.out.println(uri.toPlatformString(true));
				for (String s : uri.segments()) {
					System.out.println("Segment: " + s);
				}
				
				IProject project = workspace.getRoot().getProject(uri.segment(1));
				project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
				
				XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
				resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
				Resource resource = resourceSet.getResource(
				    uri, true);

				
//				ResourceSet rs = new XtextResourceSet();
//				Resource r = rs.getResource(uri, true);
//				generator.doGenerate(resource);
			} else {
				System.out.println("FALSE");
			}
		}
	}

}
