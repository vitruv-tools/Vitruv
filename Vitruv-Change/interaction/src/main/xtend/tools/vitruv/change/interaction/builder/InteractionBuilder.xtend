package tools.vitruv.change.interaction.builder

import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality

/**
 * Interface for functionality relevant to all types of dialog builders.<br>
 * <br>
 * InteractionBuilder rationale:<br>
 * For each type of dialog, there is a builder (...InteractionBuilderImpl) implementing a ...InteractionBuilder interface and
 * extending BaseInteractionBuilder which provides methods to specify dialog properties common to all types of dialogs. To
 * ensure that mandatory properties are set via the builder before the dialog is being built and shown, the
 * ...InteractionBuilder interfaces only declare one (mandatory) method, which then either returns further (inner) interfaces
 * representing further mandatory steps on the builder the same way, or an OptionalSteps interface, which contains
 * non-mandatory build steps as well as the common ones from the {@link InteractionBuilder} interface and the method to build
 * and display the dialog as well as get the user input (if any). A ...InteractionBuilderImpl implements all inner interfaces
 * of its ...InteractionBuilder interface to enable using it for all build steps. This is a form of a fluent Step Builder.
 * 
 * @param <V> the return type for the user input.
 * @param <T> type parameter for the return type of methods whose execution is optional when building a dialog. This
 *      makes it possible to reuse one implementation of the common methods declared here across multiple dialog
 *      builders. The type passed in has to implement this interface to ensure access to the methods declared here.
 * 
 * @author Dominik Klooz
 */
interface InteractionBuilder<V, T extends InteractionBuilder<V, T>> {

	/**
	 * Sets the title of the dialog, defaults to "Unspecified Title".
	 * 
	 * @param title The title to be set, if {@code null}, nothing happens.
	 * @return a matching DialogBuilder implementation for method chaining.
	 */
	def T title(String title)

	/**
	 * Sets the dialog window's modality, defaults to {@link tools.vitruv.change.interaction.UserInteractionOptions.WindowModality#MODELESS}.
	 * 
	 * @param windowModality    The modality of the window to be set, if {@code null}, nothing happens.
	 * @return a matching DialogBuilder implementation for method chaining.
	 */
	def T windowModality(WindowModality windowModality)

	/**
	 * Sets the text on the positive button, defaults to {@code "Yes"}.
	 * 
	 * @param text  The text to be set, if {@code null}, nothing happens.
	 * @return a matching DialogBuilder implementation for method chaining.
	 */
	def T positiveButtonText(String text)

	/**
	 * Sets the text on the negative button, defaults to {@code "No"}.
	 * 
	 * @param text  The text to be set, if {@code null}, nothing happens.
	 * @return a matching DialogBuilder implementation for method chaining.
	 */
	def T negativeButtonText(String text)

	/**
	 * Sets the text on the cancel button, defaults to {@code "Cancel"}.
	 * 
	 * @param text  The text to be set, if {@code null}, nothing happens.
	 * @return a matching DialogBuilder implementation for method chaining.
	 */
	def T cancelButtonText(String text)

	/**
	 * Creates and shows the dialog built by this builder, returns the user input (if any).
	 */
	def V startInteraction()
}
