package tools.vitruv.framework.userinteraction.impl

import java.util.function.Function
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.SWT
import org.eclipse.swt.events.VerifyEvent
import org.eclipse.swt.events.VerifyListener
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import tools.vitruv.framework.userinteraction.WindowModality
import org.eclipse.swt.widgets.Display
import org.eclipse.jface.fieldassist.ControlDecoration
import org.eclipse.jface.fieldassist.FieldDecorationRegistry
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.GridData
import tools.vitruv.framework.userinteraction.impl.TextInputDialog.InputValidator
import org.eclipse.swt.graphics.Point
import tools.vitruv.framework.userinteraction.InputFieldType

class TextInputDialog extends BaseDialog {
	private String input
	private InputFieldType inputFieldType
	private InputValidator inputValidator
	private Text inputField
	private ControlDecoration inputDecorator
	
	public static final InputValidator NUMBERS_ONLY_INPUT_VALIDATOR = new InputValidator() {
        override getInvalidInputMessage(String input) { "Only numbers are allowed as input" }
        override isInputValid(String input) { input.matches("[0-9]*") }
    }
    
    public static final InputValidator ACCEPT_ALL_INPUT_VALIDATOR = new InputValidator() {
        override getInvalidInputMessage(String input) { "" }
        override isInputValid(String input) { true }
    }
	
	new(Shell shell, WindowModality modality, String title, String message, InputFieldType fieldType, InputValidator inputValidator) {
		super(shell, modality, title, message)
		this.title = title
		this.message = message
		this.inputFieldType = fieldType
		this.inputValidator = inputValidator
	}
	
	new(Shell shell, WindowModality modality, String title, String message, InputFieldType fieldType, Function<String, Boolean> inputValidator, String invalidInputMessage) {
		this(shell, modality, title, message, fieldType, new InputValidator() {
			override getInvalidInputMessage(String input) { "" }
			override isInputValid(String input) { inputValidator.apply(input) }
		})
	}
	
	new(Shell shell, WindowModality modality, String title, String message) {
		this(shell, modality, title, message, InputFieldType.SINGLE_LINE, [ text | true ], "")
	}
	
	def String getInput() { input }
	def void setInput(String newInput) { input = newInput }
	
	def InputValidator getInputValidator() { inputValidator }
    def void setInputValidator(InputValidator newInputValidator) { inputValidator = newInputValidator }
    
    def InputFieldType getInputFieldType() { inputFieldType }
    def void setInputFieldType(InputFieldType newInputFieldType) { inputFieldType = newInputFieldType }
	
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
        		var newText =  currentText.substring(0, e.start) + e.text + currentText.substring(e.end)
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
        val image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage()
        inputDecorator.setImage(image)
        inputDecorator.hide() // hide initially
        
        // use below listener instead of verify listener to (initially) allow entering illegal characters:
		/*inputField.addModifyListener(new ModifyListener() {								
			override modifyText(ModifyEvent e) {
				val input = (e.widget as Text).text
                if (!inputValidator.isInputValid(input)) { // place your condition here
                	inputDecorator.setDescriptionText(inputValidator.getInvalidInputMessage(""))
                    inputDecorator.show()
                }
                else {
                    inputDecorator.hide()
                }
            }
        })*/
        
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
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}
	
	override void okPressed() {
		if (!inputValidator.isInputValid(inputField.text)) {
			inputDecorator.showHoverText(inputDecorator.getDescriptionText())
			return
		}
		input = inputField.text
		close()
	}
	
	override void cancelPressed() {
		close()
	}
	
	
	def static void main(String[] args) {
		val display = new Display()
		val shell = new Shell(display)
		
	    val validator = [ String text | text.matches("[a-zA-Z]*") ]
	    val invalidMessage = "Only letters allowed"
	    val dialog = new TextInputDialog(shell, WindowModality.MODAL, "Test Title",
	    	"Test Message which is a whole lot longer than the last one.", InputFieldType.MULTI_LINE, validator,
	    	invalidMessage)
		dialog.blockOnOpen = true
		dialog.show()
		System.out.println(dialog.getInput())
		display.dispose()
	}
	
	
	/**
	 * Interface for input validators used in {@link TextInputDialog}s. The {@link #isInputValid(String) isInputValid}
	 * method is used to accept or deny input changes, the message constructed in
	 * {@link #getInvalidInputMessage(String) getInvalidInputMessage} is displayed whenever the user tries to enter
	 * illegal characters as determined by {@link #isInputValid(String) isInputValid}.
	 */
	interface InputValidator {
		
		/**
		 * Get a warning message to be displayed when the user tries to add illegal characters (as determined by 
		 * {@link isInputValid(String)}).
		 */
		def String getInvalidInputMessage(String input)
		
		/**
		 * Determines whether or not the current input is to be considered valid. If not, the illegal characters will
		 * not be added to the input.
		 */
		def boolean isInputValid(String input)
	}
}
