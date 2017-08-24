package tools.vitruv.framework.versioning.tests

import java.util.List
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Test

import allElementTypes.NonRootObjectContainerHelper
import allElementTypes.Root

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.MultiChangeConflict

import static org.hamcrest.collection.IsEmptyIterable.emptyIterable
import static org.hamcrest.collection.IsCollectionWithSize.hasSize

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not

import static org.junit.Assert.assertThat

class HardConflictTest extends AbstractHardConflictTest {

	@Test
	def void testConflictDetector() {
		assertThat(conflicts, not(emptyIterable))
		assertThat(conflicts, hasSize(1))
		val conflict = conflicts.filter[it instanceof MultiChangeConflict]::map[it as MultiChangeConflict]::get(0)
		assertThat(conflict, not(equalTo(null)))
		assertThat(conflict.solvability, is(ConflictSeverity::HARD))
		assertThat(conflict.type, is(ConflictType::REPLACING_SAME_VALUE))
		assertThat(conflict.sourceChanges, hasSize(1))
		assertThat(conflict.targetChanges, hasSize(1))
		val conflictFreeEChanges = conflictDetector.conflictFreeOriginalEChanges
		assertThat(conflictFreeEChanges, hasSize(3))
	}

	@Test
	def void testModelMergerTheirChanges() {
		assertThat(conflicts, not(emptyIterable))
		assertThat(conflicts, hasSize(1))
		val originalCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict)
				return c.targetChanges
			#[]
		]
		val triggeredCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict)
				return c.triggeredTargetChanges
			#[]
		]
		val ResourceSet source = new ResourceSetImpl
		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val testOnResourceSet = [ ResourceSet resourceSet, List<EChange> es |
			assertThat(es, hasSize(4))
			assertThat(es.exists[resolved], is(false))
			es.forEach [
				resolveBeforeAndApplyForward(resourceSet)
			]

			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper]::map [
				it as NonRootObjectContainerHelper
			].exists [
				id == otherContainerID
			], is(true))
			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper]::map [
				it as NonRootObjectContainerHelper
			].forall [
				id != containerId
			], is(true))
		]

		testOnResourceSet.apply(source, echanges)
		val ResourceSet target = new ResourceSetImpl
		val targetEChanges = modelMerger.resultingTriggeredEChanges

		testOnResourceSet.apply(target, targetEChanges)
	}

	@Test
	def void testModelMergerMyChanges() {
		assertThat(conflicts, not(emptyIterable))
		assertThat(conflicts, hasSize(1))
		val originalCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict)
				return c.sourceChanges
			#[]
		]
		val triggeredCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict)
				return c.triggeredSourceChanges
			#[]
		]
		val ResourceSet source = new ResourceSetImpl
		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val testOnResourceSet = [ ResourceSet resourceSet, List<EChange> es |
			assertThat(es, hasSize(4))
			assertThat(es.exists[resolved], is(false))
			es.forEach [
				resolveBeforeAndApplyForward(resourceSet)
			]

			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper]::map [
				it as NonRootObjectContainerHelper
			].forall [
				id != otherContainerID
			], is(true))
			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper]::map [
				it as NonRootObjectContainerHelper
			].exists [
				id == containerId
			], is(true))
		]

		testOnResourceSet.apply(source, echanges)
		val ResourceSet target = new ResourceSetImpl
		val targetEChanges = modelMerger.resultingTriggeredEChanges

		testOnResourceSet.apply(target, targetEChanges)
	}

	@Test
	def void testReapplier() {
		val originalCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict) {
				return c.targetChanges
			} else
				#[]
		]
		val triggeredCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict)
				return c.triggeredTargetChanges
			#[]
		]
		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges
		val changesToRollback = versioningVirtualModel.getResolvedPropagatedChanges(sourceVURI).drop(1).toList
		if (changesToRollback.exists[!resolved])
			throw new IllegalStateException
		val reappliedChanges = reapplier.reapply(changesToRollback, echanges, virtualModel, sourceVURI)
		assertThat(reappliedChanges, hasSize(4))
		val root = virtualModel.getModelInstance(sourceVURI).firstRootEObject as Root
		assertThat(root.nonRootObjectContainerHelper.id, equalTo(otherContainerID))
	}
}
