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
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType

/**
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class NotificationDialogWindow extends BaseDialogWindow {
	private final NotificationType notificationType;
	private final String positiveButtonText;
	
	new(Shell parent, WindowModality windowModality, String title, String message, String positiveButtonText,
		NotificationType notificationType) {
		super(parent, windowModality, title, message)
		this.positiveButtonText = positiveButtonText;
		this.notificationType = notificationType;
	}

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
		createButton(parent, IDialogConstants.OK_ID, positiveButtonText, true)
	}
}
