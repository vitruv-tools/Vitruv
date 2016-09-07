package tools.vitruvius.domains.jml.monitorededitor;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.ResourceUtil;

import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.JMLMethodExpression;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpression;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.userinteraction.impl.UserInteractor;
import tools.vitruvius.framework.util.bridges.EcoreResourceBridge;
import tools.vitruvius.framework.util.datatypes.VURI;



public class JMLNumberOfSpecificationStatements extends org.eclipse.core.commands.AbstractHandler {
	
	private static final UserInteractor UI = new UserInteractor();
	
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow activeWindow = HandlerUtil.getActiveWorkbenchWindow(event);
        if (activeWindow == null) {
            return null;
        }
        IWorkbenchPage page = activeWindow.getActivePage();
        if (page == null) {
            return null;
        }
        IEditorPart editor = page.getActiveEditor();
        IResource res = ResourceUtil.getResource(editor.getEditorInput());
        IProject project = res.getProject();
        
        try {
			calculateModelStatsFor(project);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return null;
    }
    
    private void calculateModelStatsFor(IProject project) throws CoreException {
    	Collection<CompilationUnit> cus = loadJMLModels(project);
    	
    	Collection<JMLTypeExpression> typeSpecs = new Vector<JMLTypeExpression>();
    	Collection<JMLMethodExpression> methodSpecs = new Vector<JMLMethodExpression>();
    	
    	for (CompilationUnit cu : cus) {
    		typeSpecs.addAll(ModelUtilities.getChildrenOfType(cu, JMLTypeExpression.class, false));
    		methodSpecs.addAll(ModelUtilities.getChildrenOfType(cu, JMLMethodExpression.class, false));
    	}
		
    	showModelStats(typeSpecs, methodSpecs);
    }

    private void showModelStats(Collection<JMLTypeExpression> typeSpecs, Collection<JMLMethodExpression> methodSpecs) {
		StringBuilder msg = new StringBuilder();
		
		msg.append("Model stats for project:\n");
		msg.append("Type Specifications:\t"); msg.append(typeSpecs.size()); msg.append("\n");
		msg.append("Method Specifications:\t"); msg.append(methodSpecs.size()); msg.append("\n");
		msg.append("Total:\t"); msg.append(typeSpecs.size() + methodSpecs.size());
		
		UI.showMessage(UserInteractionType.MODAL, msg.toString());
    }

	private Collection<CompilationUnit> loadJMLModels(IProject project) throws CoreException {
		IFolder specFolder = project.getFolder("specs");
		final Collection<CompilationUnit> cus = new Vector<CompilationUnit>();
		specFolder.accept(new IResourceVisitor() {
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFolder) {
					return true;
				}
				
				if (resource instanceof IFile) {
					IFile file = (IFile)resource;
					if (file.getName().endsWith(".jml")) {
						ResourceSet rs = new ResourceSetImpl();
						Resource r = EcoreResourceBridge.loadResourceAtURI(VURI.getInstance(file).getEMFUri(), rs);
						cus.add((CompilationUnit)r.getContents().get(0));
					}
				}
				
				return false;
			}
		});
		return cus;
	}
    
}
