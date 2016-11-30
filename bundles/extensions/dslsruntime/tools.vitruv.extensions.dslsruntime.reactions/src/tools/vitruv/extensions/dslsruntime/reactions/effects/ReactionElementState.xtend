package tools.vitruv.extensions.dslsruntime.reactions.effects

import org.eclipse.emf.ecore.EObject

interface ReactionElementState {
	public def void postprocess();
	public def void addCorrespondingElement(EObject newCorrespondingElement, String tag);
}