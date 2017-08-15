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
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.VersioningVirtualModel
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.common.commit.Commit
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.tests.TestUserInteractor
import java.util.function.Consumer
import tools.vitruv.framework.change.description.PropagatedChangeWithCorrespondent
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.ResourceSetUtil
import tools.vitruv.framework.vsum.InternalTestVirtualModel
import tools.vitruv.framework.vsum.InternalModelRepository

abstract class AbstractLocalRepository<T> extends AbstractRepositoryImpl implements LocalRepository<T> {
	static extension BranchDiffCreator = BranchDiffCreator::instance
	static extension ChangeCloner = new ChangeClonerImpl
	static extension Logger = Logger::getLogger(AbstractLocalRepository)
	static extension URIRemapper = URIRemapper::instance
	static extension VitruviusChangeFactory = VitruviusChangeFactory::instance

	@Accessors(PUBLIC_SETTER)
	boolean allFlag

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Author author

	@Accessors(PUBLIC_GETTER)
	val Set<LocalBranch<T>> localBranches

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	LocalBranch<T> currentBranch

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VersioningVirtualModel virtualModel

	@Accessors(PUBLIC_GETTER)
	val List<RemoteBranch<T>> remoteBranches

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	T remoteProject

	Map<Branch, String> lastCommitCheckedOut
	protected val List<T> remoteRepositories

	new() {
		super()
		currentBranch = masterBranch as LocalBranch<T>
		lastCommitCheckedOut = newHashMap
		localBranches = newHashSet(currentBranch)
		remoteBranches = newArrayList
		remoteRepositories = newArrayList
	}

