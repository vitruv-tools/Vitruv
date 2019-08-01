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
		val variableName = '''«parent.parameterName»_candidate'''.toString
		if (feature.containment) {
			val featureMethod = provider.findMetaclassMethod(parent.value.metaclass, 'eContainer')
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				// val parent_candidate =  child.eContainer
				expressions +=
					provider.variableDeclaration(variableName, XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
						feature = featureMethod
						memberCallTarget = child.referenceLocalVariable
					])
				expressions += provider.generateCorrectTypeCheck(parent, variableName, null)
			]
		} else {
			// check via cross-references to find a possible parent element
			val featureMethod = provider.findMetaclassMethod(parent.value.metaclass, 'eCrossReferences')
			val parentFeature = provider.findMetaclassMethodGetter(parent.value.metaclass, feature)
			val loopParameter = TypesFactory.eINSTANCE.createJvmFormalParameter => [
				name = variableName
			]
			localVariables.put(variableName, loopParameter)
			XbaseFactory.eINSTANCE.createXForLoopExpression => [
				declaredParam = loopParameter
				forExpression = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					feature = featureMethod
					memberCallTarget = child.referenceLocalVariable
				]
				eachExpression = provider.generateCorrectTypeCheck(parent, variableName, [
					// addditional check on retrieved variable: check if parent has the chield in the correct feature
					XbaseFactory.eINSTANCE.createXBinaryOperation => [
						feature = provider.tripleEquals
						leftOperand = child.referenceLocalVariable
						rightOperand = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
							feature = parentFeature
							memberCallTarget = parent.referenceLocalVariable
						]
					]
				])
			]
//			throw new UnsupportedOperationException(
//				'''Retrieving parent of child not implemented yet for non-containment features!
//				(child: «child.nodeName» in parent: «parent.nodeName» feature: «feature.name» )''')
		}
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

	private def generateChildInParent(RoutineTypeProvider provider, MappingParameter child, MappingParameter parent,
		EReference feature) {
		retrievedParameters += child
		val featureMethod = provider.findMetaclassMethodGetter(parent.value.metaclass, feature)
		val variableName = '''«child.parameterName»_candidate'''.toString
		if (feature.many) {
			// filter possible elements and check all of them
			// for(val parameter: parameter.feature)
			// {
			// if( do checks for parameter)
			// {	=> continue here }
			// }
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
				eachExpression = provider.generateCorrectTypeCheck(child, variableName, null)
			]
		} else {
			// just retrieve the value and check for correct type and not null
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions +=
					provider.variableDeclaration(variableName, XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
						feature = featureMethod
						memberCallTarget = parent.referenceLocalVariable
					])
				expressions += provider.generateCorrectTypeCheck(child, variableName, null)
			]
//			provider.generateSimpleRetrievalCheck(child, parent, featureMethod)
		}
	}

//	private def generateSimpleRetrievalCheck(RoutineTypeProvider provider, MappingParameter newVariable,
//		MappingParameter featureVariable, JvmIdentifiableElement featureMethod) {
//		XbaseFactory.eINSTANCE.createXBlockExpression => [
//			// 1) var newVariable = featureVariable.featureMethod 
//			expressions +=
//				provider.variableDeclaration(newVariable, XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
//					feature = featureMethod
//					memberCallTarget = featureVariable.referenceLocalVariable
//				])
//			// 2) do the checks with the new variable
//			expressions += provider.generateRetrievedParameterCheck(newVariable)
//		]
//	}
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
