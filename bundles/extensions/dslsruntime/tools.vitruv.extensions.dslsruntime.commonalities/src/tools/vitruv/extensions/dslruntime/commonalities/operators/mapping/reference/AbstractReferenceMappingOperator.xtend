package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

/**
 * Note: Attribute operands are not passed to the constructor of the operator.
 */
abstract class AbstractReferenceMappingOperator implements IReferenceMappingOperator {

	protected val extension ReactionExecutionState executionState

	new(ReactionExecutionState executionState) {
		checkNotNull(executionState, "executionState is null")
		this.executionState = executionState
	}
}
