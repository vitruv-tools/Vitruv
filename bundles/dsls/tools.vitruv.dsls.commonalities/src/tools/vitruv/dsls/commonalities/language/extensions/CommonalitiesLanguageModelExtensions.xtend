package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.StaticDelegate
import edu.kit.ipd.sdq.activextendannotations.Utility

@StaticDelegate(#[
	CommonalityExtension,
	CommonalityFileExtension,
	CommonalitiesLanguageElementExtension,
	CommonalityAttributeMappingExtension,
	CommonalityReferenceMappingExtension,
	OperatorImportExtension,
	ParticipationExtension,
	ParticipationPartExtension,
	ParticipationClassExtension,
	ParticipationRelationExtension,
	ParticipationRelationOperatorExtension,
	ParticipationAttributeExtension,
	ParticipationConditionExtension,
	ParticipationConditionOperatorExtension,
	ParticipationConditionOperandExtension
])
@Utility
class CommonalitiesLanguageModelExtensions {
}
