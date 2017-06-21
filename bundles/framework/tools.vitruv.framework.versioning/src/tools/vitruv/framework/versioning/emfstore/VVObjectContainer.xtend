package tools.vitruv.framework.versioning.emfstore

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList

interface VVObjectContainer<T> {
	def T getModelElementId(EObject modelElement)

	def EList<EObject> getModelElements()

	def EObject getModelElement(T modelElementId)
}
