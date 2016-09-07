package tools.vitruvius.applications.jmljava.changesynchronizer;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import tools.vitruvius.applications.jmljava.helper.Utilities;

/**
 * Utility class for general model handling tools.
 */
public class ModelUtilities {

	/**
	 * Finds all transitively contained children of the given type.
	 * 
	 * @param eobject
	 *            The EObject, which contains the children.
	 * @param type
	 *            The type of the wanted children.
	 * @param keepOrder
	 *            Flag to choose if the implementation shall keep the order
	 *            implied by the breadth-first search.
	 * @param <T>
	 *            The type of the wanted children.
	 * @return The collection of children.
	 */
	public static <T extends EObject> Collection<T> getChildrenOfType(
			EObject eobject, Class<T> type, boolean keepOrder) {
		return Utilities.getChildrenOfType(eobject, type, keepOrder);
	}

	/**
	 * Finds the first parent of the given EObject, which is of the given type.
	 * 
	 * @param eobject
	 *            The EObject, which is owned by a wanted parent.
	 * @param type
	 *            The type of the wanted parent.
	 * @param <T>
	 *            The type of the wanted parent.
	 * @return The parent or null if no parent with the given type exists.
	 */
	public static <T extends EObject> T getParentOfType(EObject eobject,
			Class<T> type) {
		return Utilities.getParentOfType(eobject, type);
	}
	
	/**
     * Performs a breadth-first search for an element of a matching type.
     * 
     * @param rootObject
     *            The root object (search starts here).
     * @param type
     *            The wanted element type.
     * @param <T>
     *            The type parameter for the wanted element type.
     * @return The found object or null if no such element exists.
     */
	public static <T extends EObject> T getFirstChildOfType(EObject rootObject, Class<T> type) {
		return Utilities.getFirstChildOfType(rootObject, type);
	}
	
    /**
     * Clones a given object completely. All of its parents exist till the root node.
     * 
     * @param obj
     *            The object to be cloned.
     * @param <T>
     *            The type of the object.
     * @return The clone of the object.
     */
	public static <T extends EObject> T clone(T obj) {
		return Utilities.clone(obj);
	}

}
