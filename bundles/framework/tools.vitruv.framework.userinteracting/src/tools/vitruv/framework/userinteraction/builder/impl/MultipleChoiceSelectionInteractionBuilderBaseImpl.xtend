package tools.vitruv.framework.userinteraction.builder.impl

import tools.vitruv.framework.userinteraction.builder.MultipleChoiceSelectionInteractionBuilder
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceSelectionInteractionBuilder.ChoicesStep
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceSelectionInteractionBuilder.OptionalSteps
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.userinteraction.types.MultipleChoiceSelectionInteraction
import tools.vitruv.framework.userinteraction.types.InteractionFactory

/**
 * Base implementation of the dialog builder for single- and multi-select {@link MultipleChoiceInteraction}s. Implementation
 * of {@link #showDialogAndGetUserInput()} is left up to the two concrete subclasses
 * {@link MultipleChoiceSingleSelectionInteractionBuilderImpl} and {@link MultipleChoiceMultiSelectionInteractionBuilderImpl} so
 * they can specify the return type while inheriting everything else from here.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
abstract class MultipleChoiceSelectionInteractionBuilderBaseImpl<T, I extends MultipleChoiceSelectionInteraction<?>> extends BaseInteractionBuilder<T, I, OptionalSteps<T>> implements MultipleChoiceSelectionInteractionBuilder<T>, ChoicesStep<T>, OptionalSteps<T> {
	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override message(String message) {
		setMessage(message)
		return this
	}

	override choices(Iterable<String> choices) {
		if (choices === null || choices.length < 2) {
			throw new IllegalArgumentException("Provide at least two choices to pick from.")
		}
		choices.forEach[this.interactionToBuild.addChoice(it)]
		return this
	}
}
