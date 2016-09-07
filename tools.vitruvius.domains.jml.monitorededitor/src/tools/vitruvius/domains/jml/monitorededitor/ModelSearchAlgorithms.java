package tools.vitruvius.domains.jml.monitorededitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * Container for search algorithms in model hierarchies.
 */
public class ModelSearchAlgorithms {

	/**
	 * Wrapper class for a boolean condition for a single EObject.
	 */
	public interface Criterion {

		/**
		 * @param obj
		 *            The object to be checked.
		 * @return True if the criterion matches.
		 */
		boolean matches(EObject obj);
	}

	/**
	 * Performs a breadth-first search with the given criterion.
	 * 
	 * @param rootObject
	 *            The start object for the search.
	 * @param criterion
	 *            The criterion for a match.
	 * @return The first matched element or null if there is no such element.
	 */
	public static EObject breadthFirstSearch(EObject rootObject,
			Criterion criterion) {
		Collection<EObject> result = breadthFirstSearch(rootObject, criterion,
				true, false);
		if (result.size() == 0) {
			return null;
		}
		return result.iterator().next();
	}

	/**
	 * Performs a breadth-first search with the given criterion.
	 * 
	 * @param rootObject
	 *            The start object for the search.
	 * @param criterion
	 *            The criterion for a match.
	 * @param noDuplicates
	 *            Indicates if duplicate values shall be filtered. This affects
	 *            the order of the results.
	 * @return The collection of matched objects.
	 */
	public static Collection<EObject> breadthFirstSearch(EObject rootObject,
			Criterion criterion, boolean noDuplicates) {
		return breadthFirstSearch(rootObject, criterion, false, noDuplicates);
	}

	/**
	 * Performs an inverse traversal of the containment hierarchy with a given
	 * criterion. The start object might match the criterion as well and is
	 * returned in that case.
	 * 
	 * @param rootObject
	 *            The object to start the search from.
	 * @param criterion
	 *            The criterion for a match.
	 * @return The matched object or null if no such object exists.
	 */
	public static EObject inverseTraversal(EObject rootObject,
			Criterion criterion) {
		EObject parent = rootObject;
		while (parent != null && !criterion.matches(parent)) {
			parent = parent.eContainer();
		}
		return parent;
	}

	/**
	 * Performs a breadth-first search with the given criterion.
	 * 
	 * @param rootObject
	 *            The start object for the search.
	 * @param criterion
	 *            The criterion for a match.
	 * @param noDuplicates
	 *            Indicates if duplicate values shall be filtered. This affects
	 *            the order of the results.
	 * @param stopAfterFirstMatch
	 *            Indicates if only the first match shall be returned.
	 * @return The collection of matched objects.
	 */
	private static Collection<EObject> breadthFirstSearch(EObject rootObject,
			Criterion criterion, boolean stopAfterFirstMatch,
			boolean noDuplicates) {
	    if (rootObject == null) {
	        throw new IllegalArgumentException("Searches can only be performed on non null objects.");
	    }
	    
		List<EObject> result = new ArrayList<EObject>();
		LinkedList<EObject> queue = new LinkedList<EObject>();
		queue.add(rootObject);
		while (!queue.isEmpty()) {
			EObject candidate = queue.pop();
			if (criterion.matches(candidate)) {
				result.add(candidate);
				if (stopAfterFirstMatch) {
					break;
				}
			}
			queue.addAll(0, candidate.eContents());
		}

		if (noDuplicates) {
			return new HashSet<EObject>(result);
		}
		return result;
	}
}
