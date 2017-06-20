package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVLocalProject
import tools.vitruv.framework.versioning.emfstore.VVServer
import tools.vitruv.framework.versioning.emfstore.VVRemoteProject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.BasicEList

class VVLocalProjectImpl extends VVProjectImpl implements VVLocalProject {
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VVRemoteProject remoteProject

	@Accessors(PUBLIC_GETTER)
	EList<EObject> modelElements

	new() {
		modelElements = new BasicEList
	}

	override commit(String s) {
		
	}

	override update() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override delete() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override shareProject(VVServer server) {
		val remoteProjectCopy = VVFactory::instance.createRemoteProject
		remoteProjectCopy.localProject = this
		remoteProjectCopy.server = server
		remoteProject = remoteProjectCopy
		return remoteProjectCopy
	}

}
