package tools.vitruv.framework.change.description.impl

import java.io.Serializable
import java.util.HashSet

import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Represents a change in an EMF model. This change has to be instantiated when the model is in the state
 * right before the change described by the recorded {@link ChangeDescription}.
 * 
 * @author 
 * @version 0.2.0
 * @since 
 */
class LegacyEMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange, Serializable {
	val transient ChangeDescription changeDescription
	val VURI vuri
	var boolean canBeBackwardsApplied

	new(ChangeDescription changeDescription, Iterable<EChange> eChanges, VURI vuri) {
		this.changeDescription = changeDescription
		this.vuri = vuri
		canBeBackwardsApplied = false
		addChanges(eChanges)
	}

	override toString() '''
		«EMFModelChangeImpl.simpleName»: VURI «this.vuri», EChanges:
			«FOR eChange : EChanges»
				Inner change: «eChange»
			«ENDFOR»
	'''

	override getURI() {
		vuri
	}

	override containsConcreteChange() {
		true
	}

	override validate() {
		true
	}

	override applyBackward() throws IllegalStateException {
		if (!canBeBackwardsApplied)
			throw new IllegalStateException("Change " + this +
				" cannot be applied backwards as was not forward applied before.")
		applyChange
		canBeBackwardsApplied = false
	}

	override applyForward() throws IllegalStateException {
		if (canBeBackwardsApplied) {
			throw new IllegalStateException("Change " + this +
				" cannot be applied forwards as was not backwards applied before.")
		}
		applyChange
		canBeBackwardsApplied = true
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		applyForward
	}

	override applyBackwardIfLegacy() {
		applyBackward
	}

	protected def void updateTuids() {
		TuidManager::instance.updateTuidsOfRegisteredObjects
		TuidManager::instance.flushRegisteredObjectsUnderModification
	}

	private def applyChange() {
		registerOldObjectTuidsForUpdate
		changeDescription.applyAndReverse
		updateTuids
	}

	private def registerOldObjectTuidsForUpdate() {
		val tuidManager = TuidManager.instance
		val objects = new HashSet<EObject>
		objects.addAll(changeDescription.objectChanges.keySet.filterNull)
		objects.addAll(changeDescription.objectChanges.values.flatten.map[referenceValue].filterNull)
		objects.addAll(changeDescription.objectsToDetach.filterNull)
		objects.addAll(changeDescription.objectsToAttach.filterNull)
		// Add container objects
		val containerObjects = objects.map[eContainer].filterNull
		for (EObject object : objects + containerObjects) {
			tuidManager.registerObjectUnderModification(object)
		}
	}

	private def addChanges(Iterable<EChange> eChanges) {
		eChanges.forEach[addChange(VitruviusChangeFactory::instance.createConcreteChange(it, vuri))]
		if (changes.empty)
			addChange(VitruviusChangeFactory::instance.createEmptyChange(vuri))
	}
}
