package tools.vitruv.framework.versioning.emfstore.impl

import java.util.ArrayList
import java.util.Date
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVLocalProject
import tools.vitruv.framework.versioning.emfstore.VVModelElementId
import tools.vitruv.framework.versioning.emfstore.VVModelElementIdUtil
import tools.vitruv.framework.versioning.emfstore.VVRemoteProject
import tools.vitruv.framework.versioning.emfstore.VVServer

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

	override update() {
		update([c| ])
	}

	override update(Function1<List<Conflict>, Boolean> conflictCallback) {
		val List<Conflict> conflicts = new ArrayList
		conflictCallback.apply(conflicts)
	}

}
