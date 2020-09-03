package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory

import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*

abstract package class FluentReactionsSegmentChildBuilder extends FluentReactionElementBuilder {

	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
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

	def protected correspondingMethodParameter(XExpression correpondingExpression, String parameterName) {
		val method = correpondingExpression.correspondingMethod
		val parameter = method.parameters.findFirst[name == parameterName]
		if (parameter === null) {
			// most likely an error by the client
			throw new IllegalStateException('''Could not find the variable or parameter “«parameterName»” in the «
				createdElementType» “«createdElementName»”.''')
		}
		return parameter
	}

	def protected getCorrespondingMethod(XExpression correpondingExpression) {
		// Takes parent containers into account in order to work in both routine and reaction contexts:
		val method = context.jvmModelAssociator.getNearestLogicalContainer(correpondingExpression)
		if (method instanceof JvmOperation) {
			return method
		}
		throw new IllegalStateException('''Could not find the method corresponding to “«correpondingExpression
			»” in the «createdElementType» “«createdElementName»”''')
	}

	def protected static extractExpressions(XExpression expression) {
		if (expression === null) return #[]
		switch expression {
			XBlockExpression: expression.expressions
			default: #[expression]
		}
	}

	def protected featureCall(JvmIdentifiableElement element) {
		if (element === null) return null
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = element
		]
	}

	def protected getTypeProvider(XExpression scopeExpression) {
		new TypeProvider(delegateTypeProvider, referenceBuilderFactory, this, scopeExpression)
	}

	def getJvmOperationRoutineFacade(XExpression codeBlock) {
		codeBlock.correspondingMethodParameter(REACTION_USER_EXECUTION_ROUTINE_CALL_FACADE_PARAMETER_NAME).featureCall
	}
}
