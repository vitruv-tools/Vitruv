package tools.vitruv.dsls.commonalities.generator.reactions.matching

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.matching.ContainmentTree
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

/**
 * Builds the expressions to create and setup a ContainmentTree at runtime.
 */
package class ContainmentTreeBuilder {

	val extension TypeProvider typeProvider
	val JvmDeclaredType containmentTreeType
	val JvmOperation addNodeMethod
	val JvmOperation addReferenceEdgeMethod
	val JvmOperation addOperatorEdgeMethod
	val JvmOperation addAttributeReferenceEdgeMethod

	val XBlockExpression result = XbaseFactory.eINSTANCE.createXBlockExpression
	var XVariableDeclaration containmentTreeVar

	new(TypeProvider typeProvider) {
		this.typeProvider = typeProvider
		this.containmentTreeType = typeProvider.findDeclaredType(ContainmentTree).imported
		this.addNodeMethod = containmentTreeType.findMethod('addNode', String, EClass)
		this.addReferenceEdgeMethod = containmentTreeType.findMethod('addReferenceEdge', String, String, EReference)
		this.addOperatorEdgeMethod = containmentTreeType.findMethod('addOperatorEdge', String, String,
			IReferenceMappingOperator)
		this.addAttributeReferenceEdgeMethod = containmentTreeType.findMethod('addAttributeReferenceEdge', String,
			IReferenceMappingOperator)
	}

	private def checkHasContainmentTree() {
		checkState(containmentTreeVar !== null, "The ContainmentTree has not yet been created!")
	}

	/**
	 * Gets the variable declaration for the created ContainmentTree.
	 */
	def XVariableDeclaration getContainmentTreeVar() {
		checkHasContainmentTree()
		return containmentTreeVar
	}

	/**
	 * Gets the block of built expressions which create and set up the
	 * ContainmentTree.
	 */
	def getResultExpressions() {
		checkHasContainmentTree()
		return result
	}

	/**
	 * Begins the building of the ContainmentTree.
	 */
	def void newContainmentTree(String variableName) {
		checkState(containmentTreeVar === null, "ContainmentTreeBuilder can only be used once!")
		containmentTreeVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = variableName
			writeable = false
			type = jvmTypeReferenceBuilder.typeRef(containmentTreeType)
			right = XbaseFactory.eINSTANCE.createXConstructorCall => [
				it.constructor = containmentTreeType.findNoArgsConstructor
			]
		]
		result.expressions += containmentTreeVar
	}

	private def containmentTreeMemberCall(JvmIdentifiableElement featureElement) {
		return containmentTreeVar.featureCall.memberFeatureCall(featureElement)
	}

	def void setRootIntermediateType(EClass rootIntermediateType) {
		checkHasContainmentTree()
		val setRootIntermediateTypeMethod = containmentTreeType.findMethod('setRootIntermediateType', EClass)
		result.expressions += containmentTreeMemberCall(setRootIntermediateTypeMethod) => [
			explicitOperationCall = true
			memberCallArguments += getEClass(typeProvider, rootIntermediateType)
		]
	}

	def void addNode(String nodeName, EClass nodeType) {
		checkHasContainmentTree()
		result.expressions += containmentTreeMemberCall(addNodeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(nodeName),
				getEClass(typeProvider, nodeType)
			)
		]
	}

	def void addReferenceEdge(String containerNode, String containedNode, EReference containmentEReference) {
		checkHasContainmentTree()
		result.expressions += containmentTreeMemberCall(addReferenceEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containerNode),
				stringLiteral(containedNode),
				getEReference(typeProvider, containmentEReference)
			)
		]
	}

	def void addOperatorEdge(String containerNode, String containedNode, XExpression operator) {
		checkHasContainmentTree()
		result.expressions += containmentTreeMemberCall(addOperatorEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containerNode),
				stringLiteral(containedNode),
				operator
			)
		]
	}

	def void setAttributeReferenceRootNode(String nodeName, EClass nodeType) {
		checkHasContainmentTree()
		val setAttributeReferenceRootNodeMethod = containmentTreeType.findMethod('setAttributeReferenceRootNode',
			String, EClass)
		result.expressions += containmentTreeMemberCall(setAttributeReferenceRootNodeMethod) => [
			explicitOperationCall = true
			memberCallArguments += expressions(
				stringLiteral(nodeName),
				getEClass(typeProvider, nodeType)
			)
		]
	}

	def void addAttributeReferenceEdge(String containedNode, XExpression operator) {
		checkHasContainmentTree()
		result.expressions += containmentTreeMemberCall(addAttributeReferenceEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containedNode),
				operator
			)
		]
	}
}
