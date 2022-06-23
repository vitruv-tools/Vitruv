package tools.vitruv.extensions.changevisualization.handlers;

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

import tools.vitruv.extensions.changevisualization.ChangeVisualizationDataModel;
import tools.vitruv.extensions.changevisualization.ChangeVisualizationUI;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.VirtualModelManager;

/**
 * The plugin handler is used to react to clicks on the change visualization menu item. It enables or disables the
 * visualization through toggling its listening state for individual projects.
 */
@SuppressWarnings("restriction")
public class ChangeVisualizationHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get the project that has been right-clicked
		IProject project = getIProject(event);
		if (project == null) {
			// this should not happen, since we show the menuitem to click only in the popupmenu for a project
			return null;
		}

		// Get the virtual model for the project
		InternalVirtualModel virtualModel = getVirtualModelImpl(project);
		if (virtualModel == null) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			// Maybe the user clicked on the wrong project, give feeback
			MessageDialog.openInformation(window.getShell(), "Change visualization",
					"No registered virtual model found for project " + project.getName());
			return null;
		}

		enableChangeProcessing(virtualModel);
		return null;
	}

	private void enableChangeProcessing(InternalVirtualModel virtualModel) {
		final String virtualModelName = virtualModel.getFolder().getFileName().toString();
		ChangeVisualizationDataModel changeVisualization = new ChangeVisualizationDataModel();
		ChangeVisualizationUI changeVisualizationUI = new ChangeVisualizationUI(changeVisualization);
		changeVisualization.startMonitoringRepositoryChanges(virtualModelName, virtualModel);
		changeVisualizationUI.setVisible(true);
	}

	/**
	 * Retrieves the {@link InternalVirtualModel} for a given {@link IProject}. If no virtual model is registered for the
	 * given project null is returned.
	 * @param project The project
	 * @return The internalVirtualModel, may be null
	 */
	private InternalVirtualModel getVirtualModelImpl(IProject project) {
		return VirtualModelManager.getInstance().getVirtualModel(project.getLocation().toFile().toPath());
	}

	/**
	 * Retrieves the {@link IProject} for a given {@link ExecutionEvent}
	 * @param event The event
	 * @return The project generating the event, may be null
	 */
	private IProject getIProject(ExecutionEvent event) {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection) sel).getFirstElement();
			IResource resource = Platform.getAdapterManager().getAdapter(selected, IResource.class);
			if (resource != null) {
				IProject project = resource.getProject();
				return project;
			}
		}
		return null;
	}

}
