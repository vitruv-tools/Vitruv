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
import java.io.IOException
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState

abstract class AbstractEffectRealization extends Loggable {
	protected extension val ResponseExecutionState _executionState;
	
	public new(ResponseExecutionState executionState) {
		this._executionState = executionState;
	}
	
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
	
	protected def renameModel(EObject elementOfResourceInProject, EObject elementOfRenamedModel, String newModelPath, boolean relativeToSource) {
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
	
	protected def persistModel(EObject sourceModelElement, EObject objectToSaveAsRoot, String path, boolean relativeToSource) {
		val _resourceURI = if (relativeToSource) {
			getURIFromSourceResourceFolder(sourceModelElement, path, blackboard);
		} else {
			getURIFromSourceProjectFolder(sourceModelElement, path, blackboard);
		}
		transformationResult.addRootEObjectToSave(objectToSaveAsRoot, VURI.getInstance(_resourceURI));
	}
	
	public def void applyEffect() {
		// If not all parameters are set this is not a lightweight error caused by erroneous responses but an implementation
		// error of the response mechanism, so throw an exception
		if (!allParametersSet()) {
			getLogger().error('''Not all parameters were set for executing effect («this.class.simpleName»)''');
			throw new IllegalStateException('''Not all parameters were set for executing effect («this.class.simpleName»)''');
		}
		try {
			executeEffect();
		} catch (Exception exception) {
			// If an error occured during execution, avoid an application shutdown and print the error.
			getLogger().error('''«exception.class.simpleName» during execution of effect («this.class.simpleName»): «exception.message»''');
		}
	}
	
	protected abstract def boolean allParametersSet();
	protected abstract def void executeEffect() throws IOException;
	
}