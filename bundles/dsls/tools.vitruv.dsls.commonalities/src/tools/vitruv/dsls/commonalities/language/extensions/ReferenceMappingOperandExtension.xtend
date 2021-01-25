package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperand

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ReferenceMappingOperandExtension {
	static def boolean isInReferenceMappingContext(ReferenceMappingOperand operand) {
		operand.referenceMapping !== null
	}

	// Returns null if not in reference mapping context:
	static def OperatorReferenceMapping getReferenceMapping(ReferenceMappingOperand operand) {
		operand.getOptionalDirectEContainer(OperatorReferenceMapping)
	}
}
