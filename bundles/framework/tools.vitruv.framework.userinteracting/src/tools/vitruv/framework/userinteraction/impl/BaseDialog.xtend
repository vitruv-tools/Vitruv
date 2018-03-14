package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.WindowModality
import org.eclipse.jface.dialogs.Dialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.SWT

class BaseDialog extends Dialog {
	private String title
	private String message
	private WindowModality windowModality
	
	new(Shell parentShell, WindowModality windowModality, String title, String message) {
		super(parentShell)
		this.windowModality = windowModality
		this.title = title
		this.message = message
		updateWindowModality()
	}
	
	new(Shell parentShell) {
		super(parentShell)
	}
	
	def String getTitle() { title }
	def void setTitle(String title) { this.title = title }
	
	def String getMessage() { message }
	def void setMessage(String message) { this.message = message }
	
	def WindowModality getWindowModality() { windowModality }
	def void setWindowModality(WindowModality windowModality) {
		this.windowModality = windowModality
		updateWindowModality()
	}
	
	def show() {
		val screenSize = parentShell.display.getPrimaryMonitor().getBounds()
		parentShell.setLocation((screenSize.width - parentShell.getBounds().width) / 2,
			(screenSize.height - parentShell.getBounds().height) / 2
		)
		open()
	}
	
	private def updateWindowModality() {
		val modalityFlags = switch (windowModality) {
			case MODAL: SWT.APPLICATION_MODAL.bitwiseOr(SWT.MODELESS.bitwiseNot).bitwiseOr(SWT.RESIZE)
			case MODELESS: SWT.MODELESS.bitwiseOr(SWT.APPLICATION_MODAL.bitwiseNot)
			default: throw new RuntimeException("Unknown WindowModality!")
		}
		shellStyle = getShellStyle().bitwiseAnd(modalityFlags)
	}
	
	override boolean isResizable() {
		return true
	}
}