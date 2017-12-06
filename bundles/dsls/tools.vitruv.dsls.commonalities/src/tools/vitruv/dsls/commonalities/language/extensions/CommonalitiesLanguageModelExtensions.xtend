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
	CommonalityReferenceMappingExtension,
	CommonalityReferenceExtension
])
@Utility class CommonalitiesLanguageModelExtensions {
}
