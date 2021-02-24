package tools.vitruv.framework.vsum.repositories

import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.HashSet
import org.apache.log4j.Logger
import java.util.Map
import java.util.HashMap
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.recording.ChangeRecorder

class ModelRepositoryImpl {
	val logger = Logger.getLogger(ModelRepositoryImpl)
	val Set<EObject> rootElements = new HashSet()
	val Map<EObject, ChangeRecorder> rootToRecorder = new HashMap()
	var boolean isRecording = false
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
	
	new(UuidGeneratorAndResolver uuidGeneratorAndResolver) {
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver
	}
	
	def void addRootElement(EObject rootElement) {
		if (rootElements += rootElement) {
			if (logger.isTraceEnabled) logger.trace("New root in repository " + rootElement)
			val recorder = new ChangeRecorder(uuidGeneratorAndResolver)
			recorder.addToRecording(rootElement)
			rootToRecorder.put(rootElement, recorder)
			if (isRecording) {
				startRecordingForElement(rootElement);
			}
		}
	}
	
	def void cleanupRootElements() {
		for (val roots = rootElements.iterator(); roots.hasNext;) {
			val element = roots.next 
			if(element.eContainer !== null) {
				if (logger.isTraceEnabled) logger.trace("Remove root from repository: " + element);
				removeElementFromRecording(element)
				roots.remove()
			}
		}
	}
	
	def void cleanupRootElementsWithoutResource() {
		for (val roots = rootElements.iterator(); roots.hasNext;) {
			val element = roots.next
			if (element.eResource === null) {
				if (logger.isTraceEnabled) logger.trace("Remove root without resource from repository: " + element);
				removeElementFromRecording(element);
				roots.remove()
			}
		}
	}
	
	def void startRecording() {
		for (root : rootElements) {
			startRecordingForElement(root)
		}
		isRecording = true;
	}
	
	def endRecording() {
		val result = newArrayList()
		for (root : rootToRecorder.keySet) {
			rootToRecorder.get(root).endRecording()
			result += rootToRecorder.get(root).changes
			if (logger.isDebugEnabled) logger.debug("End recording for " + root)
		}
		isRecording = false
		return result
	}
	
	private def void startRecordingForElement(EObject element) {
		if (!rootElements.contains(element)) {
			throw new IllegalStateException('''«element» was not recorded!''')
		}
		val recorder = rootToRecorder.get(element)
		if (recorder === null) {
			throw new IllegalStateException("Element " + element + " has no recorder")
		}
		recorder.beginRecording();
		logger.debug("Start recording for " + element)
	}
	
	private def void removeElementFromRecording(EObject element) {
		val recorder = rootToRecorder.remove(element)
		if (recorder.isRecording) {
			recorder.endRecording()
		}
		recorder.removeFromRecording(element)
		if (logger.isDebugEnabled) logger.debug("Abort recording for " + element)
	}
	
	override toString() '''
		Model repository contents:
			«FOR element : rootElements»
				«element», resource: «element.eResource?.URI»"
			«ENDFOR»
		'''
}
