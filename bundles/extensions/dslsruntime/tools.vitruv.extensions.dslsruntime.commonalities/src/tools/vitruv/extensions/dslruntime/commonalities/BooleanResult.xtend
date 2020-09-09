package tools.vitruv.extensions.dslruntime.commonalities

import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.ToString

// TODO This is non-generic, because there seems to be a problem when we try to
// generate generic routine input parameters in the Reactions language
@ToString
@EqualsHashCode
class BooleanResult {

	var boolean value = false

	new() {
	}

	def getValue() {
		return value
	}

	def setValue(boolean value) {
		this.value = value
	}
}
