package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.change.echange.root.RootEChange
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
		this.EChange.applyForward;
		tryToSetUri;
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		this.EChange = this.EChange.resolveAfter(uuidResolver)
		tryToSetUri;
		this.EChange.applyBackward;
		tryToSetUri;
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
