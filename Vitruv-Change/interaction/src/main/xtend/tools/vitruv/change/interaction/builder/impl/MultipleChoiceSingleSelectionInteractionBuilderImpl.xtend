package tools.vitruv.change.interaction.builder.impl

import tools.vitruv.change.interaction.UserInteractionListener
import tools.vitruv.change.interaction.builder.MultipleChoiceSingleSelectionInteractionBuilder
import tools.vitruv.change.interaction.types.InteractionFactory
import tools.vitruv.change.interaction.types.MultipleChoiceSingleSelectionInteraction

/**
 * Builder class for {@link tools.vitruv.change.interaction.types.MultipleChoiceSelectionInteraction}s.
 * Creates a dialog with check boxes or radio buttons to pick multiple or a single entry from a list of choices.<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link tools.vitruv.change.interaction.builder.InteractionBuilder} javadoc.
 * @see MultipleChoiceSelectionInteractionBuilder
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class MultipleChoiceSingleSelectionInteractionBuilderImpl extends MultipleChoiceSelectionInteractionBuilderBaseImpl<Integer, MultipleChoiceSingleSelectionInteraction> implements MultipleChoiceSingleSelectionInteractionBuilder {

	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override createUserInteraction() {
		interactionFactory.createMultipleChoiceSingleSelectionInteraction();
	}

	override startInteraction() {
		val result = interactionToBuild.startInteraction();
		notifyUserInputReceived(result)
		return result.selectedIndex
	}

	override protected getSelf() {
		return this
	}

}
