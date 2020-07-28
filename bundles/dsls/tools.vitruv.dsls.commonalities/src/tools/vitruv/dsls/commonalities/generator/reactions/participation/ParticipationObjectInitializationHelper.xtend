package tools.vitruv.dsls.commonalities.generator.reactions.participation

import com.google.inject.Inject
import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.condition.ParticipationConditionInitializationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.relation.ParticipationRelationInitializationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.resource.ResourceBridgeHelper
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.IntermediateModelHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ParticipationObjectInitializationHelper extends ReactionsGenerationHelper {

	@Inject extension ResourceBridgeHelper resourceBridgeHelper
	@Inject extension ParticipationRelationInitializationHelper participationRelationInitializationHelper
	@Inject extension ParticipationConditionInitializationHelper participationConditionInitializationHelper

	package new() {
	}

	def toBlockExpression(Iterable<Function<TypeProvider, XExpression>> expressionBuilders,
		TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressionBuilders.map[apply(typeProvider)]
		]
	}

	/**
	 * Early initializations that only affect the specific participation object
	 * itself.
	 */
	def getInitializers(ParticipationClass participationClass) {
		return #[
			participationClass.resourceInitializer,
			participationClass.commonalityParticipationClassInitializer
		].filterNull
	}

	private def Function<TypeProvider, XExpression> getResourceInitializer(ParticipationClass participationClass) {
		if (!participationClass.isForResource) return null
		return [ extension TypeProvider typeProvider |
			val resourceBridge = variable(participationClass.correspondingVariableName)
			participationClass.initNewResourceBridge(resourceBridge, typeProvider)
		]
	}

	private def Function<TypeProvider, XExpression> getCommonalityParticipationClassInitializer(
		ParticipationClass participationClass) {
		if (!participationClass.participation.isCommonalityParticipation) return null
		return [ extension TypeProvider typeProvider |
			claimIntermediateId(typeProvider, variable(participationClass.correspondingVariableName))
		]
	}

	/**
	 * Initializations that need to happen after all participation objects have
	 * been created.
	 * <p>
	 * For example, this includes initializations done by operators since they
	 * may want to reference other participation objects.
	 */
	def getPostInitializers(ParticipationContext participationContext, ContextClass contextClass) {
		return (participationContext.getParticipationRelationsInitializers(contextClass)
			+ participationContext.getParticipationConditionsInitializers(contextClass)).toList
	}
}
