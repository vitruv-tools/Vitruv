package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.uuid.UuidToEObjectRepository
import tools.vitruv.framework.uuid.UuidFactory
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.Resource
import org.apache.log4j.Logger
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*;
import org.eclipse.emf.transaction.TransactionalEditingDomain
import tools.vitruv.framework.util.command.EMFCommandBridge
import java.util.concurrent.Callable
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*

/**
 * {@link UuidGeneratorAndResolver}
 */
class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)
	final ResourceSet resourceSet;
	final UuidResolver parentUuidResolver;
	final boolean strictMode;
	UuidToEObjectRepository repository;
	UuidToEObjectRepository cache;

	/**
	 * Instantiates a UUID generator and resolver with no parent resolver, 
	 * the given {@link ResourceSet} for resolving objects
	 * and no {@link Resource} in which the mapping is stored.
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(ResourceSet resourceSet, boolean strictMode) {
		this(null, resourceSet, null, strictMode)
	}

	/**
	 * Instantiates a UUID generator and resolver with the given parent resolver, used when
	 * this resolver cannot resolve a UUID, the given {@link ResourceSet} for resolving objects
	 * and no {@link Resource} in which the mapping is stored.
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate mapping, may be null
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet, boolean strictMode) {
		this(parentUuidResolver, resourceSet, null, strictMode);
	}

	/**
	 * Instantiates a UUID generator and resolver with no parent resolver, 
	 * the given {@link ResourceSet} for resolving objects
	 * and the given {@link Resource} for storing the mapping.
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param uuidResource -
	 * 		the {@link Resource} to store the mapping in, may be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(ResourceSet resourceSet, Resource uuidResource, boolean strictMode) {
		this(null, resourceSet, uuidResource, strictMode);
	}

	/**
	 * Instantiates a UUID generator and resolver with the given parent resolver, used when
	 * this resolver cannot resolve a UUID, the given {@link ResourceSet} for resolving objects
	 * and the given {@link Resource} for storing the mapping.
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate mapping, may be null
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param uuidResource -
	 * 		the {@link Resource} to store the mapping in, may be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet, Resource uuidResource, boolean strictMode) {
		if (resourceSet === null) {
			throw new IllegalArgumentException("Resource set may not be null");
		}
		this.resourceSet = resourceSet;
		this.strictMode = strictMode;
		this.parentUuidResolver = if (parentUuidResolver !== null) {
			parentUuidResolver;
		} else {
			UuidResolver.EMPTY;
		}
		loadAndRegisterUuidProviderAndResolver(uuidResource);
		this.resourceSet.eAdapters.add(new ResourceRegistrationAdapter(
			[resource | this.loadUuidsFromParent(resource)]
		));
	}

	def private loadAndRegisterUuidProviderAndResolver(Resource uuidResource) {
		var UuidToEObjectRepository repository = if (uuidResource !== null)
				EcoreResourceBridge::getResourceContentRootIfUnique(uuidResource)?.dynamicCast(UuidToEObjectRepository,
					"uuid provider and resolver model")
		if (repository === null) {
			repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository;
			if (uuidResource !== null) {
				uuidResource.getContents().add(repository)
			}
		}
		this.repository = repository;
		this.cache = UuidFactory.eINSTANCE.createUuidToEObjectRepository;
	}

	override getUuid(EObject eObject) {
		val result = internalGetUuid(eObject);
		if (result === null) {
			throw new IllegalStateException("No UUID registered for EObject: " + eObject);
		}
		return result;
	}

	private def internalGetUuid(EObject eObject) {
		val localResult = repository.EObjectToUuid.get(eObject);
		if (localResult !== null) {
			return localResult;
		}
		val uri = EcoreUtil.getURI(eObject)
		if (uri !== null) {
			try {
				val resolvedObject = resourceSet.getEObject(uri, false);
				// The EClass check avoids that an objects of another type with the same URI is resolved
				// This is, for example, the case if a modifier in a UML model is changed, as it is only a
				// marker class that is replaced, having always the same URI on the same model element.
				if (resolvedObject !== null && resolvedObject.eClass == eObject.eClass) {
					val resolvedKey = repository.EObjectToUuid.get(resolvedObject);
					if (resolvedKey !== null) {
						return resolvedKey;
					}
				} else {
					// Finally look for a proxy in the repository (due to a deleted object) and match the URI
					for (proxyObject : repository.EObjectToUuid.keySet.filter[eIsProxy]) {
						if (EcoreUtil.getURI(proxyObject).equals(EcoreUtil.getURI(eObject))) {
							return repository.EObjectToUuid.get(proxyObject);
						}
					}
				}
			} catch (RuntimeException e) {
			}
		}
		
		if (eObject instanceof EClass) {
			return eObject.generateUuid;
		}
		
		return null;
	}

	override getEObject(String uuid) {
		val eObject = internalGetEObject(uuid);
		if (eObject === null) {
			throw new IllegalStateException("No EObject could be found for UUID: " + uuid);
		}
		return eObject;
	}
	
	override getPotentiallyCachedEObject(String uuid) {
		if (cache.uuidToEObject.containsKey(uuid)) {
			return cache.uuidToEObject.get(uuid);
		}
		return getEObject(uuid);
	}
	

	private def EObject internalGetEObject(String uuid) {
		val eObject = repository.uuidToEObject.get(uuid);
		if (eObject === null) {
			return null;
		}
		if (eObject.eIsProxy) {
			// Try to resolve the proxy. This can still lead to a valid proxy element if it is a proxy on purpose
			val resolvedObject = EcoreUtil.resolve(eObject, resourceSet);
			return resolvedObject;
		} else {
			return eObject;
		}
	}

	override String generateUuid(EObject eObject) {
		val uuid = if (cache.EObjectToUuid.containsKey(eObject)) {
			val uuid = cache.EObjectToUuid.removeKey(eObject);
			cache.uuidToEObject.remove(uuid);
			uuid;
		} else {
			generateUuid;
		}
		registerEObject(uuid, eObject);
		return uuid;
	}

	override String generateUuidWithoutCreate(EObject eObject) {
		val uuid = generateUuid(eObject);
		// Register UUID globally for third party elements that are statically accessible and are never created.
		// Since this is called in the moment when an element gets created, the object can only be globally resolved
		// if it a third party element.
		if (!parentUuidResolver.registerUuidForGlobalUri(uuid, EcoreUtil.getURI(eObject))) {
			if (strictMode) {
				throw new IllegalStateException("Object has no UUID and is not globally accessible: " + eObject);
			} else {
				logger.warn("Object is not statically accessible but also has no globally mapped UUID: " + eObject);
			}
		}
		return uuid;
	}

	private def String generateUuid() {
		return EcoreUtil.generateUUID();
	}

	override registerEObject(String uuid, EObject eObject) {
		logger.debug("Adding UUID " + uuid + " for EObject: " + eObject);
		val Callable<Void> registerEObjectCall = [|
			repository.EObjectToUuid.removeIf[value == uuid];
			repository.uuidToEObject.put(uuid, eObject);
			repository.EObjectToUuid.put(eObject, uuid);
			return null;
		];
		// If there is a TransactionalEditingDomain registered on the resource set, we have
		// to also execute our command on that domain, otherwise (e.g. in change tests),
		// there is no need to execute the command on a TransactionalEditingDomain. It can even
		// lead to errors if the ResourceSet is also modified by the test, as these modifications
		// would also have to be made on the TransactionalEditingDomain once it was created.
		val domain = transactionalEditingDomain;
		if (domain !== null && (eObject.eResource?.resourceSet === resourceSet || eObject instanceof EClass)) {
			EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(registerEObjectCall, domain);
		} else {
			registerEObjectCall.call;
		}
	}

	private def synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet);
	}

	override hasPotentiallyCachedEObject(String uuid) {
		if (cache.uuidToEObject.containsKey(uuid)) {
			return true;
		}
		return internalGetEObject(uuid) !== null;
	}

	override hasUuid(EObject object) {
		return internalGetUuid(object) !== null;
	}

	override hasEObject(String uuid) {
		return internalGetEObject(uuid) !== null;
	}

	override getResourceSet() {
		return resourceSet;
	}

	private def EObject resolveObject(URI uri) {
		// TODO Some domains modify the objects of freshly loaded resources (eg. JaMoPP sets the name of loaded
		// CompilationUnits). If we are not in a write transaction currently, this model change results in an exception
		// ("Cannot modify resource set without a write transaction"). Retrying the resolution will be able to find the
		// object in the now already loaded resource. This is not really safe, since the resource loading aborted
		// exceptionally earlier. However, in our case with JaMoPP this is not an issue currently.
		var EObject resolvedObject
		try {
			resolvedObject = resourceSet.getEObject(uri, true)
		} catch (Exception e) {
			// Retry:
			try {
				resolvedObject = resourceSet.getEObject(uri, true)
			} catch (Exception e2) {
			}
		}
		return resolvedObject // null if the resolution failed
	}

	override registerUuidForGlobalUri(String uuid, URI uri) {
		try {
			val localObject = resolveObject(uri)
			if (localObject !== null) {
				registerEObject(uuid, localObject);
				// Recursively do that
				return parentUuidResolver.registerUuidForGlobalUri(uuid, uri);
			}
		} catch (Exception e) {
		}
		return false;
	}

	override registerEObject(EObject eObject) {
		if (parentUuidResolver.hasUuid(eObject)) {
			registerEObject(parentUuidResolver.getUuid(eObject), eObject);
		} else if (hasUuid(eObject)) {
			registerEObject(getUuid(eObject), eObject);
		} else {
			// throw new IllegalStateException("Given EObject has no UUID yet: " + eObject);
		}
	}
	
	override getPotentiallyCachedUuid(EObject eObject) {
		if (cache.EObjectToUuid.containsKey(eObject)) {
			return cache.EObjectToUuid.get(eObject);
		} else if (cache.EObjectToUuid.exists[EcoreUtil.equals(eObject, key)]) {
			return cache.EObjectToUuid.findFirst[EcoreUtil.equals(eObject, key)].value;
		}
		return getUuid(eObject);
	}
	
	override hasPotentiallyCachedUuid(EObject eObject) {
		if (this.cache.EObjectToUuid.containsKey(eObject)) {
			return true;
		}
		return hasUuid(eObject);
	}
	
	override registerCachedEObject(EObject eObject) {
		val uuid = generateUuid;
		cache.EObjectToUuid.put(eObject, uuid);
		cache.uuidToEObject.put(uuid, eObject);
		return uuid;
	}
	
	override void loadUuidsToChild(UuidResolver childResolver, URI uri) {
		// Only load UUIDs if resource exists (a pathmap resource always exists)
		if (!(((uri.file || uri.platform) && uri.existsResourceAtUri) || uri.isPathmap)) {
			return;
		}
		for (element : childResolver.resourceSet.getResource(uri, true).allContents.toList) {
			// Not having a UUID is only supported for pathmap resources and currently Java root elements
			if (hasUuid(element)) {
				childResolver.registerEObject(getUuid(element), element);
			} else {
				if (uri.isPathmap || uri.fileExtension.equals("java")) {
					val resolvedObject = resolveObject(EcoreUtil.getURI(element));
					if (resolvedObject !== null) {
						childResolver.registerEObject(generateUuid(resolvedObject), element);
						// LayoutInformation in Java elements may not be equal in different UuidResolvers (VirtualModel and view), so ignore it	
					} else if (!element.eClass.name.contains("LayoutInformation")) {
						throw new IllegalStateException("Object could not be resolved in this UuidResolver's resource set: " + element);
					}
				} else {
					throw new IllegalStateException("Element does not have a UUID but should have one: " + element);
				}
			}
		}
	}
	
	def void loadUuidsFromParent(Resource resource) {
		parentUuidResolver.loadUuidsToChild(this, resource.URI);
	}

}
