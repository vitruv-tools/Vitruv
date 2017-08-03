package tools.vitruv.framework.vsum.repositories

import java.util.List
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl

class ModelRepositoryImpl {
	static extension Logger = Logger::getLogger(ModelRepositoryImpl)
	/**
	 * Setting this VM argument, propagated changes get unresolved.
	 * The necessary property setting is "-DunresolvePropagatedChanges"
	 */
	static val VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES = "unresolvePropagatedChanges"
	val Set<EObject> rootElements
	val Map<EObject, AtomicEmfChangeRecorder> rootToRecorder
	boolean isRecording = false

	new() {
		rootElements = newHashSet
		rootToRecorder = newHashMap
	}

	override toString() '''
		Model repository contents:
		«FOR element : rootElements»
			«element», resource: «element.eResource?.URI»"
		«ENDFOR»
	'''

	def unresolveChanges() {
		val unresolvePropagatedChanges = System::getProperty(VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES)
		return unresolvePropagatedChanges !== null
	}

	def addRootElement(EObject rootElement) {
		rootElements += rootElement
		debug("New root in repository " + rootElement)
		if (isRecording)
			startRecordingForElement(rootElement)
	}

	def cleanupRootElements() {
		val elementsToRemove = newArrayList
		rootElements.filter[eContainer !== null && !(eContainer instanceof ChangeDescriptionImpl)].forEach [
			elementsToRemove += it
		]
		elementsToRemove.forEach [
			removeElementFromRecording(it)
			debug("Remove root from repository " + it)
			rootElements -= it
		]
	}

	def cleanupRootElementsWithoutResource() {
		val elementsToRemove = newArrayList
		rootElements.filter[null === eResource].forEach[elementsToRemove += it]
		elementsToRemove.forEach [
			debug("Remove root without resource from repository " + it)
			rootElements -= it
		]
	}

	def startRecording() {
		rootElements.forEach[startRecordingForElement]
		isRecording = true
	}

	def endRecording() {
		val List<VitruviusChange> result = newArrayList
		rootToRecorder.entrySet.forEach [
			value.endRecording
			val isUnresolved = value.unresolvedChanges !== null
			result += if (isUnresolved) value.unresolvedChanges else value.resolvedChanges
			debug("End recording for " + key)
		]
		rootToRecorder.clear
		isRecording = false
		return result
	}

	private def void startRecordingForElement(EObject element) {
		if (!rootElements.contains(element))
			throw new IllegalStateException
		if (rootToRecorder.containsKey(element))
			throw new IllegalStateException("Duplicate recording on element")
		val isUnresolved = unresolveChanges
		info('''unresolvePropagatedChanges «isUnresolved»''')
		val AtomicEmfChangeRecorder recorder = new AtomicEmfChangeRecorderImpl(isUnresolved, false)
		recorder.addToRecording(element)
		recorder.beginRecording
		rootToRecorder.put(element, recorder)
		debug("Start recording for " + element)
	}

	private def void removeElementFromRecording(EObject element) {
		rootToRecorder.get(element)?.stopRecording
		rootToRecorder.remove(element)
		debug("Abort recording for " + element)
	}
}
