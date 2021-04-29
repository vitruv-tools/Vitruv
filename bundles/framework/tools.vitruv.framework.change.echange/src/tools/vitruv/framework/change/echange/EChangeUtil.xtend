package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * Static utility class for the EChange package and subpackages.
 */
@Utility
class EChangeUtil {
	static def dispatch isContainmentRemoval(EChange change) {
		false
	}

	static def dispatch isContainmentRemoval(ReplaceSingleValuedEReference<?, ?> change) {
		change.affectedFeature.containment &&
			((change.oldValueID !== null && change.oldValueID !== change.newValueID) ||
				(change.oldValue !== null && change.oldValue != change.newValue))
	}

	static def dispatch isContainmentRemoval(RemoveEReference<?, ?> change) {
		change.affectedFeature.containment
	}

	static def dispatch isContainmentRemoval(RemoveRootEObject<?> change) {
		true
	}

	static def dispatch isContainmentInsertion(EChange change) {
		false
	}

	static def dispatch isContainmentInsertion(AdditiveReferenceEChange<?, ?> change) {
		change.affectedFeature.containment && (change.newValueID !== null || change.newValue !== null)
	}

	static def dispatch isContainmentInsertion(RemoveRootEObject<?> change) {
		true
	}

	static def dispatch isContainmentInsertion(InsertRootEObject<?> change) {
		true
	}

}
