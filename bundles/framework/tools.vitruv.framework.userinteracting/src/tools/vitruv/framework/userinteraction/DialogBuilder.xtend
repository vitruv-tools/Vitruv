package tools.vitruv.framework.userinteraction

/**
 * Interface for functionality relevant to all types of dialog builders.<br>
 * <br>
 * DialogBuilder rationale:<br>
 * For each type of dialog, there is a builder (...DialogBuilderImpl) implementing a ...DialogBuilder interface and
 * extending BaseDialogBuilder which provides methods to specify dialog properties common to all types of dialogs. To
 * ensure that mandatory properties are set via the builder before the dialog is being built and shown, the
 * ...DialogBuilder interfaces only declare one (mandatory) method, which then either returns further (inner) interfaces
 * representing further mandatory steps on the builder the same way, or an OptionalSteps interface, which contains
 * non-mandatory build steps as well as the common ones from the {@link DialogBuilder} interface and the method to build
 * and display the dialog as well as get the result. A ...DialogBuilderImpl implements all inner interfaces of its
 * ...DialogBuilder interface to enable using it for all build steps.
 * 
 * @param <V> the return type for the user input.
 * 
 * @author Dominik Klooz
 */
interface DialogBuilder<V> {
    
    /**
     * Sets the title of the dialog.
     */
    def DialogBuilder<V> title(String title)
    
    /**
     * Sets the dialog window's modality, defaults to {@link WindowModality.MODELESS}.
     */
    def DialogBuilder<V> windowModality(WindowModality windowModality)
    
    /**
     * Sets the text on the positive button, defaults to {@code "Yes"}.
     */
    def DialogBuilder<V> positiveButtonText(String text)
    
    /**
     * Sets the text on the negative button, defaults to {@code "No"}.
     */
    def DialogBuilder<V> negativeButtonText(String text)
    
    /**
     * Sets the text on the cancel button, defaults to {@code "Cancel"}.
     */
    def DialogBuilder<V> cancelButtonText(String text)

    /**
     * Creates and shows the dialog built by this builder, returns the user input.
     */
    def V showDialogAndGetUserInput()
}