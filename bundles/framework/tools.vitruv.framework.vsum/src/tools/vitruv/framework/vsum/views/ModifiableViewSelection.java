package tools.vitruv.framework.vsum.views;

import org.eclipse.emf.ecore.EObject;

/**
 * A {@link ViewSelection} that can be changed by performing selections of the
 * elements.
 */
public interface ModifiableViewSelection extends ViewSelection {
	/**
	 * Returns the elements that can be selected, i.e., those that may be passed to
	 * {@link #isSelected(EObject)} and {@link #setSelected(EObject, boolean)}.
	 * 
	 * @return the selectable {@link EObject}s
	 */
	Iterable<EObject> getSelectableElements();

	/**
	 * Returns whether the given {@link EObject} is selected. May only be called for
	 * objects that are selectable (i.e., contained in
	 * {@link #getSelectableElements()} and throws and exception otherwise.
	 * 
	 * @param eObject the {@link EObject} to check the selection state for
	 * @return whether the given {@link EObject} is selected
	 */
	boolean isSelected(EObject eObject);

	/**
	 * Sets the selection state of the given {@link EObject}. May only be called for
	 * objects that are selectable (i.e., contained in
	 * {@link #getSelectableElements()} and throws and exception otherwise.
	 * 
	 * @param eObject  the {@link EObject} to set the selection state for
	 * @param selected whether the given {@link EObject} should be selected or not
	 */
	void setSelected(EObject eObject, boolean selected);
}
