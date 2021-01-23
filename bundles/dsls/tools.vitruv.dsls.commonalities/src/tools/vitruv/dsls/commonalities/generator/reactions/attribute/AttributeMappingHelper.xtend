package tools.vitruv.dsls.commonalities.generator.reactions.attribute

import com.google.inject.Inject
import java.util.Map
import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.AttributeMappingOperatorHelper.AttributeMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsHelper
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.OperatorAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.SimpleAttributeMapping
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

// TODO For multi-valued attributes we always convert all values on the source side to some result value on the target
// side (which is possibly multi-valued as well). For simple mappings between two multi-valued attributes it would be
// sufficient to only convert individual values whenever they are added or removed. Operators would need to be able to
// indicate whether they support mapping individual values (eg. by implementing an optional interface which provides
// additional methods for the conversion of individual values).
class AttributeMappingHelper extends ReactionsGenerationHelper {

	@Inject extension AttributeMappingOperatorHelper attributeMappingOperatorHelper
	@Inject extension ParticipationObjectsHelper participationObjectsHelper

	package new() {
	}

	/*
	 * Read: From participation to commonality
	 */

	def getRelevantReadMappings(Participation participation) {
		participation.declaringCommonality.attributes.flatMap [it.getRelevantReadMappings(participation)]
	}

	def getRelevantReadMappings(CommonalityAttribute attribute, Participation participation) {
		attribute.mappings.filter [isRead && it.participation == participation]
	}

	def Function<ParticipationClass, XExpression> participationClassToNullableObject(
		Map<ParticipationClass, XVariableDeclaration> participationObjectVars) {
		[ participationClass |
			participationObjectVars.get(participationClass).featureCall
		]
	}

	def dispatch XExpression applyReadMapping(SimpleAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		// If the participation object is available for the current participation context, apply the attribute mapping,
		// otherwise the mapping is skipped:
		val participationClass = mapping.attribute.participationClass
		val participationClassToObject = [operatorContext.getParticipationObject(it)]
		val typeProvider = operatorContext.typeProvider
		return ifParticipationObjectsAvailable(typeProvider, #[participationClass], participationClassToObject) [
			mapping.readAttribute(operatorContext)
		]
	}

	def dispatch XExpression applyReadMapping(OperatorAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		// If all participation objects required by the operator are available for the current participation context,
		// apply the attribute mapping, otherwise the mapping is skipped:
		// Note: The checked operands also include the (optional) participation attribute operand.
		val participationClasses = mapping.involvedParticipationClasses
		val participationClassToObject = [operatorContext.getParticipationObject(it)]
		val typeProvider = operatorContext.typeProvider
		return ifParticipationObjectsAvailable(typeProvider, participationClasses, participationClassToObject) [
			mapping.readAttribute(operatorContext)
		]
	}

