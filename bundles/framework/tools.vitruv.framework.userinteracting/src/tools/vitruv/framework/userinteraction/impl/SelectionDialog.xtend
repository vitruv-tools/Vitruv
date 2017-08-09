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

/**
 * Returns code 0 if first message is selected, 1 for second etc. and -1 if user clicks 'postpone'
 * button.
 */
package class SelectionDialog extends Dialog {
	static val POSTPONE = -1
	val String message
	val String[] selectionDescriptions
	val boolean postponable

	protected new(
		Shell parentShell,
		boolean modal_finalParam_,
		boolean postponeable,
		String message,
		String[] selectionDescriptions
	) {
		super(parentShell)
		val modal = modal_finalParam_
		if (!modal)
			setShellStyle(getShellStyle().bitwiseAnd(SWT::APPLICATION_MODAL.bitwiseNot).bitwiseOr(SWT::MODELESS))
		this.message = message
		this.selectionDescriptions = selectionDescriptions
		this.postponable = postponeable
	}

	override protected Control createDialogArea(Composite parent) {
		val composite = (super.createDialogArea(parent) as Composite)
		composite.shell.text = "Select"
		composite.setLayout(new GridLayout(1, true))
		val msgLabel = new Label(composite, SWT::HORIZONTAL)
		msgLabel.text = message
		createRadioButtons(selectionDescriptions, parent)
		return composite
	}

	private def void createRadioButtons(String[] selections, Composite parent) {
		val radioGroup = new Group(parent, SWT::SHADOW_ETCHED_IN)
		radioGroup.setLayout(new GridLayout)
		for (var int i = 0; i < selections.length; i++) {
			val radio = new Button(radioGroup, SWT::RADIO)
			radio.setText({
				val _rdIndx_selections = i
				selections.get(_rdIndx_selections)
			})
			val j = i
			radio.addSelectionListener(new SelectionAdapter {
				override widgetSelected(SelectionEvent e) {
					setReturnCode(j)
				}
			})
		}
		radioGroup.pack
	}

	override protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants::OK_ID, IDialogConstants::OK_LABEL, true)
		if(postponable) createButton(parent, POSTPONE, "Decide Later", false)
	}

	override protected void buttonPressed(int buttonId) {
		if (buttonId === POSTPONE)
			returnCode = POSTPONE
		close
	}
}
