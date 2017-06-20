package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVUsersession
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.emfstore.VVServer

@Data
class VVUsersessionImpl implements VVUsersession {
	String name
	VVServer server
}
