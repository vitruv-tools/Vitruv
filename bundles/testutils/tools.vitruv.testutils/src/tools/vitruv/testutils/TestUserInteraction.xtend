package tools.vitruv.testutils

import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.LinkedList
import static java.lang.System.lineSeparator
import org.eclipse.xtend.lib.annotations.Accessors
import static extension tools.vitruv.testutils.printing.TestMessages.*
import java.util.Collection
import java.util.List
import java.util.ArrayList
import java.util.Set

class TestUserInteraction {
	val List<Pair<(ConfirmationInteractionDescription)=>boolean, Boolean>> confirmations = new LinkedList
	val List<Pair<(NotificationInteractionDescription)=>boolean, Void>> notificationReactions = new LinkedList
	val List<Pair<(TextInputInteractionDescription)=>boolean, String>> textInputs = new LinkedList
	val List<Pair<(MultipleChoiceInteractionDescription)=>boolean, (MultipleChoiceInteractionDescription)=>int>> multipleChoices = new LinkedList
	val List<Pair<(MultipleChoiceMultipleSelectionInteractionDescription)=>boolean, (MultipleChoiceMultipleSelectionInteractionDescription)=>int[]>> multipleChoiceMultipleSelections = new LinkedList

	def void addNextConfirmationInput(boolean nextConfirmation) {
		onNextConfirmation.respondWith(nextConfirmation)
	}

	def void addNextTextInput(String nextInput) {
		onNextTextInput.respondWith(nextInput)
	}

	def void addNextSingleSelection(int nextSelection) {
		onNextMultipleChoiceSingleSelection.respondWithChoiceAt(nextSelection)
	}

	def void addNextMultiSelection(int[] nextSelection) {
		onNextMultipleChoiceMultiSelection.respondWithChoicesAt(nextSelection)
	}
	
	/**
	 * Configure a response to a confirmation interaction. The next confirmation that matches the
	 * provided {@code condition} will be responded to with the result that is configured on the returned builder.
	 * Once the result has been provided, it will not be used again. If the {@code condition} matches an interaction
	 * that already has a programmed response, the response programmed on the returned builder will take precedence.
	 */	
	def onConfirmation((ConfirmationInteractionDescription)=>boolean condition) {
		new SimpleResponseBuilder(this, confirmations, condition)
	}
	
	/**
	 * Configures the response to the next confirmation interaction.
	 */
	def onNextConfirmation() {
		onConfirmation [true]
	}
	
	/**
	 * Acknowledges once (i.e. does not raise an error for) a notification passing the provided {@code check}.
	 */
	def acknowledgeNotification((NotificationInteractionDescription)=>boolean check) {
		notificationReactions += check -> null
		return this
	}
	
	/**
	 * Configures a response to a text input interaction. The next text input interaction that matches the
	 * provided {@code condition} will be responded to with the result that is configured on the returned builder.
	 * Once the result has been provided, it will not be used again. If the {@code condition} matches an interaction
	 * that already has a programmed response, the response programmed on the returned builder will take precedence.
	 */
	def onTextInput((TextInputInteractionDescription)=>boolean condition) {
		new SimpleResponseBuilder(this, textInputs, condition)
	}
	
	/**
	 * Configures the response to the next text input interaction.
	 */
	def onNextTextInput() {
		onTextInput [true]
	}
	
	/**
	 * Configures a response to a multiple choice interaction. The next multiple choice interaction that matches the
	 * provided {@code condition} will be responded to with the result that is configured on the returned builder.
	 * Once the result has been provided, it will not be used again. If the {@code condition} matches an interaction
	 * that already has a programmed response, the response programmed on the returned builder will take precedence.
	 */
	def onMultipleChoiceSingleSelection((MultipleChoiceInteractionDescription)=>boolean condition) {
		new MultipleChoiceResponseBuilder(this, condition)
	}
	
	/**
	 * Configures the response to the next multiple choice interaction.
	 */
	def onNextMultipleChoiceSingleSelection() {
		onMultipleChoiceSingleSelection [true]
	}
	
