package tools.vitruv.framework.userinteraction.types

import tools.vitruv.framework.userinteraction.types.SelectionType
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase
import java.util.ArrayList
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.resultprovider.InteractionResultProvider

/**
 * Implementation of a dialog providing a list of choices for the user to select a single or multiple ones, based on the
 * {@link SelectionType} specified.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
abstract class MultipleChoiceSelectionInteraction<I extends MultipleChoiceSelectionInteractionBase> extends BaseInteraction<I> {
	private static val DEFAULT_TITLE = "Please Select";
	private static val DEFAULT_MESSAGE = "";

	@Accessors(PROTECTED_GETTER)
	private final Iterable<String> choices

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
		setPositiveButtonText("Accept")
		this.choices = new ArrayList<String>();
	}

}
