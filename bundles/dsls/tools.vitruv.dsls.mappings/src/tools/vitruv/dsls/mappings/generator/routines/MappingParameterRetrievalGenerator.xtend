package tools.vitruv.dsls.mappings.generator.routines

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
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

	new(AbstractMappingRoutineGenerator parent) {
		this.prepareGenerator(parent)
		this.treeTraverser = new MappingParameterGraphTraverser(featureConditions.toList, parameters.
			map[parameterName], [it.parameterName])
	}

	public def generate(RoutineTypeProvider provider, Function0<XExpression> finishedRetrievingParameters,
		Function0<XExpression> failedToRetrieveParameters) {
		this.finishedRetrievingParameters = finishedRetrievingParameters
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
		val fromParameter = parameters.findFirst[it.parameterName == path.startNode]
		val step = path.steps.get(0)
		val toParameter = parameters.findFirst[it.parameterName == step.parameter]
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
			val featureMethod = provider.findMetaclassMethod(parent.value.metaclass, 'eContainer')
			provider.generateSimpleFeatureRetrieval(child, parent, featureMethod)
		} else {
			// check via cross-references to find a possible parent element
			val featureMethod = provider.findMetaclassMethod(parent.value.metaclass, 'eCrossReferences')
			val parentFeature = provider.findMetaclassMethodGetter(parent.value.metaclass, feature)
			provider.generateMultipleFeatureRetrieval(child, parent, featureMethod, [
				// additional check on retrieved variable: check if parent has the child in the correct feature
				val featureCall = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					feature = parentFeature
					memberCallTarget = parent.referenceLocalVariable
				]
				if (feature.many) {
					// check with    parent.feature.contains(child)
					XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
						memberCallTarget = featureCall
						feature = provider.listContains
						memberCallArguments += child.referenceLocalVariable
					]
				} else {
					// check with    child == parent.feature
					XbaseFactory.eINSTANCE.createXBinaryOperation => [
						feature = provider.tripleEquals
						leftOperand = child.referenceLocalVariable
						rightOperand = featureCall
					]
				}
			])
		}
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
			provider.generateMultipleFeatureRetrieval(parent, child, featureMethod, null)
		} else {
			// just retrieve the value and check for correct type and not null
			provider.generateSimpleFeatureRetrieval(parent, child, featureMethod)
		}
	}

	private def generateMultipleFeatureRetrieval(RoutineTypeProvider provider, MappingParameter featureParameter,
		MappingParameter targetParameter, JvmIdentifiableElement featureMethod, Function0<XExpression> extraCondition) {
		val variableName = '''«targetParameter.parameterName»_candidate'''.toString
		val loopParameter = TypesFactory.eINSTANCE.createJvmFormalParameter => [
			name = variableName
		]
		localVariables.put(variableName, loopParameter)
		XbaseFactory.eINSTANCE.createXForLoopExpression => [
			declaredParam = loopParameter
			forExpression = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				feature = featureMethod
				memberCallTarget = featureParameter.referenceLocalVariable
			]
			eachExpression = provider.generateCorrectTypeCheck(targetParameter, variableName, extraCondition)
		]
	}

	private def generateSimpleFeatureRetrieval(RoutineTypeProvider provider, MappingParameter featureParameter,
		MappingParameter targetParameter, JvmIdentifiableElement featureMethod) {
		val variableName = '''«targetParameter.parameterName»_candidate'''.toString
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions +=
				provider.variableDeclaration(variableName, XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					feature = featureMethod
					memberCallTarget = featureParameter.referenceLocalVariable
				])
			expressions += provider.generateCorrectTypeCheck(targetParameter, variableName, null)
		]
	}

	private def generateCorrectTypeCheck(RoutineTypeProvider provider, MappingParameter parameter, String variableName,
		Function0<XExpression> extraCondition) {
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
					if (extraCondition !== null) {
						// perform the check before normal retrieved parameter check
						expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
							^if = extraCondition.apply
							then = provider.generateRetrievedParameterCheck(parameter)
						]
					} else {
						expressions += provider.generateRetrievedParameterCheck(parameter)
					}
				]
			]
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
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions += finishedRetrievingParameters.apply
				expressions += XbaseFactory.eINSTANCE.createXReturnExpression
			]
		} else {
			// retrieve and check the parameters still left
			provider.generateInCondition()
		}
	}

	private def findStepToNextParamter() {
		// find the next parameter to check
		val startParameter = new ArrayList
		startParameter += retrievedParameters.map[parameterName]
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
