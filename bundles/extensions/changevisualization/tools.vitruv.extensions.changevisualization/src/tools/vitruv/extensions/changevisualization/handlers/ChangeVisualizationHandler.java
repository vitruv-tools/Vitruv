package tools.vitruv.extensions.changevisualization.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import tools.vitruv.extensions.changevisualization.ChangeVisualization;

/**
 * The plugin handler is used to react to clicks on the change visualization menu item.
 * It enables or disables the visualization through toggling its state.
 * 
 * @author Andreas Loeffler
 */
public class ChangeVisualizationHandler extends AbstractHandler {

	/**
	 * Stores the actual state of change listening
	 */
	private boolean active=false;

	/**
	 * Controls whether a popup is shown on state changes
	 */
	private final boolean SHOW_MESSAGES=false;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		if(active) {
			tools.vitruv.framework.vsum.VirtualModelImpl.removeChangeListener(ChangeVisualization.getChangeListener());
			if(SHOW_MESSAGES) {
				MessageDialog.openInformation(
						window.getShell(),
						"Change Visualization",
						"Change visualization deactivated");				
			}
			renameMenu(window,"Activate change visualization");
		}else {
			tools.vitruv.framework.vsum.VirtualModelImpl.addChangeListener(ChangeVisualization.getChangeListener());
			if(SHOW_MESSAGES) {
				MessageDialog.openInformation(
						window.getShell(),
						"Change Visualization",
						"Change visualization activated");				
			}			
			renameMenu(window,"Deactivate change visualization");
			ChangeVisualization.showUi();		}		

		active=!active;
		return null;
	}

	/**
	 * This method renames the menu item to reflect what will happen on the next click.
	 * 
	 * @param window The window needed to reach the menu item to rename
	 * @param text The new text to display in the menu item
	 */
	private void renameMenu(IWorkbenchWindow window, String text) {

	}
}
