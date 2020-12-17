package tools.vitruv.framework.tests.change

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.ecore.EStructuralFeature

import tools.vitruv.framework.change.echange.EChange
import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.io.File
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*;
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import org.eclipse.emf.ecore.resource.ResourceSet
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import static org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import org.eclipse.xtend.lib.annotations.Accessors

/** 
 * @author langhamm
 */
abstract class ChangeDescription2ChangeTransformationTest {
	@Accessors(PROTECTED_GETTER)
	var AtomicEmfChangeRecorder changeRecorder
	@Accessors(PROTECTED_GETTER)
	var Root rootElement
	var List<EChange> changes
	@Accessors(PROTECTED_GETTER)
	var File tempFolder
	var ResourceSet rs
	var UuidGeneratorAndResolver uuidGeneratorAndResolver;

	public static val SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME = "singleValuedContainmentEReference"
	public static val SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "singleValuedNonContainmentEReference"
	public static val SINGLE_VALUED_E_ATTRIBUTE_NAME = "singleValuedEAttribute"
	public static val MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME = "multiValuedContainmentEReference"
	public static val MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "multiValuedNonContainmentEReference"
	public static val MULTI_VALUE_E_ATTRIBUTE_NAME = "multiValuedEAttribute"

	protected def Root createRootInResource(int count) {
		val rootElement = AllElementTypesFactory.eINSTANCE.createRoot()
		rootElement.nonRootObjectContainerHelper = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper()
		val tmpFile = new File(tempFolder, "dummyURI" + count + ".xmi")
		val uri = EMFBridge.getEmfFileUriForFile(tmpFile);
		val resource = rs.createResource(uri)
		resource.contents += rootElement;
		return rootElement;
	}

	/** 
	 * Create a new model and initialize the change monitoring
	 */
	@BeforeEach
	def void beforeTest(@TempDir File tempFolder) {
		this.tempFolder = tempFolder
		this.rs = new ResourceSetImpl().withGlobalFactories
		this.uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(rs, false)
		this.changeRecorder = new AtomicEmfChangeRecorder(uuidGeneratorAndResolver)
		prepareRootElement();
	}

	def void prepareRootElement() {
		changeRecorder.addToRecording(rs);
		this.rootElement = createRootInResource(1);
	}

	@AfterEach
	def void afterTest() {
		if (this.changeRecorder.isRecording) {
			this.changeRecorder.endRecording()
		}
		this.changeRecorder.dispose()
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

	def List<EChange> endRecording() {
		changeRecorder.endRecording()
		val changeDescriptions = changeRecorder.changes
		return changeDescriptions.map[EChanges].flatten.toList;
	}

	def startRecording() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording;
		}
		this.changes = null
		this.changeRecorder.addToRecording(rs)
		this.changeRecorder.beginRecording()
	}

	def getRootElement() {
		return this.rootElement
	}

	static def assertChangeCount(Iterable<?> changes, int expectedCount) {
		assertEquals(
			expectedCount,
			changes.size,
			'''There were «changes.size» changes, although «expectedCount» were expected'''
		);
	}

	static def EChange claimChange(List<EChange> changes, int index) {
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
