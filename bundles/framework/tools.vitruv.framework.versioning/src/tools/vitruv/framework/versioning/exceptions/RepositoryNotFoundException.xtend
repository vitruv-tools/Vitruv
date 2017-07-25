package tools.vitruv.framework.versioning.exceptions

import java.lang.Exception
import org.eclipse.xtend.lib.annotations.Accessors

class RepositoryNotFoundException extends Exception {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
}
