package tools.vitruv.framework.userinteraction.impl

import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.userinteraction.WindowModality

/**
 * A dialog to notify the user about something.
 * 
 * @author Dominik Klooz
 */
class NotificationDialog extends BaseDialog {
	private NotificationType notificationType = NotificationType.INFORMATION
	
	new(Shell parentShell, WindowModality modality, NotificationType type, String title, String message) {
		super(parentShell, modality, title, message)
		this.notificationType = type
		setPositiveButtonText("Okay")
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
}
