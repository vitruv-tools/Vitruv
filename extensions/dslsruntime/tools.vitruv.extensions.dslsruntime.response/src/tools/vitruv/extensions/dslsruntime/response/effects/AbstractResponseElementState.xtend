package tools.vitruv.extensions.dslsruntime.response.effects

import tools.vitruv.extensions.dslsruntime.response.structure.Loggable
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.extensions.dslsruntime.response.helper.ResponseCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractResponseElementState extends Loggable implements ResponseElementState {
	protected final EObject element;
	private final List<Pair<EObject, String>> newCorrespondingElements;
	private final List<EObject> oldCorrespondingElements;
	private final TransformationResult transformationResult;
	protected final CorrespondenceModel correspondenceModel;
	protected boolean delete;
	
	public new(EObject element, CorrespondenceModel correspondenceModel, TransformationResult transformationResult) {
		this.element = element;
		this.correspondenceModel = correspondenceModel;
		this.newCorrespondingElements = newArrayList;
		this.oldCorrespondingElements = newArrayList;
		this.delete = false;
		this.transformationResult = transformationResult;
	}
	
	public override void preprocess() {
		removeCorrespondences();
		if (delete) {
			performDeletion();
		}
	}
	
	public override void postprocess() {
		addCorrespondences();
	}

	protected def void removeCorrespondences() {
		for (oldCorrespondingElement: oldCorrespondingElements) {
			ResponseCorrespondenceHelper.removeCorrespondencesBetweenElements(correspondenceModel, 
				element, null, oldCorrespondingElement, null
			);
		}
		if (delete) {
			ResponseCorrespondenceHelper.removeCorrespondencesOfObject(correspondenceModel, element, null);
		}
	}	
	
	protected def void addCorrespondences() {
		for (newCorrespondingElement: newCorrespondingElements) {
			ResponseCorrespondenceHelper.addCorrespondence(correspondenceModel, 
				element, newCorrespondingElement.key, newCorrespondingElement.value
			);
		}
	}
	
	public override void addCorrespondingElement(EObject newCorrespondingElement, String tag) {
		this.newCorrespondingElements += new Pair(newCorrespondingElement, tag);
	}
	
	public override void removeCorrespondingElement(EObject oldCorrespondingElement) {
		this.oldCorrespondingElements -= oldCorrespondingElement;
	}
	
	public override void delete() {
		this.delete = true;
	}
	
	protected def void performDeletion() {
		if (element == null) {
			return;
		}
		if (element.eContainer() == null) {
			if (element.eResource() != null) {
				logger.debug("Deleting root object: " + element);
				transformationResult.addVURIToDeleteIfNotNull(VURI.getInstance(element.eResource()));
			} else {
				logger.warn("The element to delete was already removed: " + element);
			}
		} else {
			logger.debug("Removing non-root object: " + element);
			EcoreUtil.remove(element);
		}
		// If we delete an object, we have to update TUIDs because TUIDs of child elements 
		// may have to be resolved for removing correspondences as well and must therefore be up-to-date
		TuidManager.instance.updateTuidsOfRegisteredObjects();
	}		
}