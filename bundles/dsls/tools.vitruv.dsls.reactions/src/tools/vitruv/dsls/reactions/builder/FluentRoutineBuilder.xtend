package tools.vitruv.dsls.reactions.builder

import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateCorrespondence
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.ExistingElementReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.RemoveCorrespondence
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.Taggable

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveOneElementType
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveOrRequireAbscenceOfModelElement

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

	var EClassifier valueType
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
		checkState((!requireOldValue && !requireNewValue && !requireAffectedValue) ||
			valueType !== null, '''Although required, there was no value type set on the «this»''')
		checkState(!requireAffectedEObject ||
			affectedObjectType !== null, '''Although required, there was no affected object type set on the «this»''')
		if (requireAffectedEObject) {
			addInputElementIfNotExists(affectedObjectType, CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		}
		if (requireOldValue) {
			addInputElementIfNotExists(valueType, CHANGE_OLD_VALUE_ATTRIBUTE)
		}
		if (requireNewValue) {
			addInputElementIfNotExists(valueType, CHANGE_NEW_VALUE_ATTRIBUTE)
		}
	}

	def package start() {
		new RoutineStartBuilder(this)
	}

	def private addInputElementIfNotExists(EClassifier type, String parameterName) {
		if (routine.input.modelInputElements.findFirst[name == parameterName] !== null) return;
		addInputElement(type, parameterName)
	}

	def private dispatch addInputElement(EClass type, String parameterName) {
		routine.input.modelInputElements += (MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
			name = parameterName
		]).reference(type)
	}

	def private dispatch addInputElement(EDataType type, String parameterName) {
		addInputElement(type.instanceClass, parameterName)
	}

	def private addInputElement(Class<?> type, String parameterName) {
		routine.input.javaInputElements += (MirBaseFactory.eINSTANCE.createNamedJavaElement => [
			name = parameterName
		]).reference(type)
	}

	def package setValueType(EClassifier type) {
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

	def static dispatch isAssignableFrom(EDataType a, EClass b) {
		false
	}

	def static dispatch isAssignableFrom(EClass a, EDataType b) {
		false
	}

	def static dispatch isAssignableFrom(EClass a, EClass b) {
		EcoreUtil2.isAssignableFrom(a, b)
	}

	def static dispatch isAssignableFrom(EDataType a, EDataType b) {
		a.instanceClass.isAssignableFrom(b.instanceClass)
	}

	static class MatcherOrActionBuilder extends ActionBuilder {

		private new(FluentRoutineBuilder builder) {
			super(builder)
		}

		def match(Consumer<UndecidedMatcherStatementBuilder> matchers) {
			val matcher = ReactionsLanguageFactory.eINSTANCE.createMatcher
			routine.matcher = matcher
			val statementsBuilder = new UndecidedMatcherStatementBuilder(builder)
			matchers.accept(statementsBuilder)
			if (routine.matcher.matcherStatements.size == 0) {
				throw new IllegalStateException('''No matcher statements were created in the «builder»!''')
			}
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
		
		def void model(EClass eClass, WellKnownModelInput wellKnown) {
			wellKnown.apply(eClass)
		}

		def private detectWellKnownType(EClass eClass, String parameterName) {
			switch (parameterName) {
				case CHANGE_OLD_VALUE_ATTRIBUTE,
				case CHANGE_NEW_VALUE_ATTRIBUTE: valueType = eClass
				case CHANGE_AFFECTED_ELEMENT_ATTRIBUTE: affectedObjectType = eClass
			}
		}

		def WellKnownModelInput newValue() {
			requireNewValue = true
			return [valueType = it]
		}

		def WellKnownModelInput oldValue() {
			requireOldValue = true
			return [valueType = it]
		}

		def WellKnownModelInput affectedEObject() {
			requireAffectedEObject = true
			return [affectedObjectType = it]
		}

		def void plain(Class<?> javaClass, String parameterName) {
			addInputElement(javaClass, parameterName)
		}
	}
	
	interface WellKnownModelInput {
		def void apply(EClass type)
	}

	static class RetrieveModelElementMatcherStatementBuilder {
		val extension FluentRoutineBuilder builder
		val RetrieveModelElement statement

		private new(FluentRoutineBuilder builder, RetrieveModelElement statement) {
			this.builder = builder
			this.statement = statement
		}
		
		def retrieve(EClass modelElement) {
			internalRetrieveOne(modelElement)
			return new RetrieveModelElementMatcherStatementCorrespondenceBuilder(builder, statement)
		}
		
		def retrieveOptional(EClass modelElement) {
			val retrieveOneStatement = internalRetrieveOne(modelElement);
			retrieveOneStatement.optional = true
			return new RetrieveModelElementMatcherStatementCorrespondenceBuilder(builder, statement)
		}
		
		def retrieveAsserted(EClass modelElement) {
			val retrieveOneStatement = internalRetrieveOne(modelElement);
			retrieveOneStatement.asserted = true
			return new RetrieveModelElementMatcherStatementCorrespondenceBuilder(builder, statement)
		}

		def retrieveMany(EClass modelElement) {
			reference(modelElement)
			statement.retrievalType = ReactionsLanguageFactory.eINSTANCE.createRetrieveManyModelElements();
			return new RetrieveModelElementMatcherStatementCorrespondenceBuilder(builder, statement)
		}

		private def internalRetrieveOne(EClass modelElement) {
			reference(modelElement)
			val retrieveOneElement = ReactionsLanguageFactory.eINSTANCE.createRetrieveOneModelElement();
			statement.retrievalType = retrieveOneElement
			return retrieveOneElement
		}
		
		private def void reference(EClass modelElement) {
			statement.reference(modelElement)
		}

	}

	static class RetrieveModelElementMatcherStatementCorrespondenceBuilder {
		val extension FluentRoutineBuilder builder
		val RetrieveOrRequireAbscenceOfModelElement statement

		private new(FluentRoutineBuilder builder, RetrieveOrRequireAbscenceOfModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def correspondingTo() {
			new RetrieveModelElementMatcherStatementCorrespondenceElementBuilder(builder, statement)
		}

		def correspondingTo(String element) {
			statement.correspondenceSource = correspondingElement(element)
			new TaggedWithBuilder(builder, statement)
		}
	}

	static class RetrieveModelElementMatcherStatementCorrespondenceElementBuilder {
		val extension FluentRoutineBuilder builder
		val RetrieveOrRequireAbscenceOfModelElement statement

		private new(FluentRoutineBuilder builder, RetrieveOrRequireAbscenceOfModelElement statement) {
			this.builder = builder
			this.statement = statement
		}

		def affectedEObject() {
			requireAffectedEObject = true
			statement.correspondenceSource = correspondingElement(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
			new TaggedWithBuilder(builder, statement)
		}

		def newValue() {
			requireNewValue = true
			statement.correspondenceSource = correspondingElement(CHANGE_NEW_VALUE_ATTRIBUTE)
			new TaggedWithBuilder(builder, statement)
		}
		
		def oldValue() {
			requireOldValue = true
			statement.correspondenceSource = correspondingElement(CHANGE_OLD_VALUE_ATTRIBUTE)
			new TaggedWithBuilder(builder, statement)
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
			routine.matcher.matcherStatements += statement
			new RetrieveModelElementMatcherStatementBuilder(builder, statement)
		}

		def requireAbsenceOf(EClass absentMetaclass) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createRequireAbscenceOfModelElement.reference(absentMetaclass)
			routine.matcher.matcherStatements += statement
			return new RetrieveModelElementMatcherStatementCorrespondenceBuilder(builder, statement)
		}

		def check(Function<RoutineTypeProvider, XExpression> expressionBuilder) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createMatcherCheckStatement => [
				code = XbaseFactory.eINSTANCE.createXBlockExpression.whenJvmTypes [
					expressions += extractExpressions(expressionBuilder.apply(typeProvider))
				]
			]
			routine.matcher.matcherStatements += statement
			return statement
		}
		
		def checkAsserted(Function<RoutineTypeProvider, XExpression> expressionBuilder) {
			val statement = check(expressionBuilder);
			statement.asserted = true;
		}
	}
	
	static class TaggedWithBuilder {
		val extension FluentRoutineBuilder builder
		val Taggable taggable
		
		private new(FluentRoutineBuilder builder, Taggable taggable) {
			this.builder = builder
			this.taggable = taggable
		}
		
		def void taggedWith(String tag) {
			taggable.tag = ReactionsLanguageFactory.eINSTANCE.createTagCodeBlock => [
				code = XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = tag
				]
			]
		}
		
		def void taggedWith(Function<RoutineTypeProvider, XExpression> tagExpressionBuilder) {
			taggable.tag = ReactionsLanguageFactory.eINSTANCE.createTagCodeBlock => [
				code = XbaseFactory.eINSTANCE.createXBlockExpression.whenJvmTypes [
					expressions += extractExpressions(tagExpressionBuilder.apply(typeProvider))
				]
			]
		}
	}

	static class ActionBuilder {
		protected val extension FluentRoutineBuilder builder

		private new(FluentRoutineBuilder builder) {
			this.builder = builder
		}

		def action(Consumer<ActionStatementBuilder> actions) {
			routine.action = ReactionsLanguageFactory.eINSTANCE.createAction
			val statementBuilder = new ActionStatementBuilder(builder)
			actions.accept(statementBuilder)
			if (routine.action.actionStatements.size == 0) {
				throw new IllegalStateException('''No actions were created in the «builder»!''')
			}
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
			routine.action.actionStatements += statement
		}

		def vall(String vallName) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createCreateModelElement => [
				name = vallName
			]
			routine.action.actionStatements += statement
			new CreateStatementBuilder(builder, statement)
		}

		def addCorrespondenceBetween() {
			val statement = ReactionsLanguageFactory.eINSTANCE.createCreateCorrespondence
			routine.action.actionStatements += statement
			new CorrespondenceElementBuilder(builder, new CorrespondenceTargetBuilder(builder, statement), [
				statement.firstElement = it
			])
		}

		def addCorrespondenceBetween(String existingElement) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createCreateCorrespondence => [
				firstElement = existingElement(existingElement)
			]
			routine.action.actionStatements += statement
			new CorrespondenceTargetBuilder(builder, statement)
		}

		def void update(String existingElement, Function<RoutineTypeProvider, XExpression> expressionBuilder) {
			routine.action.actionStatements += ReactionsLanguageFactory.eINSTANCE.createUpdateModelElement => [
				element = existingElement(existingElement)
				updateBlock = ReactionsLanguageFactory.eINSTANCE.createExecutionCodeBlock => [
					code = XbaseFactory.eINSTANCE.createXBlockExpression.whenJvmTypes [
						expressions += extractExpressions(expressionBuilder.apply(typeProvider))
					]
				]
			]
		}

		def removeCorrespondenceBetween() {
			val statement = ReactionsLanguageFactory.eINSTANCE.createRemoveCorrespondence
			routine.action.actionStatements += statement
			new CorrespondenceElementBuilder(builder, new CorrespondenceTargetBuilder(builder, statement), [
				statement.firstElement = it
			])
		}

		def removeCorrespondenceBetween(String existingElement) {
			val statement = ReactionsLanguageFactory.eINSTANCE.createRemoveCorrespondence => [
				firstElement = existingElement(existingElement)
			]
			routine.action.actionStatements += statement
			new CorrespondenceTargetBuilder(builder, statement)
		}

		def void execute(Function<RoutineTypeProvider, XExpression> expressionBuilder) {
			routine.action.actionStatements += ReactionsLanguageFactory.eINSTANCE.createExecuteActionStatement => [
				code = XbaseFactory.eINSTANCE.createXBlockExpression.whenJvmTypes [
					expressions += extractExpressions(expressionBuilder.apply(typeProvider))
				]
			]
		}
	}

	static class RoutineTypeProvider extends AbstractTypeProvider<FluentRoutineBuilder> {
		extension val FluentRoutineBuilder builderAsExtension

		private new(IJvmTypeProvider delegate, FluentRoutineBuilder builder, XExpression scopeExpression) {
			super(delegate, builder, scopeExpression)
			builderAsExtension = builder
		}

		/**
		 * Retrieves the routine’s user execution class.
		 */
		def routineUserExecutionType() {
			scopeExpression.correspondingMethod.declaringType
		}

		/**
		 * Retrieves a feature call to the routine’s user execution class.
		 */
		def routineUserExecution() {
			routineUserExecutionType.featureCall
		}
	}

	def private getTypeProvider(XExpression scopeExpression) {
		val delegateTypeProvider = context.typeProviderFactory.findOrCreateTypeProvider(
			attachedReactionsFile.eResource.resourceSet)
		new RoutineTypeProvider(delegateTypeProvider, this, scopeExpression)
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

		def andInitialize(Function<RoutineTypeProvider, XExpression> expressionBuilder) {
			statement.initializationBlock = ReactionsLanguageFactory.eINSTANCE.createExecutionCodeBlock => [
				code = XbaseFactory.eINSTANCE.createXBlockExpression.whenJvmTypes [
					expressions += extractExpressions(expressionBuilder.apply(typeProvider))
				]
			]
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
			requireOldValue = true
			elementConsumer.accept(existingElement(CHANGE_OLD_VALUE_ATTRIBUTE))
			next
		}

		def newValue() {
			requireNewValue = true
			elementConsumer.accept(existingElement(CHANGE_NEW_VALUE_ATTRIBUTE))
			next
		}

		def affectedEObject() {
			requireAffectedEObject = true
			elementConsumer.accept(existingElement(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE))
			next
		}
	}

	static class CorrespondenceTargetBuilder {
		val extension FluentRoutineBuilder builder
		val Taggable statement

		private new(FluentRoutineBuilder builder, Taggable statement) {
			this.builder = builder
			this.statement = statement
		}

		def and() {
			val tagBuilder = new TaggedWithBuilder(builder, statement)
			new CorrespondenceElementBuilder(builder, tagBuilder, [statement.secondElement = it])
		}

		def and(String existingElement) {
			statement.secondElement = existingElement(existingElement)
			new TaggedWithBuilder(builder, statement)
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

	def package getJvmOperation() {
		val jvmMethod = context.jvmModelAssociator.getPrimaryJvmElement(routine)
		if (jvmMethod instanceof JvmOperation) {
			return jvmMethod
		}
		throw new IllegalStateException('''Could not find the routine facade method corresponding to the routine “«routine.name»”''')
	}

	override protected getCreatedElementName() {
		routine.name
	}

	override protected getCreatedElementType() {
		"routine"
	}

}
