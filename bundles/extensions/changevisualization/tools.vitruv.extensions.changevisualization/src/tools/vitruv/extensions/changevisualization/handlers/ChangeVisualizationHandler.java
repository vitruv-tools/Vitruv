package tools.vitruv.extensions.changevisualization.handlers;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import tools.vitruv.extensions.changevisualization.ChangeVisualization;
import tools.vitruv.framework.vsum.InternalVirtualModel;
import tools.vitruv.framework.vsum.PropagatedChangeListener;
import tools.vitruv.framework.vsum.VirtualModelImpl;
import tools.vitruv.framework.vsum.VirtualModelManager;

/**
 * The plugin handler is used to react to clicks on the change visualization menu item.
 * It enables or disables the visualization through toggling its state.
 * 
 * @author Andreas Loeffler
 */
public class ChangeVisualizationHandler extends AbstractHandler {

	/**
	 * Map of known propagated change listeners	
	 */
	private Map<IProject,PropagatedChangeListener> project2listener=new Hashtable<IProject,PropagatedChangeListener>();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		
		//get the project that has been right-clicked
		IProject project = getIProject(event);
		if(project==null) {
			return null;
		}
		
		String modelID=project.getName();
		VirtualModelImpl virtualModelImpl = getVirtualModelImpl(project);
		
		if(virtualModelImpl==null) {
			MessageDialog.openInformation(
					window.getShell(),
					"Change visualization",
					"No registered virtual model found for path "+project.getLocation().toFile().getAbsolutePath()
					);
			return null;
		}
			
		boolean active=project2listener.containsKey(project);
				
		if(active) {
			virtualModelImpl.removePropagatedChangeListener(project2listener.remove(project));
			MessageDialog.openInformation(
					window.getShell(),
					"Change visualization deactivated",
					"Change Visualization deactivated for virtual model "+modelID
					);			
		}else{
			//Enable listening
			PropagatedChangeListener propagatedChangeListener=ChangeVisualization.createPropagatedChangeListener(modelID);
			project2listener.put(project, propagatedChangeListener);
			virtualModelImpl.addPropagatedChangeListener(propagatedChangeListener);
			
			//bring UI to front
			ChangeVisualization.showUi();		
		}		
		return null;
	}

	/**
	 * Retrieves the {@link VirtualModelImpl} for a given {@link IPrject}.
	 * If no virtual model is registered for the given project or its instance is not of
	 * class {@link VirtualModelImpl} null is returned.
	 * 
	 * @param project The project
	 * @return The virtualModelImpl, may be null
	 */
	private VirtualModelImpl getVirtualModelImpl(IProject project) {
	      try {
	    	  InternalVirtualModel virtualModel = VirtualModelManager.getInstance().getVirtualModel(project.getLocation().toFile());
	    	  if(virtualModel==null||!(virtualModel instanceof VirtualModelImpl)) {
	    		  return null;
	    	  }
	    	  return (VirtualModelImpl) virtualModel;
	      }catch(UnsupportedOperationException ex) {
	    	  //If none is found, an exception is thrown rather than returning null
	    	  return null;
	     }
	}

	/**
	 * Retrieves the {@link IPrject} for a given {@link ExecutionEvent}
	 * 
	 * @param event The event
	 * @return The project generating the event, may be null
	 */
	private IProject getIProject(ExecutionEvent event) {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection){
			Object selected = ((IStructuredSelection)sel).getFirstElement();
			IResource resource = (IResource)Platform.getAdapterManager().getAdapter(selected, IResource.class);
			if (resource != null) {
				IProject project = resource.getProject();
				return project;
			}
		}
		return null;
	}

}
