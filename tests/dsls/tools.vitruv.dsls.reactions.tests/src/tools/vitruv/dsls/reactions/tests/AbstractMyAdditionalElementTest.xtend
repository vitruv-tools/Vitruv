package tools.vitruv.dsls.reactions.tests

import tools.vitruv.dsls.reactions.tests.AbstractConflictTest
import allElementTypes.AllElementTypesFactory
import tools.vitruv.framework.versioning.BranchDiffCreator

class AbstractMyAdditionalElementTest extends AbstractConflictTest {
	protected static val myAdditionialID = "addId"

	override setup() {
		super.setup

		val container1 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container1.id = containerId
		rootElement.nonRootObjectContainerHelper = container1

		checkChangeMatchesLength(1, 0)

		val container2 = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container2.id = containerId
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(1, 1)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container1)]
		myAdditionialID.createAndAddNonRoot(container1)
		checkChangeMatchesLength(5, 1)

		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container2)]
		checkChangeMatchesLength(5, 4)

		assertModelsEqual
		val sourceChanges = stRecorder.getChangeMatches(sourceVURI)
		val targetChanges = stRecorder.getChangeMatches(newSourceVURI)
		branchDiff = BranchDiffCreator::instance.createVersionDiff(sourceChanges, targetChanges)

		conflictDetector.init(branchDiff)
		conflictDetector.compute
		conflicts = conflictDetector.conflicts
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList
	}
}
