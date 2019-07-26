package tools.vitruv.dsls.mappings.generator.routines

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.lib.Functions.Function0
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.TraverseStepDown
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodUtils.*

class MappingParameterRetrievalGenerator extends AbstractRoutineContentGenerator {
	private MappingParameterGraphTraverser treeTraverser
	private MappingParameter affectedEObjectParameter
	private List<MappingParameter> retrievedParameters
	private Function0<XExpression> finishedRetrievingParameters
	private Function0<XExpression> failedToRetrieveParameters

	new(AbstractMappingRoutineGenerator parent) {
		this.prepareGenerator(parent)
		this.treeTraverser = new MappingParameterGraphTraverser(featureConditions.toList, parameters.map[nodeName])
	}

	private def nodeName(MappingParameter parameter) {
		parameter.value.name
	}

	public def generate(RoutineTypeProvider provider, Function0<XExpression> finishedRetrievingParameters,
		Function0<XExpression> failedToRetrieveParameters) {
		this.finishedRetrievingParameters = finishedRetrievingParameters
		this.failedToRetrieveParameters = failedToRetrieveParameters
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			// try to retrieve and check all the parameters via the feature conditions
			expressions += parameters.map[provider.generateAffectedEObjectCheck(it)]
			// lastly, the return when the parameters could not be retrieved
			if (failedToRetrieveParameters !== null) {
				expressions += failedToRetrieveParameters.apply
			}
		]
	}

	private def generateAffectedEObjectCheck(RoutineTypeProvider provider, MappingParameter parameter) {
		// we have to check if the affectedeobject is the correct type and cast it to a new local variable
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = XbaseFactory.eINSTANCE.createXInstanceOfExpression => [
				expression = provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
				type = provider.findTypeReference(parameter)
			]
			it.then = XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions +=
					provider.variableDeclaration(parameter,
						provider.variable(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE))
				if (skipParameterCheck) {
					// if its just one parameter and no featureconditions, so the object is already fully retrieved by the reaction,
					// we dont need to and cant retrieve others
					expressions += finishedRetrievingParameters.apply
				} else {
					expressions += provider.generateConditions(parameter)
				}
			]
		]
	}

	private def generateConditions(RoutineTypeProvider provider, MappingParameter parameter) {
		retrievedParameters = new ArrayList
		affectedEObjectParameter = parameter
		retrievedParameters += affectedEObjectParameter // affectedEObject is already retrieved
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.generateParameterCheck(parameter)
			it.then = provider.generateInCondition()
		]
	}

	private def XExpression generateInCondition(RoutineTypeProvider provider) {
		val path = findStepToNextParamter
		val fromParameter = parameters.findFirst[it.nodeName == path.startNode]
		val step = path.steps.get(0)
		val toParameter = parameters.findFirst[it.nodeName == step.parameter]
		val feature = step.feature
		if (step instanceof TraverseStepDown) {
			// nextParameter is in fromParameters feature (when containing / being referenced from its feature)
			return provider.generateChildInParent(toParameter, fromParameter, feature)
		} else {
			// nextParameter is fromParameters parent (when containing / referencing it with its feature)
			return provider.generateParentContainsChild(fromParameter, toParameter, feature)
		}
	}

	private def generateParentContainsChild(RoutineTypeProvider provider, MappingParameter child,
		MappingParameter parent, EReference feature) {
		retrievedParameters += parent
		if (feature.containment) {
			val variableName = '''«parent.nodeName»_candidate'''.toString
			val featureMethod = provider.findMetaclassMethod(parent.value.metaclass, 'eContainer')
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				// val parent_candidate =  child.eContainer
				expressions +=
					provider.variableDeclaration(variableName, XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
						feature = featureMethod
						memberCallTarget = child.referenceLocalVariable
					])
				expressions += provider.generateCorrectTypeCheck(parent, variableName)
			]
		} else {
			// todo: check via cross-references to find a possible parent element
			throw new UnsupportedOperationException(
				"Retrieving parent of child not implemented yet for non-containment features!")
		}
	}

	private def generateCorrectTypeCheck(RoutineTypeProvider provider, MappingParameter parameter,
		String variableName) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			// check if not null and instance of correct type
			expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
				^if = provider.andChain(provider.notNull(variableName.referenceLocalVariable),
					XbaseFactory.eINSTANCE.createXInstanceOfExpression => [
						expression = variableName.referenceLocalVariable
						type = provider.findTypeReference(parameter)
					])
				// assign to local variable and continue with the check 
				then = XbaseFactory.eINSTANCE.createXBlockExpression => [
					expressions +=
						provider.variableDeclaration(parameter, XbaseFactory.eINSTANCE.createXCastedExpression => [
							type = provider.findTypeReference(parameter)
							target = variableName.referenceLocalVariable
						])
					expressions += provider.generateRetrievedParameterCheck(parameter)
				]
			]
		]
	}

	private def generateChildInParent(RoutineTypeProvider provider, MappingParameter child, MappingParameter parent,
		EReference feature) {
		retrievedParameters += child
		val featureMethod = provider.findMetaclassMethodGetter(parent.value.metaclass, feature)
		if (feature.many) {
			// filter possible elements and check all of them
			// for(val parameter: parameter.feature)
			// {
			// if( do checks for parameter)
			// {	=> continue here }
			// }
			val variableName = '''«child.nodeName»_candidate'''.toString
			val loopParameter = TypesFactory.eINSTANCE.createJvmFormalParameter => [
				name = variableName
			]
			localVariables.put(variableName, loopParameter)
			XbaseFactory.eINSTANCE.createXForLoopExpression => [
				declaredParam = loopParameter
				forExpression = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					feature = featureMethod
					memberCallTarget = parent.referenceLocalVariable
				]
				eachExpression = provider.generateCorrectTypeCheck(child, variableName)
			]
		} else {
			// just retrieve the value and check 
			provider.generateSimpleRetrievalCheck(child, parent, featureMethod)
		}
	}

	private def generateSimpleRetrievalCheck(RoutineTypeProvider provider, MappingParameter newVariable,
		MappingParameter featureVariable, JvmIdentifiableElement featureMethod) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			// 1) var newVariable = featureVariable.featureMethod 
			expressions +=
				provider.variableDeclaration(newVariable, XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					feature = featureMethod
					memberCallTarget = featureVariable.referenceLocalVariable
				])
			// 2) do the checks with the new variable
			expressions += provider.generateRetrievedParameterCheck(newVariable)
		]
	}

	private def generateRetrievedParameterCheck(RoutineTypeProvider provider, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			it.^if = provider.generateParameterCheck(parameter)
			it.then = provider.generateEndOfInCondition()
		]
	}

	private def generateEndOfInCondition(RoutineTypeProvider provider) {
		if (retrievedParameters.size == parameters.size) {
			// done
			XbaseFactory.eINSTANCE.createXReturnExpression => [
				expression = finishedRetrievingParameters.apply
			]
		} else {
			// retrieve and check the parameters still left
			provider.generateInCondition()
		}
	}

	private def findStepToNextParamter() {
		// find the next parameter to check
		val startParameter = new ArrayList
		startParameter += retrievedParameters.map[nodeName]
		treeTraverser.findStepToNextNode(startParameter)
	}

	private def generateParameterCheck(RoutineTypeProvider provider, MappingParameter parameter) {
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
					it.generateFeatureCondition(provider, parameter.referenceLocalVariable)
				]
			)
		}
	}

	public def skipParameterCheck() {
		parameters.size == 1 && conditions.empty
	}

	private def getParameters() {
		reactionParameters
	}

	private def getConditions() {
		featureConditions
	}

}
