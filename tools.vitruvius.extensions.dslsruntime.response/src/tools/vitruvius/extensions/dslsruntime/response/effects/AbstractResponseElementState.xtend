package tools.vitruvius.extensions.dslsruntime.response.effects

import tools.vitruvius.extensions.dslsruntime.response.structure.Loggable
import java.util.List
import org.eclipse.emf.ecore.EObject
import java.util.Collections
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruvius.extensions.dslsruntime.response.helper.ResponseCorrespondenceHelper
import tools.vitruvius.framework.correspondence.CorrespondenceModel

abstract class AbstractResponseElementState extends Loggable implements ResponseElementState {
	protected final EObject element;
	private final List<Pair<EObject, String>> newCorrespondingElements;
	private final List<EObject> oldCorrespondingElements;
	protected final CorrespondenceModel correspondenceModel;
	protected boolean delete;
	
	public new(EObject element, CorrespondenceModel correspondenceModel) {
		this.element = element;
		this.correspondenceModel = correspondenceModel;
		this.newCorrespondingElements = newArrayList;
		this.oldCorrespondingElements = newArrayList;
		this.delete = false;
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
	
	public def void updateTUID();
	
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
				element.eResource().delete(Collections.EMPTY_MAP);
			} else {
				logger.warn("The element to delete was already removed: " + element);
			}
		} else {
			logger.debug("Removing non-root object: " + element);
			EcoreUtil.remove(element);
		}
	}		
}