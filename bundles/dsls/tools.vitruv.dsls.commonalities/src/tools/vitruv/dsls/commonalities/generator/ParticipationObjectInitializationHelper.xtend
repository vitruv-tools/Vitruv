package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationObjectInitializationHelper extends ReactionsGenerationHelper {

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

	def private Function<TypeProvider, XExpression> getResourceInitializer(ParticipationClass participationClass) {
		if (!participationClass.isForResource) return null
		return [ extension TypeProvider typeProvider |
			val resourceBridge = variable(participationClass.correspondingVariableName)
			participationClass.initNewResourceBridge(resourceBridge, typeProvider)
		]
	}

	def private Function<TypeProvider, XExpression> getCommonalityParticipationClassInitializer(
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
	def getPostInitializers(ParticipationClass participationClass) {
		return (participationClass.participationRelationsInitializers
			+ participationClass.participationConditionsInitializers).toList
	}
}
