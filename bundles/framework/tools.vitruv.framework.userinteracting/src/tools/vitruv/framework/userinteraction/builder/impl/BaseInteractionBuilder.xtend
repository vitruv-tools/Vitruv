package tools.vitruv.framework.userinteraction.builder.impl

import tools.vitruv.framework.userinteraction.builder.InteractionBuilder
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.change.interaction.UserInteractionBase
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.types.BaseInteraction

/**
 * Abstract base class for dialog builder objects. The dialog to be built is created and returned in createAndShow, the
 * other methods are to be used beforehand to specify adjustments to the dialogs contents / behavior. Standard values
 * for properties not specified using the respective methods are set here or in the constructor for subclasses and
 * subclass-specific properties.<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * 
 * @param <V> type parameter for the return type of {@link #getResult() getResult()}, which returns the user input from
 *          the dialog.
 * @param <I> the interaction type to be built by this buidler
 * @param <T> type parameter for the return type of methods declared in {@link DialogBuilder}. In subclasses, this is to
 *          be set to the specific builder's {@code OptionalSteps} interface (the interface that extends
 *          {@link DialogBuilder} and contains methods whose execution is optional when building a dialog).
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
@Accessors abstract class BaseInteractionBuilder<V, I extends BaseInteraction<?>, T extends InteractionBuilder<V, T>> implements InteractionBuilder<V, T> {
	@Accessors(PROTECTED_GETTER)
	final InteractionFactory interactionFactory
	@Accessors(PROTECTED_GETTER)
	I interactionToBuild;
	@Accessors(NONE)
	Iterable<UserInteractionListener> userInteractionListener;

	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		this.interactionFactory = interactionFactory
		this.userInteractionListener = userInteractionListener
		interactionToBuild = createUserInteraction();
	}

	abstract def I createUserInteraction();

	/**
	 * @inheritDoc
	 * Implementations should call {@link #openDialog()} to make sure displaying the dialog is run on the UI thread.
	 */
	abstract override V startInteraction();

	protected abstract def T getSelf();

	def setMessage(String message) {
		if (message === null) {
			throw new IllegalArgumentException("Message is null!")
		}
		interactionToBuild.message = message
	}

	override T title(String title) {
		if (title !== null) {
			interactionToBuild.title = title
		}
		return self
	}

	override T windowModality(WindowModality windowModality) {
		if (windowModality !== null) {
			interactionToBuild.windowModality = windowModality
		}
		return self
	}

	override T positiveButtonText(String text) {
		if (text !== null) {
			interactionToBuild.positiveButtonText = text
		}
		return self
	}

	override T negativeButtonText(String text) {
		if (text !== null) {
			interactionToBuild.negativeButtonText = text
		}
		return self
	}

	override T cancelButtonText(String text) {
		if (text !== null) {
			interactionToBuild.cancelButtonText = text
		}
		return self
	}

	def notifyUserInputReceived(UserInteractionBase input) {
		for (listener : userInteractionListener) {
			listener.onUserInteractionReceived(input)
		}
	}
}
