package tools.vitruv.dsls.reactions.builder

import java.util.ArrayList
import java.util.List
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineStartBuilder
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionRoutineCall
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

import static com.google.common.base.Preconditions.*

class FluentReactionBuilder extends FluentReactionsSegmentChildBuilder {

	var Reaction reaction
	var anonymousRoutineCounter = 0
	var EClassifier valueType
	var EClass affectedObjectType

	package new (Reaction reaction, FluentBuilderContext context) {
		super(context)
		this.reaction = reaction;
	}

	package new(String reactionName, FluentBuilderContext context) {
		super(context)
		reaction = ReactionsLanguageFactory.eINSTANCE.createReaction => [
			name = reactionName
		]
	}

	def package start() {
		return new OverrideOrTriggerBuilder(this)
	}

	override protected attachmentPreparation() {
		super.attachmentPreparation()
		checkState(reaction.trigger !== null, '''No trigger was set on the «this»!''')
		checkState(reaction.callRoutine !== null, '''No routine call was set on the «this»!''')
	}

	static class OverrideOrTriggerBuilder extends TriggerBuilder {
		private new(FluentReactionBuilder builder) {
			super(builder)
		}

		def overrideSegment(FluentReactionsSegmentBuilder segmentBuilder) {
			reaction.overriddenReactionsSegment = segmentBuilder?.segment;
			new TriggerBuilder(builder)
		}
	}

	static class TriggerBuilder {
		protected val extension FluentReactionBuilder builder

		private new(FluentReactionBuilder builder) {
			this.builder = builder
		}

		def afterAnyChange() {
			reaction.trigger = ReactionsLanguageFactory.eINSTANCE.createArbitraryModelChange
			return new PreconditionOrRoutineCallBuilder(builder)
		}

		def afterElement(EClass element) {
			val change = ReactionsLanguageFactory.eINSTANCE.createModelElementChange => [
				elementType = MirBaseFactory.eINSTANCE.createMetaclassReference.reference(element)
			]
			reaction.trigger = change
			return new ChangeTypeBuilder(builder, change, element)
		}

		def afterElement() {
			val change = ReactionsLanguageFactory.eINSTANCE.createModelElementChange
			reaction.trigger = change
			return new ChangeTypeBuilder(builder, change, null)
		}

		def afterAttributeInsertIn(EAttribute attribute) {
			afterAttributeInsertIn(attribute.EContainingClass, attribute)
		}

		def afterAttributeInsertIn(EClass eClass, EAttribute attribute) {
			valueType = attribute.EType
			affectedObjectType = eClass
			reaction.trigger = ReactionsLanguageFactory.eINSTANCE.createModelAttributeInsertedChange => [
				feature = MirBaseFactory.eINSTANCE.createMetaclassEAttributeReference.reference(eClass, attribute)
			]
			return new PreconditionOrRoutineCallBuilder(builder)
		}

		def afterAttributeReplacedAt(EAttribute attribute) {
			afterAttributeReplacedAt(attribute.EContainingClass, attribute)
		}

		def afterAttributeReplacedAt(EClass eClass, EAttribute attribute) {
			valueType = attribute.EType
			affectedObjectType = eClass
			reaction.trigger = ReactionsLanguageFactory.eINSTANCE.createModelAttributeReplacedChange => [
				feature = MirBaseFactory.eINSTANCE.createMetaclassEAttributeReference.reference(eClass, attribute)
			]
			return new PreconditionOrRoutineCallBuilder(builder)
		}

		def afterAttributeRemoveFrom(EAttribute attribute) {
			afterAttributeRemoveFrom(attribute.EContainingClass, attribute)
		}

		def afterAttributeRemoveFrom(EClass eClass, EAttribute attribute) {
			valueType = attribute.EType
			affectedObjectType = eClass
			reaction.trigger = ReactionsLanguageFactory.eINSTANCE.createModelAttributeRemovedChange => [
				feature = MirBaseFactory.eINSTANCE.createMetaclassEAttributeReference.reference(eClass, attribute)
			]
			return new PreconditionOrRoutineCallBuilder(builder)
		}
	}

