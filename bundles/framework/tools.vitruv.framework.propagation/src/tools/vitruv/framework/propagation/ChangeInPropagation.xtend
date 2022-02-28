package tools.vitruv.framework.propagation

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.description.TransactionalChange
import org.eclipse.xtend.lib.annotations.Accessors

@FinalFieldsConstructor
class ChangeInPropagation {
	@Accessors(PUBLIC_GETTER)
	val TransactionalChange change
	@Accessors(PUBLIC_GETTER)
	val Boolean shouldBeFurtherPropagated
}
