package tools.vitruv.framework.versioning.emfstore.impl

import java.util.ArrayList
import java.util.List
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.branch.impl.LocalBranchImpl
import tools.vitruv.framework.versioning.branch.impl.RemoteBranchImpl
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.exceptions.LocalBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.RemoteBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.RepositoryNotFoundException

class LocalRepositoryImpl extends AbstractRepositoryImpl implements LocalRepository {
	static extension ChangeCloner = new ChangeCloner
	static extension URIRemapper = URIRemapper::instance
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Author author

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	RemoteRepository remoteProject

	@Accessors(PUBLIC_GETTER)
	val Set<LocalBranch> localBranches

	@Accessors(PUBLIC_GETTER)
	val Set<RemoteBranch> remoteBranches

	val Set<RemoteRepository> remoteRepositories

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	LocalBranch currentBranch

	new() {
		super()
		currentBranch = masterBranch as LocalBranch
		remoteRepositories = newHashSet
		localBranches = newHashSet(currentBranch)
		remoteBranches = newHashSet
	}

	override getCommits() {
		currentBranch.commits
	}

	override addCommit(Commit c) {
		addCommit(c, currentBranch)
	}

	override commit(String s, VirtualModel virtualModel, VURI vuri) {
		val changeMatches = virtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri)
		val lastChangeId = changeMatches.get(0).id
		val commit = commit(s, changeMatches)
		virtualModel.lastPropagatedChangeId = lastChangeId
		return commit
	}

	override commit(String s, List<PropagatedChange> changes) {
		val lastCommit = commits.last
		val commit = createSimpleCommit(changes, s, author, lastCommit.identifier)
		addCommit(commit)
		head = commit
		return commit
	}

	override delete() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
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

	override addRemoteRepository(RemoteRepository remoteRepository) {
		if (null !== remoteRepository) {
			remoteRepositories += remoteRepository
		}
	}

	override push() throws CommitNotExceptedException {
		push(currentBranch)
	}

	override addOrigin(LocalBranch branch, RemoteRepository remoteRepository) {
		if (!localBranches.exists[it === branch])
			throw new LocalBranchNotFoundException
		if (!remoteRepositories.exists[it === remoteRepository])
			throw new RepositoryNotFoundException
		val branchName = branch.name
		val remoteBranch = remoteRepository.branches.findFirst[name == branchName]
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val newBranch = createRemoteBranch(remoteBranch.name, remoteRepository)
		branch.remoteBranch = newBranch
	}

	override createLocalBranch(String currentName) {
		val newBranch = new LocalBranchImpl(currentName)
		localBranches += newBranch
		currentBranch = newBranch
		return newBranch
	}

	override createRemoteBranch(String name, RemoteRepository remoteRepository) {
		val remoteName = remoteRepository.name
		val newBranchName = '''«remoteName»/«name»'''
		val newBranch = new RemoteBranchImpl(newBranchName)
		addCommit(initialCommit, newBranch)
		newBranch.remoteRepository = remoteRepository
		remoteBranches += newBranch
		return newBranch
	}

	override push(LocalBranch localBranch) throws CommitNotExceptedException {
		val remoteBranch = localBranch.remoteBranch
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val remoteRepo = remoteBranch.remoteRepository
		if (null === remoteRepo)
			throw new RepositoryNotFoundException
		val ids = remoteRepo.getIdentifiers(localBranch.name)
		val currentCommits = localBranch.commits
		if (ids.length > currentCommits.length)
			throw new CommitNotExceptedException
		ids.forEach [ id, i |
			val currentCommit = currentCommits.get(i)
			if (currentCommit.identifier != id)
				throw new CommitNotExceptedException
		]
		val commitsToPush = currentCommits.drop(ids.length)
		commitsToPush.forEach [ commit |
			remoteRepo.push(commit, localBranch.name)
			addCommit(commit, remoteBranch)
		]
		val localCommits = localBranch.commits.toList
		val remoteCommits = remoteBranch.commits.toList
		val localIds = localCommits.map[identifier]
		val remoteIds = remoteCommits.map[identifier]
		localIds.forEach [ localId, i |
			val remoteId = remoteIds.get(i)
			if (remoteId != localId)
				throw new IllegalStateException('''Id at «i» should be «localId» but was «remoteId»''')
		]
	}

	override pull(LocalBranch branch) {
		val remoteBranch = branch.remoteBranch
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val remoteRepo = remoteBranch.remoteRepository
		if (null === remoteRepo)
			throw new RepositoryNotFoundException
		val toLocal = branch.commits.length === remoteBranch.commits.length
		val ids = remoteRepo.getIdentifiers(branch.name)
		val localIds = branch.commits.map[identifier]
		if (localIds.length > ids.length)
			throw new IllegalStateException
		localIds.forEach [ localId, i |
			val remoteId = ids.get(i)
			if (remoteId != localId)
				throw new IllegalStateException
		]
		ids.drop(localIds.length).forEach [ id |
			val commit = remoteRepo.pullCommit(id, branch.name)
			addCommit(commit, remoteBranch)
			if (toLocal)
				addCommit(commit, branch)
		]
	}

	override pull() {
		pull(currentBranch)
	}
}
