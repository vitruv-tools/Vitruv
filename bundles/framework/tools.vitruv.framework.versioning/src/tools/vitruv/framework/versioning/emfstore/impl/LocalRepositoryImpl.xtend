package tools.vitruv.framework.versioning.emfstore.impl

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVModelElementId
import tools.vitruv.framework.versioning.emfstore.VVModelElementIdUtil
import tools.vitruv.framework.versioning.emfstore.VVRemoteProject
import tools.vitruv.framework.versioning.emfstore.VVServer
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class LocalRepositoryImpl extends AbstractRepositoryImpl implements LocalRepository {
	static extension ChangeCloner = new ChangeCloner
	static extension URIRemapper = URIRemapper::instance
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Author author

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VVRemoteProject remoteProject

	@Accessors(PUBLIC_GETTER)
	EList<EObject> modelElements

	new() {
		super()
	}

	override commit(String s, List<PropagatedChange> changes) {
		val lastCommit = commits.last
		val commit = createSimpleCommit(changes, s, author, lastCommit)
		commits += commit
		head = commit
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

	override checkout(VirtualModel virtualModel, VURI vuri) {
		val changeMatches = commits.map[changes].flatten
		val originalChanges = changeMatches.map[originalChange]
		val myVURI = originalChanges.get(0).URI
		val processTargetEChange = createEChangeRemapFunction(myVURI, vuri)
		val newChanges = originalChanges.map [
			val eChanges = EChanges.map[cloneEChange(it)]
			eChanges.forEach[processTargetEChange.accept(it)]
			val newChange = VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(eChanges, vuri)
			return newChange
		]
		newChanges.forEach [
			virtualModel.propagateChange(it)
		]
	}

}
