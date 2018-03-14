package tools.vitruv.framework.userinteraction.impl

import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.SWT
import org.eclipse.jface.dialogs.IDialogConstants
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.NotificationType
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.jface.dialogs.Dialog
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.layout.FormData
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.GridData

class NotificationDialog extends BaseDialog {
	private NotificationType type
	
	new(Shell parentShell, WindowModality modality, NotificationType type, String title, String message) {
		super(parentShell, modality, title, message)
		this.type = type
	}
	
	//def show() {
		//var Dialog
		//open()
		//dialog = new MessageDialog(parentShell, "title", null, "message", MessageDialog.ERROR, 0, #["a", "b"])
		/*switch (type) {
			case INFORMATION: MessageDialog.openInformation(parentShell, title, message)
			case WARNING: MessageDialog.openWarning(parentShell, title, message)
			case ERROR: MessageDialog.openError(parentShell, title, message)
			default: throw new RuntimeException("Unknown Notification type!")
		}*/
		//setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL | SWT.MODELESS)
	//}
	
    protected override Control createDialogArea(Composite parent) {
        var composite = super.createDialogArea(parent) as Composite
        //composite.layout = new FormLayout()
                
        val display = parent.getDisplay();
        val icon = display.getSystemImage(switch (type) {
    		case INFORMATION: SWT.ICON_INFORMATION
    		case WARNING: SWT.ICON_WARNING
    		case ERROR: SWT.ICON_ERROR
    	})
    	
    	val gridLayout = new GridLayout(2, false)
    	gridLayout.marginWidth = 20
    	gridLayout.marginHeight = 20
    	gridLayout.horizontalSpacing = 20
    	composite.layout = gridLayout
    	
    	val shell = composite.getShell()
        shell.setText(title)
        shell.setImage(icon)
        //shell.layout = gridLayout
        //shell.setSize(340, 160)
        //shell.setLayout(new FormLayout());
        
        val iconLabel = new Label(composite, SWT.NONE);
        val iconLabelGridData = new GridData()
        iconLabelGridData.verticalAlignment = SWT.CENTER
        iconLabelGridData.grabExcessVerticalSpace = true
        /*val iconLabelFormData = new FormData();
        iconLabelFormData.top = new FormAttachment(0, 10);
        iconLabelFormData.left = new FormAttachment(0, 10);
        iconLabel.setLayoutData(iconLabelFormData);*/
        iconLabel.setImage(icon);
        iconLabel.setSize(new Point(96, 96));
        iconLabel.setLayoutData = iconLabelGridData

        val messageLabel = new Label(composite, SWT.WRAP)
        val messageLabelGridData = new GridData()
        messageLabelGridData.grabExcessHorizontalSpace = true
        messageLabelGridData.verticalAlignment = SWT.CENTER
        /*val messageLabelFormData = new FormData();
        messageLabelFormData.top = new FormAttachment(0, 15);
        messageLabelFormData.left = new FormAttachment(iconLabel, 10);
        messageLabelFormData.right = new FormAttachment(100, -10);
        // no icon:
        //messageLabelFormData.left = new FormAttachment(0, 15);
        //messageLabelFormData.right = new FormAttachment(100, -15);
        messageLabel.setLayoutData(messageLabelFormData);*/
        messageLabel.setText(message);
        messageLabel.setLayoutData(messageLabelGridData)
        
        return composite
    }
    
    // TODO: make it possible to provide other text for button
    protected override void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true)
    }
    def static void main(String[] args) {
		val display = new Display()
		val shell = new Shell(display)
		
		shell.setText("Title Area Shell")
	    shell.pack()
	    val dialog = new NotificationDialog(shell, WindowModality.MODAL, NotificationType.WARNING, "Test", "Test Message which is a whole lot longer than the last one.")
		dialog.show()//open();
		while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep()
		}
		
		display.dispose()
	}
}
