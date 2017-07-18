package tools.vitruv.dsls.reactions.tests

import allElementTypes.AllElementTypesFactory
import tools.vitruv.framework.versioning.BranchDiffCreator
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

abstract class AbstractConflictNotExistsTest extends AbstractConflictTest {
	override setup() {
		super.setup

		val container1 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container1.id = containerId
		rootElement.nonRootObjectContainerHelper = container1

		checkChangeMatchesLength(2, 1)

		val container2 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container2.id = containerId
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(2, 2)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container1)]
		checkChangeMatchesLength(5, 2)

		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container2)]
		checkChangeMatchesLength(5, 5)

		assertModelsEqual
//		assertMappedModelsAreEqual
		val sourceChanges = virtualModel.getChangeMatches(sourceVURI)
		val targetChanges = virtualModel.getChangeMatches(newSourceVURI)
		sourceChanges.forEach [
			originalChange.EChanges.forEach [ e |
				assertThat(e.resolved, is(false))
			]
		]
		branchDiff = BranchDiffCreator::instance.createVersionDiff(sourceChanges, targetChanges)
		conflictDetector.init(branchDiff)
		conflictDetector.compute
		conflicts = conflictDetector.conflicts
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList
	}
}