	private def dispatch XExpression readAttribute(SimpleAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		// Get the participation attribute value:
		val participationAttribute = mapping.attribute
		val participationAttributeValue = participationAttribute.getParticipationAttributeValue(operatorContext)

		// Apply the attribute value:
		// Assert: The commonality and participation attributes are assignment compatible.
		val typeProvider = operatorContext.typeProvider
		val intermediate = operatorContext.intermediate
		return replaceFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature,
			participationAttributeValue)
	}

	private def dispatch XExpression readAttribute(OperatorAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Get the participation attribute value:
			val participationAttribute = mapping.participationAttribute // can be null
			val participationAttributeValue = participationAttribute.getParticipationAttributeValue(operatorContext)

			// Invoke the operator:
			val newValueVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = 'newFeatureValue'
				right = mapping.applyTowardsCommonality(participationAttributeValue, operatorContext)
			]
			expressions += newValueVar

			// Apply the attribute value:
			// Assert: The operator's result value is assignment compatible with the commonality attribute.
			val typeProvider = operatorContext.typeProvider
			val intermediate = operatorContext.intermediate
			expressions += replaceFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature,
				newValueVar.featureCall)
		]
	}

	private def XExpression getParticipationAttributeValue(ParticipationAttribute participationAttribute,
		AttributeMappingOperatorContext operatorContext) {
		// The participation attribute is optional (may be null):
		if (participationAttribute === null) {
			return nullLiteral
		}

		val participationClass = participationAttribute.participationClass
		val participationObject = operatorContext.getParticipationObject(participationClass)
		val typeProvider = operatorContext.typeProvider
		return retrieveFeatureValue(typeProvider, participationObject, participationAttribute.correspondingEFeature)
	}

	/*
	 * Write: From commonality to participation
	 */

	def getRelevantWriteMappings(Participation participation) {
		participation.declaringCommonality.attributes.flatMap [it.getRelevantWriteMappings(participation)]
	}

	def getRelevantWriteMappings(CommonalityAttribute attribute, Participation participation) {
		attribute.mappings.filter [isWrite && it.participation == participation]
			// We ignore all mappings which don't specify a participation attribute:
			.filter[participationAttribute !== null]
	}

	def Function<ParticipationClass, XExpression> participationClassToOptionalObject(
		extension TypeProvider typeProvider) {
		return [ participationClass |
			val participationObjectVar = variable(participationClass.correspondingVariableName)
			if (participationClass.isRootClass) {
				return participationObjectVar.optionalGetOrNull(typeProvider)
			} else {
				return participationObjectVar
			}
		]
	}

	def dispatch XExpression applyWriteMapping(SimpleAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		// If the participation object is available for the current participation context, apply the attribute mapping,
		// otherwise the mapping is skipped:
		val participationClass = mapping.attribute.participationClass
		val participationClassToObject = [operatorContext.getParticipationObject(it)]
		val typeProvider = operatorContext.typeProvider
		return ifParticipationObjectsAvailable(typeProvider, #[participationClass], participationClassToObject) [
			mapping.writeAttribute(operatorContext)
		]
	}

	def dispatch XExpression applyWriteMapping(OperatorAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		// Assert: The mapping is 'relevant'. Otherwise it is supposed to be skipped.
		assertTrue(mapping.participationAttribute !== null)
		// If all participation objects required by the operator are available for the current participation context,
		// apply the attribute mapping, otherwise the mapping is skipped:
		// Note: The checked operands also include the (optional) participation attribute operand.
		val participationClasses = mapping.involvedParticipationClasses
		val participationClassToObject = [operatorContext.getParticipationObject(it)]
		val typeProvider = operatorContext.typeProvider
		return ifParticipationObjectsAvailable(typeProvider, participationClasses, participationClassToObject) [
			mapping.writeAttribute(operatorContext)
		]
	}

	private def dispatch XExpression writeAttribute(SimpleAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		// Get the commonality attribute value:
		val intermediate = operatorContext.intermediate
		val commonalityAttributeValue = mapping.getCommonalityAttributeValue(typeProvider, intermediate)

		// Apply the attribute value:
		// Assert: The commonality and participation attributes are assignment compatible.
		return mapping.setParticipationAttributeValue(operatorContext, commonalityAttributeValue)
	}

	private def dispatch XExpression writeAttribute(OperatorAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext) {
		val extension typeProvider = operatorContext.typeProvider
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Get the commonality attribute value:
			val intermediate = operatorContext.intermediate
			val commonalityAttributeValue = mapping.getCommonalityAttributeValue(typeProvider, intermediate)

			// Invoke the operator:
			val newValueVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = 'newFeatureValue'
				right = mapping.applyTowardsParticipation(commonalityAttributeValue, operatorContext)
			]
			expressions += newValueVar

			// Apply the attribute value:
			// Assert: The operator's result value is assignment compatible with the participation attribute.
			expressions += mapping.setParticipationAttributeValue(operatorContext, newValueVar.featureCall)
		]
	}

	private def getCommonalityAttributeValue(CommonalityAttributeMapping mapping,
		extension TypeProvider typeProvider, XExpression intermediate) {
		return retrieveFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature)
	}

	private def setParticipationAttributeValue(CommonalityAttributeMapping mapping,
		AttributeMappingOperatorContext operatorContext, XExpression attributeValue) {
		assertTrue(mapping.participationAttribute !== null)
		assertTrue(mapping.participationEFeature !== null)
		val participationClass = mapping.participationAttribute.participationClass
		val participationObject = operatorContext.getParticipationObject(participationClass)
		val typeProvider = operatorContext.typeProvider
		return replaceFeatureValue(typeProvider, participationObject, mapping.participationEFeature, attributeValue)
	}
}
