package tools.vitruv.dsls.mappings.generator.action

import java.util.List
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class ReactionFeatureConditionsGenerator {

	private List<FeatureConditionGenerator> conditions;
	private AbstractReactionTypeGenerator reactionTypeGenerator
	private FeatureRoutineCall featureRoutineCall
	public final static String MAPPING_OBJECT = 'mappingObject'

	new(AbstractReactionTypeGenerator reactionTypeGenerator, List<FeatureConditionGenerator> conditions) {
		this.reactionTypeGenerator = reactionTypeGenerator
		this.conditions = conditions;
	}

	def void generate(ActionStatementBuilder builder, FeatureRoutineCall featureRoutineCall) {
		this.featureRoutineCall = featureRoutineCall
		if (hasAnyMatchingFeatureConditions) {
			builder.call [ typeProvider |
				XbaseFactory.eINSTANCE.createXBlockExpression => [
					expressions += reactionTypeGenerator.reactionParameters.filter[filterEmptyConditions].map [
						generateParameter(typeProvider, it)
					]
				]
			]
		}
	}

	public def hasAnyMatchingFeatureConditions() {
		reactionTypeGenerator.reactionParameters.exists [
			filterEmptyConditions
		]
	}

	public def hasFeatureConditionFor(MappingParameter parameter) {
		filterEmptyConditions(parameter)
	}

	private def filterEmptyConditions(MappingParameter parameter) {
		conditions.filter[it.feasibleForParameter(parameter)].size > 0
	}

	/**
	 * should generate to the following statement:
	 * if( condition1 && condition2 ... && conditionN) call sub-routine
	 */
	private def generateParameter(RoutineTypeProvider provider, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.generateIfStatement(parameter)
			it.then = XbaseFactory.eINSTANCE.createXBlockExpression => [
				 expressions += provider.generateRoutineCall(parameter)
			]
		]
	}

	private def generateRoutineCall(RoutineTypeProvider provider, MappingParameter parameter) {
		val call = XbaseFactory.eINSTANCE.createXFeatureCall => [
			featureCallArguments += provider.affectedEObject
			explicitOperationCall = true
		]
		featureRoutineCall.connectRoutineCall(parameter, call)
		call
	}

	private def generateIfStatement(RoutineTypeProvider provider, MappingParameter parameter) {
		val feasibleConditions = conditions.filter[it.feasibleForParameter(parameter)]
		val firstExpression = feasibleConditions.get(0).generateFeatureCondition(provider)
		if (feasibleConditions.size == 1) {
			// straight forward 
			firstExpression
		} else {
			// chain them together with &&
			var leftExpression = firstExpression
			for (condition : feasibleConditions.drop(1)) {
				val expression = condition.generateFeatureCondition(provider)
				val andExpression = XbaseFactory.eINSTANCE.createXBinaryOperation
				andExpression.leftOperand = leftExpression
				andExpression.rightOperand = expression
				andExpression.feature = provider.and
				leftExpression = andExpression
			}
			return leftExpression
		}
	}

/* 
	private def generateReturn(boolean value) {
		XbaseFactory.eINSTANCE.createXReturnExpression => [
			expression = XbaseFactory.eINSTANCE.createXBooleanLiteral => [
				isTrue = value
			]
		]
	}*/


/* 
	private def generateVarAssign(RoutineTypeProvider provider, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXAssignment => [
			assignable = provider.variable(MAPPING_OBJECT)
			value = XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = parameter.value.name
			]
		]
	}*/
}
