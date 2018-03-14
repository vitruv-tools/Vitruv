package tools.vitruv.framework.userinteraction.impl

import java.util.function.Function
import org.eclipse.jface.dialogs.Dialog
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

class TextInputDialog extends Dialog {
	private String title
	private String message
	private String input
	private Function<String, String> inputValidator
	
	new(Shell shell, WindowModality modality, String title, String message, Function<String, String> inputValidator) {
		super(shell)
		this. title = title
		this.message = message
		this.inputValidator = inputValidator
	}
	
	new(Shell shell, WindowModality modality, String title, String message) {
		//super(shell)
		this(shell, modality, title, message, [ text | text ])
	}
	
	def String getInput() { input }
	
	def void setInput(String newInput) { input = newInput }
	
	override createDialogArea(Composite parent) {
		val composite = super.createDialogArea(parent) as Composite
        composite.getShell().setText("Information")
        
        val msgLabel = new Label(composite, SWT.HORIZONTAL)
        msgLabel.setText(this.message)
        
        val inputField = new Text(composite, SWT.SINGLE.bitwiseOr(SWT.CENTER))
        inputField.addVerifyListener(new VerifyListener() {
									
			override verifyText(VerifyEvent e) {
				var currentText = (e.widget as Text).getText()
        		var newText =  currentText.substring(0, e.start) + e.text + currentText.substring(e.end)
        		if (!newText.matches("[a-z]*")) {
        			e.doit = false
        		}
			}
		})
        /*val textObservable = WidgetProperties.text().observe(inputField)
        val strategy = new UpdateValueStrategy();
		strategy.setBeforeSetValidator(new IValidator() {
			def IStatus validate(Object value) {
				val input = value as String
				val validatedInput = inputValidator.apply(input)
				if (!input.equals(validatedInput)) {
					return Status.CANCEL_STATUS // TODO
				}
				return Status.OK_STATUS
			}
		});
		
		val source = PojoProperties.value(typeof(TextInputDialog), "input")
		var test = source.getValue(this)
		val binding = new DataBindingContext().bindValue(target,
		        source, 
		        strategy, null)*/
		
		//ControlDecorationSupport.create(binding, SWT.TOP.bitwiseOr(SWT.LEFT))
        
        return composite
	}
	
	override void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}
	
	override void okPressed() {
		System.out.println("OK pressed");
	}
	
	def static void main(String[] args) {
		val display = new Display()
		val shell = new Shell(display)
		
		shell.setText("Title Area Shell")
	    shell.pack()
	    val dialog = new TextInputDialog(shell, WindowModality.MODAL, "Test", "Test Message")
		dialog.open();
		while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep()
		}
		display.dispose()
	}
}