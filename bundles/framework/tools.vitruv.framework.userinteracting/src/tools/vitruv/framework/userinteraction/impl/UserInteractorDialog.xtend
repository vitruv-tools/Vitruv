package tools.vitruv.framework.userinteraction.impl

import org.eclipse.jface.dialogs.Dialog
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell

class UserInteractorDialog {
	def static void showMessage(Shell parentShell, boolean modal, String message) {
		var InformationDialog dialog = new InformationDialog(parentShell, modal, message)
		dialog.open()
	}

	def static int selectFromMessage(Shell parentShell, boolean modal, boolean postponeable, String message,
		String... selectionDescriptions) {
		var SelectionDialog dialog = new SelectionDialog(parentShell, modal, postponeable, message,
			selectionDescriptions)
		return dialog.open()
	} // default Dialog behavior is a modal window
}

package class InformationDialog extends Dialog {
	final String message

	protected new(
		Shell parentShell,
		boolean modal_finalParam_,
		String message
	) {
		super(parentShell)
		var modal = modal_finalParam_
		if (!modal) {
			setShellStyle(getShellStyle().bitwiseAnd(SWT::APPLICATION_MODAL.bitwiseNot).bitwiseOr(SWT::MODELESS))
		}
		this.message = message
	}

	override protected Control createDialogArea(Composite parent) {
		var Composite composite = (super.createDialogArea(parent) as Composite)
		composite.getShell().setText("Information")
		var Label msgLabel = new Label(composite, SWT::HORIZONTAL)
		msgLabel.setText(this.message)
		return composite
	}

	override protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants::OK_ID, IDialogConstants::OK_LABEL, true)
	}
}

/** 
 * Returns code 0 if first message is selected, 1 for second etc. and -1 if user clicks 'postpone'
 * button.
 */
package class SelectionDialog extends Dialog {
	final String message
	final String[] selectionDescriptions
	final boolean postponable
	static final int POSTPONE = -1

	protected new(
		Shell parentShell,
		boolean modal_finalParam_,
		boolean postponeable,
		String message,
		String[] selectionDescriptions
	) {
		super(parentShell)
		var modal = modal_finalParam_
		if (!modal) {
			setShellStyle(getShellStyle().bitwiseAnd(SWT::APPLICATION_MODAL.bitwiseNot).bitwiseOr(SWT::MODELESS))
		}
		this.message = message
		this.selectionDescriptions = selectionDescriptions
		this.postponable = postponeable
	}

	override protected Control createDialogArea(Composite parent) {
		var Composite composite = (super.createDialogArea(parent) as Composite)
		composite.getShell().setText("Select")
		composite.setLayout(new GridLayout(1, true))
		var Label msgLabel = new Label(composite, SWT::HORIZONTAL)
		msgLabel.setText(this.message)
		createRadioButtons(this.selectionDescriptions, parent)
		return composite
	}

	def private void createRadioButtons(String[] selections, Composite parent) {
		var Group radioGroup = new Group(parent, SWT::SHADOW_ETCHED_IN)
		radioGroup.setLayout(new GridLayout())
		for (var int i = 0; i < selections.length; i++) {
			var Button radio = new Button(radioGroup, SWT::RADIO)
			radio.setText({
				val _rdIndx_selections = i
				selections.get(_rdIndx_selections)
			})
			val int j = i
			radio.addSelectionListener(new SelectionAdapter() {
				override void widgetSelected(SelectionEvent e) {
					setReturnCode(j)
				}
			})
		}
		radioGroup.pack()
	}

	override protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants::OK_ID, IDialogConstants::OK_LABEL, true)
		if(this.postponable) createButton(parent, POSTPONE, "Decide Later", false)
	}

	override protected void buttonPressed(int buttonId) {
		if (buttonId === POSTPONE) {
			setReturnCode(POSTPONE)
		}
		close()
	}
}
