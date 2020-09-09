package tools.vitruv.framework.vsum.repositories

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.TuidAwareVitruvDomain
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.ModelInstance
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.vsum.ModelRepository

class TuidResolverImpl implements tools.vitruv.framework.tuid.TuidResolver{
	val VitruvDomainRepository domainRepository;
	val ModelRepository modelRepository;

	new(VitruvDomainRepository domainRepository, ModelRepository modelRepository) {
		this.domainRepository = domainRepository;
		this.modelRepository = modelRepository;
	}

	def private TuidAwareVitruvDomain getMetamodelHavingTuid(Tuid tuid) {
		return this.domainRepository.getDomain(tuid)
	}

	override EObject resolveEObjectFromTuid(Tuid tuid) {
		if (tuid.isMetaElementTuid) {
			// Meta element Tuids cannot be resolved
			return null;
		}
		val TuidAwareVitruvDomain domain = getMetamodelHavingTuid(tuid)
		var VURI vuri = domain.getModelVURIContainingIdentifiedEObject(tuid)
		var ModelInstance modelInstance = null
		if (vuri !== null) {
			modelInstance = this.modelRepository.getModel(vuri)
		}
		var EObject resolvedEobject = null
		try {
			resolvedEobject = resolveEObjectInModelInstance(modelInstance, tuid);
		} catch (IllegalArgumentException iae) {
			// do nothing - just try the solving again
		}
		if (null === resolvedEobject && modelInstance !== null) {
			// reload the model and try to solve it again
			try {
				modelInstance.load(null, true)
			} catch (IllegalStateException e) {
				throw new IllegalStateException("TUID " + tuid + " cannot be resolved, because the resource " + modelInstance.resource.URI + " cannot be loaded.")
			}
			resolvedEobject = resolveEObjectInModelInstance(modelInstance, tuid);
			if (null === resolvedEobject) {
				// if resolved EObject is still null throw an exception
				// TODO think about something more lightweight than throwing an exception
				throw new RuntimeException('''Could not resolve Tuid «tuid» in eObjects «
				»«modelInstance.rootElements» with VURI «vuri»'''.toString)
			}

		}
		if (null !== resolvedEobject && resolvedEobject.eIsProxy()) {
			EcoreUtil::resolve(resolvedEobject, modelInstance.resource)
		}
		return resolvedEobject
	}

	private def EObject resolveEObjectInModelInstance(ModelInstance modelInstance, Tuid tuid) {
		val TuidAwareVitruvDomain domain = getMetamodelHavingTuid(tuid)
		// if the tuid is cached because it has no resource the rootEObject is null
		var rootEObjects = if(modelInstance !== null) modelInstance.rootElements + #[null] else #[null];
		return rootEObjects.map[domain.resolveEObjectFromRootAndFullTuid(it, tuid)].findFirst[it !== null];
	}
}
