package tools.vitruv.framework.userinteraction.impl

import org.eclipse.jface.dialogs.Dialog
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell

package class InformationDialog extends Dialog {
	val String message

	protected new(
		Shell parentShell,
		boolean modal_finalParam_,
		String message
	) {
		super(parentShell)
		val modal = modal_finalParam_
		if (!modal)
			setShellStyle(shellStyle.bitwiseAnd(SWT::APPLICATION_MODAL.bitwiseNot).bitwiseOr(SWT::MODELESS))
		this.message = message
	}

	override protected Control createDialogArea(Composite parent) {
		val Composite composite = (super.createDialogArea(parent) as Composite)
		composite.shell.text = "Information"
		val Label msgLabel = new Label(composite, SWT::HORIZONTAL)
		msgLabel.text = this.message
		return composite
	}

	override protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants::OK_ID, IDialogConstants::OK_LABEL, true)
	}
}
