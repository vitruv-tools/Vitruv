package tools.vitruv.framework.correspondence

import java.util.Set
import org.eclipse.emf.ecore.EObject

import tools.vitruv.framework.correspondence.Correspondence
import java.util.List
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class CorrespondenceModelUtil {
	/**
	 * Returns all corresponding objects for the specified object and an empty set if the object has
	 * no correspondences.
	 * 
	 * @param eObject
	 *            the object for which corresponding objects are to be returned
	 * @return all corresponding objects for the specified object and an empty set if the object has
	 *         no correspondences.
	 */
	def static <U extends Correspondence> Set<EObject> getCorrespondingEObjects(CorrespondenceModelView<U> ci, EObject eObject) {
		getCorrespondingEObjects(ci, eObject, EObject)
	}

	/**
	 * Returns the corresponding objects of the specified type for the specified object and an empty set if
	 * the object has no matching corresponding elements.
	 * 
	 * @param eObject
	 *            the object for which the corresponding object is to be returned
	 * @param type
	 *            the class to filter the corresponding elements by
	 * @return the corresponding objects of the specified type for the specified object
	 */
	def static <T, U extends Correspondence> Set<T> getCorrespondingEObjects(CorrespondenceModelView<U> ci,
		EObject eObject, Class<T> type) {
		getCorrespondingEObjects(ci, eObject, type, null)
	}
	
	
	/**
	 * Returns the corresponding objects of the specified type for the specified object at in correspondences
	 * with the given tag and an empty set if the object has no matching corresponding elements.
	 * 
	 * @param eObject
	 *            the object for which the corresponding object is to be returned
	 * @param type
	 *            the class to filter the corresponding elements by
	 * @param tag
	 *            the tag of the correspondences for which corresponding elements shall be retrieved
	 * @return the corresponding objects of the specified type for the specified object
	 */
	def static <T, U extends Correspondence> Set<T> getCorrespondingEObjects(CorrespondenceModelView<U> ci,
		EObject eObject, Class<T> type, String tag) {
		ci.getCorrespondingEObjects(List.of(eObject), tag).flatten.filter(type).toSet
	}

}
