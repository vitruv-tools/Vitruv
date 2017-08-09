package tools.vitruv.framework.change.description.impl

import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
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
class LegacyEMFModelChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	static extension TuidManager = TuidManager::instance
	private final ChangeDescription changeDescription;
	private var boolean canBeBackwardsApplied;
	private val VURI savedURI;

	public new(ChangeDescription changeDescription, Iterable<EChange> eChanges) {
		this.changeDescription = changeDescription;
		// Save an URI of the changes: if only a remove change is given, the URI is null otherwise
		// but here the change is not applied yet, so the URI can be extracted
		val changeUris = eChanges.map[URI].filterNull;
		this.savedURI = if (!changeUris.empty) changeUris.get(0);
		this.canBeBackwardsApplied = false;
		addChanges(eChanges);
	}

	private def ChangeDescription getChangeDescription() {
		return this.changeDescription;
	}

	override String toString() '''
		«LegacyEMFModelChangeImpl.simpleName»: VURI «this.URI», EChanges:
			«FOR eChange : EChanges»
				Inner change: «eChange»
			«ENDFOR»
	'''

	override getURI() {
		val changeUris = changes.map[URI].filterNull
		return if (!changeUris.empty) changeUris.get(0) else return savedURI;
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
		updateTuidsOfRegisteredObjects
		flushRegisteredObjectsUnderModification
	}

	def applyForwardWithoutTuidUpdate() throws IllegalStateException {
		if (canBeBackwardsApplied)
			throw new IllegalStateException("Change " + this +
				" cannot be applied forwards as was not backwards applied before.")
		changeDescription.applyAndReverse
		canBeBackwardsApplied = true
	}

	private def applyChange() {
		registerOldObjectTuidsForUpdate
		changeDescription.applyAndReverse
		updateTuids
	}

	private def registerOldObjectTuidsForUpdate() {
		val objects = new HashSet<EObject>
		objects.addAll(changeDescription.objectChanges.keySet.filterNull)
		objects.addAll(changeDescription.objectChanges.values.flatten.map[referenceValue].filterNull)
		objects.addAll(changeDescription.objectsToDetach.filterNull)
		objects.addAll(changeDescription.objectsToAttach.filterNull)
		// Add container objects
		val containerObjects = objects.map[eContainer].filterNull
		for (EObject object : objects + containerObjects) {
			registerObjectUnderModification(object)
		}
	}

	private def addChanges(Iterable<EChange> eChanges) {
		eChanges.forEach[addChange(VitruviusChangeFactory::instance.createConcreteChange(it))]
		if (changes.empty)
			addChange(VitruviusChangeFactory::instance.createEmptyChange(null))
	}
}
