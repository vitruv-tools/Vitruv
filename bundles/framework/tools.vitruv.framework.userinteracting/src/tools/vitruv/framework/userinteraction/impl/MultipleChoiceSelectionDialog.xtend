package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.impl.BaseDialog
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.userinteraction.WindowModality
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Composite
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Display
import java.util.Collection
import java.util.List
import java.util.ArrayList

class MultipleChoiceSelectionDialog extends BaseDialog {
	private SelectionType selectionType
	private String[] choices
	private int[] selectedChoices = #[]
	private Button[] choiceButtons
	
	new(Shell parentShell, WindowModality windowModality, String title, String message, String[] choices, SelectionType selectionType) {
		super(parentShell, windowModality, title, message)
		this.selectionType = selectionType
		setPositiveButtonText("Accept")
		this.choices = choices
	}
	
	def int[] getSelectedChoices() { selectedChoices }
	
	protected override Control createDialogArea(Composite parent) {
        var composite = super.createDialogArea(parent) as Composite
    	
    	val margins = 20
    	val spacing = 40
    	
    	val gridLayout = new GridLayout(1, false)
    	gridLayout.marginWidth = margins
    	gridLayout.marginHeight = margins
    	gridLayout.horizontalSpacing = spacing
    	composite.layout = gridLayout

        val messageLabel = new Label(composite, SWT.WRAP)
        var gridData = new GridData()
        gridData.grabExcessHorizontalSpace = true
        gridData.verticalAlignment = SWT.CENTER
        messageLabel.setText(message);
        messageLabel.layoutData = gridData
        
        val buttonType = switch(selectionType) {
        	case SINGLE_SELECT: SWT.RADIO
        	case MULTI_SELECT: SWT.CHECK
        }
        
        val group1 = new Group(composite, SWT.SHADOW_IN)
	    var choicesLayout = new RowLayout(SWT.VERTICAL)
	    choicesLayout.justify = true
	    choicesLayout.fill = true
	    choicesLayout.wrap = true
	    group1.setLayout(choicesLayout)
	    val Collection<Button> choiceButtons = new ArrayList<Button>()
	    choices.forEach[ choice |
	    	val button = new Button(group1, buttonType)
	    	button.setText(choice)
	    	choiceButtons.add(button)
	    ]
	    this.choiceButtons = choiceButtons.toArray(#[])
        
        return composite
    }
    
    protected override void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, positiveButtonText, true)
        createButton(parent, IDialogConstants.CANCEL_ID, cancelButtonText, true)
    }
    
    override okPressed() {
    	selectedChoices = choiceButtons.indexed().filter(pair | pair.value.selection).map(pair | pair.key)
    	close()
    }
    
    override cancelPressed() {
    	close()
    }
    
    def static void main(String[] args) {
		val display = new Display()
		val shell = new Shell(display)
		
	    val dialog = new MultipleChoiceSelectionDialog(shell, WindowModality.MODAL, "Test Title",
	    	"Test Message which is a whole lot longer than the last one.", #["AAAAAA", "B", "C"] ,SelectionType.SINGLE_SELECT)
		dialog.blockOnOpen = true
		dialog.show()
		System.out.println(dialog.getSelectedChoices.join(", "))
		display.dispose()
	}
    
	
	public enum SelectionType {
		SINGLE_SELECT, MULTI_SELECT
	}
}