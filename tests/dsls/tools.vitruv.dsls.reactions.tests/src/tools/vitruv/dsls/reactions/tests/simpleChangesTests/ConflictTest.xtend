package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.Root
import java.util.Collection
import java.util.Map
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ConflictTest extends AbstractVersioningTest {
	protected static val newTestSourceModelName = "Further_Source_Test_Model"
	protected static val newTestTargetModelName = "Further_Target_Test_Model"
	protected static val containerId = "NonRootObjectContainer"
	protected Map<String, String> modelPairs
	protected Collection<Root> roots
	protected SourceTargetRecorder stRecorder
	protected VURI newSourceVURI
	protected VURI sourceVURI

	override setup() {
		super.setup
		// Setup sourceTargetRecorder 
		stRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		stRecorder.registerObserver
		modelPairs = #{
			TEST_SOURCE_MODEL_NAME -> newTestSourceModelName,
			TEST_TARGET_MODEL_NAME -> newTestTargetModelName
		}
	}

	override unresolveChanges() {
		true
	}

	protected def checkChangeMatchesLength(int l1, int l2) {
		roots.forEach[saveAndSynchronizeChanges]
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(l1))
		assertThat(stRecorder.getChangeMatches(newSourceVURI).length, is(l2))
	}

}
