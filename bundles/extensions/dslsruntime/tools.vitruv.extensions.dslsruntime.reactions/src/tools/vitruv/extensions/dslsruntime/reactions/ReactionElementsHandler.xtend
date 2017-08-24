package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.emf.ecore.EObject

interface ReactionElementsHandler {
	public def void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag);
	public def void removeCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag);
	public def void deleteObject(EObject element);
	public def void registerObjectUnderModification(EObject element);
	public def void postprocessElements();
}