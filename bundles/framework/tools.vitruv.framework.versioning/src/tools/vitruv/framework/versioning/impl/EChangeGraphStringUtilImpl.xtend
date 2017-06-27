package tools.vitruv.framework.versioning.impl

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.versioning.EChangeGraphStringUtil
import tools.vitruv.framework.versioning.EdgeType

class EChangeGraphStringUtilImpl implements EChangeGraphStringUtil {
	static def EChangeGraphStringUtil init() {
		new EChangeGraphStringUtilImpl
	}

	private static def String getShortString(EObject e) { e.shortStringImpl }

	private static def String getEdgeLabelImpl(EdgeType type) { type.toString }

	private static def String createEdgeNameImpl(EChange e1, EChange e2, EdgeType type) {
		'''«e1» «type.toString» «e2»'''
	}

	private static dispatch def String getShortStringImpl(EObject e) {
		e.toString
	}

	private static dispatch def String getShortStringImpl(EAttribute e) {
		'''«e.name»'''
	}

	private static dispatch def String getNodeLabelImpl(EChange e) {
		'''EChange «Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabelImpl(ReplaceSingleValuedEReference<?, ?> e) {
		'''ReplaceSingleValuedEReference@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabelImpl(ReplaceSingleValuedEAttribute<?, ?> e) {
		'''replace «e.affectedFeature.shortString»  with "«e.newValue»"'''
	}

	private static dispatch def String getNodeLabelImpl(CreateAndInsertNonRoot<?, ?> e) {
		'''CreateAndInsertNonRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabelImpl(CreateAndReplaceNonRoot<?, ?> e) {
		'''CreateAndReplaceNonRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private new() {
	}

	override createEdgeName(EChange e1, EChange e2, EdgeType type) { createEdgeNameImpl(e1, e2, type) }

	override getEdgeLabel(EdgeType type) { type.edgeLabelImpl }

	override getNodeId(EChange e) { e.toString }

	override getNodeLabel(EChange e) { e.nodeLabelImpl }

}
