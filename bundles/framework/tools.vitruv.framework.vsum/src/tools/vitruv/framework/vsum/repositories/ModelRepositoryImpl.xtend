package tools.vitruv.framework.vsum.repositories

import java.util.List
import java.util.Map
import java.util.Set

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.change.recording.impl.AtomicEmfChangeRecorderImpl
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.xtend.lib.annotations.Accessors

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

	@Accessors(PUBLIC_GETTER)
	val List<VitruviusChange> lastResolvedChanges
	@Accessors(PUBLIC_GETTER)
	val List<VitruviusChange> lastUnresolvedChanges

	private static def VURI getVURI(Resource resource) {
		VURI::getInstance(resource)
	}

	new() {
		lastResolvedChanges = newArrayList
		lastUnresolvedChanges = newArrayList
		rootElements = newHashSet
		rootToRecorder = newHashMap
	}

	override toString() '''
		
			Model repository contents:
			«FOR element : rootElements»
				«element», resource: «element.eResource?.URI»"
			«ENDFOR»
	'''

	override unresolveChanges() {
		val unresolvePropagatedChanges = System::getProperty(VM_ARGUMENT_UNRESOLVE_PROPAGATED_CHANGES)
		return unresolvePropagatedChanges !== null
	}

	override addRootElement(EObject rootElement) {
		rootElements += rootElement
		debug("New root in repository " + rootElement)
		if (isRecording)
			startRecordingForElement(rootElement)
	}

	override cleanupRootElements() {
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

	override cleanupRootElementsWithoutResource() {
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

	override startRecording() {
		rootElements.forEach[startRecordingForElement]
		isRecording = true
	}

	override endRecording() {
		lastResolvedChanges.clear
		lastUnresolvedChanges.clear
		val List<VitruviusChange> result = newArrayList
		rootToRecorder.entrySet.forEach [
			value.endRecording
			val isUnresolved = value.unresolvedChanges !== null
			result += if (isUnresolved) value.unresolvedChanges else value.resolvedChanges
			lastResolvedChanges += value.resolvedChanges
			lastUnresolvedChanges += value.unresolvedChanges
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
		rootToRecorder.get(element)?.stopRecording
		rootToRecorder.remove(element)
		debug("Abort recording for " + element)
	}

}
