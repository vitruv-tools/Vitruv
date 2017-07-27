package tools.vitruv.dsls.reactions.tests.versioning

import java.util.List
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractHardConflictTest
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictSeverity
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.MultiChangeConflict

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat
import allElementTypes.NonRootObjectContainerHelper
import allElementTypes.Root

class HardConflictTest extends AbstractHardConflictTest {

	@Test
	def void testConflictDetector() {
		assertThat(conflicts.empty, is(false))
		assertThat(conflicts.size, is(1))
		val conflict = conflicts.filter[it instanceof MultiChangeConflict].map[it as MultiChangeConflict].get(0)
		assertThat(conflict, not(equalTo(null)))
		assertThat(conflict.solvability, is(ConflictSeverity::HARD))
		assertThat(conflict.type, is(ConflictType::REPLACING_SAME_VALUE))
		assertThat(conflict.sourceChanges.size, is(1))
		assertThat(conflict.targetChanges.size, is(1))
		val conflictFreeEChanges = conflictDetector.conflictFreeOriginalEChanges
		assertThat(conflictFreeEChanges.length, is(1))
	}

	@Test
	def void testModelMergerTheirChanges() {
		assertThat(conflicts.empty, is(false))
		assertThat(conflicts.size, is(1))
		val originalCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict) {
				return c.targetChanges
			} else
				#[]
		]
		val triggeredCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict) {
				return c.triggeredTargetChanges
			} else
				#[]
		]
		val ResourceSet source = new ResourceSetImpl
		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val testOnResourceSet = [ ResourceSet resourceSet, List<EChange> es |
			assertThat(es.length, is(2))
			assertThat(es.exists[resolved], is(false))
			es.forEach [
				resolveBeforeAndApplyForward(resourceSet)
			]

			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper].map [
				it as NonRootObjectContainerHelper
			].exists [
				id == otherContainerID
			], is(true))
			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper].map [
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
		assertThat(conflicts.empty, is(false))
		assertThat(conflicts.size, is(1))
		val originalCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict) {
				return c.sourceChanges
			} else
				#[]
		]
		val triggeredCallback = [ Conflict c |
			if (c instanceof MultiChangeConflict) {
				return c.triggeredSourceChanges
			} else
				#[]
		]
		val ResourceSet source = new ResourceSetImpl
		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges

		val testOnResourceSet = [ ResourceSet resourceSet, List<EChange> es |
			assertThat(es.length, is(2))
			assertThat(es.exists[resolved], is(false))
			es.forEach [
				resolveBeforeAndApplyForward(resourceSet)
			]

			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper].map [
				it as NonRootObjectContainerHelper
			].forall [
				id != otherContainerID
			], is(true))
			assertThat(resourceSet.allContents.filter[it instanceof NonRootObjectContainerHelper].map [
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
			if (c instanceof MultiChangeConflict) {
				return c.triggeredTargetChanges
			} else
				#[]
		]
		modelMerger.init(branchDiff, originalCallback, triggeredCallback)
		modelMerger.compute
		val echanges = modelMerger.resultingOriginalEChanges
		val changesToRollback = virtualModel.getResolvedPropagatedChanges(sourceVURI).drop(1).toList
		if (changesToRollback.exists[!resolved])
			throw new IllegalStateException
		val reappliedChanges = reapplier.reapply(sourceVURI, changesToRollback, echanges, virtualModel)
		assertThat(reappliedChanges.size, is(2))
		val root = virtualModel.getModelInstance(sourceVURI).firstRootEObject as Root
		assertThat(root.nonRootObjectContainerHelper.id, equalTo(otherContainerID))
	}
}
