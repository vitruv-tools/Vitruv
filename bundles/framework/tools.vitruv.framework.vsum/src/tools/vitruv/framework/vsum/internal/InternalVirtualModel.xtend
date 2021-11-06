package tools.vitruv.framework.vsum.internal

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.vsum.VirtualModel

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()

	def ResourceSet getResourceSet();

	def ModelInstance getModelInstance(URI modelUri)

	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)

	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)

	def void dispose()
}
