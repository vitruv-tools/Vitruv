package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes

import java.util.Set
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import com.google.common.collect.Sets

class CorrespondenceInstanceUtil {
	private new (){
	}
	
	/**
     * Returns the corresponding objects of the specified type for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondences of this type exist.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @param type
     *            the class of which instances are to be returned
     * @return the corresponding objects of the specified type for the specified object
     */
     //FIXME ML is this method correct? Is there some cool Xtend feature which makes this method shorter?
	def public static <T> Set<T> claimCorrespondingEObjectsByType(CorrespondenceInstance ci, EObject eObject, Class<T> type) {
		val correspondingEObjects = ci.getCorrespondingEObjects(eObject.toList)
		val eObjects = Sets.newHashSet
		correspondingEObjects.forEach[list|eObjects.addAll(list.filter[eObj|type.isInstance(eObj)])]
		return eObjects as Set<T>
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
	def public static Correspondence claimUniqueCorrespondence(CorrespondenceInstance ci, EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimOne
	}
	
	 /**
     * Returns the correspondences for the specified object and throws a
     * {@link java.lang.RuntimeException} if no correspondence exists.
     *
     * @param eObject
     *            the object for which correspondences are to be returned
     * @return the correspondences for the specified object
     */
	def public static Set<Correspondence> claimCorrespondences(CorrespondenceInstance ci, EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimNotEmpty as Set<Correspondence>
	}
	
	 /**
     * Returns the corresponding object for the specified object if there is exactly one
     * corresponding object and throws a {@link java.lang.RuntimeException} otherwise.
     *
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @return the corresponding object for the specified object if there is exactly one
     *         corresponding object
     */
	def public static EObject claimUniqueCorrespondingEObject(CorrespondenceInstance ci, EObject eObject) {
		return ci.getCorrespondingEObjects(eObject.toList).claimOne.claimOne
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
    //FIXME ML is this method correct? Is there some cool Xtend feature which makes this method shorter? 
	def public static Set<EObject> getCorrespondingEObjects(CorrespondenceInstance ci, EObject eObject) {
		val correspondingEObjects = ci.getCorrespondingEObjects(eObject.toList)
		val eObjects = Sets.newHashSet
		correspondingEObjects.forEach(list|eObjects.addAll(list))
		return eObjects
	}
	
	def public static Correspondence claimUniqueOrNullCorrespondenceForEObject(CorrespondenceInstance ci,EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimNotMany
	}

	def public static Correspondence claimUniqueSameTypeCorrespondence(CorrespondenceInstance ci, EObject a, EObject b) {
		return ci.claimUniqueCorrespondence(a.toList,b.toList)
	}	
	
	def public static Correspondence createAndAddCorrespondence(CorrespondenceInstance ci, EObject a, EObject b) {
		return ci.createAndAddCorrespondence(a.toList,b.toList)
	}
	
	/**
     * Returns the corresponding object of the specified type for the specified object if there is
     * exactly one corresponding object of this type and throws a {@link java.lang.RuntimeException}
     * otherwise.
     *
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @param type
     *            the class of which an instance is to be returned
     * @return the corresponding object of the specified type for the specified object if there is
     *         exactly one corresponding object of this type
     */
	def public static <T> Set<T> getCorrespondingEObjectsByType(CorrespondenceInstance ci, EObject eObject, Class<T> type) {
		return getCorrespondingEObjects(ci, eObject).filter[eObj|type.isInstance(eObj)] as Set<T>
	}
	
	
	/**
     * Returns all eObjects that have some correspondence and are an instance of the given class.
     *
     * @param type
     *            the class for which instances should be returned
     * @return a set containing all eObjects of the given type that have a correspondence
     */
     // FIXME MK, ML decide whether to keep this evil method
	//def public static <T> Set<T> getAllEObjectsOfTypeInCorrespondences(Class<T> type) {
//		var Set<T> correspondencesWithType = new HashSet<T>()
//		var List<Correspondence> allCorrespondences = this.correspondences.getCorrespondences()
//		for (Correspondence correspondence : allCorrespondences) {
//			var EObject element = resolveEObjectFromTUIDWithoutException(correspondence.getElementATUID())
//			if (null !== element && type.isInstance(element)) {
//				/*FIXME Cannot add Annotation to Variable declaration. Java code: @SuppressWarnings("unchecked")*/
//				var T t = element as T
//				correspondencesWithType.add(t)
//			} else {
//				element = resolveEObjectFromTUIDWithoutException(correspondence.getElementBTUID())
//				if (null !== element && type.isInstance(element)) {
//					/*FIXME Cannot add Annotation to Variable declaration. Java code: @SuppressWarnings("unchecked")*/
//					var T t = element as T
//					correspondencesWithType.add(t)
//				}
//
//			} // currently nothing else to do as every correspondence is a One2OneCorrespondence
//		}
//		return correspondencesWithType
//		return null
//	}
}