	/**
	 * Configures a response to a multiple choice interaction with multiple possible selections. The next such 
	 * interaction that matches the provided {@code condition} will be responded to with the result that is configured 
	 * on the returned builder.
	 * Once the result has been provided, it will not be used again. If the {@code condition} matches an interaction
	 * that already has a programmed response, the response programmed on the returned builder will take precedence.
	 */
	def onMultipleChoiceMultiSelection((MultipleChoiceMultipleSelectionInteractionDescription)=>boolean condition) {
		new MultipleChoiceMultipleSelectionResponseBuilder(this, condition)
	}
	
	/**
	 * Configures the response to the next multiple choice interaction with multiple possible selections.
	 */
	def onNextMultipleChoiceMultiSelection() {
		onMultipleChoiceMultiSelection [true]
	}
	
	def assertAllInteractionsOccurred() {
		val interactionsLeft = confirmations.size + notificationReactions.size + textInputs.size 
			+ multipleChoices.size + multipleChoiceMultipleSelections.size
		if (interactionsLeft > 0) {
			val resultMessage = new StringBuilder()
			resultMessage.joinSemantic(List.<Pair<String, Collection<?>>>of(
				"confirmation" -> confirmations,
				"notification" -> notificationReactions,
				"text input" -> textInputs,
				"single selection" -> multipleChoices,
				"multi selection" -> multipleChoiceMultipleSelections
			) .filter [!value.isEmpty].toList, 'and') [
				resultMessage.append(value.size).append(' ').append(plural(value, key))
			]
			.append(' ')
			.append(if (interactionsLeft === 1) 'was' else 'were')
			.append(' expected but did not occur!')
			
			throw new AssertionError(resultMessage.toString())
		} 
	}
	
	/**
	 * Removes all responses that have been programmed.
	 */
	def clearResponses() {
		confirmations.clear()
		notificationReactions.clear()
		textInputs.clear()
		multipleChoices.clear()
		multipleChoiceMultipleSelections.clear()
	}
	
	def listSize(StringBuilder result, Collection<?> list, String type) {
		if (!list.isEmpty) {
			result.append(list.size).append(' ').append(plural(list, type))
		} else null
	} 
	
	@FinalFieldsConstructor
	static class SimpleResponseBuilder<Description, Result> {
		val TestUserInteraction owner
		val List<Pair<(Description)=>boolean, Result>> targetQueue
		val (Description)=>boolean condition
		
		/**
		 * Responds the interaction with the provided {@code result} if the condition this builder was created for holds.
		 */
		def TestUserInteraction respondWith(Result result) {
			targetQueue += condition -> result
			return owner
		}
	}
	
	@FinalFieldsConstructor
	static class MultipleChoiceResponseBuilder {
		val TestUserInteraction owner
		val (MultipleChoiceInteractionDescription)=>boolean condition
		
		def TestUserInteraction respondWith(String result) {
			respondWithChoiceMatching [it == result]
		}
		
		def TestUserInteraction respondWithChoiceAt(int resultIndex) {
			owner.multipleChoices.add(condition -> [resultIndex])
			return owner
		}
		
		def TestUserInteraction respondWithChoiceMatching((String)=>boolean selector) {
			owner.multipleChoices.add(condition -> [assertedIndexBy(selector)])
			return owner
		}
		
		def private static int assertedIndexBy(MultipleChoiceInteractionDescription interaction, (String)=>boolean selector) {
			for (var i = 0, val choiceIt = interaction.choices.iterator; choiceIt.hasNext; i += 1) {
				if (selector.apply(choiceIt.next)) {
					return i
				}
			}
			throw new AssertionError('''«interaction.type» without an acceptable choice:«lineSeparator»«interaction»''')
		}
	}
	
	@FinalFieldsConstructor
	static class MultipleChoiceMultipleSelectionResponseBuilder {
		val TestUserInteraction owner
		val (MultipleChoiceMultipleSelectionInteractionDescription)=>boolean condition
		
		def TestUserInteraction respondWith(String... results) {
			respondWith(Set.of(results))
		}
		
		def TestUserInteraction respondWith(Set<String> results) {
			respondWithChoicesMatching [results.contains(it)]
		}
		
		def TestUserInteraction respondWithChoicesAt(int... resultIndeces) {
			owner.multipleChoiceMultipleSelections.add(condition -> [resultIndeces])
			return owner
		}
		
		def TestUserInteraction respondWithChoicesMatching((String)=>boolean selector) {
			owner.multipleChoiceMultipleSelections.add(condition -> [assertedIndecesBy(selector)])
			return owner
		}
		
