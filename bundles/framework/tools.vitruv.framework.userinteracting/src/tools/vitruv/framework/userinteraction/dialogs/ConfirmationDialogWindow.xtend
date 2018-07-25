package tools.vitruv.framework.userinteraction.dialogs

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.graphics.Point
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.widgets.Shell

/**
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class ConfirmationDialogWindow extends BaseDialogWindow {
	private boolean confirmed = false
	private final String positiveButtonText;
	private final String negativeButtonText;
	private final String cancelButtonText;

	new(Shell parent, WindowModality windowModality, String title, String message, String positiveButtonText,
		String negativeButtonText, String cancelButtonText) {
		super(parent, windowModality, title, message)
		this.positiveButtonText = positiveButtonText;
		this.negativeButtonText = negativeButtonText;
		this.cancelButtonText = cancelButtonText;
	}

	protected override Control createDialogArea(Composite parent) {
		var composite = super.createDialogArea(parent) as Composite

		val margins = 20
		val spacing = 40
		val iconSideLength = 96

		val display = parent.getDisplay();
		val icon = display.getSystemImage(SWT.ICON_QUESTION)

		val shell = composite.getShell()
		shell.setImage(icon)

		val gridLayout = new GridLayout(2, false)
		gridLayout.marginWidth = margins
		gridLayout.marginHeight = margins
		gridLayout.horizontalSpacing = spacing
		composite.layout = gridLayout

		val iconLabel = new Label(composite, SWT.NONE);
		var gridData = new GridData()
		gridData.verticalAlignment = SWT.CENTER
		gridData.grabExcessVerticalSpace = true
		iconLabel.setImage(icon);
		iconLabel.setSize(new Point(iconSideLength, iconSideLength));
		iconLabel.layoutData = gridData

		val messageLabel = new Label(composite, SWT.WRAP)
		gridData = new GridData()
		gridData.grabExcessHorizontalSpace = true
		gridData.verticalAlignment = SWT.CENTER
		messageLabel.setText(message);
		messageLabel.layoutData = gridData

		return composite
	}

	protected override void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, positiveButtonText, true)
		createButton(parent, IDialogConstants.CANCEL_ID, cancelButtonText, true)
	}

	override okPressed() {
		this.confirmed = true
		close()
	}

	override cancelPressed() {
		this.confirmed = false
		close()
	}

	def boolean getConfirmed() {
		return confirmed
	}

}
