package tools.vitruv.framework.correspondence

import com.google.common.collect.Sets
import java.util.Set
import org.eclipse.emf.ecore.EObject

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.toList
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.GenericCorrespondenceModel
import tools.vitruv.framework.correspondence.Correspondence

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
	// FIXME ML is this method correct? Is there some cool Xtend feature which makes this method shorter? 
	def public static Set<EObject> getCorrespondingEObjects(GenericCorrespondenceModel<?> ci, EObject eObject) {
		val correspondingEObjects = ci.getCorrespondingEObjects(eObject.toList)
		val eObjects = Sets.newHashSet
		correspondingEObjects.forEach(list|eObjects.addAll(list))
		return eObjects
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
	def public static Correspondence claimUniqueCorrespondence(CorrespondenceModelView<Correspondence> ci,
		EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimOne
	}

	/**
	 * Returns the correspondences for the specified object and throws a
	 * {@link RuntimeException} if no correspondence exists.
	 * 
	 * @param eObject
	 *            the object for which correspondences are to be returned
	 * @return the correspondences for the specified object
	 */
	def public static Set<Correspondence> claimCorrespondences(CorrespondenceModelView<?> ci,
		EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimNotEmpty as Set<Correspondence>
	}

	def public static Correspondence createAndAddCorrespondence(CorrespondenceModel ci, EObject a, EObject b) {
		return ci.createAndAddCorrespondence(a.toList, b.toList)
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
	def public static <T, U extends Correspondence> Set<T> getCorrespondingEObjectsByType(GenericCorrespondenceModel<U> ci,
		EObject eObject, Class<T> type) {
		// return getCorrespondingEObjects(ci, eObject).filter[eObj | type.isInstance(eObj)].toSet
		val Set<T> retSet = Sets.newHashSet
		getCorrespondingEObjects(ci, eObject).forEach[if (type.isInstance(it)) {retSet.add(it as T)}]
		return retSet
	}

}
