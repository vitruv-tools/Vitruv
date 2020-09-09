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
import tools.vitruv.extensions.dslruntime.commonalities.matching.ContainmentContext
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.IReferenceMappingOperator

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

/**
 * Builds the expressions to create and setup a ContainmentContext at runtime.
 */
package class ContainmentContextBuilder {

	val extension TypeProvider typeProvider
	val JvmDeclaredType containmentContextType
	val JvmOperation addNodeMethod
	val JvmOperation addReferenceEdgeMethod
	val JvmOperation addOperatorEdgeMethod
	val JvmOperation addAttributeReferenceEdgeMethod

	val XBlockExpression result = XbaseFactory.eINSTANCE.createXBlockExpression
	var XVariableDeclaration containmentContextVar

	new(TypeProvider typeProvider) {
		this.typeProvider = typeProvider
		this.containmentContextType = typeProvider.findDeclaredType(ContainmentContext).imported
		this.addNodeMethod = containmentContextType.findMethod('addNode', String, EClass, String)
		this.addReferenceEdgeMethod = containmentContextType.findMethod('addReferenceEdge', String, String, EReference)
		this.addOperatorEdgeMethod = containmentContextType.findMethod('addOperatorEdge', String, String,
			IReferenceMappingOperator)
		this.addAttributeReferenceEdgeMethod = containmentContextType.findMethod('addAttributeReferenceEdge', String,
			IReferenceMappingOperator)
	}

	private def checkHasContainmentContext() {
		checkState(containmentContextVar !== null, "The ContainmentContext has not yet been created!")
	}

	/**
	 * Gets the variable declaration for the created ContainmentContext.
	 */
	def XVariableDeclaration getContainmentContextVar() {
		checkHasContainmentContext()
		return containmentContextVar
	}

	/**
	 * Gets the block of built expressions which create and set up the
	 * ContainmentContext.
	 */
	def getResultExpressions() {
		checkHasContainmentContext()
		return result
	}

	/**
	 * Begins the building of the ContainmentContext.
	 */
	def void newContainmentContext(String variableName) {
		checkState(containmentContextVar === null, "ContainmentContextBuilder can only be used once!")
		containmentContextVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = variableName
			writeable = false
			type = jvmTypeReferenceBuilder.typeRef(containmentContextType)
			right = XbaseFactory.eINSTANCE.createXConstructorCall => [
				it.constructor = containmentContextType.findNoArgsConstructor
			]
		]
		result.expressions += containmentContextVar
	}

	private def containmentContextMemberCall(JvmIdentifiableElement featureElement) {
		return containmentContextVar.featureCall.memberFeatureCall(featureElement)
	}

	def void setRootIntermediateType(EClass rootIntermediateType) {
		checkHasContainmentContext()
		val setRootIntermediateTypeMethod = containmentContextType.findMethod('setRootIntermediateType', EClass)
		result.expressions += containmentContextMemberCall(setRootIntermediateTypeMethod) => [
			explicitOperationCall = true
			memberCallArguments += getEClass(typeProvider, rootIntermediateType)
		]
	}

	def void addNode(String nodeName, EClass nodeType, String correspondenceTag) {
		checkHasContainmentContext()
		result.expressions += containmentContextMemberCall(addNodeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(nodeName),
				getEClass(typeProvider, nodeType),
				stringLiteral(correspondenceTag)
			)
		]
	}

	def void addReferenceEdge(String containerNode, String containedNode, EReference containmentEReference) {
		checkHasContainmentContext()
		result.expressions += containmentContextMemberCall(addReferenceEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containerNode),
				stringLiteral(containedNode),
				getEReference(typeProvider, containmentEReference)
			)
		]
	}

	def void addOperatorEdge(String containerNode, String containedNode, XExpression operator) {
		checkHasContainmentContext()
		result.expressions += containmentContextMemberCall(addOperatorEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containerNode),
				stringLiteral(containedNode),
				operator
			)
		]
	}

	def void setAttributeReferenceRootNode(String nodeName, EClass nodeType, String correspondenceTag) {
		checkHasContainmentContext()
		val setAttributeReferenceRootNodeMethod = containmentContextType.findMethod('setAttributeReferenceRootNode',
			String, EClass, String)
		result.expressions += containmentContextMemberCall(setAttributeReferenceRootNodeMethod) => [
			explicitOperationCall = true
			memberCallArguments += expressions(
				stringLiteral(nodeName),
				getEClass(typeProvider, nodeType),
				stringLiteral(correspondenceTag)
			)
		]
	}

	def void addAttributeReferenceEdge(String containedNode, XExpression operator) {
		checkHasContainmentContext()
		result.expressions += containmentContextMemberCall(addAttributeReferenceEdgeMethod) => [
			memberCallArguments += expressions(
				stringLiteral(containedNode),
				operator
			)
		]
	}
}
