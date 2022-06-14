package tools.vitruv.framework.vsum.internal

import org.eclipse.emf.common.util.URI
import java.util.Collection
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.propagation.ChangeRecordingModelRepository

package interface ModelRepository extends ChangeRecordingModelRepository {
	/**
	 * Returns the model at the given {@link URI} if it was already loaded to or created in
	 * the repository. Returns <code>null</code> otherwise.
	 */
	def ModelInstance getModel(URI modelUri)

	def void loadExistingModels()

	def void saveOrDeleteModels()

	def Collection<Resource> getModelResources()
}
