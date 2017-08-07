package tools.vitruv.framework.versioning.emfstore.impl

import java.util.List
import java.util.Map
import java.util.Set

import org.apache.log4j.Logger

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1

import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.impl.ChangeClonerImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.XtendAssertHelper
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.BranchDiffCreator
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ModelMerger
import tools.vitruv.framework.versioning.Reapplier
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.impl.LocalBranchImpl
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.VersioningVirtualModel
import tools.vitruv.framework.versioning.branch.RemoteBranch

abstract class AbstractLocalRepository<T> extends AbstractRepositoryImpl implements LocalRepository<T> {
	static extension BranchDiffCreator = BranchDiffCreator::instance
	static extension ChangeCloner = new ChangeClonerImpl
	static extension Logger = Logger::getLogger(AbstractLocalRepository)
	static extension URIRemapper = URIRemapper::instance
	static extension VitruviusChangeFactory = VitruviusChangeFactory::instance

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Author author

	@Accessors(PUBLIC_GETTER)
	val Set<LocalBranch<T>> localBranches

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	LocalBranch<T> currentBranch

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VersioningVirtualModel virtualModel

	Map<Branch, String> lastCommitCheckedOut
	@Accessors(PUBLIC_GETTER)
	val List<RemoteBranch<T>> remoteBranches

	protected val List<T> remoteRepositories

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	T remoteProject

	new() {
		super()
		currentBranch = masterBranch as LocalBranch<T>
		lastCommitCheckedOut = newHashMap
		localBranches = newHashSet(currentBranch)
		remoteBranches = newArrayList
		remoteRepositories = newArrayList
	}

	override addRemoteRepository(T remoteRepository) {
		if (null !== remoteRepository) remoteRepositories += remoteRepository

	}

	override getCommits() {
		currentBranch.commits
	}

	override addCommit(Commit c) {
		addCommit(c, currentBranch)
	}

	override commit(String s) {
		commit(s, virtualModel)
	}

	override commit(String s, VersioningVirtualModel currentVirtualModel) {
		val changeMatches = currentVirtualModel.allUnresolvedPropagatedChangesSinceLastCommit.immutableCopy
		if (changeMatches.empty)
			throw new IllegalStateException('''No changes since last commit''')
		val commit = commit(s, changeMatches)
		val lastChangeId = changeMatches.last.id
		currentVirtualModel.allLastPropagatedChangeId = lastChangeId
		return commit
	}

