package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import edu.kit.ipd.sdq.vitruvius.framework.changedescription2change.ChangeDescription2ChangeTransformation
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.util.changes.ForwardChangeRecorder
import java.util.List
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.After
import org.junit.Before

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*
import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*
import org.eclipse.emf.common.util.EList
import allElementTypes.NonRoot
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import java.util.Collection
import org.eclipse.emf.common.notify.Notifier

/** 
 * This class is the test class for the new {@link ChangeDescription2ChangeTransformation}. It
 * reuses some test cases from the test for the old metamodel that can be found in the project
 * edu.kit.ipd.sdq.vitruvius.tests.casestudies.emf.changedescription2change However, it is adapted
 * to test the new change metamodel.
 * @author langhamm
 */
abstract class ChangeDescription2ChangeTransformationTest {
	var protected ForwardChangeRecorder changeRecorder
	var protected Root rootElement
	var protected List<EChange> changes

	public static val SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME = "singleValuedContainmentEReference"
	public static val SINGLE_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME = "singleValuedNonContainmentEReference"
	public static val SINGE_VALUE_E_ATTRIBUTE_NAME = "singleValuedEAttribute"
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
		this.changeRecorder = new ForwardChangeRecorder()
	}

	@After
	def void afterTest() {
		if (this.changeRecorder.isRecording) {
			this.changeRecorder.endRecording()
		}
		this.changeRecorder.dispose()
	}
	
	public def List<EChange> getChanges() {
		if (this.changes == null) {
			this.changes = endRecording()
		}
		return this.changes
	}
	
	public def EChange claimChange(int index) {
		return getChanges().claimElementAt(index)
	}
	
	public def List<EChange> endRecording() {
		val changesDescriptions = changeRecorder.endRecording()
		return new ChangeDescription2ChangeTransformation(changesDescriptions).getChanges().filter(EMFModelChange).map[it.EChange].toList;
	}
	
	public def startRecording(Collection<Notifier> additionalElements) {
		this.changes = null
		this.changeRecorder.beginRecording((#[rootElement] + if (additionalElements != null) additionalElements else #[]).toList)
	}
	
	public def startRecording(){
		startRecording(null);
	}
	
	public def getRootElement(){
		return this.rootElement
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
