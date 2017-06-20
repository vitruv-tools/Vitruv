package tools.vitruv.framework.versioning.emfstore.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.emfstore.VVLocalProject
import tools.vitruv.framework.versioning.emfstore.VVRemoteProject
import tools.vitruv.framework.versioning.emfstore.VVServer
import tools.vitruv.framework.versioning.emfstore.VVFactory

class VVRemoteProjectImpl extends VVProjectImpl implements VVRemoteProject {
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VVLocalProject localProject

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VVServer server

	override checkout(String name) {
		val localProjectCopy = VVFactory::instance.createLocalProject
		localProjectCopy.name = name
		localProjectCopy.remoteProject = this
		return localProjectCopy
	}

	override delete() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
