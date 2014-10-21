package utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import de.uka.ipd.sdq.pcm.core.entity.Entity;

/**
 * @author Jonas Kunz
 *
 */
public final class PCMUtils {

	private PCMUtils () { }
	
	public static boolean isPCMObject(EObject element) {
		return element instanceof Entity;
	}
	
	/**
	 * Finds an element with the given name.
	 * The element itself and all its contents are considered, 
	 * if there are several elements with the same name, only the first one is returned.
	 * 
	 * For Example:
	 * 		my.test.package.ClassA.method1 will return the Method "method1" of ClassA in my.test.package
	 * 
	 * @param parentOrPossibleTarget
	 * 		the object to begin with
	 * @param name
	 * 		the fully qualified name of the element to find
	 * @return
	 * 		the found element or null if it was not found
	 */
	public static Entity findElementByName(EObject parentOrPossibleTarget, String name) {			
		if (parentOrPossibleTarget instanceof Entity) {
			String parentName = ((Entity) parentOrPossibleTarget).getEntityName();
			if (name.equals(parentName)) {
				return (Entity) parentOrPossibleTarget;
			}
			//if the name doesnt start with our name the lement is not in this branch
			if (!name.startsWith(parentName + ".")) {
				return null;
			}
			//remove our name from the search pattern
			name = name.substring((parentName + ".").length());		
		}		
		List<EObject> children = EMFUtils.listDirectContents(parentOrPossibleTarget, EObject.class);
		for (EObject child : children) {		
			Entity foundElement = findElementByName(child, name);
			if (foundElement != null) {
				return foundElement;
			}
		}
		return null;
	}
	
	public static String getFullElementName(EObject obj) {
		EObject parent = obj.eContainer();
		String name = "";
		if (parent != null) {
			name = getFullElementName(parent)+".";
		}
		if(obj instanceof Entity) {
			name += ((Entity) obj).getEntityName();
		}
		return name;
	}
	
	/**
	 * Finds all elements with the given name.
	 * The element itself and all its contents are considered, 
	 * 
	 * For Example:
	 * 		my.test.package.ClassA.method1 will return the Method "method1" of ClassA in my.test.package
	 * 
	 * @param name
	 * 		the fully qualified name of the elements to find
	 * @return
	 * 		a list of all elements found
	 */
	public static List<Entity> findAllElementsByName(EObject parentOrPossibleTarget, String name) {	
		List<Entity> foundElements = new ArrayList<Entity>();
		findAllElementsByName(parentOrPossibleTarget, name, foundElements);
		return foundElements;
	}

	/**
	 * Finds all elements with the given name.
	 * The element itself and all its contents are considered, 
	 * 
	 * For Example:
	 * 		my.test.package.ClassA.method1 will return the Method "method1" of ClassA in my.test.package
	 * 
	 * @param parentOrPossibleTarget
	 * 		the object to begin with
	 * @param name
	 * 		the fully qualified name of the elements to find
	 * @param foundElements
	 * 		the list where the found elements are appended
	 * @return
	 * 		a list of all elements found
	 */
	public static void findAllElementsByName(EObject parentOrPossibleTarget, String name, List<Entity> foundElements) {			
		if (parentOrPossibleTarget instanceof Entity) {
			String parentName = ((Entity) parentOrPossibleTarget).getEntityName();
			if (name.equals(parentName)) {
				foundElements.add((Entity) parentOrPossibleTarget);
				return;
			}
			//if the name doesnt start with our name the lement is not in this branch
			if (!name.startsWith(parentName + ".")) {
				return;
			}
			//remove our name from the search pattern
			name = name.substring((parentName + ".").length());		
		}		
		List<EObject> children = EMFUtils.listDirectContents(parentOrPossibleTarget, EObject.class);
		for (EObject child : children) {		
			findAllElementsByName(child, name, foundElements);
		}
	}
	
	
}
