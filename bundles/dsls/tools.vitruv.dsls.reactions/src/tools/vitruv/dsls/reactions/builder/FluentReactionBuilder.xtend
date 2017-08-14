package tools.vitruv.dsls.reactions.builder

import java.util.ArrayList
import java.util.function.Consumer
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineStartBuilder
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

class FluentReactionBuilder extends FluentReactionsSegmentChildBuilder {

	var Reaction reaction
	var anonymousRoutineCounter = 0
	var EClass valueType
	var EClass affectedElementType

	package new(String reactionName, FluentBuilderContext context) {
		super(context)
		reaction = ReactionsLanguageFactory.eINSTANCE.createReaction => [
			name = reactionName
		]
	}

	def package start() {
		return new TriggerBuilder(this)
	}

	override protected attachmentPreparation() {
		super.attachmentPreparation()
		checkState(reaction.trigger !== null, '''No trigger was set on the «this»!''')
		checkState(reaction.callRoutine !== null, '''No routine call was set on the «this»!''')
	}

	static class TriggerBuilder {
		val extension FluentReactionBuilder builder

		private new(FluentReactionBuilder builder) {
			this.builder = builder
		}

		def afterAnyChange() {
			reaction.trigger = ReactionsLanguageFactory.eINSTANCE.createArbitraryModelChange
			return new RoutineCallBuilder(builder)
		}

		def afterElement(EClass element) {
			val change = ReactionsLanguageFactory.eINSTANCE.createModelElementChange => [
				elementType = MirBaseFactory.eINSTANCE.createMetaclassReference.reference(element)
			]
			reaction.trigger = change
			affectedElementType = element
			return new ChangeTypeBuilder(builder, change)
		}
	}

	static class ChangeTypeBuilder {
		val extension FluentReactionBuilder builder
		val ModelElementChange modelElementChange

		private new(FluentReactionBuilder builder, ModelElementChange modelElementChange) {
			this.builder = builder
			this.modelElementChange = modelElementChange
		}

		def created() {
			modelElementChange.changeType = ReactionsLanguageFactory.eINSTANCE.createElementCreationChangeType
			return new RoutineCallBuilder(builder)
		}

		def deleted() {
			modelElementChange.changeType = ReactionsLanguageFactory.eINSTANCE.createElementDeletionChangeType
			return new RoutineCallBuilder(builder)
		}
	}

	static class RoutineCallBuilder {
		val extension FluentReactionBuilder builder

		private new(FluentReactionBuilder builder) {
			this.builder = builder
		}

		def call(FluentRoutineBuilder routineBuilder) {
			checkNotNull(routineBuilder)
			checkState(routineBuilder.
				readyToBeAttached, '''The «routineBuilder» is not sufficiently initialised to be set on the «builder»''')
			checkState((!routineBuilder.requireNewValue && !routineBuilder.requireOldValue) || valueType !==
				null, '''The «routineBuilder» requires a model value type, but the «builder» doesn’t have one!''')
			builder.transferReactionsSegmentTo(routineBuilder)
			reaction.callRoutine = ReactionsLanguageFactory.eINSTANCE.createReactionRoutineCall => [
				code = (XbaseFactory.eINSTANCE.createXFeatureCall => [
					explicitOperationCall = true
				]).whenJvmTypes [
					val routineCallMethod = routineCallMethod
					feature = routineBuilder.jvmOperation
					implicitReceiver = routineCallMethod.argument(
						REACTION_USER_EXECUTION_ROUTINE_CALL_FACADE_PARAMETER_NAME)
					featureCallArguments += routineBuilder.requiredArgumentsFrom(routineCallMethod)
				]
			]
			if (affectedElementType !== null) {
				routineBuilder.affectedElementType = affectedElementType
			}
			if (valueType !== null) {
				routineBuilder.valueType = valueType
			}
			readyToBeAttached = true
			return builder
		}

		def call(String routineName, Consumer<RoutineStartBuilder> routineInitializer) {
			val routineBuilder = new FluentRoutineBuilder(routineName, context)
			routineInitializer.accept(routineBuilder.start())
			call(routineBuilder)
		}

		def call(Consumer<RoutineStartBuilder> routineInitializer) {
			anonymousRoutineCounter++
			call('''«reaction.name.toFirstLower»Repair«IF anonymousRoutineCounter !== 1»anonymousRoutineCounter«ENDIF»''',
				routineInitializer)
		}
	}

	def private requiredArgumentsFrom(FluentRoutineBuilder routineBuilder, JvmOperation routineCallMethod) {
		val parameterList = new ArrayList<XExpression>(2)
		if (routineBuilder.requireNewValue) {
			parameterList += routineCallMethod.argument(CHANGE_NEW_VALUE_ATTRIBUTE)
		}
		if (routineBuilder.requireOldValue) {
			parameterList += routineCallMethod.argument(CHANGE_OLD_VALUE_ATTRIBUTE)
		}
		if (routineBuilder.requireAffectedEObject) {
			parameterList += routineCallMethod.argument(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		}
		return parameterList
	}

	def private argument(JvmOperation routineCallMethod, String parameterName) {
		val parameter = routineCallMethod.parameters.findFirst[name == parameterName]
		if (parameter === null) {
			throw new IllegalStateException('''The reaction “«reaction.name»” does not provide a value called “«parameterName»”!''')
		}
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			feature = parameter
		]
	}

	def private getRoutineCallMethod() {
		val routineCallMethod = context.jvmModelAssociator.getPrimaryJvmElement(reaction.callRoutine.code)
		if (routineCallMethod instanceof JvmOperation) {
			return routineCallMethod
		}
		throw new IllegalStateException('''Could not find the routine call method corresponding to the routine call of the reaction “«reaction.name»”''')
	}

	def package getReaction() {
		reaction
	}

	override toString() {
		'''reaction builder for “«reaction.name»”'''
	}

}
