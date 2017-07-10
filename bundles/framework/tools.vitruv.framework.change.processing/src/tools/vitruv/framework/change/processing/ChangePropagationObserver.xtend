package tools.vitruv.framework.change.processing

import org.eclipse.emf.ecore.EObject

interface ChangePropagationObserver {
	def void objectCreated(EObject createdObject);
}