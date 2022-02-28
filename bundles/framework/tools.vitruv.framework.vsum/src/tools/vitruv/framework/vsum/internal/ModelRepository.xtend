package tools.vitruv.framework.vsum.internal

import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.Collection
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.propagation.ChangeInPropagation

package interface ModelRepository extends ResourceAccess, AutoCloseable {
	def VitruviusChange applyChange(VitruviusChange change)

	def CorrespondenceModel getCorrespondenceModel()

	/**
	 * Returns the model at the given {@link URI} if it was already loaded to or created in
	 * the repository. Returns <code>null</code> otherwise.
	 */
	def ModelInstance getModel(URI modelUri)

	def void loadExistingModels()

	def void saveOrDeleteModels()

	def void startRecording()

	def Iterable<ChangeInPropagation> endRecording()

	def Collection<Resource> getModelResources()
}
