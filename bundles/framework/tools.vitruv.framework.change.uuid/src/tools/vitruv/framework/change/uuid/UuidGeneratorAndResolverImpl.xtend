package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.uuid.UuidToEObjectRepository
import tools.vitruv.framework.change.uuid.UuidFactory
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.Resource
import org.apache.log4j.Logger
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*;
import org.eclipse.emf.transaction.TransactionalEditingDomain
import tools.vitruv.framework.util.command.EMFCommandBridge
import java.util.concurrent.Callable

class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)
	UuidToEObjectRepository repository;
	ResourceSet resourceSet;
	Resource uuidResource;

	new(ResourceSet resourceSet, Resource uuidResource) {
		this.uuidResource = uuidResource;
		this.resourceSet = resourceSet;
		loadAndRegisterUuidProviderAndResolver(uuidResource);
	}

	def private loadAndRegisterUuidProviderAndResolver(Resource uuidResource) {
		var UuidToEObjectRepository repository = if(uuidResource !== null)
				EcoreResourceBridge::getResourceContentRootIfUnique(uuidResource)?.dynamicCast(UuidToEObjectRepository,
					"uuid provider and resolver model")
		if(repository === null) {
			repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository;
			if(uuidResource !== null) {
				uuidResource.getContents().add(repository)
			}
		}
		this.repository = repository;
	}

	override getUuid(EObject eObject) {
		val result = internalGetUuid(eObject);
		if(result === null) {
			throw new IllegalStateException("No UUID registered for EObject: " + eObject);
		}
		return result;
	}

	private def internalGetUuid(EObject eObject) {
		val localResult = repository.EObjectToUuid.get(eObject);
		if(localResult !== null) {
			return localResult;
		}
		val uri = EcoreUtil.getURI(eObject)
		if(uri !== null) {
			try {
				val resolvedObject = resourceSet.getEObject(uri, false);
				// The EClass check avoids that an objects of another type with the same URI is resolved
				// This is, for example, the case if a modifier in a UML model is changed, as it is only a
				// marker class that is replaced, having always the same URI on the same model element.
				if(resolvedObject !== null && resolvedObject.eClass == eObject.eClass) {
					val resolvedKey = repository.EObjectToUuid.get(resolvedObject);
					if(resolvedKey !== null) {
						return resolvedKey;
					}
				} else {
					// Finally look for a proxy in the repository (due to a deleted object) and match the URI
					for (proxyObject : repository.EObjectToUuid.keySet.filter[eIsProxy]){
						if (EcoreUtil.getURI(proxyObject).equals(EcoreUtil.getURI(eObject))) {
							return repository.EObjectToUuid.get(proxyObject);
						}
					}
				}
			} catch(RuntimeException e) {
				return null;
			}
		}
		return null;
	}

	override getEObject(String uuid) {
		val eObject = repository.uuidToEObject.get(uuid);
		if (eObject === null) {
			throw new IllegalStateException();
		}
		if(eObject.eIsProxy) {
			val resolvedObject = EcoreUtil.resolve(eObject, resourceSet);
			if(resolvedObject === null || resolvedObject.eIsProxy) {
				throw new IllegalStateException("No EObject could be found for UUID: " + uuid);
			}
			return resolvedObject;
		} else {
			return eObject;
		}
	}

	override String registerEObject(EObject eObject) {
		val uuid = generateUuid;
		registerEObject(uuid, eObject);
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
		if (domain !== null && eObject.eResource?.resourceSet === resourceSet) {
			EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(registerEObjectCall, domain);
		} else {
			registerEObjectCall.call;
		}
	}

	private def synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
		return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet);
	}

	override hasUuid(EObject object) {
		return internalGetUuid(object) !== null;
	}

	override getOrRegisterUuid(EObject object) {
		val existingUuid = internalGetUuid(object);
		if(existingUuid === null) {
			return registerEObject(object);
		} else {
			return existingUuid;
		}
	}
	
	override getResourceSet() {
		return resourceSet;
	}

}
