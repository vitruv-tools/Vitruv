package edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import org.eclipse.core.resources.IFile
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.util.EcoreUtil

public final class ResponseRuntimeHelper {
	public static def EObject getModelRoot(EObject modelObject) {
		var result = modelObject;
		while (result.eContainer() != null) {
			result = result.eContainer();
		}
		return result;
	}
	
	private static def getTUID(CorrespondenceInstance correspondenceInstance, EObject object, EObject parent) {
		if (parent == null) {
			return correspondenceInstance.calculateTUIDFromEObject(object);
		} else {
			val rootTUID = correspondenceInstance.calculateTUIDFromEObject(parent);
			val String prefix = rootTUID.toString;
			return correspondenceInstance.calculateTUIDFromEObject(object, object.eContainer(), prefix);
		}
	}
	
	public static def removeCorrespondence(CorrespondenceInstance correspondenceInstance, EObject source, EObject sourceParent, EObject target, EObject targetParent) {
		val sourceTUID = correspondenceInstance.getTUID(source, sourceParent);
		val targetTUID = correspondenceInstance.getTUID(target, targetParent);
		val correspondences = correspondenceInstance.getCorrespondencesForTUIDs(#[sourceTUID]);
		for (correspondence : correspondences.toList) {
			if ((correspondence.ATUIDs.contains(sourceTUID)
				&& correspondence.BTUIDs.contains(targetTUID))
				|| (correspondence.BTUIDs.contains(sourceTUID)
				&& correspondence.ATUIDs.contains(targetTUID))) {
				correspondenceInstance.removeCorrespondencesAndDependendCorrespondences(correspondence);		
			}
		}
	}
	
	public static def addCorrespondence(CorrespondenceInstance correspondenceInstance, EObject source, EObject target) {
		correspondenceInstance.createAndAddCorrespondence(#[source], #[target]);
	}
	
	public static def <T> Iterable<T> getCorrespondingObjectsOfType(CorrespondenceInstance correspondenceInstance, EObject source, EObject sourceParent,
			Class<T> type) {
		val tuid = correspondenceInstance.getTUID(source, sourceParent);
		return correspondenceInstance.getCorrespondencesForTUIDs(#[tuid]).map[it.getCorrespondingObjectsOfTypeInCorrespondence(tuid, type)].flatten;
	}
	
	private static def <T> Iterable<T> getCorrespondingObjectsOfTypeInCorrespondence(Correspondence correspondence, TUID source, Class<T> type) {
		var Iterable<T> correspondences = 
			if (correspondence.ATUIDs.contains(source)) {
				correspondence.^bs.filter(type);
			} else {
				correspondence.^as.filter(type);
			}
		return correspondences;
	}
	
	private static def URI getURIOfElementResourceFolder(EObject element) {
		return element.eResource().getURI().trimSegments(1);
	}
	
	private static def URI getURIOfElementProject(EObject element) {
		val IFile sourceModelFile = EMFBridge.getIFileForEMFUri(element.eResource().URI);
		val IProject projectSourceModel = sourceModelFile.getProject();
		// TODO HK: Check if the last / is necessary
		var String srcFolderPath = projectSourceModel.getFullPath().toString() + "/";
		return URI.createPlatformResourceURI(srcFolderPath, true);
	}
	
	private static def URI appendPathToURI(URI baseURI, String relativePath, Blackboard blackboard) {
		val newModelFileSegments = relativePath.split("/");
		if (!newModelFileSegments.last.contains(".")) {
			// No file extension was specified, add the first one that is valid for the metamodel
			val fileExtension = blackboard.getCorrespondenceInstance().getMapping().getMetamodelB().getFileExtensions().get(0);
			newModelFileSegments.set(newModelFileSegments.size-1, newModelFileSegments.last + "." + fileExtension);
		}
		return baseURI.appendSegments(newModelFileSegments);
	}
	
	public static def URI getURIFromSourceResourceFolder(EObject source, String relativePath, Blackboard blackboard) {
		val baseURI = getURIOfElementResourceFolder(source);
		return baseURI.appendPathToURI(relativePath, blackboard);
	}
	
	public static def URI getURIFromSourceProjectFolder(EObject source, String relativePath, Blackboard blackboard) {
		val baseURI = getURIOfElementProject(source);
		return baseURI.appendPathToURI(relativePath, blackboard);
	}
	
	public static def renameModel(Blackboard blackboard, EObject elementOfResourceInProject, EObject elementOfRenamedModel, String newModelPath, TransformationResult transformationResult) {
		if (elementOfResourceInProject.eResource() == null) {
			throw new IllegalStateException("Element must be in a resource to determine the containing project.");			
		}
		val sourceRoot = elementOfRenamedModel.modelRoot;
		val oldVURI = if (sourceRoot.eResource() != null) {
			VURI.getInstance(sourceRoot.eResource());
		}
		val newVURI = VURI.getInstance(elementOfResourceInProject.getURIFromSourceProjectFolder(newModelPath, blackboard));
		EcoreUtil.remove(sourceRoot);
		transformationResult.addRootEObjectToSave(elementOfRenamedModel, newVURI);
		transformationResult.addVURIToDeleteIfNotNull(oldVURI);
	}
	
}
