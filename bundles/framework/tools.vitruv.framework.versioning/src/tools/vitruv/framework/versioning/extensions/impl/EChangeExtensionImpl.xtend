package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.extensions.EChangeExtension
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot

class EChangeExtensionImpl implements EChangeExtension {
	static def EChangeExtension init() {
		new EChangeExtensionImpl
	}

	private static def String getShortString(EObject e) { e.shortStringImpl }

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
		'''replace «e.affectedFeature.shortString»  with "«e.newValue»" at «e.affectedEObject.class.simpleName»'''
	}
	
	private static dispatch def String getNodeLabelImpl(RemoveAndDeleteNonRoot<?, ?> e) {
		'''delete «e.deleteChange.affectedEObject.class.simpleName»'''
	}

	private static dispatch def String getNodeLabelImpl(CreateAndInsertRoot<?> e) {
		'''CreateAndInsertRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabelImpl(CreateAndInsertNonRoot<?, ?> e) {
		'''CreateAndInsertNonRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getNodeLabelImpl(CreateAndReplaceNonRoot<?, ?> e) {
		'''CreateAndReplaceNonRoot@«Integer.toHexString(e.hashCode)»'''
	}

	private static dispatch def String getFullStringImpl(EChange e) {
		throw new UnsupportedOperationException('''«e»''')
	}

	private static dispatch def String getFullStringImpl(ReplaceSingleValuedEAttribute<?, ?> e) {
		val fullString = '''
			ReplaceSingleValuedEAttribute
			«e.affectedEObject.toString» 
			«e.affectedFeature.toString»
			«e.newValue»
			«e.oldValue»
		'''
		return fullString
	}

	private static dispatch def String getFullStringImpl(ReplaceSingleValuedEReference<?, ?> e) {
		val fullString = '''
			ReplaceSingleValuedEReference
			«e.affectedEObject.toString» 
			«e.affectedFeature.toString»
			«e.newValue»
			«e.oldValue»
		'''
		return fullString
	}

	private static dispatch def String getFullStringImpl(CreateAndInsertRoot<?> e) {
		val fullString = '''
			CreateAndReplaceNonRoot
			«e.createChange.affectedEObject.toString» 
			«e.insertChange.uri»
			«e.insertChange.index»
		'''
		return fullString
	}

	private static dispatch def String getFullStringImpl(CreateAndReplaceNonRoot<?, ?> e) {
		val fullString = '''
			CreateAndReplaceNonRoot
			«e.createChange.affectedEObject.toString» 
			«e.insertChange.affectedEObject.toString»
			«e.insertChange.affectedFeature.toString»
		'''
		return fullString
	}

	private static dispatch def String getFullStringImpl(CreateAndInsertNonRoot<?, ?> e) {
		val fullString = '''
			CreateAndInsertNonRoot
			«e.createChange.affectedEObject.toString» 
			«e.insertChange.affectedEObject.toString»
			«e.insertChange.affectedFeature.toString»
			«e.insertChange.index»
		'''
		return fullString
	}

	private new() {
	}

	override createEdgeName(EChange e1, EChange e2, EdgeType type) { createEdgeNameImpl(e1, e2, type) }

	override getNodeId(EChange e) { e?.toString }

	override getNodeLabel(EChange e) { e.nodeLabelImpl }

	override getFullString(EChange e) {
		e.fullStringImpl
	}

}
