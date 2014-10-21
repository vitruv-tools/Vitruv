package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Jonas Kunz
 *
 */
public final class EMFUtils {

	private EMFUtils() { }

	
	public static boolean isDirectOrIndirectParent(EObject parent, EObject child) {
		if (child.eContainer() == null) {
			return false;
		} else if (child.eContainer() == parent) {
			return true;
		} else {
			return isDirectOrIndirectParent(parent, child.eContainer());
		}
	}
	
	/**
	 * Lists all direct and indirect contents (children, children of children and so on) of the given type and returns them.
	 * @param parent
	 * 		the parent of the children to get.
	 * @param type
	 * 		the type of the childrne to get
	 * @return
	 * 		a lsit of children of the given parent and the given type
	 */
	public static <T> List<T> listDirectAndIndirectContents(EObject parent, Class<T> type) {
		ArrayList<T> children = new ArrayList<T>();
		EMFUtils.listDirectAndIndirectContents(parent, type, children);
		return children;
	}

	/**
	 * Lists all direct and indirect contents (children, children of children and so on)  of the given type and puts them in a list.
	 * @param parent
	 * 		the parent of the children to get.
	 * @param type
	 * 		the type of the childrne to get
	 * @param children
	 * 		the lsit to add the children to
	 */
	@SuppressWarnings("unchecked")
	public static <T> void listDirectAndIndirectContents(EObject parent, Class<T> type, List<T> children) {
		for(EObject obj: parent.eContents()) {
			if(type.isInstance(obj)) {
				children.add((T) obj);
			}
			listDirectAndIndirectContents(obj, type, children);
		}
	}

	/**
	 * Lists all direct contents (children) of the given type and returns them.
	 * @param parent
	 * 		the parent of the children to get.
	 * @param type
	 * 		the type of the childrne to get
	 * @return
	 * 		a lsit of children of the given parent and the given type
	 */
	public static <T> List<T> listDirectContents(EObject parent, Class<T> type) {
		ArrayList<T> children = new ArrayList<T>();
		EMFUtils.listDirectContents(parent, type, children);
		return children;
	}

	/**
	 * Lists all direct contents (children)  of the given type and puts them in a list.
	 * @param parent
	 * 		the parent of the children to get.
	 * @param type
	 * 		the type of the childrne to get
	 * @param children
	 * 		the lsit to add the children to
	 */
	@SuppressWarnings("unchecked")
	public static <T> void listDirectContents(EObject parent, Class<T> type, List<T> children) {
		for(EObject obj: parent.eContents()) {
			if(type.isInstance(obj)) {
				children.add((T) obj);
			}
		}
	}

	/**
	 * Helper method for checking whether the given class is a subclass of the other class
	 * @param parent
	 * 		the super class to check
	 * @param child
	 * 		the possible subclass to check
	 * @return
	 * 		true if child is a subclass of parent, false otherwise
	 */
	public static <T, E> boolean isClassSubclass(Class<T> parent, Class<E> child) {
		try {
			child.asSubclass(parent);
			return true;
		} catch (ClassCastException e) {
			return false;
		}
	}

	/**
	 * Walks up the containment-hierarchy and returns the first element foudn with the given type.
	 * The first considered element is the given Commentable itself.
	 * @param obj
	 * 		the object to start with
	 * @param type
	 * 		the type to find
	 * @return
	 * 		The parent of the given type, or null if the element does not have a parent of the given type.
	 */
	public static <T extends EObject> T getElementOrParentOfTyp(EObject obj, Class<T> type) {
		if (type.isInstance(obj)) {
			return type.cast(obj);
		} else {
			EObject parent = obj.eContainer();
			if(parent != null) {
				return getElementOrParentOfTyp(parent, type);	
			} else {
				return null;
			}
		}
	}

	/**
	 * Works like getElementOrParentOfTyp and puts the returned element in the given Collection.
	 * @param obj
	 * 		the object to start with
	 * @param collection
	 * 		the colelction to add the found parent to
	 * @param type
	 * 		the type to find
	 */
	public static <T extends EObject> void putElementOrParentOfTypeInCollection(EObject obj, Collection<T> collection, Class<T> type) {
		T elem = getElementOrParentOfTyp(obj, type);
		if (elem != null) {
			collection.add(elem);
		}
	}
	
}
