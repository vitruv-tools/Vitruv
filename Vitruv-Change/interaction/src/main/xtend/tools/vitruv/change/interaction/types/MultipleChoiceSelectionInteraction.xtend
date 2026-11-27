package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.MultipleChoiceSelectionInteractionBase
import java.util.ArrayList
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.InteractionResultProvider
import java.util.List

/**
 * Implementation of an interaction providing a list of choices for the user to select a single or multiple ones.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
abstract class MultipleChoiceSelectionInteraction<I extends MultipleChoiceSelectionInteractionBase> extends BaseInteraction<I> {
	static val DEFAULT_TITLE = "Please Select";
	static val DEFAULT_MESSAGE = "";

	@Accessors(PROTECTED_GETTER)
	final List<String> choices

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
		setPositiveButtonText("Accept")
		this.choices = new ArrayList<String>();
	}

	def void addChoice(String choice) {
		this.choices += choice;
	}
}
