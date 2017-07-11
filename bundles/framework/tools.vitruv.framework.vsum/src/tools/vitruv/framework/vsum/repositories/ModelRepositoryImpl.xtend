package tools.vitruv.framework.vsum.repositories

import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl

import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
import tools.vitruv.framework.util.datatypes.VURI

class ModelRepositoryImpl {
	static extension Logger = Logger::getLogger(ModelRepositoryImpl)
	val Set<EObject> rootElements;
	val Map<EObject, AtomicEmfChangeRecorder> rootToRecorder;
	var boolean isRecording = false;

	new() {
		rootElements = new HashSet<EObject>();
		rootToRecorder = new HashMap<EObject, AtomicEmfChangeRecorder>();
	}

	public def void addRootElement(EObject rootElement) {
		this.rootElements += rootElement;
		debug("New root in repository " + rootElement);
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
		elementsToRemove.forEach [
			removeElementFromRecording(it);
			debug("Remove root from repository " + it);
			rootElements.remove(it)
		];
	}

	public def void cleanupRootElementsWithoutResource() {
		val elementsToRemove = newArrayList()
		for (rootElement : rootElements) {
			if (null === rootElement.eResource) {
				elementsToRemove += rootElement;
			}
		}
		elementsToRemove.forEach [
			debug("Remove root without resource from repository " + it);
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
			result += rootToRecorder.get(root).endRecording();
			debug("End recording for " + root);
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
		val AtomicEmfChangeRecorder recorder = new AtomicEmfChangeRecorderImpl(false, false);
		val vuri = if (element.eResource !== null) VURI.getInstance(element.eResource) else null;
		recorder.beginRecording(vuri, #[element]);
		rootToRecorder.put(element, recorder);
		debug("Start recording for " + element);
	}

	private def void removeElementFromRecording(EObject element) {
		val recorder = rootToRecorder.get(element);
		if (recorder !== null) {
			recorder.stopRecording;
		}
		rootToRecorder.remove(element);
		debug("Abort recording for " + element);
	}

	override toString() '''
	Model repository contents:
		«FOR element : rootElements»
		«element»«ENDFOR»'''

}
