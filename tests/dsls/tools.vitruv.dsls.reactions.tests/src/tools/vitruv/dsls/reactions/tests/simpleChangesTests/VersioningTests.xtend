// package tools.vitruv.dsls.reactions.tests.simpleChangesTests

// import allElementTypes.AllElementTypesFactory
// import java.util.Collections
// import org.junit.Assert
// import org.junit.Test
// import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
// import tools.vitruv.framework.util.datatypes.VURI

// class VersioningTests extends SimpleChangesTests {

// 	protected override setup() {
// 		val root = AllElementTypesFactory::eINSTANCE.createRoot
// 		root.id = TEST_SOURCE_MODEL_NAME
// 		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
// 		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
// 		container.id = "NonRootObjectContainer"
// 		rootElement.nonRootObjectContainerHelper = container
// 		nonContainmentNonRootIds.forEach [
// 			val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
// 			nonRoot.id = it
// 			container.nonRootObjectsContainment.add(nonRoot)
// 		]
// 		val recorder = new AtomicEmfChangeRecorder
// 		val resource = getModelResource(TEST_TARGET_MODEL_NAME.projectModelPath)
// 		val vuri = VURI::getInstance(resource)
// 		recorder.beginRecording(vuri, Collections::singleton(resource))
// 		// Changes matchen ?
// 		// SaveAndSynchonize aufspalten => 
// 		// bisher alles im CompositeChange 
// 		//
// 		saveAndSynchronizeChanges(rootElement)
// 		assertModelsEqual
// 		val changes = recorder.endRecording
// 		Assert.assertTrue(changes.size > 0)
// 	}

// 	@Test
// 	def void test1() {
// 		Assert::assertTrue(true)
// 	}
// }
