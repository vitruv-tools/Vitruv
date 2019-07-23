package tools.vitruv.dsls.mappings.generator.action

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterTreeTraverser.TraverseStepDown
import tools.vitruv.dsls.mappings.generator.conditions.impl.EqualsValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*
import org.eclipse.emf.ecore.EClass

class ReactionFeatureConditionsGenerator {

	private List<FeatureConditionGenerator> conditions;
	private List<MappingParameter> parameters
	private FeatureRoutineCall featureRoutineCall
	private FluentRoutineBuilder routineBuilder
	private MappingParameterTreeTraverser treeTraverser
	private MappingParameter affectedEObjectParameter
	private Map<MappingParameter, XVariableDeclaration> createdVariables
	public final static String MAPPING_OBJECT = 'mappingObject'

	new(List<MappingParameter> parameters, List<FeatureConditionGenerator> conditions) {
		this.parameters = parameters
		this.conditions = conditions;
		this.treeTraverser = new MappingParameterTreeTraverser(conditions, parameters.map[nodeName])
	}

	private def nodeName(MappingParameter parameter) {
		parameter.value.name
	}

	def void generate(ActionStatementBuilder builder, FluentRoutineBuilder routineBuilder,
		FeatureRoutineCall featureRoutineCall) {
		createdVariables = new HashMap
		this.routineBuilder = routineBuilder
		this.featureRoutineCall = featureRoutineCall
		parameters.forEach [ parameter |
			builder.call [ typeProvider |
				XbaseFactory.eINSTANCE.createXBlockExpression => [
					expressions += parameters.map[typeProvider.generateConditions(parameter)]
				]
			]
		]
	}