		def private static int[] assertedIndecesBy(MultipleChoiceMultipleSelectionInteractionDescription interaction, (String)=>boolean selector) {
			val result = new ArrayList<Integer>
			for (var i = 0, val choiceIt = interaction.getChoices.iterator; choiceIt.hasNext; i += 1) {
				if (selector.apply(choiceIt.next)) {
					result += i
				}
			}
			if (result.isEmpty) {
				throw new AssertionError('''«interaction.type» without any acceptable choice:«lineSeparator»«interaction»''')
			}
			return result
		}
	}
	
	@FinalFieldsConstructor
	@Accessors 
	static abstract class InteractionDescription {
		val WindowModality windowModality
		val String title
		val String message
		val String positiveDecisionText
		def abstract String getType()
		
		override toString() {
			'''«title»: «message»'''
		}
	}	
	
	@FinalFieldsConstructor
	@Accessors
	static class ConfirmationInteractionDescription extends InteractionDescription {
		val String negativeDecisionText
		val String cancelDecisionText
		
		override getType() {
			"confirmation interaction"
		}
	}
	
	@FinalFieldsConstructor
	@Accessors
	static class TextInputInteractionDescription extends InteractionDescription {
		val String cancelDecisionText
		val InputValidator inputValidator
		
		override getType() {
			"text input interaction"
		}
	}
	
	@FinalFieldsConstructor
	@Accessors
	static class NotificationInteractionDescription extends InteractionDescription {
		val NotificationType notificationType
		
		override toString() {
			'''«notificationType» «super.toString()»'''
		}
		
		override getType() {
			"notification interaction"
		}
	}
	
	@FinalFieldsConstructor
	@Accessors
	static class MultipleChoiceInteractionDescription extends InteractionDescription {
		val String cancelDecisionText
		val Iterable<String> choices
		
		override toString() {
			super.toString() + choices.join('') ['''«lineSeparator»  • «it»''']
		}
		
		override getType() {
			"multiple choice interaction"
		}
	}
	
	static class MultipleChoiceMultipleSelectionInteractionDescription extends MultipleChoiceInteractionDescription {
		new(WindowModality windowModality, String title, String message, String positiveDecisionText, 
			String cancelDecisionText, Iterable<String> choices
		) {
			super(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices)
		}
		
		override getType() {
			"multiple choice interaction with multiple selections"
		}
	}

	@FinalFieldsConstructor
	static class ResultProvider implements InteractionResultProvider {
		val TestUserInteraction source
		
		override getConfirmationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
			source.confirmations.requireMatchingInteraction(
				new ConfirmationInteractionDescription(windowModality, title, message, positiveDecisionText,
					negativeDecisionText, cancelDecisionText)
			)
		}
		
		override getNotificationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, NotificationType notificationType) {
			source.notificationReactions.requireMatchingInteraction(
				new NotificationInteractionDescription(windowModality, title, message, 
					positiveDecisionText, notificationType)
			)
		}
		
		override getTextInputInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
			source.textInputs.requireMatchingInteraction(
				new TextInputInteractionDescription(windowModality, title, message, positiveDecisionText,
					cancelDecisionText, inputValidator)
			)
		}
		
		override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title, 
			String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
			val interaction = new MultipleChoiceInteractionDescription(windowModality, title, message,
					 positiveDecisionText, cancelDecisionText, choices)
			val resultProvider = source.multipleChoices.requireMatchingInteraction(interaction)
			return resultProvider.apply(interaction)
		}
		
		override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
			val interaction = new MultipleChoiceMultipleSelectionInteractionDescription(windowModality, title, message,
			 	positiveDecisionText, cancelDecisionText, choices)
			val resultProvider = source.multipleChoiceMultipleSelections.requireMatchingInteraction(interaction)
			return resultProvider.apply(interaction)
		}
		
		private static def <Description extends InteractionDescription, Result> requireMatchingInteraction(
			Iterable<Pair<(Description)=>boolean, Result>> options,
			Description interaction
		) {
			for (val optionsIt = options.iterator(); optionsIt.hasNext;) {
				val option = optionsIt.next()
				if (option.key.apply(interaction)) {
					optionsIt.remove()
					return option.value
				}
			}
			throw new AssertionError('''An unexpected «interaction.type» occurred:«lineSeparator»«interaction»''')
		}
	}
}
