package tools.vitruv.framework.vsum.internal

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.Collection
import org.eclipse.emf.ecore.resource.Resource

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

	def Iterable<? extends TransactionalChange> endRecording()

	/**
     * IMPORTANT: This method is only temporary, and should be replaced a inversion of control based mechanism to supply
     * viewtypes with the necessary resources for view creation.
     */
    @Deprecated
    def ResourceSet getResourceSet()
    
    def Collection<Resource> getModelResources()
}
