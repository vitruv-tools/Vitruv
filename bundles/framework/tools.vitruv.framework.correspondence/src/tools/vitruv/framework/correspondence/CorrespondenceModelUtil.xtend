package tools.vitruv.framework.correspondence

import java.util.Set
import org.eclipse.emf.ecore.EObject

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.Correspondence
import java.util.List

class CorrespondenceModelUtil {
	private new() {
	}

	/**
	 * Returns all corresponding objects for the specified object and an empty set if the object has
	 * no correspondences. Should never return {@link null}.
	 * 
	 * @param eObject
	 *            the object for which corresponding objects are to be returned
	 * @return all corresponding objects for the specified object and an empty set if the object has
	 *         no correspondences.
	 */
	def static Set<EObject> getCorrespondingEObjects(CorrespondenceModelView<?> ci, EObject eObject) {
		ci.getCorrespondingEObjects(List.of(eObject)).flatten.toSet
	}

	/**
	 * Returns the M2NCorrespondence for the given eObject
	 * {@link RuntimeException} if there is no such correspondence. Note that a has to be an element
	 * of metamodel a and b an instance of metamodel b.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	def static Correspondence claimUniqueCorrespondence(CorrespondenceModelView<Correspondence> ci,
		EObject eObject) {
		return ci.getCorrespondences(List.of(eObject)).claimOne
	}

	/**
	 * Returns the correspondences for the specified object and throws a
	 * {@link RuntimeException} if no correspondence exists.
	 * 
	 * @param eObject
	 *            the object for which correspondences are to be returned
	 * @return the correspondences for the specified object
	 */
	def static Set<Correspondence> claimCorrespondences(CorrespondenceModelView<?> ci,
		EObject eObject) {
		return ci.getCorrespondences(List.of(eObject)).claimNotEmpty as Set<Correspondence>
	}

	def static Correspondence createAndAddCorrespondence(CorrespondenceModel ci, EObject a, EObject b) {
		return ci.createAndAddCorrespondence(List.of(a), List.of(b), null)
	}

	/**
	 * Returns the corresponding object of the specified type for the specified object if there is
	 * exactly one corresponding object of this type and throws a {@link RuntimeException}
	 * otherwise.
	 * 
	 * @param eObject
	 *            the object for which the corresponding object is to be returned
	 * @param type
	 *            the class of which an instance is to be returned
	 * @return the corresponding object of the specified type for the specified object if there is
	 *         exactly one corresponding object of this type
	 */
	def static <T, U extends Correspondence> Set<T> getCorrespondingEObjectsByType(CorrespondenceModelView<U> ci,
		EObject eObject, Class<T> type) {
		getCorrespondingEObjects(ci, eObject).filter(type).toSet
	}

}