	private def generateConditions(RoutineTypeProvider provider, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.generateEqualsCondition(parameter)
			affectedEObjectParameter = parameter
			if (parameters.size > 1) {
				val retrievedParameters = new ArrayList
				retrievedParameters += parameter
				it.then = provider.generateInCondition(retrievedParameters)
			} else {
				// no other parameters
				it.then = provider.generateResultCall()
			}
		]
	}

	private def XExpression generateInCondition(RoutineTypeProvider provider,
		List<MappingParameter> retrievedParameters) {
		val nextParameter = parameters.findFirst[!retrievedParameters.contains(it)]
		val path = retrievedParameters.findStepToNextParamter(nextParameter)
		val fromParameter = parameters.findFirst[it == path.startNode]
		val step = path.steps.get(0)
		val feature = step.feature
		if (step instanceof TraverseStepDown) {
			// nextParameter is in fromParameters feature (when containing) / being referenced from its feature
			return provider.generateInDownCondition(retrievedParameters, nextParameter, fromParameter, feature)
		} else {
			// nextParameter is fromParameters parent (when containing) / referencing it with its feature
		}
		XbaseFactory.eINSTANCE.createXBlockExpression => []
	}

	private def generateInDownCondition(RoutineTypeProvider provider, List<MappingParameter> retrievedParameters,
		MappingParameter child, MappingParameter parent, EStructuralFeature feature) {
		retrievedParameters += child
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			val element = child.nodeName
			val variable = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = element
				right = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					explicitOperationCall = true
					implicitReceiver = provider.retrieveVariable(parent)
					feature =provider.findFeatureMethod(parent.value.metaclass, feature)
					if (feature.many) {
						
					}
				]
			]
			createdVariables.put(child, variable)
			expressions += variable
			expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
				// check if the retrieved parameter is not null
				it.^if = provider.notNull(child)
				it.then = provider.generateEndOfInCondition(retrievedParameters)
			]
		]
	}
	
	

	private def retrieveVariable(RoutineTypeProvider provider, MappingParameter parameter) {
		if (parameter == affectedEObjectParameter) {
			return provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		} else {
			val declaration = createdVariables.get(parameter)
		// todo			
		}
	}

	private def notNull(RoutineTypeProvider provider, MappingParameter variable) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = provider.retrieveVariable(variable)
			feature = provider.tripleNotEquals
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}

	private def generateEndOfInCondition(RoutineTypeProvider provider, List<MappingParameter> retrievedParameters) {
		if (retrievedParameters.size == parameters.size) {
			// done
			provider.generateResultCall
		} else {
			// retrieve and check the parameters still left
			provider.generateInCondition(retrievedParameters)
		}
	}

	private def findStepToNextParamter(List<MappingParameter> retrievedParameters, MappingParameter parameter) {
		// find the next parameter to check
		val startParameter = new ArrayList
		startParameter += retrievedParameters.map[nodeName]
		startParameter += parameter.nodeName
		val path = treeTraverser.findPathToNextNode(startParameter)
		if (path.steps.size != 1) {
			// this should not happen, the next parameter is always reachable in one step
			throw new IllegalStateException(
				'Could not reach next parameter in one step, something must be terribly wrong!')
		}
		path
	}

	private def retrieveAndCheckParameter(RoutineTypeProvider provider, List<MappingParameter> retrievedParameters,
		MappingParameter parameter) {
	}

	private def generateResultCall(RoutineTypeProvider provider) {
		val expression = XbaseFactory.eINSTANCE.createXReturnExpression => [
			val call = XbaseFactory.eINSTANCE.createXFeatureCall => [
				featureCallArguments += provider.affectedEObject
				explicitOperationCall = true
			]
			expression = call
			featureRoutineCall.connectRoutineCall(routineBuilder, call)
		]
		expression
	}

	private def generateEqualsCondition(RoutineTypeProvider provider, MappingParameter parameter) {
		// find all where conditions to this parameter
		provider.generateAndChain(conditions.filter [
			feasibleForParameter(parameter) && it instanceof EqualsValueConditionGenerator
		].map [
			it.generateFeatureCondition(provider.retrieveVariable(parameter))
		])
	}

	private def generateAndChain(RoutineTypeProvider provider, XExpression... expressions) {
		if (expressions.empty) {
			return XbaseFactory.eINSTANCE.createXBooleanLiteral => [
				isTrue = true
			]
		}
		var leftExpression = expressions.get(0)
		for (expression : expressions.drop(1)) {
			val andExpression = XbaseFactory.eINSTANCE.createXBinaryOperation
			andExpression.leftOperand = leftExpression
			andExpression.rightOperand = expression
			andExpression.feature = provider.and
			leftExpression = andExpression
		}
		return leftExpression
	}

	/**
	 * should generate to the following statement:
	 * if( condition1 && condition2 ... && conditionN) call sub-routine
	 */
	private def generateParameter(RoutineTypeProvider provider, FluentRoutineBuilder routineBuilder,
		MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.generateIfStatement(parameter)
			it.then = XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions += provider.generateRoutineCall(routineBuilder, parameter)
			]
		]
	}

	private def generateRoutineCall(RoutineTypeProvider provider, FluentRoutineBuilder routineBuilder,
		MappingParameter parameter) {
		val call = XbaseFactory.eINSTANCE.createXFeatureCall => [
			featureCallArguments += provider.affectedEObject
			if (reactionTypeGenerator.usesNewValue) {
				featureCallArguments += provider.newValue
			}
			explicitOperationCall = true
		]
		featureRoutineCall.connectRoutineCall(parameter, routineBuilder, call)
		call
	}

	private def generateIfStatement(RoutineTypeProvider provider, MappingParameter parameter) {
		val feasibleConditions = conditions.filter[it.feasibleForParameter(parameter)]
		val firstExpression = feasibleConditions.get(0).generateFeatureCondition(provider,
			reactionTypeGenerator.usesNewValue)
		if (feasibleConditions.size == 1) {
			// straight forward 
			firstExpression
		} else {
			// chain them together with &&
			var leftExpression = firstExpression
			for (condition : feasibleConditions.drop(1)) {
				val expression = condition.generateFeatureCondition(provider, reactionTypeGenerator.usesNewValue)
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
 * 	private def generateReturn(boolean value) {
 * 		XbaseFactory.eINSTANCE.createXReturnExpression => [
 * 			expression = XbaseFactory.eINSTANCE.createXBooleanLiteral => [
 * 				isTrue = value
 * 			]
 * 		]
 }*/
/* 
 * 	private def generateVarAssign(RoutineTypeProvider provider, MappingParameter parameter) {
 * 		XbaseFactory.eINSTANCE.createXAssignment => [
 * 			assignable = provider.variable(MAPPING_OBJECT)
 * 			value = XbaseFactory.eINSTANCE.createXStringLiteral => [
 * 				value = parameter.value.name
 * 			]
 * 		]
 }*/
}
