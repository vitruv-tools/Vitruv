package tools.vitruv.framework.versioning.repository.impl

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.repository.Repository
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.change.copy.ChangeCopyFactory
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.echange.EChange
import java.util.ArrayList

class RepositoryImpl implements Repository {
	static extension CommitFactory = CommitFactory::instance
	static extension ChangeCopyFactory = ChangeCopyFactory::instance
	@Accessors(PUBLIC_GETTER)
	val List<Commit> commits
	@Accessors(PUBLIC_GETTER)
	val List<Branch> branches
	@Accessors(PUBLIC_GETTER)
	InitialCommit initialCommit
	@Accessors(PUBLIC_GETTER)
	Commit head

	new() {
		branches = newArrayList
		initialCommit = createInitialCommit
		commits = newArrayList(initialCommit)
		head = initialCommit
	}

	override add(Commit c) {
		addCommit(c)
	}

	private dispatch def addCommit(Commit c) {
		throw new UnsupportedOperationException
	}

	private dispatch def addCommit(SimpleCommit c) {
		val lastCommit = commits.last
		val parent = c.parent
		if (lastCommit.identifier !== parent.identifier)
			throw new CommitNotExceptedException
		val resourceSet = new ResourceSetImpl
		val List<EChange> echanges = new ArrayList
		allEChanges.forEach [
			echanges += it
		]
		echanges.forEach[resolveAfter(resourceSet)]
		
		commits += c
		head = c
	}

	private def getAllEChanges() {
		commits.map[changes].flatten
	}

	override getCopiedEChanges(VURI vuri, Pair<String, String> pair) {
		val eChangeCopier = createEChangeCopier(#[pair])
		val copiedChanges = allEChanges.map [
			eChangeCopier.copyEChanges(it, vuri)
		].toList
		return copiedChanges

	}

}
