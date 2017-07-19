package tools.vitruv.framework.vsum.repositories

import java.util.Map
import java.util.Set

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.List

class ModelRepositoryImpl implements ModelRepositoryInterface {
	static extension Logger = Logger::getLogger(ModelRepositoryImpl)
	/**
	 * Setting this VM argument, propagated changes get unresolved.
	 * The necessary property setting is "-DunresolvePropagatedChanges"
	 */
	static val VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES = "unresolvePropagatedChanges"
	val Set<EObject> rootElements
	val Map<EObject, AtomicEmfChangeRecorder> rootToRecorder
	boolean isRecording = false

	private static def VURI getVURI(Resource resource) {
		VURI::getInstance(resource)
	}

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

	override boolean unresolveChanges() {
		val unresolvePropagatedChanges = System::getProperty(VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES)
		return unresolvePropagatedChanges !== null
	}

	override void addRootElement(EObject rootElement) {
		rootElements += rootElement
		debug("New root in repository " + rootElement)
		if (isRecording)
			startRecordingForElement(rootElement)
	}

	override void cleanupRootElements() {
		val elementsToRemove = newArrayList()
		for (rootElement : rootElements) {
			if (rootElement.eContainer !== null && !(rootElement.eContainer instanceof ChangeDescriptionImpl))
				elementsToRemove += rootElement
		}
		elementsToRemove.forEach [
			removeElementFromRecording(it)
			debug("Remove root from repository " + it)
			rootElements.remove(it)
		]
	}

	override void cleanupRootElementsWithoutResource() {
		val elementsToRemove = newArrayList()
		for (rootElement : rootElements) {
			if (null === rootElement.eResource)
				elementsToRemove += rootElement
		}
		elementsToRemove.forEach [
			debug("Remove root without resource from repository " + it)
			rootElements.remove(it)
		]
	}

	override void startRecording() {
		rootElements.forEach[startRecordingForElement]
		isRecording = true
	}

	override endRecording() {
		val List<VitruviusChange> result = newArrayList
		rootToRecorder.entrySet.forEach [
			value.endRecording
			result += if (value.unresolvedChanges !== null) value.unresolvedChanges else value.resolvedChanges
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
		val vuri = element.eResource?.VURI
		recorder.beginRecording(vuri, #[element])
		rootToRecorder.put(element, recorder)
		debug("Start recording for " + element)
	}

	private def void removeElementFromRecording(EObject element) {
		val recorder = rootToRecorder.get(element)
		if (recorder !== null)
			recorder.stopRecording
		rootToRecorder.remove(element)
		debug("Abort recording for " + element)
	}

}
