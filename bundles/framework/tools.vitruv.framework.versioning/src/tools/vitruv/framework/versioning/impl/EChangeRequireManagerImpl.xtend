package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.versioning.EChangeRequireManager

class EChangeRequireManagerImpl implements EChangeRequireManager {
	static def EChangeRequireManager init() {
		new EChangeRequireManagerImpl
	}

	private static dispatch def boolean checkForRequireEdgeImpl(EChange e1, EChange e2) {
		false
	}

	private static dispatch def checkForRequireEdgeImpl(CreateAndReplaceNonRoot<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2) {
		val x = e1.createChange.affectedEObject === e2.affectedEObject
		return x
	}

	private static dispatch def checkForRequireEdgeImpl(CreateAndInsertEObject<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2) {
		val x = e1.createChange.affectedEObject === e2.affectedEObject
		return x
	}

	private static dispatch def checkForRequireEdgeImpl(CreateAndReplaceNonRoot<?, ?> e1,
		CreateAndInsertEObject<?, ? extends InsertEReference<?, ?>> e2) {
		val x = e1.createChange.affectedEObject === e2.insertChange.affectedEObject
		return x
	}

	private new() {
	}

	override checkForRequireEdge(EChange parent, EChange child) {
		checkForRequireEdgeImpl(parent, child)
	}

}
