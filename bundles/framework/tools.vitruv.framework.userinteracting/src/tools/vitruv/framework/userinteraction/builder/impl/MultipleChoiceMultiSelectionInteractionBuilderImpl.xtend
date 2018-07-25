package tools.vitruv.framework.userinteraction.builder.impl

import java.util.Collection
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.types.MultipleChoiceMultipleSelectionInteraction
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceMultiSelectionInteractionBuilder

/**
 * Implementation of an interaction builder for multiple choice dialogs that allow multiple items to be selected.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
public class MultipleChoiceMultiSelectionInteractionBuilderImpl extends MultipleChoiceSelectionInteractionBuilderBaseImpl<Collection<Integer>, MultipleChoiceMultipleSelectionInteraction> implements MultipleChoiceMultiSelectionInteractionBuilder {

	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override startInteraction() {
		val result = interactionToBuild.startInteraction();
		notifyUserInputReceived(result)
		return result.selectedIndices
	}

	override createUserInteraction() {
		interactionFactory.createMultipleChoiceMultipleSelectionInteraction();
	}

	override protected getSelf() {
		return this;
	}

}
