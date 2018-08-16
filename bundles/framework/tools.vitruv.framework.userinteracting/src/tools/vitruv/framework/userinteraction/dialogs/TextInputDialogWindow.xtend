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
import org.eclipse.swt.widgets.Text
import org.eclipse.jface.fieldassist.ControlDecoration
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputFieldType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import org.eclipse.swt.events.VerifyEvent
import org.eclipse.swt.events.VerifyListener
import org.eclipse.jface.fieldassist.FieldDecorationRegistry

/**
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class TextInputDialogWindow extends BaseDialogWindow {
	private Text inputField
	private ControlDecoration inputDecorator
	private InputFieldType inputFieldType
	private final InputValidator inputValidator;
	private final String positiveButtonText;
	private final String cancelButtonText;
	private String input = "";
	
	new(Shell parent, WindowModality windowModality, String title, String message, String positiveButtonText,
		String cancelButtonText, InputValidator inputValidator) {
		super(parent, windowModality, title, message)
		this.inputValidator = inputValidator
		this.positiveButtonText = positiveButtonText;
		this.cancelButtonText = cancelButtonText;
	}

	override Control createDialogArea(Composite parent) {
		val composite = super.createDialogArea(parent) as Composite

		val margins = 20
		val spacing = 20

		val gridLayout = new GridLayout(1, false)
		gridLayout.marginWidth = margins
		gridLayout.marginHeight = margins
		gridLayout.verticalSpacing = spacing
		composite.layout = gridLayout

		val messageLabel = new Label(composite, SWT.WRAP)
		messageLabel.setText(this.message)
		var gridData = new GridData()
		gridData.horizontalAlignment = SWT.FILL
		gridData.grabExcessHorizontalSpace = true
		messageLabel.layoutData = gridData

		val linesProperty = switch (inputFieldType) {
			case SINGLE_LINE: SWT.SINGLE.bitwiseOr(SWT.CENTER)
			case MULTI_LINE: SWT.MULTI.bitwiseOr(SWT.V_SCROLL)
		}
		inputField = new Text(composite, linesProperty.bitwiseOr(SWT.BORDER))
		inputField.addVerifyListener(new VerifyListener() {

			override verifyText(VerifyEvent e) {
				var currentText = (e.widget as Text).getText()
				var newText = currentText.substring(0, e.start) + e.text + currentText.substring(e.end)
				if (!inputValidator.isInputValid(newText)) {
					e.doit = false
					inputDecorator.setDescriptionText(inputValidator.getInvalidInputMessage(newText))
					inputDecorator.show()
				} else {
					inputDecorator.hide()
				}
			}
		})

		inputDecorator = new ControlDecoration(inputField, SWT.CENTER)
		inputDecorator.setDescriptionText(inputValidator.getInvalidInputMessage(""))
		val image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).
			getImage()
		inputDecorator.setImage(image)
		inputDecorator.hide() // hide initially
		gridData = new GridData()
		gridData.horizontalAlignment = SWT.FILL
		gridData.grabExcessHorizontalSpace = true
		if (inputFieldType == InputFieldType.MULTI_LINE) {
			gridData.grabExcessVerticalSpace = true
			gridData.verticalAlignment = SWT.FILL
			composite.shell.minimumSize = new Point(300, 300)
		}
		inputField.layoutData = gridData

		return composite
	}

	override void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, positiveButtonText, true);
		createButton(parent, IDialogConstants.CANCEL_ID, cancelButtonText, true);
	}

	override void okPressed() {
		if (!inputValidator.isInputValid(inputField.text)) {
			inputDecorator.showHoverText(inputDecorator.getDescriptionText())
			return
		}
		this.input = inputField.text
		close()
	}

	override void cancelPressed() {
		this.input = null
		close()
	}

	public def String getInputText() {
		return input
	}
}
