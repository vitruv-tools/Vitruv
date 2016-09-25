package tools.vitruv.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject

interface ResponseElementState {
	public def void postprocess();
	public def void addCorrespondingElement(EObject newCorrespondingElement, String tag);
}