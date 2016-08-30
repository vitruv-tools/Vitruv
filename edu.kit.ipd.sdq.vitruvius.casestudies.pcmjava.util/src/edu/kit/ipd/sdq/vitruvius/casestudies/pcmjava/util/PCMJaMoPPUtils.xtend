package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util

import com.google.common.collect.Sets
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUID
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants

class PCMJaMoPPUtils {
	private static val Logger logger = Logger.getLogger(PCMJaMoPPUtils.simpleName)

	static Set<Class<?>> pcmJavaRootObjects = Sets.newHashSet(Repository, System, Package, CompilationUnit)

	protected new() {
	}

	/**
	 * Checks whether the affectedAttribute is in the featureCorrespondenceMap and returns all corresponding objects, 
	 * if any. otherwise null is returned.
	 */
	def static checkKeyAndCorrespondingObjects(EObject eObject, EStructuralFeature affectedEFeature,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap, CorrespondenceModel correspondenceModel) {
		if (!featureCorrespondenceMap.containsKey(affectedEFeature)) {
			logger.debug("no feature correspondence found for affectedEeature: " + affectedEFeature)
			return null
		}
		var correspondingEObjects = correspondenceModel.getCorrespondingEObjects(eObject)
		if (correspondingEObjects.nullOrEmpty) {
			logger.info("No corresponding objects found for " + eObject)
		}
		return correspondingEObjects
	}

	def private static boolean eObjectInstanceOfRootEObject(EObject object, Set<Class<? extends EObject>> classes) {
		for (c : classes) {
			if (c.isInstance(object)) {
				return true
			}
		}
		return false
	}

	/**
	 * Signatures are considered equal if methods have the same name, the same parameter types and the same return type
	 * We do not consider modifiers (e.g. public or private here)
	 */
	public static def boolean hasSameSignature(Method method1, Method method2) {
		if (method1 == method2) {
			return true
		}
		if (!method1.name.equals(method2.name)) {
			return false
		}
		if (!method1.typeReference.hasSameTargetReference(method2.typeReference)) {
			return false
		}
		if (method1.parameters.size != method2.parameters.size) {
			return false
		}
		var int i = 0
		for (param1 : method1.parameters) {
			if (!hasSameTargetReference(param1.typeReference, method2.parameters.get(i).typeReference)) {
				return false
			}
			i++
		}
		return true
	}

