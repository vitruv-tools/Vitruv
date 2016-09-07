package tools.vitruvius.applications.jmljava.changesynchronizer

import tools.vitruvius.framework.change.description.GeneralChange
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.meta.change.feature.FeatureFactory
import tools.vitruvius.framework.meta.change.feature.attribute.AttributeFactory
import tools.vitruvius.framework.meta.change.feature.reference.ReferenceFactory
import tools.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory
import javax.activation.UnsupportedDataTypeException
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference

/**
 * Constructor for various change types. Usually the changed elements and some
 * additional information is passed to the methods and the correct change model
 * is determined.
 */
class ChangeBuilder {

	public static def dispatch createReplaceChangeInList(EObject original, EObject changed, EAttribute feature,
		Object newValue, Object oldValue) {
		return createReplaceEAttributeValue(original, changed, feature, oldValue, newValue)
	}

	public static def dispatch createReplaceChangeInList(EObject original, EObject changed, EReference feature,
		EObject newValue, EObject oldValue) {
		if (feature.containment) {
			return ChangeBuilder.createReplaceNonRootEObjectInList(original, changed, feature, newValue, oldValue)
		}
		return createReplaceNonContainmentEReference(original, changed, feature, newValue, oldValue)
	}

	public static def dispatch EMFModelChange createUpdateChange(EObject original, EObject changed, EReference feature) {
		if (feature.upperBound == 1) {
			val newValue = changed.eGet(feature)
			if (newValue == null) {
				return createUnsetEReference(original, changed, feature)
			}
			if (feature.containment) {
				return ChangeBuilder.createReplaceNonRootEObjectSingle(original, changed, feature)
			}
			return ChangeBuilder.createUpdateSingleValuedNonContainmentEReference(original, changed, feature)
		}

		throw new UnsupportedDataTypeException(
			"EReferences with cardinality greater than 1 are not supported at the moment.")
	}

	public static def dispatch EMFModelChange createUpdateChange(EObject original, EObject changed, EAttribute feature) {
		if (feature.upperBound == 1) {
			val newValue = changed.eGet(feature)
			if (newValue == null) {
				return createUnsetEAttribute(original, changed, feature)
			}
			return createUpdateSingleValuedEAttribute(original, changed, feature, newValue)
		}

		throw new UnsupportedDataTypeException(
			"EAttributes with cardinality greater than 1 are not supported at the moment.")
	}

	public static def EMFModelChange createCreateChange(EObject createdEObject, EObject oldParent) {
		if (createdEObject.eContainingFeature.upperBound == 1) {
			return ChangeBuilder.createCreateNonRootEObjectSingle(createdEObject, oldParent)
		} else {
			return ChangeBuilder.createCreateNonRootEObjectInList(createdEObject, oldParent)
		}
	}

	public static def EMFModelChange createDeleteChange(EObject deletedEObject, EObject changedParent) {
		if (deletedEObject.eContainingFeature.upperBound == 1) {
			return ChangeBuilder.createDeleteNonRootEObjectSingle(deletedEObject, changedParent)
		} else {
			return ChangeBuilder.createDeleteNonRootEObjectInList(deletedEObject, changedParent)
		}
	}

