package tools.vitruv.framework.vsum.repositories

import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.HashSet
import org.apache.log4j.Logger
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import java.util.Map
import java.util.HashMap
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl

class ModelRepositoryImpl {
	/**
	 * Setting this VM argument, propagated changes get unresolved.
	 * The necessary property setting is "-DunresolvePropagatedChanges"
	 */
	private static val VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES = "unresolvePropagatedChanges"
	private val logger = Logger.getLogger(ModelRepositoryImpl);
	val Set<EObject> rootElements;
	val Map<EObject, AtomicEmfChangeRecorder> rootToRecorder;
	var boolean isRecording = false;
	
	new() {
		rootElements = new HashSet<EObject>();
		rootToRecorder = new HashMap<EObject, AtomicEmfChangeRecorder>();
	}
	
	public def void addRootElement(EObject rootElement) {
		this.rootElements += rootElement;
		logger.debug("New root in repository " + rootElement);
		if (isRecording) {
			startRecordingForElement(rootElement);
		}
	}
	
	public def void cleanupRootElements() {
		val elementsToRemove = newArrayList() 
		for (rootElement : rootElements) {
			if (rootElement.eContainer !== null && !(rootElement.eContainer instanceof ChangeDescriptionImpl)) {
				elementsToRemove += rootElement;
			}
		}
		elementsToRemove.forEach[
			removeElementFromRecording(it);
			logger.debug("Remove root from repository " + it);
			rootElements.remove(it)
		];
	}
	
	public def void cleanupRootElementsWithoutResource() {
		val elementsToRemove = newArrayList() 
		for (rootElement : rootElements) {
			if (rootElement.eResource === null) {
				elementsToRemove += rootElement;
			}
		}
		elementsToRemove.forEach[
			logger.debug("Remove root without resource from repository " + it);
			rootElements.remove(it)
		];
	}
	
	public def void startRecording() {
		for (root : rootElements) {
			startRecordingForElement(root)
		}
		isRecording = true;
	}
	
	public def endRecording() {
		val result = newArrayList();
		for (root : rootToRecorder.keySet) {
			rootToRecorder.get(root).endRecording();
			if (rootToRecorder.get(root).unresolvedChanges !== null) {
				result += rootToRecorder.get(root).unresolvedChanges;	
			} else {
				result += rootToRecorder.get(root).resolvedChanges;
			}
			
			logger.debug("End recording for " + root);
		}
		rootToRecorder.clear();
		isRecording = false;
		return result;
	}
	
	private def void startRecordingForElement(EObject element) {
		if (!rootElements.contains(element)) {
			throw new IllegalStateException();
		}
		if (rootToRecorder.containsKey(element)) {
			throw new IllegalStateException("Duplicate recording on element")
		}
		val unresolvePropagatedChanges = System.getProperty(VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES);
		val recorder = new AtomicEmfChangeRecorder(unresolvePropagatedChanges !== null, false);
		recorder.addToRecording(element);
		recorder.beginRecording();
		rootToRecorder.put(element, recorder);
		logger.debug("Start recording for " + element);
	}
	
	private def void removeElementFromRecording(EObject element) {
		val recorder = rootToRecorder.get(element);
		if (recorder !== null) {
			recorder.stopRecording;
		}
		rootToRecorder.remove(element);
		logger.debug("Abort recording for " + element);
	}
	
	override toString() '''
		Model repository contents:
			«FOR element : rootElements»
				«element», resource: «element.eResource?.URI»"
			«ENDFOR»
		'''
				
	
}
