package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
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
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import org.emftext.language.java.containers.CompilationUnit
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace

class PCMJaMoPPUtils {
	private static val Logger logger = Logger.getLogger(PCMJaMoPPUtils.simpleName)

	protected new() {
	}

	/**
	 * Checks whether the affectedAttribute is in the featureCorrespondenceMap and returns all corresponding objects, 
	 * if any. otherwise null is returned.
	 */
	def static checkKeyAndCorrespondingObjects(EObject eObject, EStructuralFeature affectedEFeature,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance) {
			if (!featureCorrespondenceMap.containsKey(affectedEFeature)) {
				logger.debug("no feature correspondence found for affectedEeature: " + affectedEFeature)
				return null
			}
			var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
			if (correspondingEObjects.nullOrEmpty) {
				logger.info("No corresponding objects found for " + eObject)
			}
			return correspondingEObjects
		}

		def static updateNameAttribute(Set<EObject> correspondingEObjects, Object newValue,
			EStructuralFeature affectedFeature,
			ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
			CorrespondenceInstance correspondenceInstance, boolean saveFilesOfChangedEObjects,
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
				if (null == correspondingObject || null == correspondingObject.eResource) {
					logger.error(
						"corresponding object is null or correspondingObject is not contained in a resource!: " +
							correspondingObject + " (object.isProxy=" + correspondingObject.eIsProxy + ")")
				} else {
					val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(correspondingObject)
					correspondingObject.eSet(eStructuralFeature, newValue)
					correspondenceInstance.update(oldTUID, correspondingObject)
					if (saveFilesOfChangedEObjects) {
						// nothing to do here?
					}
				}
			}
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

		private static def boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
			if (reference1 == reference2 || reference1.equals(reference2)) {
				return true
			}
			val target1 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference1)
			val target2 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference2)
			return target1 == target2 || target1.equals(target2)
		}

		def dispatch static addRootChangeToTransformationResult(Repository repo, Blackboard blackboard,
			VURI sourceModelVURI, TransformationResult transformationResult) {
			handlePCMRootEObject(repo, sourceModelVURI, blackboard, "repostiory", transformationResult)
		}

		def dispatch static addRootChangeToTransformationResult(System system, Blackboard blackboard,
			VURI sourceModelVURI, TransformationResult transformationResult) {
			handlePCMRootEObject(system, sourceModelVURI, blackboard, "system", transformationResult)
		}

		def dispatch static addRootChangeToTransformationResult(JavaRoot newJavaRoot, Blackboard blackboard,
			VURI sourceModelVURI, TransformationResult transformationResult) {
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
			Blackboard blackboard, String fileExt, TransformationResult transformationResult) {
			var String folderName = getFolderPathInProjectOfResource(sourceModelVURI, "model");
			val String fileName = namedElement.getEntityName() + "." + fileExt;
			if (!folderName.endsWith("/")) {
				folderName = folderName + "/";
			}
			val VURI vuri = VURI.getInstance(folderName + fileName);
			transformationResult.addRootEObjectToSave(namedElement, vuri)
		}

		def dispatch static handleRootChanges(Iterable<EObject> eObjects, Blackboard blackboard, VURI sourceModelVURI,
			TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
			eObjects.forEach [ eObject |
				handleSingleRootChange(eObject, blackboard, sourceModelVURI, transformationResult, vuriToDelete,
					oldTUID)
			]
		}

		def dispatch static handleRootChanges(EObject[] eObjects, Blackboard blackboard, VURI sourceModelVURI,
			TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
			eObjects.forEach [ eObject |
				handleSingleRootChange(eObject, blackboard, sourceModelVURI, transformationResult, vuriToDelete,
					oldTUID)
			]
		}

		def dispatch static handleRootChanges(EObject eObject, Blackboard blackboard, VURI sourceModelVURI,
			TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
			handleSingleRootChange(eObject, blackboard, sourceModelVURI, transformationResult, vuriToDelete, oldTUID)
		}

		def static handleSingleRootChange(EObject eObject, Blackboard blackboard, VURI sourceModelVURI,
			TransformationResult transformationResult, VURI vuriToDelete, TUID oldTUID) {
			EcoreUtil.remove(eObject)
			PCMJaMoPPUtils.addRootChangeToTransformationResult(eObject, blackboard, sourceModelVURI,
				transformationResult)
			blackboard.correspondenceInstance.update(oldTUID, eObject)
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

	}
