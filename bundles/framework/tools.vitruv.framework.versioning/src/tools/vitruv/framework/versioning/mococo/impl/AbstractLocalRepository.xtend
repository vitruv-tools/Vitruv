package tools.vitruv.framework.versioning.mococo.impl

import java.util.List
import java.util.Map
import java.util.Set
import java.util.function.Consumer
import java.util.stream.Collectors

import org.apache.log4j.Logger

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.PropagatedChangeWithCorrespondent
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.impl.PropagatedChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.copy.EChangeCopier
import tools.vitruv.framework.tests.TestUserInteractor
import tools.vitruv.framework.util.ResourceSetUtil
import tools.vitruv.framework.util.XtendAssertHelper
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.BranchDiffCreator
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ModelMerger
import tools.vitruv.framework.versioning.Reapplier
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.branch.impl.LocalBranchImpl
import tools.vitruv.framework.versioning.common.commit.Commit
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.versioning.mococo.InternalTestLocalRepository
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.InternalModelRepository
import tools.vitruv.framework.vsum.InternalTestVersioningVirtualModel
import tools.vitruv.framework.vsum.InternalTestVirtualModel
import tools.vitruv.framework.vsum.VersioningVirtualModel

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.mapFixed

abstract class AbstractLocalRepository<T> extends AbstractRepositoryImpl implements InternalTestLocalRepository<T> {
	// Extensions.
	static extension BranchDiffCreator = BranchDiffCreator::instance
	static extension Logger = Logger::getLogger(AbstractLocalRepository)
	static extension URIRemapper = URIRemapper::instance
	static extension VitruviusChangeFactory = VitruviusChangeFactory::instance
	static extension EChangeCopier = EChangeCopier::createEChangeCopier(#{})

	// Values. 
	@Accessors(PUBLIC_GETTER)
	val List<RemoteBranch<T>> remoteBranches

	@Accessors(PUBLIC_GETTER)
	val Set<LocalBranch<T>> localBranches

	protected val List<T> remoteRepositories

	val Map<Branch, String> lastCommitCheckedOut

	val Set<Pair<String, String>> idPairs

	// PS Map to save VURI to VURI relation. 
	// Needed for VURI remapping in reapplier.
	val Map<VURI, VURI> vuriToVuriMap

	// Variables.
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Author author

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	LocalBranch<T> currentBranch

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	VersioningVirtualModel virtualModel

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	T remoteProject

	@Accessors(PUBLIC_SETTER)
	boolean allFlag

	@Accessors(PUBLIC_SETTER)
	boolean isRePropagatePropagatedChangesActive

	new() {
		super()
		currentBranch = masterBranch as LocalBranch<T>
		idPairs = newHashSet
		isRePropagatePropagatedChangesActive = false
		lastCommitCheckedOut = newHashMap
		localBranches = newHashSet(currentBranch)
		remoteBranches = newArrayList
		remoteRepositories = newArrayList
		vuriToVuriMap = newHashMap
	}

	private static def getClonedEChanges(VitruviusChange vitruviusChange) {
		// PS At this point the EChangeCopier::copy method must be used, not the 
		// ChangeCloner::cloneEChange. This is only creating a shallow copy, whereas
		// here a "deeper" copy is needed.
		vitruviusChange.EChanges.map[copy(it)]
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

	override addIDPair(Pair<String, String> pair) {
		idPairs += pair
	}

	override addRemoteRepository(T remoteRepository) {
		if(null !== remoteRepository) remoteRepositories += remoteRepository
	}

	override getCommits() {
		currentBranch.commits
	}

	override addCommit(Commit c) {
		addCommit(c, currentBranch)
	}

	override commit(String message) { commit(message, virtualModel) }

	override commit(String message, VURI vuri) {
		if(null === vuri)
			throw new IllegalStateException("VURI must not be null!")
		commit(message, virtualModel, vuri)
	}

	override commit(String message, VersioningVirtualModel currentVirtualModel) {
		commit(message, currentVirtualModel, null)
	}

	override commit(String message, VersioningVirtualModel currentVirtualModel, VURI vuriWhichShouldBeCommited) {
		val changeMatches = if(null === vuriWhichShouldBeCommited)
				currentVirtualModel.allUnresolvedPropagatedChangesSinceLastCommit
			else
				(currentVirtualModel as InternalTestVersioningVirtualModel).
					getUnresolvedPropagatedChangesSinceLastCommit(vuriWhichShouldBeCommited)
		if(changeMatches.empty)
			throw new IllegalStateException('''No changes since last commit''')
		val userInteractions = (currentVirtualModel as InternalTestVersioningVirtualModel).
			userInteractionsSinceLastCommit
		val commit = commit(message, changeMatches, userInteractions)
		val lastChangeId = changeMatches.last.id
		if(null === vuriWhichShouldBeCommited)
			currentVirtualModel.allLastPropagatedChangeId = lastChangeId
		else
			(currentVirtualModel as InternalTestVersioningVirtualModel).setLastPropagatedChangeId(
				vuriWhichShouldBeCommited, lastChangeId)
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
		if(changeMatches.empty) {
			info("Nothing to checkout")
			return
		}
		if(isRePropagatePropagatedChangesActive)
			checkoutPropagatedChanges(changeMatches, currentVirtualModel, vuri)
		else
			checkoutOriginalChanges(changeMatches, currentVirtualModel, vuri)
		// PS Test, if the identifiers remain the same,
		val newChangeMatches = if(null === vuri)
				currentVirtualModel.allUnresolvedPropagatedChanges
			else
				(currentVirtualModel as InternalTestVersioningVirtualModel).
					getUnresolvedPropagatedChangesSinceLastCommit(vuri)

		val oldLastChangeId = changeMatches.last.id
		val newLastChangeId = newChangeMatches.last.id
		XtendAssertHelper::assertTrue(oldLastChangeId == newLastChangeId)

		// PS Set the last propagate change ID.
		if(null === vuri)
			currentVirtualModel.allLastPropagatedChangeId = newLastChangeId
		else
			(currentVirtualModel as InternalTestVersioningVirtualModel).setLastPropagatedChangeId(vuri, newLastChangeId)
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
		merge(source -> null, target -> null, originalCallback, triggeredCallback, currentVirtualModel)
	}

	private def Pair<Integer, Integer> calculateCommitsToDrop(
		List<String> sourceCommitsId,
		List<String> targetCommitsId
	) {
		if(sourceCommitsId.length < targetCommitsId.length)
			return calculateCommitsNumber(sourceCommitsId, targetCommitsId.reverseView)
		val pair = calculateCommitsNumber(targetCommitsId, sourceCommitsId.reverseView)
		return pair.value -> pair.key
	}

	private def calculateCommitsNumber(
		List<String> smallerList,
		List<String> greaterList
	) {
		val map = newHashMap
		for (i : 0 ..< smallerList.size)
			map.put(smallerList.get(i), i)
		for (greaterIndex : 0 ..< greaterList.size) {
			val greaterId = greaterList.get(greaterIndex)
			if(map.containsKey(greaterId)) {
				// PS greaterId is the last common predecessor.
				val smallerIndex = map.get(greaterId)
				// PS Calculate the commits to drop.
				val commitsToDropOnTheSmallerSide = smallerList.size - smallerIndex
				val commitsToDropOnTheGreaterSide = greaterList.size - greaterIndex
				return commitsToDropOnTheSmallerSide -> commitsToDropOnTheGreaterSide
			}
		}
		throw new IllegalStateException('''The initial commit must be equal!''')
	}

	override merge(
		Pair<Branch, VersioningVirtualModel> sourcePair,
		Pair<Branch, VersioningVirtualModel> targetPair,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VersioningVirtualModel currentVirtualModel
	) {
		val source = sourcePair.key
		val target = targetPair.key
		val sourceCommits = source.commits
		val targetCommits = target.commits
		val sourceCommitsId = sourceCommits.map[identifier]
		val targetCommitsId = targetCommits.map[identifier]
		val vuri = targetCommits.last.changes.get(0).originalChange.URI
		var commitsNumbersToDrop = calculateCommitsToDrop(sourceCommitsId, targetCommitsId)

		// PS Drop all common commits 
		val sourceCommitsToCompare = sourceCommits.drop(commitsNumbersToDrop.key).toList
		val targetCommitsToCompare = targetCommits.drop(commitsNumbersToDrop.value).toList
		val sourceChanges = sourceCommitsToCompare.map[changes].flatten.toList
		val targetChanges = targetCommitsToCompare.map[changes].flatten.toList

		// PS Roll back the changes
		if(null !== sourcePair.value && null !== targetPair.value) {
			rollback(sourcePair.value, sourceChanges)
			rollback(targetPair.value, targetChanges)
		}

		val branchDiff = createVersionDiff(sourceChanges, targetChanges)
		val modelMerger = ModelMerger::createModelMerger
		val lastPropagatedTargetChange = targetCommits.get(commitsNumbersToDrop.value - 1).changes.last.id

		modelMerger.init(branchDiff, originalCallback, triggeredCallback, idPairs)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val resolvedTargetChanges = (currentVirtualModel as InternalTestVersioningVirtualModel).
			getResolvedPropagatedChanges(vuri)
		val changesToRollback = resolvedTargetChanges.dropWhile[id !== lastPropagatedTargetChange].drop(1).toList
		val reapplier = Reapplier::createReapplier

		val reappliedChanges = if(allFlag)
				reapplier.reapply(changesToRollback, echanges, currentVirtualModel)
			else
				reapplier.reapply(changesToRollback, echanges, currentVirtualModel, vuri, vuriToVuriMap)
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
		targetCommitsToCompare.reverseView.forEach [
			removeCommit(it, target)
		]
		sourceCommitsToCompare.forEach[addCommit(it, target)]
		targetCommitsToCompare.forEach[reapplyCommit(it, target)]
		addCommit(mergeCommit, target)
		return mergeCommit
	}

	protected def Iterable<Commit> getRelevantCommits() {
		val returnValue = if(lastCommitCheckedOut.containsKey(currentBranch)) {
				val lastCommitId = lastCommitCheckedOut.get(currentBranch)
				commits.dropWhile[identifier !== lastCommitId].drop(1)
			} else {
				commits
			}
		return returnValue
	}

	private def checkoutPropagatedChanges(
		Iterable<PropagatedChange> changeMatches,
		VersioningVirtualModel currentVirtualModel,
		VURI vuri
	) {
		val originalChanges = changeMatches.map[originalChange]
		if(originalChanges.empty) {
			info("Nothing to checkout")
			return
		}
		val myVURI = originalChanges.get(0).URI
		val myTriggeredVURIs = changeMatches.fold(newHashSet, [ Set<VURI> set, changeMatch |
			set.add(changeMatch.consequentialChanges.URI)
			return set
		])
		val pairedSet = myTriggeredVURIs.filterNull.map[it -> correspondentURI].toSet
		val triggeredConsumers = pairedSet.map[calculateMapFunction(key, value)]

		val processTargetEChange = calculateMapFunction(myVURI, vuri)
		val processTriggeredEChange = [EChange e|triggeredConsumers.forEach[accept(e)]]

		val List<PropagatedChange> newPropagateChanges = changeMatches.map [ changeMatch |
			val originalEChanges = changeMatch.originalChange.clonedEChanges
			originalEChanges.parallelStream.forEach(processTargetEChange)
			val newOriginalChange = createEMFModelChangeFromEChanges(originalEChanges)
			val triggeredEChanges = changeMatch.consequentialChanges.clonedEChanges
			triggeredEChanges.parallelStream.forEach(processTriggeredEChange)
			val newTriggeredChange = createEMFModelChangeFromEChanges(triggeredEChanges)

			val PropagatedChange propagatedChange = new PropagatedChangeImpl(
				changeMatch.id,
				newOriginalChange,
				newTriggeredChange
			)
			return propagatedChange
		].toList

		// PS Propagate changes,
		newPropagateChanges.forEach [
			(currentVirtualModel as InternalTestVersioningVirtualModel).propagateChange(it, vuri)
		]
	}

	private def checkoutOriginalChanges(
		Iterable<PropagatedChange> changeMatches,
		VersioningVirtualModel currentVirtualModel,
		VURI vuri
	) {
		val originalChanges = changeMatches.map[id -> originalChange]
		if(originalChanges.empty) {
			info("Nothing to checkout")
			return
		}
		val oldVURI = originalChanges.get(0).value.URI
		val oldTriggeredVURIs = changeMatches.toList.stream.map[consequentialChanges.URI].collect(Collectors::toSet)

		(currentVirtualModel as InternalTestVersioningVirtualModel).addMappedVURIs(oldVURI, vuri)
		// PS In when reapplying changes to the old VURI, this VURI should be replace by 
		// the new one.
		vuriToVuriMap.put(oldVURI, vuri)
		val processTargetEChange = calculateMapFunction(oldVURI, vuri)

		// PS Create a list with the id of the change and a new change with the adjusted 
		// VURI.
		val newChanges = originalChanges.map [
			val eChanges = value.EChanges.mapFixed[copy(it)]
			eChanges.forEach(processTargetEChange)
			val newChange = createEMFModelChangeFromEChanges(eChanges)
			return key -> newChange
		]

		// PS Get user interactions from commits and give them to the 
		// virtual model.  
		val userInteractions = relevantCommits.map[userInteractions].flatten
		// TODO PS These two lines are only necessary because the original changes have to be reapplied.
		// Remove these lines when reapplying the PropagatedChanges.
		val userInteractor = currentVirtualModel.userInteractor as TestUserInteractor
		userInteractor.addNextSelections(userInteractions)

		// PS Propagate changes,
		val newTriggerdVURIs = newHashSet
		newChanges.forEach [
			val x = (currentVirtualModel as InternalTestVersioningVirtualModel).propagateChange(vuri, value, key)
			x.forEach[newTriggerdVURIs += consequentialChanges.URI]
		]
		oldTriggeredVURIs.forEach [ oldTriggeredVURI |
			val newTriggeredVURI = newTriggerdVURIs.filterNull.findFirst [ n |
				n.EMFUri.fileExtension == oldTriggeredVURI.EMFUri.fileExtension
			]
			if(null !== newTriggeredVURI) {
				(currentVirtualModel as InternalTestVersioningVirtualModel).addMappedVURIs(oldTriggeredVURI,
					newTriggeredVURI)
				(currentVirtualModel as InternalTestVersioningVirtualModel).addTriggeredRelation(vuri, newTriggeredVURI)
				vuriToVuriMap.put(oldTriggeredVURI, newTriggeredVURI)

			}

		]
	}

	private def calculateChangeMatches() {
		val currentRelevantCommits = relevantCommits
		if(currentRelevantCommits.empty) {
			info('''No new commits to checkout!''')
			return #[]
		}
		val changeMatches = currentRelevantCommits.map[changes].flatten
		if(changeMatches.empty) {
			info('''«currentRelevantCommits» has/have no new changes to apply''')
			return #[]
		}
		return changeMatches

	}

	private def Consumer<EChange> calculateMapFunction(VURI myVURI, VURI vuri) {
		if(null === vuri)
			return []
		return createEChangeRemapFunction(myVURI, vuri)
	}

	private def commit(String message, List<PropagatedChange> changes, List<Integer> userInteractions) {
		val lastCommit = commits.last
		val commit = createSimpleCommit(
			changes,
			message,
			author.name,
			author.email,
			lastCommit.identifier,
			userInteractions
		)
		addCommit(commit)
		head = commit
		return commit
	}

	private def void reapplyCommit(Commit commit, Branch branch) {
		if(commit instanceof SimpleCommit) {
			val lastCommit = commits.last
			val newCommit = createSimpleCommit(
				commit.changes,
				commit.commitmessage.message,
				commit.commitmessage.authorName,
				commit.commitmessage.authorEMail,
				lastCommit.identifier,
				lastCommit.userInteractions
			)
			addCommit(newCommit, branch)
		} else
			throw new IllegalStateException
	}

}
