package tools.vitruv.framework.propagation

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.description.TransactionalChange
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Encapsulates a change that emerged from change propagation together
 * with the information whether this change shall be further propagated,
 * i.e., whether it should be used as an input for further change 
 * propagation specifications or not.
 */
@FinalFieldsConstructor
class ChangeInPropagation {
	@Accessors(PUBLIC_GETTER)
	val TransactionalChange change
	@Accessors(PUBLIC_GETTER)
	val Boolean shouldBeFurtherPropagated
}
