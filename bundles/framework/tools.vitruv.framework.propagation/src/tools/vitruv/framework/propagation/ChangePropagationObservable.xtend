package tools.vitruv.framework.propagation

import org.eclipse.emf.ecore.EObject

interface ChangePropagationObservable {
	def void notifyObjectCreated(EObject createdObject);
	def void registerObserver(ChangePropagationObserver observer);
	def void deregisterObserver(ChangePropagationObserver observer);
}