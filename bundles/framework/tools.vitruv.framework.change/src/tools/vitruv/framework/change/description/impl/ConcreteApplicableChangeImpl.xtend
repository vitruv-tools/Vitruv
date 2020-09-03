package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidResolver

import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import tools.vitruv.framework.change.description.VitruviusChange

class ConcreteApplicableChangeImpl extends ConcreteChangeImpl {
	var VURI vuri;

	new(EChange eChange) {
		super(eChange);
		tryToSetUri;
	}

	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		this.EChange = this.EChange.resolveBefore(uuidResolver)
		tryToSetUri;
		this.registerOldObjectTuidsForUpdate(getObjectsWithPotentiallyModifiedTuids)
		this.EChange.applyForward;
		tryToSetUri;
		this.updateTuids
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		this.EChange = this.EChange.resolveAfter(uuidResolver)
		tryToSetUri;
		this.registerOldObjectTuidsForUpdate(getObjectsWithPotentiallyModifiedTuids)
		this.EChange.applyBackward;
		tryToSetUri;
		this.updateTuids
	}

	private def void tryToSetUri() {
		val eChange = EChange;
		var resolvedResources = affectedNotReferencedEObjects.map[eResource].filterNull.toList;
		if (eChange instanceof RootEChange) {
			resolvedResources += eChange.resource;
		}
		if (resolvedResources.size > 0) {
			this.vuri = VURI.getInstance(resolvedResources.get(0));
			return;
		}
		val proxyUris = affectedNotReferencedEObjects.filter(InternalEObject).map[eProxyURI].filterNull.filter[segmentCount > 0]
		if (proxyUris.size > 0) {
			this.vuri = VURI.getInstance(proxyUris.get(0).trimFragment);
			return;
		}
	}

	override getURI() {
		return vuri;
	}

	private def getObjectsWithPotentiallyModifiedTuids() {
		// We currently support 3 hierarchy layers upwards update. This is necessary
		// e.g. for Operations whose TUIDs depend on the values of their parameter type references.
		// This number of layers may still be too few, this is just a random number.
		this.affectedEObjects.map[#{it, it.eContainer, it.eContainer?.eContainer, it.eContainer?.eContainer?.eContainer}].flatten.filterNull.toSet
	}

	private def void registerOldObjectTuidsForUpdate(Iterable<EObject> objects) {
		val tuidManager = TuidManager.instance
		for (object : objects) {
			tuidManager.registerObjectUnderModification(object)
		}
	}

	private def void updateTuids() {
		TuidManager.instance.updateTuidsOfRegisteredObjects
		TuidManager.instance.flushRegisteredObjectsUnderModification
	}

	override unresolveIfApplicable() {
		EChanges.forEach[EChangeUnresolver.unresolve(it)]
	}

	override equals(Object object) {
		return object.isEqual // delegate to dynamic dispatch
	}

	override changedEObjectEquals(VitruviusChange change) {
		return change.isChangedEObjectEqual
	}

	private def dispatch boolean isEqual(Object object) { super.equals(object) } // use super implementation if anything else than ConcreteApplicableChangeImpl

	private def dispatch boolean isEqual(ConcreteApplicableChangeImpl change) {
		return vuri == change.URI && EcoreUtil.equals(EChange, change.EChange)
	}

	private def dispatch boolean isChangedEObjectEqual(VitruviusChange change) { super.changedEObjectEquals(change) }

	private def dispatch boolean isChangedEObjectEqual(ConcreteApplicableChangeImpl change) {
		return vuri == change.URI && EcoreUtil.equals(EChange.involvedEObjects, change.EChange.involvedEObjects)
	}
}
