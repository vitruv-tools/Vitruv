package tools.vitruv.framework.userinteraction.dialogs

import org.eclipse.jface.dialogs.Dialog
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import org.eclipse.swt.SWT

/**
 * @author Dominik Klooz
 * @author Heiko Klare
 */
abstract class BaseDialogWindow extends Dialog {
	protected final String title;
	protected final String message;
	
	new(Shell parent, WindowModality windowModality, String title, String message) {
		super(parent);
		this.title = title;
		this.message = message;
		updateWindowModality(windowModality)
	}

	protected override Control createDialogArea(Composite parent) {
		val composite = super.createDialogArea(parent) as Composite
		composite.shell.text = title
		composite.shell.minimumSize = new Point(300, 200)
		return composite
	}

	override boolean isResizable() {
		return true // make dialogs resizable by default
	}

	/**
	 * Opens the dialog centered on the primary monitor.
	 */
	def show() {
		if (parentShell !== null) {
			val screenSize = parentShell.display.getPrimaryMonitor().getBounds()
			parentShell.setLocation((screenSize.width - parentShell.getBounds().width) / 2,
				(screenSize.height - parentShell.getBounds().height) / 2)
		}
		open()
	}

	private def updateWindowModality(WindowModality windowModality) {
		if (windowModality == WindowModality.MODAL) {
			shellStyle = shellStyle.bitwiseOr(SWT.APPLICATION_MODAL)
			blockOnOpen = true
		}
		if (windowModality == WindowModality.MODELESS) {
			shellStyle = shellStyle.bitwiseOr(SWT.MODELESS.bitwiseOr(SWT.APPLICATION_MODAL.bitwiseNot))
			blockOnOpen = false
		}
	}

}
