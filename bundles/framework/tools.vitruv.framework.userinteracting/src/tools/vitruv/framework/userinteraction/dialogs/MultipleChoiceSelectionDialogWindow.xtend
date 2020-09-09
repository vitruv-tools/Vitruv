package tools.vitruv.framework.userinteraction.dialogs

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.layout.GridData
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Button
import java.util.List
import java.util.ArrayList
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.Group
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

/**
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class MultipleChoiceSelectionDialogWindow extends BaseDialogWindow {
	final boolean multiple;
	final Iterable<String> choices;
	List<Button> choiceButtons
	final String positiveButtonText;
	final String cancelButtonText;
	Iterable<Integer> selectedChoices;

	new(Shell parent, WindowModality windowModality, String title, String message, String positiveButtonText,
		String cancelButtonText, boolean multiple, Iterable<String> choices) {
		super(parent, windowModality, title, message)
		this.multiple = multiple;
		this.choices = choices;
		this.positiveButtonText = positiveButtonText;
		this.cancelButtonText = cancelButtonText;
		this.selectedChoices = new ArrayList<Integer>();
	}

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

		val buttonType = if (multiple) {
				SWT.CHECK;
			} else {
				SWT.RADIO
			}

		val group1 = new Group(composite, SWT.SHADOW_IN)
		var choicesLayout = new RowLayout(SWT.VERTICAL)
		choicesLayout.justify = true
		choicesLayout.fill = true
		choicesLayout.wrap = true
		group1.setLayout(choicesLayout)
		choiceButtons = new ArrayList<Button>()
		choices.forEach [ choice |
			val button = new Button(group1, buttonType)
			button.setText(choice)
			choiceButtons.add(button)
		]

		return composite
	}

	protected override void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, positiveButtonText, true)
		createButton(parent, IDialogConstants.CANCEL_ID, cancelButtonText, true)
	}

	override okPressed() {
		selectedChoices = choiceButtons.indexed().filter(pair|pair.value.selection).mapFixed(pair|pair.key);
		close()
	}

	override cancelPressed() {
		close()
	}

	def Iterable<Integer> getSelectedChoices() {
		return selectedChoices
	}
}
