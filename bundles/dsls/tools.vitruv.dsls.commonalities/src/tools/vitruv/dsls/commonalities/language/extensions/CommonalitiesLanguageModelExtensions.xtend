package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.StaticDelegate
import edu.kit.ipd.sdq.activextendannotations.Utility

@StaticDelegate(#[
	tools.vitruv.dsls.commonalities.language.extensions.AttributeMappingExtension,
	AttributeMappingOperandExtension,
	CommonalityExtension,
	CommonalitiesLanguageElementExtension,
	CommonalityAttributeMappingExtension,
	CommonalityReferenceExtension,
	CommonalityReferenceMappingExtension,
	OperandExtension,
	OperatorAttributeMappingExtension,
	OperatorReferenceMappingExtension,
	ParticipationExtension,
	ParticipationPartExtension,
	ParticipationClassExtension,
	ParticipationRelationExtension,
	ParticipationRelationOperatorExtension,
	ParticipationAttributeExtension,
	ParticipationConditionExtension,
	ParticipationConditionOperatorExtension,
	ParticipationConditionOperandExtension,
	ReferenceMappingOperandExtension,
	tools.vitruv.dsls.commonalities.language.extensions.ReferenceMappingExtension
])
@Utility
class CommonalitiesLanguageModelExtensions {
}