	public static def boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
		if (reference1 == reference2 || reference1.equals(reference2)) {
			return true
		}
		val target1 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference1)
		val target2 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference2)
		return target1 == target2 || target1.equals(target2)
	}

	def dispatch static addRootChangeToTransformationResult(Repository repo, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, TransformationResult transformationResult) {
		handlePCMRootEObject(repo, sourceModelVURI, correspondenceModel, PCMNamespace.REPOSITORY_FILE_EXTENSION,
			transformationResult)
	}

	def dispatch static addRootChangeToTransformationResult(System system, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, TransformationResult transformationResult) {
		handlePCMRootEObject(system, sourceModelVURI, correspondenceModel, PCMNamespace.SYSTEM_FILE_EXTENSION,
			transformationResult)
	}

	def dispatch static addRootChangeToTransformationResult(JavaRoot newJavaRoot,
		CorrespondenceModel correspondenceModel, VURI sourceModelVURI, TransformationResult transformationResult) {
		// TODO: use configured src-folder path instead of hardcoded "src"
		val String srcFolderPath = getFolderPathInProjectOfResource(sourceModelVURI, "src");
		var String javaRootPath = newJavaRoot.getNamespacesAsString().replace(".", "/").replace("$", "/") +
			newJavaRoot.getName().replace("$", ".");
		if (newJavaRoot instanceof Package) {
			javaRootPath = javaRootPath + "/package-info.java";
		}
		val VURI cuVURI = VURI.getInstance(srcFolderPath + javaRootPath);
		transformationResult.addRootEObjectToSave(newJavaRoot, cuVURI)
	}

	def static VURI getSourceModelVURI(EObject eObject) {
		if (null == eObject || null == eObject.eResource) {
			logger.warn("can not get SourceModelVURI cause eObject or its resource is null: " + eObject)
			return VURI.getInstance("")
		}
		val VURI vuri = VURI.getInstance(eObject.eResource)
		return vuri
	}

	def private static void handlePCMRootEObject(NamedElement namedElement, VURI sourceModelVURI,
		CorrespondenceModel correspondenceModel, String fileExt, TransformationResult transformationResult) {
		var String folderName = getFolderPathInProjectOfResource(sourceModelVURI, "model");
		val String fileName = namedElement.getEntityName() + "." + fileExt;
		if (!folderName.endsWith("/")) {
			folderName = folderName + "/";
		}
		val VURI vuri = VURI.getInstance(folderName + fileName);
		transformationResult.addRootEObjectToSave(namedElement, vuri)
	}

	def dispatch static handleRootChanges(Iterable<EObject> eObjects, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
		eObjects.forEach [ eObject |
			handleSingleRootChange(eObject, correspondenceModel, sourceModelVURI, transformationResult, vuriToDelete,
				oldTUID)
		]
	}

	def dispatch static handleRootChanges(EObject[] eObjects, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
		eObjects.forEach [ eObject |
			handleSingleRootChange(eObject, correspondenceModel, sourceModelVURI, transformationResult, vuriToDelete,
				oldTUID)
		]
	}

	def dispatch static handleRootChanges(EObject eObject, CorrespondenceModel correspondenceModel,
		VURI sourceModelVURI, TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
		handleSingleRootChange(eObject, correspondenceModel, sourceModelVURI, transformationResult, vuriToDelete,
			oldTUID)
	}

	def static handleSingleRootChange(EObject eObject, CorrespondenceModel correspondenceModel, VURI sourceModelVURI,
		TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
		EcoreUtil.remove(eObject)
		PCMJaMoPPUtils.addRootChangeToTransformationResult(eObject, correspondenceModel, sourceModelVURI,
			transformationResult)
		correspondenceModel.updateTUID(oldTUID, eObject)
		transformationResult.addVURIToDeleteIfNotNull(vuriToDelete)
	}

	private static def String getFolderPathInProjectOfResource(VURI sourceModelVURI, String folderName) {
		val IFile fileSourceModel = EMFBridge.getIFileForEMFUri(sourceModelVURI.getEMFUri());
		val IProject projectSourceModel = fileSourceModel.getProject();
		var String srcFolderPath = projectSourceModel.getFullPath().toString() + "/" + folderName + "/";
		if (srcFolderPath.startsWith("/")) {
			srcFolderPath = srcFolderPath.substring(1, srcFolderPath.length());
		}
		return srcFolderPath;
	}

	public dispatch static def getNameFromPCMDataType(PrimitiveDataType primitiveDataType) {
		return primitiveDataType.type.getName
	}

	public dispatch static def getNameFromPCMDataType(CollectionDataType collectionDataType) {
		return collectionDataType.entityName
	}

	public dispatch static def getNameFromPCMDataType(CompositeDataType compositeDataType) {
		return compositeDataType.entityName
	}

	def public static deleteNonRootEObjectInList(EObject affectedEObject, EObject oldEObject,
		CorrespondenceModel correspondenceModel) {
		val transformationResult = new TransformationResult
		removeCorrespondenceAndAllObjects(oldEObject, affectedEObject, correspondenceModel, pcmJavaRootObjects)
		return transformationResult
	}

	def static TransformationResult removeCorrespondenceAndAllObjects(EObject object, EObject exRootObject,
		CorrespondenceModel correspondenceModel) {
		removeCorrespondenceAndAllObjects(object, exRootObject, correspondenceModel, pcmJavaRootObjects)
	}

	def static TransformationResult removeCorrespondenceAndAllObjects(EObject object, EObject exRootObject,
		CorrespondenceModel correspondenceModel, Set<Class<?>> rootObjects) {
		var Set<Correspondence> correspondences = null
		if (null != exRootObject) {
			val rootTUID = correspondenceModel.calculateTUIDFromEObject(exRootObject)
			val String prefix = rootTUID.toString
			EcoreUtil.remove(object)
			val tuid = correspondenceModel.calculateTUIDFromEObject(object, exRootObject, prefix)
			correspondences = correspondenceModel.
				removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(tuid.toSet)
		} else {
			correspondences = correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(object.toSet)
		}
		val transformationResult = new TransformationResult
		for (correspondence : correspondences) {
			resolveAndRemoveEObject(correspondence.getATUIDs, correspondenceModel, transformationResult, rootObjects)
			resolveAndRemoveEObject(correspondence.getBTUIDs, correspondenceModel, transformationResult, rootObjects)
		}
		return transformationResult
	}

	def private static resolveAndRemoveEObject(Iterable<TUID> tuids, CorrespondenceModel correspondenceModel,
		TransformationResult transformationResult, Set<Class<?>> rootObjectClasses) {
		for (tuid : tuids) {
			try {
				val eObject = correspondenceModel.resolveEObjectFromTUID(tuid)
				if (null != eObject) {
					EcoreUtil.delete(eObject)
				}
				if (eObject.isInstanceOfARootClass(rootObjectClasses)) {
					val vuri = tuid.getVURIFromTUID()
					transformationResult.addVURIToDeleteIfNotNull(vuri)
				}
			} catch (RuntimeException e) {
				// ignore runtime exception during object deletion
			}
		}
	}

	def private static boolean isInstanceOfARootClass(EObject eObject, Set<Class<?>> rootObjectClasses) {
		for (rootClass : rootObjectClasses) {
			if (rootClass.isInstance(eObject)) {
				return true
			}
		}
		return false
	}

	/**
	 * returns the VURI of the second part of the TUID (which should be the VURI String) 
	 */
	def private static VURI getVURIFromTUID(TUID tuid) {
		val segments = tuid.toString.split(VitruviusConstants.getTUIDSegmentSeperator)
		if (2 <= segments.length) {
			val key = segments.get(1)
			return VURI.getInstance(key)
		}
		return null
	}
	
	def static updateNameAttribute(Set<EObject> correspondingEObjects, Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceModel correspondenceModel, boolean saveFilesOfChangedEObjects,
		Set<Class<? extends EObject>> classesOfRootObjects) {
			val EStructuralFeature eStructuralFeature = featureCorrespondenceMap.claimValueForKey(affectedFeature)

			val boolean rootAffected = correspondingEObjects.exists [ eObject |
				eObjectInstanceOfRootEObject(eObject, classesOfRootObjects)
			]
			if (rootAffected) {
				logger.error("The method updateNameattribut is not able to rename root objects")
				return
			}
			for (EObject correspondingObject : correspondingEObjects) {
				if (null == correspondingObject) {
					logger.error("corresponding object is null")
				} else {
					val TUID oldTUID = correspondenceModel.calculateTUIDFromEObject(correspondingObject)
					correspondingObject.eSet(eStructuralFeature, newValue)
					correspondenceModel.updateTUID(oldTUID, correspondingObject)
					if (saveFilesOfChangedEObjects) {
						// nothing to do here?
					}
				}
			}
		}
}
