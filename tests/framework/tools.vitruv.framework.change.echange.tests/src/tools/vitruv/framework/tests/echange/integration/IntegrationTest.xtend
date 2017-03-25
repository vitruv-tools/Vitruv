package tools.vitruv.framework.tests.echange.integration

import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EAttribute
import org.junit.After
import org.junit.Assert
import org.junit.Before
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder
import tools.vitruv.framework.tests.echange.EChangeTest
import tools.vitruv.framework.tests.echange.util.EChangeAssertEquals

/**
 * Abstract test class for testing the recording, resolving and applying methods together.
 */
abstract class IntegrationTest extends EChangeTest {
	var private AtomicEMFChangeRecorder changeRecorder = null
	var protected List<EChange> unresolvedChangesToApply = null
	var protected List<EChange> resolvedAppliedChanges = null
	var protected List<EChange> unresolvedRecordedChanges = null
	var protected List<EChange> resolvedRecordedChanges = null
	
	/**
	 * Initialize the monitoring.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		this.changeRecorder = new AtomicEMFChangeRecorder(true)
		this.unresolvedChangesToApply = new ArrayList<EChange>
		this.resolvedAppliedChanges = new ArrayList<EChange>
		this.resolvedRecordedChanges = new ArrayList<EChange>
	}
	
	@After
	override public void afterTest() {
		if (this.changeRecorder.isRecording) {
			this.changeRecorder.endRecording()
		}
		this.changeRecorder.dispose()		
	}
	
	/**
	 * Starts recording with additional elements, besides the root element.
	 */
	def protected startRecording(Collection<Notifier> additionalElements) {
		this.unresolvedRecordedChanges = null
		this.changeRecorder.beginRecording(null, (#[rootObject] + if (additionalElements != null) additionalElements else #[]).toList)
	}
	
	/**
	 * Starts recording without additional elements.
	 */
	def protected startRecording() {
		startRecording(null)
	}
	
	/**
	 * End recording and fills the unresolved recorded changes.
	 */
	def protected void endRecording() {
		val changeDescriptions = this.changeRecorder.endRecording()
		this.unresolvedRecordedChanges = changeDescriptions.map[
			val changes = it.EChanges;
			return changes;
		].flatten.toList;		
	}
	
	/**
	 * Resolves and applies all changes forward which were added.
	 */
	def protected resolveAndApplyChanges() {
		for (EChange c : this.unresolvedChangesToApply) {
			resolvedAppliedChanges.add(c.resolveBeforeAndApplyForward(resourceSet))
		}
	}
	
	/**
	 * Resolves and applies all changes backward which was recorded.
	 */
	def protected resolveAndRevertChanges() {
		for (EChange c : this.unresolvedRecordedChanges.reverseView) {
			this.resolvedRecordedChanges.add(c.resolveAfterAndApplyBackward(resourceSet))
		}
		this.resolvedRecordedChanges.reverse
	}
	
	/**
	 * The applied and recorded changes are the same.
	 */
	def protected assertAppliedAreRecordedChanges() {
		Assert.assertEquals(resolvedAppliedChanges.size, resolvedRecordedChanges.size)
		for (var i = 0; i < resolvedAppliedChanges.size; i++) {
			EChangeAssertEquals.assertEquals(resolvedAppliedChanges.get(i), resolvedRecordedChanges.get(i))
		}
	}
	
	/**
	 * Adds a {@link ReplaceSingleValuedEAttribute} EChange to the list of changes, which will be applied.
	 */
	def protected addReplaceEAttributeChange(EAttribute feature, Object oldValue, Integer newValue) {
		val change = atomicFactory.createReplaceSingleAttributeChange(rootObject, feature, oldValue, newValue)
		unresolvedChangesToApply.add(change)
	}
	
	/**
	 * Adds a {@link InsertEAttributeValue} EChange to the list of changes, which will be applied.
	 */	
	def protected addInsertEAttributeValueChange(EAttribute feature, int index, Integer newValue) {
		val change = atomicFactory.createInsertAttributeChange(rootObject, feature, index, newValue)
		unresolvedChangesToApply.add(change)
	}
	
	/**
	 * Adds a {@link RemoveEAttributeValue} EChange to the list of changes, which will be applied.
	 */	
	def protected addRemoveEAttributeValueChange(EAttribute feature, int index, Integer oldValue) {
		val change = atomicFactory.createRemoveAttributeChange(rootObject, feature, index, oldValue)
		unresolvedChangesToApply.add(change)
	}
}