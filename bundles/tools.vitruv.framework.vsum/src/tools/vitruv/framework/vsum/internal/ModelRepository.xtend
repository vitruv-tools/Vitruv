package tools.vitruv.framework.vsum.internal

import org.eclipse.emf.common.util.URI
import java.util.Collection
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.change.propagation.ChangeRecordingModelRepository
import tools.vitruv.change.atomic.uuid.UuidResolver

package interface ModelRepository extends ChangeRecordingModelRepository {
	/**
	 * Returns the model at the given {@link URI} if it was already loaded to or created in
	 * the repository. Returns <code>null</code> otherwise.
	 */
	def ModelInstance getModel(URI modelUri)

	def void loadExistingModels()

	def void saveOrDeleteModels()

	def Collection<Resource> getModelResources()
	
	/**
	 * Returns the {@link UuidResolver} associated with all resources in this repository.
	 */
	def UuidResolver getUuidResolver()
}
