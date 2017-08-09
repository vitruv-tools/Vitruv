package tools.vitruv.framework.userinteraction.impl

import java.util.Date
import javax.swing.JOptionPane
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.PlatformUI
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.util.bridges.EclipseUIBridge

/**
 * @author messinger
 * Implements {@link UserInteracting} and decouples {@link UserInteractorDialog} from
 * Platform.
 */
class UserInteractor implements UserInteracting {
	static val VITRUVIUS_TEXT_INPUT_DIALOG = "Vitruvius Text Input Dialog"
	protected val Display display
	protected val Shell shell

	new() {
		display = PlatformUI::workbench.display
		shell = null
	}

	override showMessage(UserInteractionType type, String message) {
		display.syncExec([
			switch (type) {
				case MODAL,
				case MODAL_POSTPONABLE: {
					UserInteractorDialog.showMessage(UserInteractor.this.shell, true, message)
				}
				case MODELESS: {
					UserInteractorDialog.showMessage(UserInteractor.this.shell, false, message)
				}
				default: {
				}
			}
		])
	}

	override selectFromMessage(UserInteractionType type, String message, String... selectionDescriptions) {
		val int[] result = newIntArrayOfSize(1)
		display.syncExec([
			switch (type) {
				case MODAL: {
					val _wrVal_result = result
					_wrVal_result.set(0,
						UserInteractorDialog.selectFromMessage(UserInteractor.this.shell, true, false, message,
							selectionDescriptions))
				}
				case MODAL_POSTPONABLE: {
					val _wrVal_result = result
					_wrVal_result.set(0,
						UserInteractorDialog.selectFromMessage(UserInteractor.this.shell, true, true, message,
							selectionDescriptions))
				}
				case MODELESS: {
					val _wrVal_result = result
					_wrVal_result.set(0,
						UserInteractorDialog.selectFromMessage(UserInteractor.this.shell, false, false, message,
							selectionDescriptions))
				}
				default: {
					val _wrVal_result = result
					_wrVal_result.set(0, -1)
				}
			}
		])
		return result.get(0)
	}

	override getTextInput(String msg) {
		val String textInput = JOptionPane::showInputDialog(null, msg, VITRUVIUS_TEXT_INPUT_DIALOG,
			JOptionPane.OK_OPTION)
		return textInput
	}

	override selectURI(String message) {
		EclipseUIBridge::askForNewResource(message)
	}

	override getAllUserInteractions() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override getAllUserInteractionsSince(Date date) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
