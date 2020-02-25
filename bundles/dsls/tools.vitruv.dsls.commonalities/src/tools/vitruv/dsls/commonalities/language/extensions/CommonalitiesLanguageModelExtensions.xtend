package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.StaticDelegate
import edu.kit.ipd.sdq.activextendannotations.Utility

@StaticDelegate(#[
	CommonalityExtension,
	CommonalitiesLanguageElementExtension,
	CommonalityAttributeMappingExtension,
	ParticipationPartExtension,
	ParticipationClassExtension,
	ParticipationExtension,
	ParticipationAttributeExtension,
	ParticipationConditionExtension,
	CommonalityReferenceMappingExtension,
	CommonalityReferenceExtension
])
@Utility
class CommonalitiesLanguageModelExtensions {
}
