package tools.vitruv.extensions.dslsruntime.reactions.effects

import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel

abstract class AbstractReactionElementState extends Loggable implements ReactionElementState {
	protected final EObject element;
	private final List<Pair<EObject, String>> newCorrespondingElements;
	protected final CorrespondenceModel correspondenceModel;
	
	public new(EObject element, CorrespondenceModel correspondenceModel) {
		this.element = element;
		this.correspondenceModel = correspondenceModel;
		this.newCorrespondingElements = newArrayList;
	}
	
	public override void postprocess() {
		addCorrespondences();
	}

	protected def void addCorrespondences() {
		for (newCorrespondingElement: newCorrespondingElements) {
			ReactionsCorrespondenceHelper.addCorrespondence(correspondenceModel, 
				element, newCorrespondingElement.key, newCorrespondingElement.value
			);
		}
	}
	
	public override void addCorrespondingElement(EObject newCorrespondingElement, String tag) {
		this.newCorrespondingElements += new Pair(newCorrespondingElement, tag);
	}
	
}