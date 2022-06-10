package tools.vitruv.testutils

import org.eclipse.emf.ecore.EObject

/** 
 * DO NOT USE THIS CLASS!
 * <p>
 * This is a temporary class for providing access to correspondences in the outdated {@link LegacyVitruvApplicationTest}.
 * It should not be used in any other/new scenario scenario but only when using that legacy test class.
 */
interface LegacyCorrespondenceRetriever {
	/**
	 * Retrieves objects corresponding to the given {@link EObject} having the specified type.
	 */
	def <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject eObject, Class<T> type)

	/**
	 * Retrieves objects corresponding to the given {@link EObject} having the specified type
	 * and whose correspondence is tagged with the given String tag.
	 */
	def <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject eObject, Class<T> type, String tag)
}
