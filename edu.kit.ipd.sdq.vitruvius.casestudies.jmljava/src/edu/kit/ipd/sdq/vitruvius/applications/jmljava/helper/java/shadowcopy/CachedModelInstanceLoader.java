package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.JaMoPPConcreteSyntax;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;

/**
 * Cached loader for model instances. A model instance is loaded by an URI.
 * After loading the model instance is stored in a cache. Any subsequent load
 * action on the same URI returns the same model.
 */
public class CachedModelInstanceLoader {

	/**
	 * ModelInstance, which does not need a VURI. The URI required for working
	 * with the model is taken from the resource. Thereby it does not cause any
	 * side effects on Vitruvius.
	 */
	private static class ModelInstanceWithoutVURI extends ModelInstance {

		/**
		 * Constructs the model instance by a resource, which has to contain an
		 * URI.
		 * 
		 * @param resource
		 *            The resource, which shall be used to load the model.
		 * @throws IllegalArgumentException
		 *             In case of a resource without URI.
		 */
		public ModelInstanceWithoutVURI(Resource resource) {
			super(null, resource);
			if (resource.getURI() == null) {
				throw new IllegalArgumentException();
			}
		}

		@Override
		public EObject getUniqueRootEObject() {
			return EcoreResourceBridge.getUniqueContentRoot(getResource(),
					getResource().getURI().toString());
		}

		@Override
		public <T extends EObject> T getUniqueRootEObjectIfCorrectlyTyped(
				Class<T> rootElementClass) {
			return EcoreResourceBridge.getUniqueContentRootIfCorrectlyTyped(
					getResource(), getResource().getURI().toString(),
					rootElementClass);
		}

		@Override
		public <T extends EObject> T getUniqueTypedRootEObject(
				Class<T> rootElementClass) {
			return EcoreResourceBridge.getUniqueTypedRootEObject(getResource(),
					getResource().getURI().toString(), rootElementClass);
		}

	}

	private final ResourceSet rs = new ResourceSetImpl();
	//private final ECrossReferenceAdapter crossReferenceAdapter;
	private final Map<URI, ModelInstance> loadedModels = new ConcurrentHashMap<URI, ModelInstance>();

	public CachedModelInstanceLoader() {
//		crossReferenceAdapter = new ECrossReferenceAdapter();
//		rs.eAdapters().add(crossReferenceAdapter);
	}

	/**
	 * Loads a model instance at the given URI. The model is added to a cache
	 * and returned on any subsequent method call with the same URI.
	 * 
	 * @param emfURI
	 *            The URI of the model to load.
	 * @return The loaded model instance.
	 */
	public ModelInstance loadModelInstance(final URI emfURI) {
		if (!loadedModels.containsKey(emfURI)) {
			Resource modelResource = EcoreResourceBridge.loadResourceAtURI(
					emfURI, rs,
					JaMoPPConcreteSyntax.getJaMoPPLayoutDisabledOptions());
			ModelInstance modelInstance = new ModelInstanceWithoutVURI(
					modelResource);
			loadedModels.put(emfURI, modelInstance);
		}
		return loadedModels.get(emfURI);
	}

	/**
	 * @return All cached model instances.
	 */
	public Collection<ModelInstance> getAllCachedModelInstances() {
		return loadedModels.values();
	}

	/**
	 * Resolves all proxies of the cached model instances.
	 */
	public void resolveAll() {
		EcoreUtil.resolveAll(rs);
	}

	/**
	 * @return Unresolved cross references.
	 */
	public Map<EObject, Collection<EStructuralFeature.Setting>> findUnresolvedReferences() {
			return JaMoPPUnresolvedProxyCrossReferencer.find(rs);
	}

	/**
	 * Finds all references to the given object in the cached model instances.
	 * The returned collection EXCLUDES the containment reference.
	 * 
	 * @param obj
	 *            The referenced object.
	 * @return A collection of settings, which contain the referencing object
	 *         and its structural feature
	 */
	public Collection<Setting> findObjectReferences(EObject obj) {
//		Collection<Setting> crossReferences = crossReferenceAdapter
//				.getInverseReferences(obj);
//		for (Iterator<Setting> iter = crossReferences.iterator(); iter
//				.hasNext();) {
//			Setting setting = iter.next();
//			if (setting.getEObject() == obj.eContainer()
//					&& setting.getEStructuralFeature() == obj
//							.eContainmentFeature()) {
//				iter.remove();
//			}
//		}
//		return crossReferences;
		return UsageCrossReferencer.find(obj, rs);
	}
}
