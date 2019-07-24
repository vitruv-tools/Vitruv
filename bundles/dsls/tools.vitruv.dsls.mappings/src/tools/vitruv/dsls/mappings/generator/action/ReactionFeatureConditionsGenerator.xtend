package tools.vitruv.dsls.mappings.generator.action

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.TraverseStepDown
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.XtextFactory
import org.eclipse.xtext.common.types.TypesFactory
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator

class ReactionFeatureConditionsGenerator {

	private List<FeatureConditionGenerator> conditions;
	private List<MappingParameter> parameters
	private FeatureRoutineCall featureRoutineCall
	private FluentRoutineBuilder routineBuilder
	private MappingParameterGraphTraverser treeTraverser
	private MappingParameter affectedEObjectParameter
	private Map<MappingParameter, XVariableDeclaration> createdVariables
	public final static String MAPPING_OBJECT = 'mappingObject'

	new(List<MappingParameter> parameters, List<FeatureConditionGenerator> conditions) {
		this.parameters = parameters
		this.conditions = conditions;
		this.treeTraverser = new MappingParameterGraphTraverser(conditions, parameters.map[nodeName])
	}

	private def nodeName(MappingParameter parameter) {
		parameter.value.name
	}

	public def skipParameterCheck() {
		parameters.size == 1 && conditions.empty
	}

