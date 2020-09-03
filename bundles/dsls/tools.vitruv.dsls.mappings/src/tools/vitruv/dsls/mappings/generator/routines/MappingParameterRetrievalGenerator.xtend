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
import tools.vitruv.dsls.mappings.generator.conditions.MappingParameterGraphTraverser.TraverseStepUp
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*
import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodUtils.*

class MappingParameterRetrievalGenerator extends AbstractRoutineContentGenerator {
	var MappingParameterGraphTraverser treeTraverser
	var MappingParameter affectedEObjectParameter
	var List<MappingParameter> retrievedParameters
	var Function0<XExpression> finishedRetrievingParameters

	new(AbstractMappingRoutineGenerator parent) {
		this.prepareGenerator(parent)
		this.treeTraverser = new MappingParameterGraphTraverser(featureConditions.toList, parameters.
			map[parameterName], [it.parameterName])
	}
	
	def generate(TypeProvider provider, Function0<XExpression> finishedRetrievingParameters,
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

	private def generateAffectedEObjectCheck(TypeProvider provider, MappingParameter parameter) {
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

	private def generateConditions(TypeProvider provider, MappingParameter parameter) {
		retrievedParameters = new ArrayList
		affectedEObjectParameter = parameter
		retrievedParameters += affectedEObjectParameter // affectedEObject is already retrieved
		var XExpression expression
		if(parameters.size == 1){
			//no additional retrieval needed, just check conditions for this parameter
			expression = finishedRetrievingParameters.apply
		}
		else{
			//retrieve remaining parameters
			expression = provider.generateInCondition
		}
		provider.generateRetrievedParameterCheck(parameter, expression)
	}

	private def XExpression generateInCondition(TypeProvider provider) {
		val path = findStepToNextParamter
		val fromParameter = parameters.findFirst[it.parameterName == path.startNode]
		val step = path.steps.get(0)
		val toParameter = parameters.findFirst[it.parameterName == step.parameter]
		val feature = step.feature
		step.doNextTraverseStep(provider, fromParameter , toParameter, feature)
	}
	
	private def dispatch doNextTraverseStep(TraverseStepDown step, TypeProvider provider, MappingParameter from, MappingParameter to, EReference feature){
		// nextParameter is in fromParameters feature (when containing / being referenced from its feature)
		provider.generateChildInParent(to, from, feature)		
	}
	
	private def dispatch doNextTraverseStep(TraverseStepUp step, TypeProvider provider, MappingParameter from, MappingParameter to, EReference feature){
		// nextParameter is fromParameters parent (when containing / referencing it with its feature)
		provider.generateParentContainsChild(from, to, feature)	
	}

	private def generateParentContainsChild(TypeProvider provider, MappingParameter child,
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
						explicitOperationCall = true
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

	private def generateChildInParent(TypeProvider provider, MappingParameter child, MappingParameter parent,
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

	private def generateMultipleFeatureRetrieval(TypeProvider provider, MappingParameter featureParameter,
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

	private def generateSimpleFeatureRetrieval(TypeProvider provider, MappingParameter featureParameter,
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

	private def generateCorrectTypeCheck(TypeProvider provider, MappingParameter parameter, String variableName,
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
					val retrieveParameter = provider.generateRetrievedParameterCheck(parameter,
						provider.generateEndOfInCondition())
					if (extraCondition !== null) {
						// perform the check before normal retrieved parameter check
						expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
							^if = extraCondition.apply
							then = retrieveParameter
						]
					} else {
						expressions += retrieveParameter
					}
				]
			]
		]
	}

	private def generateRetrievedParameterCheck(TypeProvider provider, MappingParameter parameter,
		XExpression thenBlock) {
		val check = provider.generateParameterCheck(parameter)
		if (check !== null) {
			// check the conditions for this parameter
			XbaseFactory.eINSTANCE.createXIfExpression => [
				it.^if = check
				it.then = thenBlock
			]
		} else {
			// no conditions to check, just continue
			thenBlock
		}
	}

	private def generateEndOfInCondition(TypeProvider provider) {
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

	private def generateParameterCheck(TypeProvider provider, MappingParameter parameter) {
		// find all conditions to this parameter (we have to filter out InValueConditions)
		val checks = conditions.filter [
			feasibleForParameter(parameter) && (it instanceof InValueConditionGenerator == false)
		]
		if (checks.empty) {
			// no conditions for that element
			return null
		} else {
			provider.andChain(
				checks.map [
					it.generateFeatureCondition(provider, parameter.referenceLocalVariable)
				]
			)
		}
	}

	def skipParameterCheck() {
		parameters.size == 1 && conditions.empty
	}

	private def getParameters() {
		reactionParameters
	}

	private def getConditions() {
		featureConditions
	}

}
