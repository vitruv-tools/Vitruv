package tools.vitruv.framework.versioning.exceptions

import tools.vitruv.framework.versioning.exceptions.BranchNotFoundException
import org.eclipse.xtend.lib.annotations.Accessors

class LocalBranchNotFoundException extends BranchNotFoundException {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
}
