package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.UserInteractionBase
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.change.interaction.InteractionResultProvider

/**
 * Base class for interactions defining methods common to all types of interaction like texts for common elements and code to
 * handle changes to the window modality.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
abstract class BaseInteraction<T extends UserInteractionBase> {
	@Accessors(PROTECTED_GETTER)
	final InteractionResultProvider interactionResultProvider;
	String title
	String message
	String positiveButtonText = "Yes"
	String negativeButtonText = "No"
	String cancelButtonText = "Cancel"
	WindowModality windowModality

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality, String title,
		String message) {
		this.interactionResultProvider = interactionResultProvider;
		this.title = title
		this.message = message
		this.windowModality = windowModality
	}

	def String getTitle() { title }

	def void setTitle(String title) { this.title = title }

	def String getMessage() { message }

	def void setMessage(String message) { this.message = message }

	def String getPositiveButtonText() { positiveButtonText }

	def setPositiveButtonText(String positiveButtonText) { this.positiveButtonText = positiveButtonText }

	def String getNegativeButtonText() { negativeButtonText }

	def setNegativeButtonText(String negativeButtonText) { this.negativeButtonText = negativeButtonText }

	def String getCancelButtonText() { cancelButtonText }

	def setCancelButtonText(String cancelButtonText) { this.cancelButtonText = cancelButtonText }

	def WindowModality getWindowModality() { windowModality }

	def void setWindowModality(WindowModality windowModality) {
		this.windowModality = windowModality
	}

	abstract def T startInteraction();
}
