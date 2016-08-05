package edu.kit.ipd.sdq.vitruvius.tests.components

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.tests.VitruviusCasestudyTest
import edu.kit.ipd.sdq.vitruvius.tests.VitruviusEMFCasestudyTest
import java.util.List
import multicontainment_a.Multicontainment_aPackage
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.change.ChangeDescription
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.changes.changerecorder.AtomicEMFChangeRecorder
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.VitruviusChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.RecordedChangeFactory
import edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer.EMFModelChangeTransformation

class ChangeDescription2ChangeTest extends VSUMTest {
	static val LOGGER = Logger.getLogger(CorrespondenceTest.getSimpleName())
	static val ALL_ELEMENT_TYPES_FILE_EXT = "allelementtypes"
	static val MULTICONTAINMENT_A_FILE_EXT = "multicontainment_a"
	
	var ResourceSet resourceSet
	var AtomicEMFChangeRecorder changeRecorder
	
//	val vECTest = new VitruviusEMFCasestudyTest() {
//			
//			override protected createMetaRepository() {
//				val metaRepository = testMetaRepository()
//				// FIXME MK make AbstractVSUMTest.testMetaRepositoryAndVSUMCreation also return a meta repository and remove the following three copied lines
//		        testAddMapping(metaRepository, PCM_MM_URI, PCM_FILE_EXT, UML_MM_URI, UML_FILE_EXT)
//		        testAddMapping(metaRepository, AllElementTypesPackage.eNS_URI, ALL_ELEMENT_TYPES_FILE_EXT, Multicontainment_aPackage.eNS_URI, MULTICONTAINMENT_A_FILE_EXT)
//				return metaRepository
//			}
//			
//			override protected getChange2CommandTransformerClass() {
//				throw new UnsupportedOperationException("TODO: auto-generated method stub")
//			}
//		}
	
//	@BeforeClass
//    def static void beforeClass() {
//		MetaRepositoryTest.beforeClass()
//		VitruviusCasestudyTest.setUpAllTests()
//	}
//	
	@Before
	override beforeTest() {
		super.beforeTest()
//		vECTest.beforeTest()
        this.resourceSet = new ResourceSetImpl()
        this.changeRecorder = new AtomicEMFChangeRecorder()
        this.changeRecorder.beginRecording(null, newArrayList(this.resourceSet))
	}
//	
//	@After
//	override afterTest() {
//		super.afterTest()
//	}
//	
//	@AfterClass
//    def static void afterClass() {
//        MetaRepositoryTest.afterClass()
//    }
    

	
	// TODO ML needs @Rule from VitruviusCasestudyTest?
	def List<VitruviusChange> triggerChangeDescription2Change() {
		val changes = this.changeRecorder.endRecording()
//      final List<Change> changes = this.changeDescrition2ChangeConverter.getChanges(cd, vuri);
		LOGGER.trace("monitored change descriptions: " + changes)
        val transformedChanges = changes.map[new EMFModelChangeTransformation(it).getChange()]
        LOGGER.trace("transformed changes: " + transformedChanges)
        //cd.applyAndReverse() // there and back again: side-effects of first applyAndReverse in endRec(false) are undone
        this.changeRecorder.restartRecording()
        return transformedChanges
	}
	
//	def private void beginRecording() {
////		vECTest.changeRecorder.beginRecording(#[vECTest.resourceSet])
//		this.changeRecorder.beginRecording(#[this.resourceSet])
//	}
//	
//	def private ChangeDescription endRecording() {
////		return vECTest.changeRecorder.endRecording()
//		return this.changeRecorder.endRecording()
//	}
//	
//	def private ChangeDescription restartRecording() {
//		val cd = endRecording()
//		beginRecording()
//		return cd
//	}
	
//	@Test
//	def void testLoading() {
//		testInstanceCreation(vECTest.vsum)
//		val changes = triggerChangeDescription2Change()
//		assertNotNull(changes)
//		assertTrue(changes.size == 0)
//	}
	
	@Test
	def void testCreateRootElement() {
		val aURI = EMFBridge.createPlatformResourceURI(getAllElementTypesInstanceAURI())
		val resource = EcoreResourceBridge.loadResourceAtURI(aURI, this.resourceSet)
//		EcoreResourceBridge.saveEObjectAsOnlyContent(null, aURI, this.resourceSet)
		this.changeRecorder.restartRecording()
		val rootElement = AllElementTypesFactory.eINSTANCE.createRoot()
		resource.contents.add(rootElement)
//		EcoreResourceBridge.saveResource(resource)
//		EcoreResourceBridge.saveEObjectAsOnlyContent(rootElement, aURI, this.resourceSet)
		val changes = triggerChangeDescription2Change()
		assertNotNull(changes)
	}
	
	def protected String getAllElementTypesInstanceAURI() {
		return getCurrentProjectModelFolder() + "a" + "." + ALL_ELEMENT_TYPES_FILE_EXT
	}
}