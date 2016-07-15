package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject

interface ResponseElementState {
	public def void preprocess();
	public def void postprocess();
	public def void addCorrespondingElement(EObject newCorrespondingElement, String tag);
	public def void removeCorrespondingElement(EObject oldCorrespondingElement);
	public def void delete();
}