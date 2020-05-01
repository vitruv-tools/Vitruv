package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import java.util.List
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

abstract class AbstractReferenceMappingOperator implements IReferenceMappingOperator {

	protected val extension ReactionExecutionState executionState
	// Note: Only literal operands get passed to the operator.
	protected val List<?> operands

	new(ReactionExecutionState executionState, List<?> operands) {
		checkNotNull(executionState, "executionState is null")
		checkNotNull(operands, "operands is null")
		this.executionState = executionState
		this.operands = operands
	}
}
