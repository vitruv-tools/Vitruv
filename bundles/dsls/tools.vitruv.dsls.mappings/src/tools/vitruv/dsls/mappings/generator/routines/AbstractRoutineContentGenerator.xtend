package tools.vitruv.dsls.mappings.generator.routines

import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.AbstractMappingEntityGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodUtils.*

abstract class AbstractRoutineContentGenerator extends AbstractMappingEntityGenerator {

	@Accessors(PROTECTED_GETTER, PROTECTED_SETTER)
	var List<AbstractSingleSidedCondition<?>> singleSidedConditions
	@Accessors(PROTECTED_GETTER, PROTECTED_SETTER)
	var List<AbstractBidirectionalCondition> bidirectionConditions
	@Accessors(PROTECTED_GETTER, PROTECTED_SETTER)
	var List<FeatureConditionGenerator> correspondingFeatureConditions
	@Accessors(PROTECTED_GETTER)
	var Map<String, JvmIdentifiableElement> localVariables = new HashMap

	def void prepareGenerator(AbstractRoutineContentGenerator parentGenerator) {
		this.initWith(parentGenerator)
		this.singleSidedConditions = parentGenerator.singleSidedConditions
		this.bidirectionConditions = parentGenerator.bidirectionConditions
		this.correspondingFeatureConditions = parentGenerator.correspondingFeatureConditions
		this.localVariables = parentGenerator.localVariables
	}

	protected def variableDeclaration(TypeProvider provider, String name, XExpression rightSide) {
		val declaration = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			it.name = name
			writeable = true
			right = rightSide
		]
		localVariables.put(name, declaration)
		declaration
	}

	protected def variableDeclaration(TypeProvider provider, MappingParameter parameter) {
		val name = parameter.parameterName
		val declaration = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			it.name = name
			writeable = true
			type = provider.findTypeReference(parameter)
		]
		localVariables.put(name, declaration)
		declaration
	}

	protected def variableDeclaration(TypeProvider provider, MappingParameter parameter, XExpression rightSide) {
		val declaration = provider.variableDeclaration(parameter)
		declaration.right = rightSide
		declaration
	}

	protected def retrieveLocalVariable(String parameter) {
		localVariables.get(parameter)
	}

	protected def retrieveLocalVariable(MappingParameter parameter) {
		parameter.parameterName.retrieveLocalVariable
	}

	protected def referenceLocalVariable(String parameter) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			// explicitOperationCall = true
			feature = parameter.retrieveLocalVariable
		]
	}

	protected def referenceLocalVariable(MappingParameter parameter) {
		parameter.parameterName.referenceLocalVariable
	}

	protected def getFeatureConditions() {
		singleSidedConditions.featureConditions
	}

	protected def getFeatureConditions(List<AbstractSingleSidedCondition<?>> conditions) {
		conditions.filter[it instanceof FeatureConditionGenerator].map[it as FeatureConditionGenerator]
	}
}
