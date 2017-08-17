package tools.vitruv.dsls.reactions.builder

import java.util.function.Consumer
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.ActionStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateCorrespondence
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.ExistingElementReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.RemoveCorrespondence
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

import static extension org.eclipse.xtext.EcoreUtil2.isAssignableFrom

class FluentRoutineBuilder extends FluentReactionsSegmentChildBuilder {

	@Accessors(PACKAGE_GETTER)
	var Routine routine
	@Accessors(PACKAGE_GETTER)
	var requireOldValue = false
	@Accessors(PACKAGE_GETTER)
	var requireNewValue = false
	@Accessors(PACKAGE_GETTER)
	var requireAffectedEObject = false
	@Accessors(PACKAGE_GETTER)
	var requireAffectedValue = false
	
	var EClass valueType
	var EClass affectedObjectType

	package new(String routineName, FluentBuilderContext context) {
		super(context)
		this.routine = ReactionsLanguageFactory.eINSTANCE.createRoutine => [
			name = routineName
			input = ReactionsLanguageFactory.eINSTANCE.createRoutineInput
		]
	}

	override protected attachmentPreparation() {
		super.attachmentPreparation()
		checkState(routine.action !== null, "No action was set on this routine!")
		checkState((!requireOldValue && !requireNewValue && !requireAffectedValue) || valueType !== null,
			"Although required, there was no value type set on the «this»")
		checkState(!requireAffectedEObject ||
			affectedObjectType !== null, '''Although required, there was no affected object type set on the «this»''')
		if (requireOldValue) {
			addInputElementIfNotExists(valueType, CHANGE_OLD_VALUE_ATTRIBUTE)
		}
		if (requireNewValue) {
			addInputElementIfNotExists(valueType, CHANGE_NEW_VALUE_ATTRIBUTE)
		}
		if (requireAffectedEObject) {
			addInputElementIfNotExists(affectedObjectType, CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		}
	}

	def package start() {
		new RoutineStartBuilder(this)
	}
	
	def private addInputElementIfNotExists(EClass type, String parameterName) {
		if (routine.input.modelInputElements.findFirst [name == parameterName] !== null) return;
		addInputElement(type, parameterName)
	}

	def private addInputElement(EClass type, String parameterName) {
		routine.input.modelInputElements += (MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
			name = parameterName
		]).reference(type)
	}

	def package setValueType(EClass type) {
		if (valueType === null) {
			valueType = type
		}
		if (!valueType.isAssignableFrom(type)) {
			throw new IllegalStateException('''The «this» already has the value type “«valueType.name»” set, which is not a super type of “«type.name»”. The value type can thus not be set to “«type.name»”!''')
		}
	}

	def package setAffectedElementType(EClass type) {
		if (affectedObjectType === null) {
			affectedObjectType = type
		}
		if (!affectedObjectType.isAssignableFrom(type)) {
			throw new IllegalStateException('''The «this» already has the affected element type “«affectedObjectType.name»” set, which is not a super type of “«type.name»”. The affected element type can thus not be set to “«type.name»”!''')
		}
	}

	static class MatcherOrActionBuilder extends ActionBuilder {

		private new(FluentRoutineBuilder builder) {
			super(builder)
		}

		def match(Consumer<UndecidedMatcherStatementBuilder> matchers) {
			val matcher = ReactionsLanguageFactory.eINSTANCE.createMatcher
			builder.routine.matcher = matcher
			val statementsBuilder = new UndecidedMatcherStatementBuilder(builder)
			matchers.accept(statementsBuilder)
			new ActionBuilder(builder)
		}
	}

	static class RoutineStartBuilder extends MatcherOrActionBuilder {

		private new(FluentRoutineBuilder builder) {
			super(builder)
		}

		def input(Consumer<InputBuilder> inputs) {
			inputs.accept(new InputBuilder(builder))
			new MatcherOrActionBuilder(builder)
		}

	}

	static class InputBuilder {
		val extension FluentRoutineBuilder builder

		private new(FluentRoutineBuilder builder) {
			this.builder = builder
		}

		def void model(EClass eClass, String parameterName) {
			detectWellKnownType(eClass, parameterName)
			addInputElement(eClass, parameterName)
		}
		
