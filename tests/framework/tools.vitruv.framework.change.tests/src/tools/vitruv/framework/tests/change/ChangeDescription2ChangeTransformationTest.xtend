package tools.vitruv.framework.tests.change

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.After
import org.junit.Before

import java.util.Collection
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder
import tools.vitruv.framework.change.echange.EChange
import org.junit.Assert
import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*

/** 
 * @author langhamm
 */
abstract class ChangeDescription2ChangeTransformationTest {
	var protected AtomicEMFChangeRecorder changeRecorder
	var protected Root rootElement
	var private List<EChange> changes

	public static val SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME = "singleValuedContainmentEReference"
	public static val SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "singleValuedNonContainmentEReference"
	public static val SINGLE_VALUED_E_ATTRIBUTE_NAME = "singleValuedEAttribute"
	public static val MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME =  "multiValuedContainmentEReference"
	public static val MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "multiValuedNonContainmentEReference"
	public static val MULTI_VALUE_E_ATTRIBUTE_NAME = "multiValuedEAttribute"

	/** 
	 * Create a new model and initialize the change monitoring
	 */
	@Before
	def void beforeTest() {
		this.rootElement = AllElementTypesFactory.eINSTANCE.createRoot()
		this.rootElement.nonRootObjectContainerHelper = AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper()
		this.changeRecorder = new AtomicEMFChangeRecorder()
	}

	@After
	def void afterTest() {
		if (this.changeRecorder.isRecording) {
			this.changeRecorder.endRecording()
		}
		this.changeRecorder.dispose()
	}
	
	protected def List<EChange> getChanges() {
		if (this.changes == null) {
			this.changes = endRecording()
		}
		return this.changes
	}
	
	public def List<EChange> endRecording() {
		val changeDescriptions = changeRecorder.endRecording()
//		for (var i = changeDescriptions.size -1; i>= 0; i--) {
//			changeDescriptions.get(i).changeDescription.applyAndReverse();
//		}
		// FIXME HK dont use the calculate method, prepare all changes in forall loop and take the changes afterwards
		return changeDescriptions.map[
			val changes = it.EChanges;
//			it.changeDescription.applyAndReverse();
			return changes;
		].flatten.toList;
//		val change = new changes.changepreparerTransformation(changeDescriptions, true).getChange();
//		val changes = if (change instanceof ProcessableCompositeChange)
//		map[it.EChanges].flatten.toList;
	}
	
	public def startRecording(Collection<Notifier> additionalElements) {
		this.changes = null
		this.changeRecorder.beginRecording(null, (#[rootElement] + if (additionalElements != null) additionalElements else #[]).toList)
	}
	
	public def startRecording(){
		startRecording(null);
	}
	
	public def getRootElement(){
		return this.rootElement
	}
	
	public static def assertChangeCount(List<?> changes, int expectedCount) {
		Assert.assertEquals("There were " + changes.size + " changes, although " + expectedCount + " were expected",
			expectedCount, changes.size
		);
	}
	
	public static def EChange claimChange(List<EChange> changes, int index) {
		return changes.claimElementAt(index)
	}
	
	protected  def createAndAddNonRootToFeature(EStructuralFeature eStructuralFeature, boolean shouldStartRecording) {
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
