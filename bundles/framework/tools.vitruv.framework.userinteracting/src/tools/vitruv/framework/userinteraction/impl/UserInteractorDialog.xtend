package tools.vitruv.framework.userinteraction.impl

import org.eclipse.swt.widgets.Shell

class UserInteractorDialog {
	static def void showMessage(Shell parentShell, boolean modal, String message) {
		val dialog = new InformationDialog(parentShell, modal, message)
		dialog.open
	}

	static def int selectFromMessage(
		Shell parentShell,
		boolean modal,
		boolean postponeable,
		String message,
		String... selectionDescriptions
	) {
		// default Dialog behavior is a modal window
		val dialog = new SelectionDialog(parentShell, modal, postponeable, message, selectionDescriptions)
		return dialog.open
	}
}
