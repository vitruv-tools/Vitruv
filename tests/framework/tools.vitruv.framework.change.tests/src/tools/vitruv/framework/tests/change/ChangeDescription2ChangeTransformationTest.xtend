package tools.vitruv.framework.tests.change

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.After
import org.junit.Before

import tools.vitruv.framework.change.echange.EChange
import org.junit.Assert
import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.io.File
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import java.util.ArrayList
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolverImpl
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*;
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver

/** 
 * @author langhamm
 */
abstract class ChangeDescription2ChangeTransformationTest {
	var protected AtomicEmfChangeRecorder changeRecorder
	var protected Root rootElement
	var private List<EChange> changes
	
	var rs = new ResourceSetImpl
	var UuidGeneratorAndResolver uuidGeneratorAndResolver;
	val private List<File> filesToDelete = new ArrayList<File>();

	public static val SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME = "singleValuedContainmentEReference"
	public static val SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "singleValuedNonContainmentEReference"
	public static val SINGLE_VALUED_E_ATTRIBUTE_NAME = "singleValuedEAttribute"
	public static val MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME = "multiValuedContainmentEReference"
	public static val MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "multiValuedNonContainmentEReference"
	public static val MULTI_VALUE_E_ATTRIBUTE_NAME = "multiValuedEAttribute"

	new() {
		rs.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl());
	}

	protected def Root createRootInResource(int count) {
		val rootElement = AllElementTypesFactory.eINSTANCE.createRoot()
		rootElement.nonRootObjectContainerHelper = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper()
		val tmpFile = File.createTempFile("dummyURI" + count, ".xmi");
		val uri = EMFBridge.getEmfFileUriForFile(tmpFile);
		val resource = rs.createResource(uri)
		filesToDelete += tmpFile;
		resource.contents += rootElement;
		return rootElement;
	}

	/** 
	 * Create a new model and initialize the change monitoring
	 */
	@Before
	def void beforeTest() {
		val uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(rs, false)
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver;
		this.changeRecorder = new AtomicEmfChangeRecorder(uuidGeneratorAndResolver)
		prepareRootElement();
	}
	
	def void prepareRootElement() {
		changeRecorder.addToRecording(rs);
		changeRecorder.beginRecording;
		this.rootElement = createRootInResource(1);
	}

	@After
	def void afterTest() {
		if (this.changeRecorder.isRecording) {
			this.changeRecorder.endRecording()
		}
		this.changeRecorder.dispose()
		for (file : filesToDelete) {
			file.delete();
		}
		filesToDelete.clear();
	}

	protected def List<EChange> getChanges() {
		if (this.changes === null) {
			this.changes = endRecording()
			this.changes.forEach[EChangeUnresolver.unresolve(it)]
			for (var i = this.changes.length - 1; i >= 0; i--) {
				this.changes.set(i, changes.get(i).resolveAfterAndApplyBackward(this.uuidGeneratorAndResolver));
			}	
			for (change : this.changes) {
				change.applyForward;
			}
		}
		return this.changes
	}

	public def List<EChange> endRecording() {
		changeRecorder.endRecording()
		val changeDescriptions = changeRecorder.changes
		return changeDescriptions.map[EChanges].flatten.toList;
	}

	public def startRecording() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording;
		}
		this.changes = null
		this.changeRecorder.addToRecording(rs)
		this.changeRecorder.beginRecording()
	}

	public def getRootElement() {
		return this.rootElement
	}

	public static def assertChangeCount(Iterable<?> changes, int expectedCount) {
		Assert.assertEquals(
			"There were " + changes.size + " changes, although " + expectedCount + " were expected",
			expectedCount,
			changes.size
		);
	}

	public static def EChange claimChange(List<EChange> changes, int index) {
		return changes.claimElementAt(index)
	}

	protected def createAndAddNonRootToFeature(EStructuralFeature eStructuralFeature, boolean shouldStartRecording) {
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.nonRootObjectContainerHelper.nonRootObjectsContainment.add(nonRoot)
		if (shouldStartRecording) {
			startRecording
		}
		return nonRoot
	}

	protected def createAndAddNonRootToContainment(boolean shouldStartRecording) {
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot;
		this.rootElement.singleValuedContainmentEReference = nonRoot;
		if (shouldStartRecording) {
			startRecording
		}
		return nonRoot
	}

	protected def createAndAddNonRootToRootContainer(boolean shouldStartRecording) {
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.nonRootObjectContainerHelper.nonRootObjectsContainment.add(nonRoot)
		if (shouldStartRecording) {
			startRecording
		}
		return nonRoot
	}
}
