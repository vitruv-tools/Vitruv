package tools.vitruv.framework.userinteraction.builder.impl

import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceSingleSelectionInteractionBuilder
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.types.MultipleChoiceSingleSelectionInteraction

/**
 * Builder class for {@link MultipleChoiceSelectionInteraction}s.
 * Creates a dialog with check boxes or radio buttons to pick multiple or a single entry from a list of choices.<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * @see MultipleChoiceSelectionInteractionBuilder
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
public class MultipleChoiceSingleSelectionInteractionBuilderImpl extends MultipleChoiceSelectionInteractionBuilderBaseImpl<Integer, MultipleChoiceSingleSelectionInteraction> implements MultipleChoiceSingleSelectionInteractionBuilder {

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