	static class ChangeTypeBuilder {
		val extension FluentReactionBuilder builder
		val ModelElementChange modelElementChange
		val EClass element

		private new(FluentReactionBuilder builder, ModelElementChange modelElementChange, EClass element) {
			this.builder = builder
			this.modelElementChange = modelElementChange
			this.element = element
		}

		def created() {
			affectedObjectType = element ?: EcorePackage.eINSTANCE.EObject
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementCreationChangeType)
		}

		def deleted() {
			affectedObjectType = element ?: EcorePackage.eINSTANCE.EObject
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementDeletionChangeType)
		}

		def insertedAsRoot() {
			valueType = element ?: EcorePackage.eINSTANCE.EObject
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementInsertionAsRootChangeType)
		}

		def insertedIn(EReference reference) {
			insertedIn(reference.EContainingClass, reference)
		}

		def insertedIn(EClass eClass, EReference reference) {
			valueType = element ?: reference.EReferenceType
			affectedObjectType = eClass
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementInsertionInListChangeType => [
				feature = MirBaseFactory.eINSTANCE.createMetaclassEReferenceReference.reference(eClass, reference)
			])
		}

		def removedFrom(EReference reference) {
			removedFrom(reference.EContainingClass, reference)
		}

		def removedFrom(EClass eClass, EReference reference) {
			valueType = element ?: reference.EReferenceType
			affectedObjectType = eClass
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementRemovalFromListChangeType => [
				feature = MirBaseFactory.eINSTANCE.createMetaclassEReferenceReference.reference(eClass, reference)
			])
		}

		def removedAsRoot() {
			valueType = element ?: EcorePackage.eINSTANCE.EObject
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementRemovalAsRootChangeType)
		}

		def replacedAt(EReference reference) {
			replacedAt(reference.EContainingClass, reference)
		}

		def replacedAt(EClass eClass, EReference reference) {
			valueType = element ?: reference.EReferenceType
			affectedObjectType = eClass
			continueWithChangeType(ReactionsLanguageFactory.eINSTANCE.createElementReplacementChangeType => [
				feature = MirBaseFactory.eINSTANCE.createMetaclassEReferenceReference.reference(eClass, reference)
			])
		}

		def private continueWithChangeType(ElementChangeType changeType) {
			modelElementChange.changeType = changeType
			return new PreconditionOrRoutineCallBuilder(builder)
		}
	}

	static class RoutineCallBuilder {
		protected val extension FluentReactionBuilder builder

		private new(FluentReactionBuilder builder) {
			this.builder = builder
		}

		def call(FluentRoutineBuilder[] routineBuilders) {
			checkNotNull(routineBuilders)
			checkArgument(routineBuilders.length > 0, "Must provide at least one routineBuilder!")
			for (routineBuilder : routineBuilders) {
				call(routineBuilder)
			}
			return builder
		}

		def call(FluentRoutineBuilder routineBuilder, RoutineCallParameter... parameters) {
			checkNotNull(routineBuilder)
			checkState(routineBuilder.readyToBeAttached,
				'''The «routineBuilder» is not sufficiently initialised to be set on the «builder»''')
			checkState(!routineBuilder.requireNewValue || valueType !== null,
				'''The «routineBuilder» requires a new value, but the «builder» doesn’t create one!''')
			checkState(!routineBuilder.requireOldValue || valueType !== null,
				'''The «routineBuilder» requires an old value, but the «builder» doesn’t create one!''')
			checkState(!routineBuilder.requireAffectedEObject || affectedObjectType !== null,
				'''The «routineBuilder» requires an affectedElement, but the «builder» doesn’t create one!''')
			// TODO check validity of explicitly specified parameters

			if (affectedObjectType !== null && routineBuilder.requireAffectedEObject) {
				routineBuilder.affectedObjectType = affectedObjectType
			}
			if (valueType !== null && (routineBuilder.requireNewValue || routineBuilder.requireOldValue)) {
				routineBuilder.valueType = valueType
			}

			builder.transferReactionsSegmentTo(routineBuilder)
			addRoutineCall(routineBuilder, parameters)

			readyToBeAttached = true
			return builder
		}

		def private addRoutineCall(FluentRoutineBuilder routineBuilder, RoutineCallParameter... parameters) {
			if (reaction.callRoutine === null) {
				reaction.callRoutine = ReactionsLanguageFactory.eINSTANCE.createReactionRoutineCall => [
					code = routineCall(routineBuilder, parameters)
				]
			} else {
				val callRoutine = reaction.callRoutine
				val callRoutineCode = callRoutine.code
				if (callRoutineCode instanceof XBlockExpression) {
					callRoutineCode.expressions += callRoutine.routineCall(routineBuilder, parameters)
				} else {
					reaction.callRoutine.code = XbaseFactory.eINSTANCE.createXBlockExpression => [
						expressions += callRoutineCode
						expressions += callRoutine.routineCall(routineBuilder, parameters)
					]
				}
			}
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

		def private routineCall(ReactionRoutineCall routineCall, FluentRoutineBuilder routineBuilder,
			RoutineCallParameter... parameters) {
			(XbaseFactory.eINSTANCE.createXFeatureCall => [
				explicitOperationCall = true
			]).whenJvmTypes [
				feature = routineBuilder.jvmOperation
				implicitReceiver = jvmOperationRoutineFacade
				val typeProvider = typeProvider
				if (parameters.isNullOrEmpty) {
					featureCallArguments += routineBuilder.requiredArguments(typeProvider)
				} else {
					featureCallArguments += parameters.map[getExpression(typeProvider)]
				}
			]
		}

		def private List<XExpression> requiredArguments(FluentRoutineBuilder routineBuilder, TypeProvider typeProvider) {
			val parameterList = new ArrayList<XExpression>(3)
			if (routineBuilder.requireAffectedEObject) {
				parameterList += typeProvider.affectedEObject
			}
			if (routineBuilder.requireNewValue) {
				parameterList += typeProvider.newValue
			}
			if (routineBuilder.requireOldValue) {
				parameterList += typeProvider.oldValue
			}
			return parameterList
		}
	}

	static class PreconditionOrRoutineCallBuilder extends RoutineCallBuilder {
		private new(FluentReactionBuilder builder) {
			super(builder)
		}

		def with(Function<TypeProvider, XExpression> expressionProvider) {
			reaction.trigger.precondition = ReactionsLanguageFactory.eINSTANCE.createPreconditionCodeBlock => [
				code = XbaseFactory.eINSTANCE.createXBlockExpression.whenJvmTypes [
					expressions += extractExpressions(expressionProvider.apply(typeProvider))
				]
			]
			return new RoutineCallBuilder(builder)
		}
	}

	def package getReaction() {
		reaction
	}

	override toString() {
		'''reaction builder for “«reaction.name»”'''
	}

	override protected getCreatedElementName() {
		reaction.name
	}

	override protected getCreatedElementType() {
		"reaction"
	}

	def call(FluentRoutineBuilder[] routineBuilders) {
		new RoutineCallBuilder(this).call(routineBuilders)
	}

	def call(FluentRoutineBuilder routineBuilder, RoutineCallParameter... parameters) {
		new RoutineCallBuilder(this).call(routineBuilder, parameters)
	}

	def call(String routineName, Consumer<RoutineStartBuilder> routineInitializer) {
		new RoutineCallBuilder(this).call(routineName, routineInitializer)
	}

	def call(Consumer<RoutineStartBuilder> routineInitializer) {
		new RoutineCallBuilder(this).call(routineInitializer)
	}
}
