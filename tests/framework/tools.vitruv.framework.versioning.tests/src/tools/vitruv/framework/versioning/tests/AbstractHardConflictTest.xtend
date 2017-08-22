package tools.vitruv.framework.versioning.tests

abstract class AbstractHardConflictTest extends AbstractConflictTest {
	protected static val otherContainerID = "test"

	override setup() {
		super.setup
		val container1 = createNonRootObjectContainerHelper
		container1.id = containerId
		rootElement.nonRootObjectContainerHelper = container1

		checkChangeMatchesLength(2, 1)

		val container2 = createNonRootObjectContainerHelper
		container2.id = otherContainerID
		rootElement2.nonRootObjectContainerHelper = container2

		checkChangeMatchesLength(2, 2)

		val sourceChanges = versioningVirtualModel.getUnresolvedPropagatedChanges(sourceVURI)
		val targetChanges = versioningVirtualModel.getUnresolvedPropagatedChanges(newSourceVURI)
		branchDiff = createVersionDiff(sourceChanges, targetChanges)
		conflictDetector.init(branchDiff)
		conflictDetector.compute
		conflicts = conflictDetector.conflicts
		changes = branchDiff.baseChanges.map[originalChange].toList
		echanges = changes.map[EChanges].flatten.toList
	}

}