		def private detectWellKnownType(EClass eClass, String parameterName) {
			switch (parameterName) {
				case CHANGE_OLD_VALUE_ATTRIBUTE,
				case CHANGE_NEW_VALUE_ATTRIBUTE: valueType = eClass
				case CHANGE_AFFECTED_ELEMENT_ATTRIBUTE: affectedObjectType = eClass
			}
		}

		def void plain(Class<?> javaClass, String parameterName) {
			throw new RuntimeException("not yet implemented") // TODO
		}
	}

	static class RetrieveModelElementMatcherStatementBuilder {
		val extension FluentRoutineBuilder builder
		val RetrieveModelElement statement

		private new(FluentRoutineBuilder builder, RetrieveModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def retrieve(EClass modelElement) {
			statement.reference(modelElement)
			new RetrieveModelElementMatcherStatementCorrespondenceBuilder(builder, statement)
		}

		def retrieveOptional(EClass modelElement) {
			statement.optional = true
			retrieve(modelElement)
		}

	}

	static class RetrieveModelElementMatcherStatementCorrespondenceBuilder {
		val extension FluentRoutineBuilder builder
		val RetrieveModelElement statement

		private new(FluentRoutineBuilder builder, RetrieveModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def correspondingTo() {
			new RetrieveModelElementMatcherStatementCorrespondenceElementBuilder(builder, statement)
		}

		def void correspondingTo(String element) {
			statement.correspondenceSource = correspondingElement(element)
		}
	}

	static class RetrieveModelElementMatcherStatementCorrespondenceElementBuilder {
		val extension FluentRoutineBuilder builder
		val RetrieveModelElement statement

		private new(FluentRoutineBuilder builder, RetrieveModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def void affectedEObject() {
			builder.requireAffectedEObject = true
			statement.correspondenceSource = correspondingElement(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		}
	}

	static class UndecidedMatcherStatementBuilder {
		val extension FluentRoutineBuilder builder

		private new(FluentRoutineBuilder builder) {
			this.builder = builder
		}

		def vall(String valName) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createRetrieveModelElement => [
				name = valName
			]
			builder.routine.matcher.matcherStatements += statement
			new RetrieveModelElementMatcherStatementBuilder(builder, statement)
		}

		def void requireAbsenceOf(EClass absentMetaclass) {
			val statement = (ReactionsLanguageFactory.eINSTANCE.createRetrieveModelElement => [
				abscence = true
			]).reference(absentMetaclass)
			builder.routine.matcher.matcherStatements += statement
		}
	}

	static class ActionBuilder {
		protected val extension FluentRoutineBuilder builder

		private new(FluentRoutineBuilder builder) {
			this.builder = builder
		}

		def action(Consumer<ActionStatementBuilder> actions) {
			this.builder.routine.action = ReactionsLanguageFactory.eINSTANCE.createAction
			val statementBuilder = new ActionStatementBuilder(builder)
			actions.accept(statementBuilder)
			readyToBeAttached = true
			return builder
		}
	}

	static class ActionStatementBuilder {
		val extension FluentRoutineBuilder builder

		private new(FluentRoutineBuilder builder) {
			this.builder = builder
		}

		def delete(String existingElement) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createDeleteModelElement => [
				element = existingElement(existingElement)
			]
			builder.routine.action.actionStatements += statement
		}

		def vall(String vallName) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createCreateModelElement => [
				name = vallName
			]
			builder.routine.action.actionStatements += statement
			new CreateStatementBuilder(builder, statement)
		}

		def addCorrespondenceBetween() {
			val statement = ReactionsLanguageFactory.eINSTANCE.createCreateCorrespondence
			builder.routine.action.actionStatements += statement
			new CorrespondenceElementBuilder(builder, new CorrespondenceTargetBuilder(builder, statement), [
				statement.firstElement = it
			])
		}

		def addCorrespondenceBetween(String existingElement) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createCreateCorrespondence => [
				firstElement = existingElement(existingElement)
			]
			builder.routine.action.actionStatements += statement
			new CorrespondenceTargetBuilder(builder, statement)
		}

		def removeCorrespondenceBetween() {
			val statement = ReactionsLanguageFactory.eINSTANCE.createRemoveCorrespondence
			builder.routine.action.actionStatements += statement
			new CorrespondenceElementBuilder(builder, new CorrespondenceTargetBuilder(builder, statement), [
				statement.firstElement = it
			])
		}

