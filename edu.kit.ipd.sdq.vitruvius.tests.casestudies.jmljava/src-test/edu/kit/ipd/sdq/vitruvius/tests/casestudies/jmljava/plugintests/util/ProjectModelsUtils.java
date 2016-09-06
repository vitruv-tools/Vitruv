package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FieldDeclaration;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MethodDeclaration;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalClassDeclaration;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VariableDeclarator;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.ModelUtilities;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;

public class ProjectModelsUtils {

	private static class Container<T> {
		private T element = null;
		public T getElement() {
			return element;
		}
		public void setElement(T element) {
			this.element = element;
		}
	}
	
	private final IProject project;
	
	public ProjectModelsUtils(IProject project) {
		this.project = project;
	}
	
	public NormalClassDeclaration getType(final String fileName, final String typeName) throws CoreException {
		CompilationUnit cu = loadJMLModel(fileName);
		
		Optional<NormalClassDeclaration> type = Iterables.tryFind(ModelUtilities.getChildrenOfType(cu, NormalClassDeclaration.class, false), new Predicate<NormalClassDeclaration>() {
			@Override
			public boolean apply(NormalClassDeclaration input) {
				return input.getIdentifier().equals(typeName);
			}
		});
		
		if (!type.isPresent()) {
			return null;
		}
		return type.get();
	}
	
	public MethodDeclaration getMethod(final String fileName, final String typeName, final String methodName) throws CoreException {
		NormalClassDeclaration type = getType(fileName, typeName);
		if (type == null) {
			return null;
		}
		
		Optional<MethodDeclaration> method = Iterables.tryFind(ModelUtilities.getChildrenOfType(type, MethodDeclaration.class, false), new Predicate<MethodDeclaration>() {
			@Override
			public boolean apply(MethodDeclaration input) {
				return input.getIdentifier().equals(methodName);
			}
		});
		
		if (!method.isPresent()) {
			return null;
		}
		return method.get();
	}
	
	public VariableDeclarator getField(final String fileName, final String typeName, final String fieldName) throws CoreException {
		NormalClassDeclaration type = getType(fileName, typeName);
		if (type == null) {
			return null;
		}
		
		final Predicate<VariableDeclarator> variableDeclaratorPrecidate = new Predicate<VariableDeclarator>() {
			@Override
			public boolean apply(VariableDeclarator input) {
				return input.getIdentifier().equals(fieldName);
			}
		};
		
		Optional<FieldDeclaration> field = Iterables.tryFind(ModelUtilities.getChildrenOfType(type, FieldDeclaration.class, false), new Predicate<FieldDeclaration>() {
			@Override
			public boolean apply(FieldDeclaration input) {
				return Iterables.any(input.getVariabledeclarator(), variableDeclaratorPrecidate);
			}
		});
		
		if (!field.isPresent()) {
			return null;
		}
		
		Optional<VariableDeclarator> varDecl = Iterables.tryFind(field.get().getVariabledeclarator(), variableDeclaratorPrecidate);
		
		return varDecl.isPresent() ? varDecl.get() : null;
	}
	
	private CompilationUnit loadJMLModel(final String fileName) throws CoreException {
		final Container<IFile> result = new Container<IFile>();
		IFolder specFolder = project.getFolder("specs");
		specFolder.accept(new IResourceVisitor() {
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFolder) {
					return true;
				}
				
				if (resource instanceof IFile) {
					IFile file = (IFile)resource;
					if (file.getName().equals(fileName)) {
						result.setElement(file);
					}
				}
				
				return false;
			}
		});
		
		if (result.getElement() == null) {
			return null;
		}
		
		ResourceSet rs = new ResourceSetImpl();
		Resource r = EcoreResourceBridge.loadResourceAtURI(VURI.getInstance(result.getElement()).getEMFUri(), rs);
		return (CompilationUnit)r.getContents().get(0);
	}
	
}
