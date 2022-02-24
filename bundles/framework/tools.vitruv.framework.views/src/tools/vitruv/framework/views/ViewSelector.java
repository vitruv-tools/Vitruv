package tools.vitruv.framework.views;

/**
 * A selector for selecting the elements to be represented in a view. It
 * encapsulates a modifiable {@link ViewSelection}, which it is able to validate
 * and which is then passed to a created view. It is capable of acting as a
 * builder for a view by providing an appropriate creation method.
 */
public interface ViewSelector extends ModifiableViewSelection {
	/**
	 * Creates a view for the underlying source models and the view type this
	 * selector has been created for as well as for the selection performed in this
	 * selector. May only be called if the current selection is valid as returned by
	 * {@link #isValid()}.
	 * 
	 * @return the created {@link View}
	 */
	View createView();

	/**
	 * Checks whether the current selection is valid and thus calling
	 * {@link #createView()} is possible.
	 * 
	 * @return whether the current selection is valid
	 */
	boolean isValid();

	/**
	 * Returns an immutable copy of the selection resulting from this selector.
	 * 
	 * @return an immutable copy of the selection in this selector
	 */
	ViewSelection getSelection();
}