	private static def void rollback(VersioningVirtualModel vm, List<PropagatedChange> changes) {
		val modelRepo = (vm as InternalTestVirtualModel).modelRepository as InternalModelRepository
		val rSet = modelRepo.resourceSet
		val domain = ResourceSetUtil::getTransactionalEditingDomain(rSet)
		changes.filter(PropagatedChangeWithCorrespondent).map[correspondent].forEach [
			val current = it
			EMFCommandBridge::createAndExecuteVitruviusRecordingCommand(
				[
					current.applyBackward
					return null
				],
				domain
			)
		]
		rSet.resources.forEach[save(#{})]
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

	override commit(String s) { commit(s, virtualModel) }

	override commit(String s, VURI vuri) {
		if (null === vuri)
			throw new IllegalStateException("VURI must not be null!")
		commit(s, virtualModel, vuri)
	}

	override commit(String s, VersioningVirtualModel currentVirtualModel) {
		commit(s, currentVirtualModel, null)
	}

	override commit(String s, VersioningVirtualModel currentVirtualModel, VURI vuri) {
		val changeMatches = if (null === vuri)
				currentVirtualModel.allUnresolvedPropagatedChangesSinceLastCommit.immutableCopy
			else
				currentVirtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri).immutableCopy
		if (changeMatches.empty)
			throw new IllegalStateException('''No changes since last commit''')
		val userInteractions = currentVirtualModel.userInteractionsSinceLastCommit
		val commit = commit(s, changeMatches, userInteractions)
		val lastChangeId = changeMatches.last.id
		if (null === vuri)
			currentVirtualModel.allLastPropagatedChangeId = lastChangeId
		else
			currentVirtualModel.setLastPropagatedChangeId(vuri, lastChangeId)
		return commit
	}

	override checkout(VURI vuri) {
		checkout(virtualModel, vuri)
	}

	override checkout() {
		checkout(virtualModel)
	}

	override checkout(VersioningVirtualModel currentVirtualModel) {
		checkout(currentVirtualModel, null)
	}

	override checkout(VersioningVirtualModel currentVirtualModel, VURI vuri) {
		val changeMatches = calculateChangeMatches
		val originalChanges = changeMatches.map[id -> originalChange].toList.immutableCopy
		if (originalChanges.empty) {
			info("Nothing to checkout")
			return
		}
		val myVURI = originalChanges.get(0).value.URI
		val processTargetEChange = calculateMapFunction(myVURI, vuri)

		// PS Create a list with the id of the change and a new change with the adjusted 
		// VURI.
		val newChanges = originalChanges.map [
			val eChanges = value.EChanges.map[cloneEChange(it)].toList.immutableCopy
			eChanges.forEach[processTargetEChange.accept(it)]
			val newChange = createEMFModelChangeFromEChanges(eChanges)
			return key -> newChange
		].toList.immutableCopy

		// PS Get user interactions from commits and give them to the 
		// virtual model.  
		val userInteractions = relevantCommits.map[userInteractions].flatten.toList
		val userInteractor = currentVirtualModel.userInteractor as TestUserInteractor
		userInteractor.addNextSelections(userInteractions)

		// PS Propagate changes,
		if (vuri === null)
			newChanges.forEach[currentVirtualModel.propagateChange(value, key)]
		else
			newChanges.forEach[currentVirtualModel.propagateChange(vuri, value, key)]

		// PS Test, if the identifiers remain the same,
		val newChangeMatches = if (null === vuri)
				currentVirtualModel.allUnresolvedPropagatedChanges
			else
				currentVirtualModel.getUnresolvedPropagatedChangesSinceLastCommit(vuri)
		val oldLastChangeId = changeMatches.last.id
		val newLastChangeId = newChangeMatches.last.id
		XtendAssertHelper::assertTrue(oldLastChangeId == newLastChangeId)

		// PS Set the last propagate change ID.
		if (null === vuri)
			currentVirtualModel.allLastPropagatedChangeId = newLastChangeId
		else
			currentVirtualModel.setLastPropagatedChangeId(vuri, newLastChangeId)
		val lastCommitId = relevantCommits.last.identifier
		lastCommitCheckedOut.put(currentBranch, lastCommitId)

	}

	override push() {
		push(currentBranch)
	}

	override createLocalBranch(String currentName) {
		val newBranch = new LocalBranchImpl(currentName)
		val currentCommits = commits
		localBranches += newBranch
		currentCommits.forEach[addCommit(newBranch)]
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
		merge(source, target, originalCallback, triggeredCallback, currentVirtualModel, null)
	}

	override merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VersioningVirtualModel currentVirtualModel,
		VersioningVirtualModel otherVirtualModel
	) {
		val sourceCommits = source.commits
		val targetCommits = target.commits
		val sourceCommitsId = sourceCommits.map[identifier]
		val targetCommitsId = targetCommits.map[identifier]
		val vuri = sourceCommits.last.changes.get(0).originalChange.URI
		var firstDifferentIndex = 0

		// PS Find the first different commit identifier
		for (i : 0 ..< Math.min(sourceCommits.length, targetCommits.length)) {
			val sourceId = sourceCommitsId.get(i)
			val targetId = targetCommitsId.get(i)
			if (sourceId != targetId)
				firstDifferentIndex = i
		}
		if (firstDifferentIndex === 0)
			throw new IllegalStateException('''The intial commit must be equal!''')

		// PS Drop all common commits 
		val sourceCommitsToCompare = sourceCommits.drop(firstDifferentIndex).toList
		val targetCommitsToCompare = targetCommits.drop(firstDifferentIndex).toList
		val sourceChanges = sourceCommitsToCompare.map[changes].flatten.toList
		val targetChanges = targetCommitsToCompare.map[changes].flatten.toList

		// PS Roll back the changes
		if (null !== otherVirtualModel) {
			rollback(otherVirtualModel, sourceChanges)
			rollback(currentVirtualModel, targetChanges)
		}

		val branchDiff = createVersionDiff(sourceChanges, targetChanges)
		val modelMerger = ModelMerger::createModelMerger
		val lastPropagatedTargetChange = targetCommits.get(firstDifferentIndex - 1).changes.last.id

		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val resolvedTargetChanges = currentVirtualModel.getResolvedPropagatedChanges(vuri)
		val changesToRollback = resolvedTargetChanges.dropWhile[id !== lastPropagatedTargetChange].drop(1).toList
		val reapplier = Reapplier::createReapplier

		val reappliedChanges = if (allFlag)
				reapplier.reapply(changesToRollback, echanges, currentVirtualModel)
			else
				reapplier.reapply(vuri, changesToRollback, echanges, currentVirtualModel)
		val sourceIds = sourceCommitsToCompare.map[identifier].last
		val tagetIds = targetCommitsToCompare.map[identifier].last

		val mergeCommit = createMergeCommit(
			reappliedChanges,
			'''Merged «source.name» into «target.name»''',
			author.name,
			author.email,
			sourceIds,
			tagetIds,
			#[]
		)
		targetCommitsToCompare.reverseView.immutableCopy.forEach [
			removeCommit(it, target)
		]
		sourceCommitsToCompare.forEach[addCommit(it, target)]
		targetCommitsToCompare.forEach[reapplyCommit(it, target)]
		addCommit(mergeCommit, target)
		return mergeCommit
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

	private def calculateChangeMatches() {
		val currentRelevantCommits = relevantCommits
		if (currentRelevantCommits.empty) {
			info('''No new commits to checkout!''')
			return #[]
		}
		val changeMatches = currentRelevantCommits.map[changes].flatten
		if (changeMatches.empty) {
			info('''«currentRelevantCommits» has/have no new changes to apply''')
			return #[]
		}
		return changeMatches

	}

	private def Consumer<EChange> calculateMapFunction(VURI myVURI, VURI vuri) {
		if (null === vuri)
			return []
		return createEChangeRemapFunction(myVURI, vuri)
	}

	private def commit(String s, List<PropagatedChange> changes, List<Integer> userInteractions) {
		val lastCommit = commits.last
		val commit = createSimpleCommit(
			changes,
			s,
			author.name,
			author.email,
			lastCommit.identifier,
			userInteractions
		)
		addCommit(commit)
		head = commit
		return commit
	}

	private def void reapplyCommit(Commit c, Branch branch) {
		if (c instanceof SimpleCommit) {
			val lastCommit = commits.last
			val newCommit = createSimpleCommit(
				c.changes,
				c.commitmessage.message,
				c.commitmessage.authorName,
				c.commitmessage.authorEMail,
				lastCommit.identifier,
				lastCommit.userInteractions
			)
			addCommit(newCommit, branch)
		} else
			throw new IllegalStateException
	}
}
