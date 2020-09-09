package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ResourceAccess

import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*

class TypeProvider implements IJvmTypeProvider {

	protected val IJvmTypeProvider delegate
	@Accessors(PUBLIC_GETTER)
	protected val JvmTypeReferenceBuilder jvmTypeReferenceBuilder
	protected val extension FluentReactionsSegmentChildBuilder builder
	protected val XExpression scopeExpression

	protected new(IJvmTypeProvider delegate, JvmTypeReferenceBuilder jvmTypeReferenceBuilder,
		FluentReactionsSegmentChildBuilder builder, XExpression scopeExpression) {
		this.delegate = delegate
		this.builder = builder
		this.jvmTypeReferenceBuilder = jvmTypeReferenceBuilder
		this.scopeExpression = scopeExpression
	}

	override findTypeByName(String name) {
		delegate.findTypeByName(name).possiblyImported
	}

	override findTypeByName(String name, boolean binaryNestedTypeDelimiter) {
		delegate.findTypeByName(name, binaryNestedTypeDelimiter).possiblyImported
	}

	override getResourceSet() {
		delegate.resourceSet
	}

	def <T extends JvmIdentifiableElement> imported(T type) {
		builder.possiblyImported(type)
	}

	def staticImported(JvmOperation operation) {
		builder.staticImported(operation)
	}

	def staticExtensionImported(JvmOperation operation) {
		builder.staticExtensionImported(operation)
	}

	def staticExtensionWildcardImported(JvmOperation operation) {
		builder.staticExtensionWildcardImported(operation)
	}

	def staticExtensionAllImported(JvmDeclaredType type) {
		builder.staticExtensionAllImported(type)
	}

	def XFeatureCall affectedEObject() {
		variable(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
	}

	def XFeatureCall oldValue() {
		variable(CHANGE_OLD_VALUE_ATTRIBUTE)
	}

	def XFeatureCall newValue() {
		variable(CHANGE_NEW_VALUE_ATTRIBUTE)
	}

	def JvmFormalParameter variableRaw(String variableName) {
		scopeExpression.correspondingMethodParameter(variableName)
	}

	/**
	 * Retrieves a feature call to a previously declared variable or
	 * routine/reaction parameter if it is present.
	 */
	def XFeatureCall variable(String variableName) {
		variableRaw(variableName).featureCall
	}

	/**
	 * Retrieves the user execution class.
	 */
	def userExecutionType() {
		scopeExpression.correspondingMethod.declaringType
	}

	/**
	 * Retrieves a feature call to the user execution class.
	 */
	def userExecution() {
		userExecutionType.featureCall
	}

	def executionStateField() {
		userExecutionType.findAttribute('executionState')
	}

	def executionStateType() {
		executionStateField.type.type as JvmDeclaredType
	}

	def executionState() {
		executionStateField.featureCall
	}

	def resourceAccessMethod() {
		executionStateType.findMethod('getResourceAccess')
	}

	def resourceAccessType() {
		findTypeByName(ResourceAccess.name) as JvmDeclaredType
	}

	def resourceAccess() {
		return XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = resourceAccessMethod
			implicitReceiver = executionState
		]
	}

	def correspondenceModelMethod() {
		executionStateType.findMethod('getCorrespondenceModel')
	}

	def correspondenceModelType() {
		findTypeByName(CorrespondenceModel.canonicalName) as JvmDeclaredType
	}

	def correspondenceModel() {
		return XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = correspondenceModelMethod
			implicitReceiver = executionState
		]
	}

	def protected static JvmField findAttribute(JvmDeclaredType declaredType, String attributeName) {
		val result = (declaredType.members.filter[simpleName == attributeName].filter(JvmField) +
			declaredType.superTypes.map[type].filter(JvmDeclaredType).map[findAttribute(attributeName)]
		).head
		if (result === null) {
			throw new IllegalStateException('''Could not find the attribute “«attributeName»” in ‹«
				»«declaredType.qualifiedName»›!''')
		}
		return result
	}

	def protected static JvmOperation findMethod(JvmDeclaredType declaredType, String methodName) {
		val result = declaredType.members.filter(JvmOperation).filter[simpleName == methodName].head
		if (result === null) {
			throw new IllegalStateException('''Could not find the method “«methodName»” in ‹«
				»«declaredType.qualifiedName»›!''')
		}
		return result
	}
}
