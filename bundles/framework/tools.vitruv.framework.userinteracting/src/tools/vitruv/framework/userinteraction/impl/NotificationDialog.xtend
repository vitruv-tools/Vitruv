package tools.vitruv.framework.userinteraction.impl

import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.userinteraction.WindowModality

class NotificationDialog extends BaseDialog {
	private NotificationType notificationType = NotificationType.INFORMATION
	
	new(Shell parentShell, WindowModality modality, NotificationType type, String title, String message) {
		super(parentShell, modality, title, message)
		this.notificationType = type
		setPositiveButtonText("Okay")
	}
	
	new(Shell parentShell, WindowModality modality, String title, String message) {
		this(parentShell, modality, NotificationType.INFORMATION, title, message)
	}
	
	new(Shell parentShell, String title, String message) {
		this(parentShell, WindowModality.MODAL, title, message)
	}
	
	new(Shell parentShell, WindowModality modality, String message) {
		this(parentShell, modality, "Information", message)
	}
	
	new(Shell parentShell, String message) {
		this(parentShell, WindowModality.MODAL, "Information", message)
	}
	
	def NotificationType getNotificationType() { notificationType }
	def setNotificationType(NotificationType type) { this.notificationType = type }
	
    protected override Control createDialogArea(Composite parent) {
        var composite = super.createDialogArea(parent) as Composite
                
        val display = parent.getDisplay();
        val icon = display.getSystemImage(switch (notificationType) {
    		case INFORMATION: SWT.ICON_INFORMATION
    		case WARNING: SWT.ICON_WARNING
    		case ERROR: SWT.ICON_ERROR
    	})
    	
    	val margins = 20
    	val spacing = 40
    	val iconSideLength = 96
    	
    	val gridLayout = new GridLayout(2, false)
    	gridLayout.marginWidth = margins
    	gridLayout.marginHeight = margins
    	gridLayout.horizontalSpacing = spacing
    	composite.layout = gridLayout
    	
    	val shell = composite.getShell()
        shell.setImage(icon)
        
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
        createButton(parent, IDialogConstants.OK_ID, getPositiveButtonText(), true)
    }
    
    def static void main(String[] args) {
		val display = new Display()
		val shell = new Shell(display)
		
		shell.setText("Title Area Shell")
	    shell.pack()
	    val dialog = new NotificationDialog(shell, WindowModality.MODAL, NotificationType.INFORMATION, "Test Title", "Test Message which is a whole lot longer than the last one.")
		//dialog.positiveButtonText = "Copy That"
		dialog.blockOnOpen = true
		dialog.show()//open();
		display.dispose()
		
		/*while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep()
		}
		
		display.dispose()*/
	}
}
