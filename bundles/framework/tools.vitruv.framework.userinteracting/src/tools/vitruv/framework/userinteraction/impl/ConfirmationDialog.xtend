package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.impl.BaseDialog
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.userinteraction.WindowModality
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.graphics.Point
import org.eclipse.ui.PlatformUI

class ConfirmationDialog extends BaseDialog {
	private boolean confirmed = false
	
	new(Shell parentShell, WindowModality windowModality, String title, String message) {
		super(parentShell, windowModality, title, message)
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
    
    def boolean getResult() {
    	return confirmed
    }
    
    def static void main(String[] args) {
		val display = Display.^default//PlatformUI.getWorkbench().display//new Display()
		val shell = new Shell(display)
		val dialog = new ConfirmationDialog(shell, WindowModality.MODAL, "Title", "Really?")
		//dialog.blockOnOpen = true
		dialog.show()
		display.dispose()
	}
	
}


/**
 * Builder class for {@link ConfirmationDialog}s. Use the add/set... methods to specify details and then call
 * createAndShow() to display and get a reference to the configured dialog.
 * Creates a dialog with a question and buttons to give a positive or negative answer.
 */
class ConfirmationDialogBuilder extends DialogBuilder {
    private ConfirmationDialog dialog
    
    public static final String STANDARD_TITLE = "Please Confirm"
    public static final String UNSPECIFIED_MESSAGE = "No confirmation message specified."
    
    new(Shell shell) {
        super(shell)
        title = "Please Confirm"
    }

    override def ConfirmationDialog createAndShow() {
        dialog = new ConfirmationDialog(shell, windowModality, title, message)
        dialog.show()
        return dialog
    }
}
