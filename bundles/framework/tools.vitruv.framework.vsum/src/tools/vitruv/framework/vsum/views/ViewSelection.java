package tools.vitruv.framework.vsum.views;

import org.eclipse.emf.ecore.EObject;

/**
 * A representation of the elements selected to be represented in a view.
 */
public interface ViewSelection {
	/**
	 * Returns the selected elements to be represented in a view.
	 * 
	 * @return the {@link EObject}s to be represented in the view
	 */
	Iterable<EObject> getSelectedElements();
}
