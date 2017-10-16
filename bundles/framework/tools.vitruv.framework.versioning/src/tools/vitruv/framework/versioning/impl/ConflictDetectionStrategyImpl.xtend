package tools.vitruv.framework.versioning.impl

import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.versioning.ConflictDetectionStrategy
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot

class ConflictDetectionStrategyImpl implements ConflictDetectionStrategy {
	// Extensions.
	static extension EChangeCompareUtil = EChangeCompareUtil::instance

	override conflicts(EChange e1, EChange e2) {
		isConflicting(e1, e2)
	}

	override getConflictType(EChange e1, EChange e2) {
		determineConflictType(e1, e2)
	}

	override getConflictSolvability(EChange e1, EChange e2, ConflictType type) {
		determineConflictSolvability(e1, e2, type)
	}

	private static dispatch def boolean isConflicting(EChange e1, EChange e2) {
		false
	}

	private static dispatch def boolean isConflicting(
		CreateAndReplaceNonRoot<?, ?> e1,
		CreateAndReplaceNonRoot<?, ?> e2
	) {
		val createdObjectIsEqual = isEObjectEqual(e1.createChange.affectedEObject, e2.createChange.affectedEObject)
		val containerIsEqual = EcoreUtil::equals(e1.insertChange.affectedEObject, e2.insertChange.affectedEObject)
		val affectedContainer1 = e1.insertChange.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		var containerIsRootAndMapped = containerIsRootAndMapped(
			affectedContainerPlatformString1,
			e2.insertChange.affectedEObject as InternalEObject
		)
		val newValueIsEqual = EcoreUtil::equals(e1.insertChange.newValue, e2.insertChange.newValue)
		return createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && !newValueIsEqual
	}

	private static dispatch def boolean isConflicting(
		CreateAndInsertNonRoot<?, ?> e1,
		CreateAndInsertNonRoot<?, ?> e2
	) {
		val createdObjectIsEqual = isEObjectEqual(e1.createChange.affectedEObject, e2.createChange.affectedEObject)
		val containerIsEqual = EcoreUtil::equals(e1.insertChange.affectedEObject, e2.insertChange.affectedEObject)
		val affectedContainer1 = e1.insertChange.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		var containerIsRootAndMapped = containerIsRootAndMapped(
			affectedContainerPlatformString1,
			e2.insertChange.affectedEObject as InternalEObject
		)
		val indexEqual = e1.insertChange.index === e2.insertChange.index
		return createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && indexEqual
	}

	private static dispatch def boolean isConflicting(
		ReplaceSingleValuedEAttribute<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2
	) {
		val affectedObjectIsEqual = isEObjectEqual(e1.affectedEObject, e2.affectedEObject)
		val affectedFeatureIsEqual = EcoreUtil::equals(e1.affectedFeature, e2.affectedFeature)
		val newValueIsEqual = e1.newValue == e2.newValue
		val affectedContainer1 = e1.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		val containerIsRootAndMapped = containerIsRootAndMapped(
			affectedContainerPlatformString1,
			e2.affectedEObject as InternalEObject
		)
		val returnValue = (affectedObjectIsEqual || containerIsRootAndMapped) && affectedFeatureIsEqual &&
			!newValueIsEqual
		return returnValue
	}

	private static dispatch def boolean isConflicting(
		ReplaceSingleValuedEAttribute<?, ?> e1,
		RemoveAndDeleteNonRoot<?, ?> e2
	) {
		isConflicting(e2, e1)
	}

	private static dispatch def boolean isConflicting(
		RemoveAndDeleteNonRoot<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2
	) {
		val affectedObjectIsEqual = isEObjectEqual(e1.deleteChange.affectedEObject, e2.affectedEObject)
		val affectedContainer1 = e1.deleteChange.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		val containerIsRootAndMapped = containerIsRootAndMapped(
			affectedContainerPlatformString1,
			e2.affectedEObject as InternalEObject
		)
		val returnValue = (affectedObjectIsEqual || containerIsRootAndMapped)
		return returnValue
	}

	private static dispatch def determineConflictSolvability(EChange e1, EChange e2, ConflictType type) {
		ConflictSeverity::HARD
	}

	private static dispatch def determineConflictSolvability(
		ReplaceSingleValuedEAttribute<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2,
		ConflictType type
	) {
		ConflictSeverity::HARD
	}

	private static dispatch def determineConflictSolvability(
		CreateAndInsertNonRoot<?, ?> e1,
		CreateAndInsertNonRoot<?, ?> e2,
		ConflictType type
	) {
		return if(type === ConflictType::INSERTING_IN_SAME_CONTANER)
			ConflictSeverity::SOFT
		else
			ConflictSeverity::HARD
	}

	private static dispatch def determineConflictType(EChange e1, EChange e2) {
		ConflictType::UNKNOWN
	}

	private static dispatch def determineConflictType(
		ReplaceSingleValuedEAttribute<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2
	) {
		ConflictType::REPLACING_SAME_VALUE
	}

	private static dispatch def determineConflictType(
		CreateAndReplaceNonRoot<?, ?> e1,
		CreateAndReplaceNonRoot<?, ?> e2
	) {
		ConflictType::UNKNOWN
	}

	private static dispatch def determineConflictType(
		CreateAndInsertNonRoot<?, ?> e1,
		CreateAndInsertNonRoot<?, ?> e2
	) {
		ConflictType::INSERTING_IN_SAME_CONTANER
	}

}
