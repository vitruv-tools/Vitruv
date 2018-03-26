package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.WindowModality
import org.eclipse.jface.dialogs.Dialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.graphics.Point
import org.eclipse.ui.PlatformUI
import org.eclipse.swt.widgets.Display

class BaseDialog extends Dialog {
	private String title
	private String message
	private String positiveButtonText = "Yes"
	private String negativeButtonText = "No"
	private String cancelButtonText = "Cancel"
	private WindowModality windowModality
	
	new(Shell parentShell, WindowModality windowModality, String title, String message) {
		super(parentShell)//null as Shell)
		this.windowModality = windowModality
		this.title = title
		this.message = message
		updateWindowModality()
	}
	
	def String getTitle() { title }
	def void setTitle(String title) { this.title = title }
	
	def String getMessage() { message }
	def void setMessage(String message) { this.message = message }
	
	def String getPositiveButtonText() { positiveButtonText }
	def setPositiveButtonText(String positiveButtonText) { this.positiveButtonText = positiveButtonText }
	
	def String getNegativeButtonText() { negativeButtonText }
	def setNegativeButtonText(String negativeButtonText) { this.negativeButtonText = negativeButtonText }
	
	def String getCancelButtonText() { cancelButtonText }
	def setCancelButtonText(String cancelButtonText) { this.cancelButtonText = cancelButtonText }
	
	def WindowModality getWindowModality() { windowModality }
	def void setWindowModality(WindowModality windowModality) {
		this.windowModality = windowModality
		updateWindowModality()
	}
	
	def show() {
		val screenSize = parentShell.display.getPrimaryMonitor().getBounds()
		parentShell.setLocation((screenSize.width - parentShell.getBounds().width) / 2,
			(screenSize.height - parentShell.getBounds().height) / 2
		)
		open()
	}
	
	private def updateWindowModality() {
	    /* TODO: in order for SWT.APPLICATION_MODAL hint to correctly work and make the dialog modal, the shell passed
	    to UserInteractor and ultimately to the dialog would have to be the one used by the workbench */
		/*val modalityFlags = switch (windowModality) {
			case MODAL: SWT.APPLICATION_MODAL.bitwiseOr(SWT.MODELESS.bitwiseNot)//.bitwiseOr(SWT.RESIZE)
			case MODELESS: SWT.MODELESS.bitwiseOr(SWT.APPLICATION_MODAL.bitwiseNot)
			default: throw new RuntimeException("Unknown WindowModality!")
		}*/
		shellStyle = shellStyle.bitwiseOr(SWT.APPLICATION_MODAL)
		if (windowModality == WindowModality.MODELESS) {
			shellStyle = shellStyle.bitwiseOr(SWT.MODELESS.bitwiseOr(SWT.APPLICATION_MODAL.bitwiseNot))
		}
	}
	
	protected override Control createDialogArea(Composite parent) {
		val composite = super.createDialogArea(parent) as Composite
		composite.shell.text = title
		composite.shell.minimumSize = new Point(300, 200)
		return composite
	}
	
	override boolean isResizable() {
		return true
	}
}


/**
 * Abstract base class for dialog builder objects. The dialog to be built is created and returned in createAndShow, the
 * other methods are to be used beforehand to specify adjustments to the dialogs contents / behavior. Standard values
 * for properties not specified using the respective methods are set here or in the constructor for subclasses and
 * subclass-specific properties.
 */
abstract class DialogBuilder {
    protected BaseDialog dialog
    protected Shell shell
    protected Display display
    protected String title = "Unspecified Title"
    protected String message = "No message specified."
    protected WindowModality windowModality = WindowModality.MODELESS
    protected String positiveButtonText = "Yes"
    protected String negativeButtonText = "No"
    protected String cancelButtonText = "Cancel"
    
    new(Shell shell, Display display) {
        this.shell = shell
        this.display = display
    }
    
    /**
     * Instantiates, displays and returns the dialog built by this object.
     */
    def abstract BaseDialog createAndShow()
    
    /**
     * Specifies the window title.
     */
    def DialogBuilder addTitle(String title) {
        this.title = title
        return this
    }
    
    /**
     * Specifies the dialog's message.
     */
    def DialogBuilder addMessage(String message) {
        this.message = message
        return this
    }
    
    /**
     * Sets the dialog window's modality, see {@link WindowModality}.
     */
    def DialogBuilder setWindowModality(WindowModality windowModality) {
        this.windowModality = windowModality
        return this
    }
    
    /**
     * Sets the text of the button used to agree to the dialog's intent, like "Yes", "Okay", "Accept" etc.
     */
    def DialogBuilder setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText
        return this
    }
    
    /**
     * Sets the text of the button used to decline the dialog's intent, like "No", "Deny" etc.
     */
    def DialogBuilder setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText
        return this
    }
    
    /**
     * Sets the text of the button used to cancel the dialog's intent, like "Cancel", "Abort" etc.
     */
    def DialogBuilder setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText
        return this
    }
    
    protected def void openDialog() {
        display.syncExec(new Runnable() {
            override void run() {
                dialog.show();
            }
        });
    }
}