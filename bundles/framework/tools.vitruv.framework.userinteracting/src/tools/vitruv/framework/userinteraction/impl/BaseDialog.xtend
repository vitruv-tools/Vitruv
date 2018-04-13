package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.WindowModality
import org.eclipse.jface.dialogs.Dialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.graphics.Point

/**
 * Base class for dialogs defining methods common to all types of dialogs like texts for common elements and code to
 * handle changes to the window modality.
 * 
 * @author Dominik Klooz
 */
abstract class BaseDialog extends Dialog {
	private String title
	private String message
	private String positiveButtonText = "Yes"
	private String negativeButtonText = "No"
	private String cancelButtonText = "Cancel"
	private WindowModality windowModality
	
	new(Shell parentShell, WindowModality windowModality, String title, String message) {
		super(parentShell)//null as Shell)
		this.windowModality = windowModality
		this.title = title
		this.message = message
		updateWindowModality()
	}
	
	def String getTitle() { title }
	def void setTitle(String title) { this.title = title }
	
	def String getMessage() { message }
	def void setMessage(String message) { this.message = message }
	
	def String getPositiveButtonText() { positiveButtonText }
	def setPositiveButtonText(String positiveButtonText) { this.positiveButtonText = positiveButtonText }
	
	def String getNegativeButtonText() { negativeButtonText }
	def setNegativeButtonText(String negativeButtonText) { this.negativeButtonText = negativeButtonText }
	
	def String getCancelButtonText() { cancelButtonText }
	def setCancelButtonText(String cancelButtonText) { this.cancelButtonText = cancelButtonText }
	
	def WindowModality getWindowModality() { windowModality }
	def void setWindowModality(WindowModality windowModality) {
		this.windowModality = windowModality
		updateWindowModality()
	}
	
	/**
	 * Opens the dialog centered on the primary monitor.
	 */
	def show() {
		val screenSize = parentShell.display.getPrimaryMonitor().getBounds()
		parentShell.setLocation((screenSize.width - parentShell.getBounds().width) / 2,
			(screenSize.height - parentShell.getBounds().height) / 2
		)
		open()
	}
	
	private def updateWindowModality() {
	    /* TODO DK: in order for SWT.APPLICATION_MODAL hint to correctly work and make the dialog modal, the shell passed
	    to UserInteractor and ultimately to the dialog would have to be the one used by the workbench */
		shellStyle = shellStyle.bitwiseOr(SWT.APPLICATION_MODAL)
		if (windowModality == WindowModality.MODELESS) {
			shellStyle = shellStyle.bitwiseOr(SWT.MODELESS.bitwiseOr(SWT.APPLICATION_MODAL.bitwiseNot))
		}
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
}
