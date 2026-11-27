package tools.vitruv.change.interaction.builder.impl

import tools.vitruv.change.interaction.builder.MultipleChoiceSelectionInteractionBuilder
import tools.vitruv.change.interaction.builder.MultipleChoiceSelectionInteractionBuilder.ChoicesStep
import tools.vitruv.change.interaction.builder.MultipleChoiceSelectionInteractionBuilder.OptionalSteps
import tools.vitruv.change.interaction.UserInteractionListener
import tools.vitruv.change.interaction.types.MultipleChoiceSelectionInteraction
import tools.vitruv.change.interaction.types.InteractionFactory

/**
 * Base implementation of the dialog builder for single- and multi-select {@link MultipleChoiceSelectionInteraction}s.
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
