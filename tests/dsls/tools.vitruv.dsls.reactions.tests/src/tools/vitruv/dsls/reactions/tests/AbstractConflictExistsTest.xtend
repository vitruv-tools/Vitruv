package tools.vitruv.dsls.reactions.tests

abstract class AbstractConflictExistsTest extends AbstractConflictTest {
	protected static val otherNonContainmentId = "OtherID"

	override setup() {
		super.setup
		val container1 = createNonRootObjectContainerHelper
		container1.id = containerId
		rootElement.nonRootObjectContainerHelper = container1

		checkChangeMatchesLength(2, 1)

		val container2 = createNonRootObjectContainerHelper
		container2.id = containerId
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(2, 2)

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container1)]
		checkChangeMatchesLength(5, 2)

		NON_CONTAINMENT_NON_ROOT_IDS.take(2).forEach[createAndAddNonRoot(container2)]
		otherNonContainmentId.createAndAddNonRoot(container2)
		checkChangeMatchesLength(5, 5)

		assertModelsEqual
		assertDifferentNonRootContainment

		val sourceChanges = virtualModel.getChangeMatches(sourceVURI)
		val targetChanges = virtualModel.getChangeMatches(newSourceVURI)
		branchDiff = createVersionDiff(sourceChanges, targetChanges)
		conflictDetector.init(branchDiff)
		conflictDetector.compute
		conflicts = conflictDetector.conflicts
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList

	}

}