		def removeCorrespondenceBetween(String existingElement) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createRemoveCorrespondence => [
				firstElement = existingElement(existingElement)
			]
			builder.routine.action.actionStatements += statement
			new CorrespondenceTargetBuilder(builder, statement)
		}
	}

	static class CreateStatementBuilder {
		val extension FluentRoutineBuilder builder
		val CreateModelElement statement

		private new(FluentRoutineBuilder builder, CreateModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def create(EClass element) {
			statement.reference(element)
			new CreateStatementIntializationBuilder(builder, statement)
		}
	}

	static class CreateStatementIntializationBuilder {
		val extension FluentRoutineBuilder builder
		val CreateModelElement statement

		private new(FluentRoutineBuilder builder, CreateModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def andInitializeWith() {
			// TODO
		}
	}

	static class CorrespondenceElementBuilder<NextType> {
		val extension FluentRoutineBuilder builder
		val Consumer<ExistingElementReference> elementConsumer
		val NextType next

		private new(FluentRoutineBuilder builder, NextType next, Consumer<ExistingElementReference> elementConsumer) {
			this.builder = builder
			this.elementConsumer = elementConsumer
			this.next = next
		}

		def oldValue() {
			builder.requireOldValue = true
			elementConsumer.accept(existingElement(CHANGE_OLD_VALUE_ATTRIBUTE))
			next
		}

		def newValue() {
			builder.requireNewValue = true
			elementConsumer.accept(existingElement(CHANGE_NEW_VALUE_ATTRIBUTE))
			next
		}

		def affectedEObject() {
			builder.requireAffectedEObject = true
			elementConsumer.accept(existingElement(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE))
			next
		}
	}

	static class CorrespondenceTargetBuilder {
		val extension FluentRoutineBuilder builder
		val ActionStatement statement

		private new(FluentRoutineBuilder builder, ActionStatement statement) {
			this.builder = builder
			this.statement = statement
		}

		def and() {
			new CorrespondenceElementBuilder(builder, null, [statement.secondElement = it])
		}

		def and(String existingElement) {
			statement.secondElement = existingElement(existingElement)
		}

		def private dispatch setSecondElement(CreateCorrespondence correspondenceStatement,
			ExistingElementReference existingElement) {
			correspondenceStatement.secondElement = existingElement
		}

		def private dispatch setSecondElement(RemoveCorrespondence correspondenceStatement,
			ExistingElementReference existingElement) {
			correspondenceStatement.secondElement = existingElement
		}
	}

	def private existingElement(String name) {
		ReactionsLanguageFactory.eINSTANCE.createExistingElementReference => [
			code = XbaseFactory.eINSTANCE.createXFeatureCall.whenJvmTypes [
				feature = correspondingMethodParameter(name)
			]
		]
	}

	def private correspondingElement(String name) {
		ReactionsLanguageFactory.eINSTANCE.createCorrespondingObjectCodeBlock => [
			code = XbaseFactory.eINSTANCE.createXFeatureCall.whenJvmTypes [
				feature = correspondingMethodParameter(name)
			]
		]
	}

	override toString() {
		'''routine builder for “«routine.name»”'''
	}

	def private correspondingMethodParameter(XExpression correpondingExpression, String parameterName) {
		val retrievalMethod = context.jvmModelAssociator.getPrimaryJvmElement(correpondingExpression)
		if (retrievalMethod instanceof JvmOperation) {
			val result = retrievalMethod.parameters.findFirst[name == parameterName]
			if (result === null) {
				// most likely an error by the client
				throw new IllegalStateException('''Could not find the parameter “«parameterName»” of the routine “«routine.name»”''')
			}
			return result
		}
		throw new IllegalStateException('''Could not find the retrieval method corresponding to the element reference “«parameterName»” in the routine “«routine.name»”''')
	}

	def package getJvmOperation() {
		val jvmMethod = context.jvmModelAssociator.getPrimaryJvmElement(routine)
		if (jvmMethod instanceof JvmOperation) {
			return jvmMethod
		}
		throw new IllegalStateException('''Could not find the routine facade method corresponding to the routine “«routine.name»”''')
	}

}
