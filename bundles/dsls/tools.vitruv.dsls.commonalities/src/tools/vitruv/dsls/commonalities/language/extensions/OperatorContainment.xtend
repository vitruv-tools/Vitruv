package tools.vitruv.dsls.commonalities.language.extensions

import java.util.List
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperand
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperator

@Data
class OperatorContainment extends Containment {

	ReferenceMappingOperator operator
	List<ReferenceMappingOperand> operands
}