	private static def createReplaceEAttributeValue(EObject original, EObject changed, EAttribute feature,
		Object newValue, Object oldValue) {
		val change = AttributeFactory.eINSTANCE.createReplaceEAttributeValue()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.index = (original.eGet(feature) as EList<Object>).indexOf(oldValue)
		change.newValue = newValue
		change.oldValue = oldValue
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createReplaceNonRootEObjectInList(EObject original, EObject changed, EReference feature,
		EObject newValue, EObject oldValue) {
		val change = ContainmentFactory.eINSTANCE.createReplaceNonRootEObjectInList()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.index = (original.eGet(feature) as EList<EObject>).indexOf(oldValue)
		change.newValue = newValue
		change.oldValue = oldValue
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createReplaceNonContainmentEReference(EObject original, EObject changed, EReference feature,
		EObject newValue, EObject oldValue) {
		val change = ReferenceFactory.eINSTANCE.createReplaceNonContainmentEReference()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.index = (original.eGet(feature) as EList<EObject>).indexOf(oldValue)
		change.newValue = newValue
		change.oldValue = oldValue
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createUnsetEReference(EObject original, EObject changed, EReference feature) {
		if (feature.containment) {
			val change = FeatureFactory.eINSTANCE.createUnsetContainmentEReference
			change.oldAffectedEObject = original
			change.newAffectedEObject = changed
			change.affectedFeature = feature
			return new EMFModelChange(change, VURI.getInstance(original.eResource))
		}
		val change = FeatureFactory.eINSTANCE.createUnsetNonContainmentEReference
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.oldValue = original.eGet(feature) as EObject
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createUpdateSingleValuedEAttribute(EObject original, EObject changed, EAttribute feature,
		Object newValue) {
		val change = AttributeFactory.eINSTANCE.createUpdateSingleValuedEAttribute()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.oldValue = original.eGet(feature)
		change.newValue = newValue
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createUnsetEAttribute(EObject original, EObject changed, EAttribute feature) {
		val change = FeatureFactory.eINSTANCE.createUnsetEAttribute()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.oldValue = original.eGet(feature)
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createReplaceNonRootEObjectSingle(EObject original, EObject changed, EReference feature) {
		val change = ContainmentFactory.eINSTANCE.createReplaceNonRootEObjectSingle()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.oldValue = original.eGet(feature) as EObject
		change.newValue = changed.eGet(feature) as EObject
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createUpdateSingleValuedNonContainmentEReference(EObject original, EObject changed,
		EReference feature) {
		val change = ReferenceFactory.eINSTANCE.createUpdateSingleValuedNonContainmentEReference()
		change.oldAffectedEObject = original
		change.newAffectedEObject = changed
		change.affectedFeature = feature
		change.oldValue = original.eGet(feature) as EObject
		change.newValue = changed.eGet(feature) as EObject
		return new EMFModelChange(change, VURI.getInstance(original.eResource))
	}

	private static def createCreateNonRootEObjectInList(EObject createEObject, EObject oldParent) {
		val change = ContainmentFactory.eINSTANCE.createCreateNonRootEObjectInList()
		change.newAffectedEObject = createEObject.eContainer();
		change.oldAffectedEObject = oldParent;
		change.setAffectedFeature(createEObject.eContainingFeature() as EReference);
		change.setNewValue(createEObject);
		change.index = (createEObject.eContainer.eGet(createEObject.eContainingFeature) as EList<EObject>).
			indexOf(createEObject)
		return new EMFModelChange(change, VURI.getInstance(createEObject.eResource));
	}

	private static def createCreateNonRootEObjectSingle(EObject createEObject, EObject oldParent) {
		val change = ContainmentFactory.eINSTANCE.createCreateNonRootEObjectSingle()
		change.newAffectedEObject = createEObject.eContainer();
		change.oldAffectedEObject = oldParent;
		change.setAffectedFeature(createEObject.eContainingFeature() as EReference);
		change.newValue = createEObject
		return new EMFModelChange(change, VURI.getInstance(createEObject.eResource));
	}

	private static def createDeleteNonRootEObjectInList(EObject deletedEObject, EObject changedParent) {
		val change = ContainmentFactory.eINSTANCE.createDeleteNonRootEObjectInList()
		change.oldAffectedEObject = deletedEObject.eContainer
		change.newAffectedEObject = changedParent
		change.affectedFeature = deletedEObject.eContainingFeature() as EReference
		change.oldValue = deletedEObject
		change.index = (deletedEObject.eContainer.eGet(deletedEObject.eContainingFeature) as EList<EObject>).
			indexOf(deletedEObject)
		return new EMFModelChange(change, VURI.getInstance(deletedEObject.eResource));
	}

	private static def createDeleteNonRootEObjectSingle(EObject deletedEObject, EObject changedParent) {
		val change = ContainmentFactory.eINSTANCE.createDeleteNonRootEObjectSingle()
		change.oldAffectedEObject = deletedEObject.eContainer
		change.newAffectedEObject = changedParent
		change.affectedFeature = deletedEObject.eContainingFeature() as EReference
		change.oldValue = deletedEObject
		return new EMFModelChange(change, VURI.getInstance(deletedEObject.eResource));
	}

}
