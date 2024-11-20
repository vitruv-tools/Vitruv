package tools.vitruv.framework.views;

import org.eclipse.emf.ecore.EObject;

/**
 * A representation of the elements selected to be represented in a view.
 */
public interface ViewSelection {
	/**
	 * Returns whether the given element is selected to be represented in a view.
	 * 
	 * @return whether the given {@link EObject} is to be represented in the view
	 */
	boolean isViewObjectSelected(EObject eObject);
}
