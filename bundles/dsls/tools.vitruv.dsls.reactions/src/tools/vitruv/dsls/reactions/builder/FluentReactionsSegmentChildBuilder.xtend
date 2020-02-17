package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder

import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*

abstract package class FluentReactionsSegmentChildBuilder extends FluentReactionElementBuilder {
	@Accessors(PACKAGE_SETTER)
	var FluentReactionsSegmentBuilder segmentBuilder

	protected new(FluentBuilderContext context) {
		super(context)
	}

	def protected void transferReactionsSegmentTo(FluentReactionsSegmentChildBuilder infector,
		FluentReactionsSegmentChildBuilder infected) {
		infector.beforeAttached[infect(infected)]
		infected.beforeAttached[checkReactionsSegmentIsCompatibleTo(infector)]
	}

	def private infect(FluentReactionsSegmentChildBuilder infector, FluentReactionsSegmentChildBuilder infected) {
		infector.checkReactionsSegmentIsCompatibleTo(infected)
		if (infector.segmentBuilder !== null && infected.segmentBuilder === null) {
			infector.segmentBuilder.add(infected)
		}
	}

	def private checkReactionsSegmentIsCompatibleTo(FluentReactionsSegmentChildBuilder a,
		FluentReactionsSegmentChildBuilder b) {
		if (a.attachedReactionsFile !== null && b.attachedReactionsFile !== null &&
			a.attachedReactionsFile != b.attachedReactionsFile) {
			throw new RuntimeException('''«a» is in a different reaction file than «b»!''')
		}
	}

	def protected String getCreatedElementName();

	def protected String getCreatedElementType();

	protected static abstract class AbstractTypeProvider<BuilderType extends FluentReactionsSegmentChildBuilder> implements IJvmTypeProvider {
		protected val IJvmTypeProvider delegate
		@Accessors(PUBLIC_GETTER)
		protected val JvmTypeReferenceBuilder jvmTypeReferenceBuilder
		protected val extension BuilderType builder
		protected val XExpression scopeExpression

		protected new(IJvmTypeProvider delegate, JvmTypeReferenceBuilder jvmTypeReferenceBuilder, BuilderType builder, XExpression scopeExpression) {
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

		def affectedEObject() {
			variable(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		}
		
		def oldValue() {
			variable(CHANGE_OLD_VALUE_ATTRIBUTE)
		}

		def newValue() {
			variable(CHANGE_NEW_VALUE_ATTRIBUTE)
		}

		/**
		 * Retrieves a feature call to vala previously declared variable or custom
		 * routine parameter if it’s present
		 */
		def variableRaw(String variableName) {
			scopeExpression.correspondingMethodParameter(variableName)
		}
		
		def variable(String variableName) {
			variableRaw(variableName).featureCall
		}

		def protected featureCall(JvmIdentifiableElement element) {
			if (element === null) return null
			XbaseFactory.eINSTANCE.createXFeatureCall => [
				feature = element
			]
		}

		def protected static JvmField findAttribute(JvmDeclaredType declaredType, String attributeName) {
			val result = (declaredType.members.filter[simpleName == attributeName].filter(JvmField) +
				declaredType.superTypes.map[type].filter(JvmDeclaredType).map[findAttribute(attributeName)]
			).head
			if (result === null) {
				throw new IllegalStateException('''Could not find the attribute “«attributeName»” in ‹«declaredType.qualifiedName»›!''')
			}
			return result
		}
	}

	def protected correspondingMethodParameter(XExpression correpondingExpression, String parameterName) {
		val retrievalMethod = correpondingExpression.correspondingMethod
		val result = retrievalMethod.parameters.findFirst[name == parameterName]
		if (result === null) {
			// most likely an error by the client
			throw new IllegalStateException('''Could not find the variable or parameter “«parameterName»” in the «createdElementType» “«createdElementName»”''')
		}
		return result
	}

	def protected getCorrespondingMethod(XExpression correpondingExpression) {
		val retrievalMethod = context.jvmModelAssociator.getLogicalContainer(correpondingExpression)
		if (retrievalMethod instanceof JvmOperation) {
			return retrievalMethod
		}
		throw new IllegalStateException('''Could not find the method corresponding to “«correpondingExpression»” in the «createdElementType» “«createdElementName»”''')
	}

	def protected static extractExpressions(XExpression expression) {
		if (expression === null) return #[]
		switch expression {
			XBlockExpression: expression.expressions
			default: #[expression]
		}
	}
}
