package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import org.eclipse.emf.ecore.util.EcoreUtil
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper.*;
import java.util.function.Supplier
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper

abstract class AbstractEffectRealization {
	protected val UserInteracting userInteracting;
	protected val Blackboard blackboard;
	protected val TransformationResult transformationResult;
	
	public new(UserInteracting userInteracting, Blackboard blackboard, TransformationResult transformationResult) {
		this.userInteracting = userInteracting;
		this.blackboard = blackboard;
		this.transformationResult = transformationResult;
	}
	
	protected abstract def Logger getLogger();
	
	protected def void deleteElement(EObject element) {
		if (element == null) {
			return;
		}
		if (element.eContainer() == null) {
			if (element.eResource() != null) {
				logger.debug("Deleting root object: " + element);
				element.eResource().delete(Collections.EMPTY_MAP);
			} else {
				logger.warn("The element to delete was already removed: " + element);
			}
		} else {
			logger.debug("Removing non-root object: " + element);
			EcoreUtil.remove(element);
		}
	}
	
	public def renameModel(EObject elementOfResourceInProject, EObject elementOfRenamedModel, String newModelPath, boolean relativeToSource) {
		if (elementOfResourceInProject.eResource() == null) {
			throw new IllegalStateException("Element must be in a resource to determine the containing project.");			
		}
		val sourceRoot = elementOfRenamedModel.modelRoot;
		val oldVURI = if (sourceRoot.eResource() != null) {
			VURI.getInstance(sourceRoot.eResource());
		}
		
		val newVURI = if (relativeToSource) {
			// TODO HK This can eventually go wrong, if the renamed model is not persisted yet
			VURI.getInstance(getURIFromSourceResourceFolder(elementOfRenamedModel, newModelPath, blackboard));
		} else {
			VURI.getInstance(getURIFromSourceProjectFolder(elementOfResourceInProject, newModelPath, blackboard));
		}
		EcoreUtil.remove(sourceRoot);
		transformationResult.addRootEObjectToSave(sourceRoot, newVURI);
		transformationResult.addVURIToDeleteIfNotNull(oldVURI);
	}
	
	public def persistModel(EObject sourceModelElement, EObject objectToSaveAsRoot, String path, boolean relativeToSource) {
		val _resourceURI = if (relativeToSource) {
			getURIFromSourceResourceFolder(sourceModelElement, path, blackboard);
		} else {
			getURIFromSourceProjectFolder(sourceModelElement, path, blackboard);
		}
		this.transformationResult.addRootEObjectToSave(objectToSaveAsRoot, VURI.getInstance(_resourceURI));
	}
	
}