	def void generateCorrespondenceInitialization(ActionStatementBuilder builder) {
		builder.execute [ provider |
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions += conditions.map[generateCorrespondenceInitialization(provider)].filter[it !== null]
			]
		]
	}

	def void generate(AbstractReactionTypeGenerator generator, ActionStatementBuilder builder,
		FluentRoutineBuilder routineBuilder, FeatureRoutineCall featureRoutineCall) {
		createdVariables = new HashMap
		this.routineBuilder = routineBuilder
		this.featureRoutineCall = featureRoutineCall
		builder.call [ typeProvider |
			if (skipParameterCheck) {
				// if its just one parameter and no featureconditions we
				// can just call the subroutine directly with the affectedEObject
				typeProvider.generateResultCall
			} else {
				XbaseFactory.eINSTANCE.createXBlockExpression => [
					expressions += parameters.filter [
						// only check for parameters that are the type of the calling reaction trigger
						it.value.metaclass == generator.metaclass
					].map[typeProvider.generateConditions(it)]
				]
			}
		]
	}

	private def generateConditions(RoutineTypeProvider provider, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.generateParameterCheck(parameter)
			affectedEObjectParameter = parameter
			if (parameters.size > 1) {
				val retrievedParameters = new ArrayList
				retrievedParameters += parameter // affectedEObject is already retrieved
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
			// nextParameter is in fromParameters feature (when containing / being referenced from its feature)
			return provider.generateChildInParent(retrievedParameters, nextParameter, fromParameter, feature)
		} else {
			// nextParameter is fromParameters parent (when containing / referencing it with its feature)
			return provider.generateParentContainsChild(retrievedParameters, nextParameter, fromParameter, feature)
		}
	}

	private def generateParentContainsChild(RoutineTypeProvider provider, List<MappingParameter> retrievedParameters,
		MappingParameter child, MappingParameter parent, EReference feature) {
		retrievedParameters += parent
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			if (feature.containment) {
				// simple case: just the eContainer, retrieve and check the value from it
				expressions +=
					provider.generateSimpleFeatureRetrievelCheck(retrievedParameters, child, parent,
						provider.findMetaclassMethod(parent.value.metaclass, 'eContainer'))
			} else {
				// todo: check via cross-references to find a possible parent element
				throw new UnsupportedOperationException(
					"Retrieving parent of child not implemented yet for non-containment features!")
			}
		]
	}

	private def generateChildInParent(RoutineTypeProvider provider, List<MappingParameter> retrievedParameters,
		MappingParameter child, MappingParameter parent, EReference feature) {
		retrievedParameters += child
		val featureMethod = provider.findMetaclassMethod(parent.value.metaclass, feature)
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			if (feature.many) {
				// filter possible elements and check all of them
				// for(val parameter: parameter.feature)
				// {
				// if( do checks for parameter)
				// {	=> continue here }
				// }
				// XbaseFactory.eINSTANCE.createXBasicForLoopExpression
				expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
					val loopParameter = TypesFactory.eINSTANCE.createJvmFormalParameter => [
						name = child.nodeName
					]
					createdVariables.put(child, loopParameter)
					declaredParam = loopParameter
					forExpression = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
						explicitOperationCall = true
						implicitReceiver = provider.retrieveVariable(parent)
						it.feature = featureMethod
					]
					eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
						expressions += provider.generateRetrievedParameterCheck(retrievedParameters, child)
					]
				]
			} else {
				// just retrieve the value and check
				expressions +=
					provider.generateSimpleFeatureRetrievelCheck(retrievedParameters, parent, child, featureMethod)
			}
		]
	}

	def String candidatesName(MappingParameter parameter) {
		parameter.nodeName + 'Candidates'
	}

	private def generateSimpleFeatureRetrievelCheck(RoutineTypeProvider provider,
		List<MappingParameter> retrievedParameters, MappingParameter resource, MappingParameter parameter,
		JvmIdentifiableElement feature) {
		val expressions = new ArrayList<XExpression>
		// retrieve the value
		expressions += provider.createVariable(
			parameter,
			XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				explicitOperationCall = true
				it.implicitReceiver = provider.retrieveVariable(resource)
				it.feature = feature
			]
		)
		// check if the value is not null plus the parameter conditions
		expressions += provider.generateRetrievedParameterCheck(retrievedParameters, parameter)
		expressions
	}

	private def generateRetrievedParameterCheck(RoutineTypeProvider provider,
		List<MappingParameter> retrievedParameters, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.andChain(provider.notNull(parameter), provider.generateParameterCheck(parameter))
			it.then = provider.generateEndOfInCondition(retrievedParameters)
		]
	}

	private def createVariable(RoutineTypeProvider provider, MappingParameter parameter, XExpression assignment) {
		XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			createdVariables.put(parameter, it)
			name = parameter.nodeName
			right = assignment
		]
	}

	private def retrieveVariable(RoutineTypeProvider provider, MappingParameter parameter) {
		if (parameter == affectedEObjectParameter) {
			return provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		} else {
			val declaration = createdVariables.get(parameter)
			XbaseFactory.eINSTANCE.creat
			return declaration
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
		treeTraverser.findStepToNextNode(startParameter)
	}

	private def generateResultCall(RoutineTypeProvider provider) {
		val expression = XbaseFactory.eINSTANCE.createXReturnExpression => [
			val call = XbaseFactory.eINSTANCE.createXFeatureCall => [
				featureCallArguments += parameters.map[provider.retrieveVariable(it)]
				explicitOperationCall = true
			]
			expression = call
			featureRoutineCall.connectRoutineCall(routineBuilder, call)
		]
		expression
	}

	private def generateParameterCheck(RoutineTypeProvider provider, MappingParameter parameter) {
		provider.generateParameterCheck(parameter, provider.retrieveVariable(parameter))
	}

	private def generateParameterCheck(RoutineTypeProvider provider, MappingParameter parameter, XExpression variable) {
		// find all conditions to this parameter (we have to filter out InValueConditions)
		val checks = conditions.filter [
			feasibleForParameter(parameter) && (it instanceof InValueConditionGenerator == false)
		]
		if (checks.empty) {
			// no conditions for that element, so we just return true
			XbaseFactory.eINSTANCE.createXBooleanLiteral => [
				isTrue = true
			]
		} else {
			provider.andChain(
				checks.map [
					it.generateFeatureCondition(provider, variable)
				]
			)
		}
	}

}
