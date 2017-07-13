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
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.junit.runners.Parameterized.Parameter
import java.util.Collection

/** 
 * @author langhamm
 */
 @RunWith(Parameterized)
abstract class ChangeDescription2ChangeTransformationTest {
	var protected AtomicEmfChangeRecorder changeRecorder
	var protected Root rootElement
	var private List<EChange> changes
	
	@Parameter
	public boolean unresolveAndResolveRecordedEChanges
	var rs = new ResourceSetImpl
	val private List<File> filesToDelete = new ArrayList<File>();

	public static val SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME = "singleValuedContainmentEReference"
	public static val SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "singleValuedNonContainmentEReference"
	public static val SINGLE_VALUED_E_ATTRIBUTE_NAME = "singleValuedEAttribute"
	public static val MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME = "multiValuedContainmentEReference"
	public static val MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "multiValuedNonContainmentEReference"
	public static val MULTI_VALUE_E_ATTRIBUTE_NAME = "multiValuedEAttribute"

	@Parameters
	public def static Collection<Boolean> data() {
		return #[true, false];
	}
	
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
		this.changeRecorder = new AtomicEmfChangeRecorder(this.unresolveAndResolveRecordedEChanges)
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
			if (this.unresolveAndResolveRecordedEChanges) {
				for (var i = this.changes.length - 1; i >= 0; i--) {
					this.changes.set(i, changes.get(i).resolveAfterAndApplyBackward(this.rs));
				}	
				for (change : this.changes) {
					change.applyForward;
				}
			}
		}
		return this.changes
	}

	public def List<EChange> endRecording() {
		changeRecorder.endRecording()
		val changeDescriptions = if (unresolveAndResolveRecordedEChanges) {
			changeRecorder.unresolvedChanges
		} else {
			changeRecorder.resolvedChanges
		}
//		for (var i = changeDescriptions.size -1; i>= 0; i--) {
//			changeDescriptions.get(i).changeDescription.applyAndReverse();
//		}
		// FIXME HK dont use the calculate method, prepare all changes in forall loop and take the changes afterwards
		return changeDescriptions.map [
			val changes = it.EChanges;
//			it.changeDescription.applyAndReverse();
			return changes;
		].flatten.toList;
//		val change = new changes.changepreparerTransformation(changeDescriptions, true).getChange();
//		val changes = if (change instanceof ProcessableCompositeChange)
//		map[it.EChanges].flatten.toList;
	}

	public def startRecording() {
		this.changes = null
		this.changeRecorder.beginRecording(null, #[rs])
	}

	public def getRootElement() {
		return this.rootElement
	}

	public static def assertChangeCount(List<?> changes, int expectedCount) {
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
		// prepare --> insert the non root in the containment - but do not test the containment
//		createAndAddNonRootToFeature(this.rootElement.getFeatureByName(SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME),
//			shouldStartRecording)
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot;
		this.rootElement.singleValuedContainmentEReference = nonRoot;
		if (shouldStartRecording) {
			startRecording
		}
		return nonRoot
	}

	protected def createAndAddNonRootToRootContainer(boolean shouldStartRecording) {
		// prepare --> insert the non root in the containment - but do not test the containment
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.nonRootObjectContainerHelper.nonRootObjectsContainment.add(nonRoot)
		if (shouldStartRecording) {
			startRecording
		}
		return nonRoot
	}
}
