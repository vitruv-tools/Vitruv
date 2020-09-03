package tools.vitruv.framework.vsum.repositories

import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.HashSet
import org.apache.log4j.Logger
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import java.util.Map
import java.util.HashMap
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver

class ModelRepositoryImpl {
	val logger = Logger.getLogger(ModelRepositoryImpl);
	val Set<EObject> rootElements;
	val Map<EObject, AtomicEmfChangeRecorder> rootToRecorder;
	var boolean isRecording = false;
	val UuidGeneratorAndResolver uuidGeneratorAndResolver;
	
	new(UuidGeneratorAndResolver uuidGeneratorAndResolver) {
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver;
		rootElements = new HashSet<EObject>();
		rootToRecorder = new HashMap<EObject, AtomicEmfChangeRecorder>();
	}
	
	def void addRootElement(EObject rootElement) {
		if (rootElements.contains(rootElement)) {
			return;
		}
		this.rootElements += rootElement;
		logger.debug("New root in repository " + rootElement);
		val recorder = new AtomicEmfChangeRecorder(uuidGeneratorAndResolver);
		recorder.addToRecording(rootElement);
		rootToRecorder.put(rootElement, recorder);
		if (isRecording) {
			startRecordingForElement(rootElement);
		}
	}
	
	def void cleanupRootElements() {
		val elementsToRemove = newArrayList() 
		for (rootElement : rootElements) {
			if (rootElement.eContainer !== null && !(rootElement.eContainer instanceof ChangeDescriptionImpl)) {
				elementsToRemove += rootElement;
			}
		}
		elementsToRemove.forEach[
			removeElementFromRecording(it);
			rootElements -= it;
			logger.debug("Remove root from repository " + it);
		];
	}
	
	def void cleanupRootElementsWithoutResource() {
		val elementsToRemove = newArrayList() 
		for (rootElement : rootElements) {
			if (rootElement.eResource === null) {
				elementsToRemove += rootElement;
			}
		}
		elementsToRemove.forEach[
			removeElementFromRecording(it);
			logger.debug("Remove root without resource from repository " + it);
			rootElements.remove(it)
		];
	}
	
	def void startRecording() {
		for (root : rootElements) {
			startRecordingForElement(root)
		}
		isRecording = true;
	}
	
	def endRecording() {
		val result = newArrayList();
		for (root : rootToRecorder.keySet) {
			rootToRecorder.get(root).endRecording();
			result += rootToRecorder.get(root).changes;
			logger.debug("End recording for " + root);
		}
		isRecording = false;
		return result;
	}
	
	private def void startRecordingForElement(EObject element) {
		if (!rootElements.contains(element)) {
			throw new IllegalStateException();
		}
		if (!rootToRecorder.containsKey(element)) {
			throw new IllegalStateException("Element " + element + " has no recorder")
		}
		val recorder = rootToRecorder.get(element);
		recorder.beginRecording();
		logger.debug("Start recording for " + element);
	}
	
	private def void removeElementFromRecording(EObject element) {
		val recorder = rootToRecorder.get(element);
		if (recorder !== null && recorder.isRecording) {
			recorder.stopRecording;
		}
		recorder.removeFromRecording(element);
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
