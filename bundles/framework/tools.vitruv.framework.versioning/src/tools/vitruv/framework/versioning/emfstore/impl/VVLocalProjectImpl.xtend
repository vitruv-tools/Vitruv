package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVLocalProject
import tools.vitruv.framework.versioning.emfstore.VVServer
import tools.vitruv.framework.versioning.emfstore.VVRemoteProject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.BasicEList
import tools.vitruv.framework.versioning.commit.CommitFactory
import java.util.Date
import tools.vitruv.framework.versioning.emfstore.VVModelElementId
import tools.vitruv.framework.versioning.emfstore.VVModelElementIdUtil

class VVLocalProjectImpl extends VVProjectImpl implements VVLocalProject {

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VVRemoteProject remoteProject

	@Accessors(PUBLIC_GETTER)
	EList<EObject> modelElements

	new() {
		modelElements = new BasicEList
	}

	override commit(String s) {
		val commit = CommitFactory::eINSTANCE.createSimpleCommit
		val commitMessage = CommitFactory::eINSTANCE.createCommitMessage
		commitMessage.message = s
		commitMessage.date = new Date
		commit.commitmessage = commitMessage
		val lastCommit = commits.last
		remoteProject.pushCommit(lastCommit, commit)

		commits += commit
		return commit
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

	override getModelElementId(EObject modelElement) {
		VVModelElementIdUtil::instance.getModelElementId(modelElement)
	}

	override getModelElement(VVModelElementId modelElementId) {
		modelElements.findFirst[VVModelElementIdUtil::instance.getModelElementId(it).id == modelElementId.id]
	}

}