	override commit(String s, VersioningVirtualModel currentVirtualModel, VURI vuri) {
		val changeMatches = currentVirtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri).immutableCopy
		if (changeMatches.empty)
			throw new IllegalStateException('''No changes since last commit''')
		val commit = commit(s, changeMatches)
		val lastChangeId = changeMatches.last.id
		currentVirtualModel.setLastPropagatedChangeId(vuri, lastChangeId)
		return commit
	}

	override commit(String s, List<PropagatedChange> changes) {
		warn("Please use commit(String s, VersioningVirtualModel virtualModel, VURI vuri)")
		val lastCommit = commits.last
		val commit = createSimpleCommit(
			changes,
			s,
			author.name,
			author.email,
			lastCommit.identifier
		)
		addCommit(commit)
		head = commit
		return commit
	}

	override checkout() {
		checkout(virtualModel)
	}

	protected def List<Commit> getRelevantCommits() {
		val returnValue = if (lastCommitCheckedOut.containsKey(currentBranch)) {
				val lastCommitId = lastCommitCheckedOut.get(currentBranch)
				commits.dropWhile[identifier !== lastCommitId].drop(1).toList.immutableCopy
			} else {
				commits
			}
		return returnValue
	}

	override checkout(VersioningVirtualModel currentVirtualModel) {
		val currentRelevantCommits = relevantCommits
		if (currentRelevantCommits.empty) {
			info('''No new commits to checkout!''')
			return
		}
		val changeMatches = currentRelevantCommits.map[changes].flatten
		if (changeMatches.empty) {
			info('''«currentRelevantCommits» has/have no new changes to apply''')
			return
		}
		val originalChanges = changeMatches.map[id -> originalChange].toList.immutableCopy
		val newChanges = originalChanges.map [
			val eChanges = value.EChanges.map[cloneEChange(it)].toList.immutableCopy
			val newChange = createEMFModelChangeFromEChanges(eChanges)
			return key -> newChange
		].toList.immutableCopy
		newChanges.forEach [
			currentVirtualModel.propagateChange(value, key)
		]
		val newChangeMatches = currentVirtualModel.allUnresolvedPropagatedChanges
		val oldLastChangeId = changeMatches.last.id
		val newLastChangeId = newChangeMatches.last.id
		XtendAssertHelper::assertTrue(oldLastChangeId == newLastChangeId)
		currentVirtualModel.allLastPropagatedChangeId = newLastChangeId
		lastCommitCheckedOut.put(currentBranch, currentRelevantCommits.last.identifier)
	}

	override checkout(VersioningVirtualModel currentVirtualModel, VURI vuri) {
		val changeMatches = commits.map[changes].flatten
		val originalChanges = changeMatches.map[originalChange].toList.immutableCopy
		val myVURI = originalChanges.get(0).URI
		val processTargetEChange = createEChangeRemapFunction(myVURI, vuri)
		val newChanges = originalChanges.map [
			val eChanges = EChanges.map[cloneEChange(it)].toList.immutableCopy
			eChanges.forEach[processTargetEChange.accept(it)]
			val newChange = createEMFModelChangeFromEChanges(eChanges)
			return newChange
		].toList.immutableCopy
		newChanges.forEach [
			currentVirtualModel.propagateChange(it)
		]
		val newChangeMatches = currentVirtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri)
		val lastChangeId = newChangeMatches.last.id
		currentVirtualModel.setLastPropagatedChangeId(vuri, lastChangeId)
	}

	override push() {
		push(currentBranch)
	}

	override createLocalBranch(String currentName) {
		val newBranch = new LocalBranchImpl(currentName)
		localBranches += newBranch
		currentBranch = newBranch
		return newBranch
	}

	override pull() {
		pull(currentBranch)
	}

	override merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback
	) {
		merge(source, target, originalCallback, triggeredCallback, virtualModel)
	}

	override merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VersioningVirtualModel currentVirtualModel
	) {
		val sourceCommits = source.commits
		val targetCommits = target.commits
		val sourceCommitsId = sourceCommits.map[identifier]
		val targetCommitsId = targetCommits.map[identifier]
		val vuri = sourceCommits.last.changes.get(0).originalChange.URI
		var firstDifferentIndex = 0
		for (i : 0 ..< Math.min(sourceCommits.length, targetCommits.length)) {
			val sourceId = sourceCommitsId.get(i)
			val targetId = targetCommitsId.get(i)
			if (sourceId != targetId)
				firstDifferentIndex = i
		}
		if (firstDifferentIndex === 0)
			throw new IllegalStateException('''The intial commit must be equal!''')
		val sourceCommitsToCompare = sourceCommits.drop(firstDifferentIndex).toList
		val targetCommitsToCompare = targetCommits.drop(firstDifferentIndex).toList
		val sourceChanges = sourceCommitsToCompare.map[changes].flatten.toList
		val targetChanges = targetCommitsToCompare.map[changes].flatten.toList
		val branchDiff = createVersionDiff(sourceChanges, targetChanges)
		val modelMerger = ModelMerger::createModelMerger
		val lastPropagatedTargetChange = targetCommits.get(firstDifferentIndex - 1).changes.last.id

		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val resolvedTargetChanges = currentVirtualModel.getResolvedPropagatedChanges(vuri)
		val changesToRollback = resolvedTargetChanges.dropWhile[id !== lastPropagatedTargetChange].drop(1).toList
		val reapplier = Reapplier::createReapplier
		val reappliedChanges = reapplier.reapply(vuri, changesToRollback, echanges, currentVirtualModel)
		val sourceIds = sourceCommitsToCompare.map[identifier].last
		val tagetIds = targetCommitsToCompare.map[identifier].last
		val mergeCommit = createMergeCommit(
			reappliedChanges,
			'''Merged «source.name» into «target.name»''',
			author.name,
			author.email,
			sourceIds,
			tagetIds
		)
		targetCommitsToCompare.reverseView.immutableCopy.forEach [
			removeCommit(it, target)
		]
		sourceCommitsToCompare.forEach[addCommit(it, target)]
		targetCommitsToCompare.forEach[reapplyCommit(it, target)]
		addCommit(mergeCommit, target)
		return mergeCommit
	}

	private def void reapplyCommit(Commit c, Branch branch) {
		if (c instanceof SimpleCommit) {
			val lastCommit = commits.last
			val newCommit = createSimpleCommit(
				c.changes,
				c.commitmessage.message,
				c.commitmessage.authorName,
				c.commitmessage.authorEMail,
				lastCommit.identifier
			)
			addCommit(newCommit, branch)
		} else
			throw new IllegalStateException
	}
}
