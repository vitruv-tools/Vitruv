package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.ResourceAccess
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.vsum.variability.InternalVaveModel

interface ModelRepository extends ResourceAccess, AutoCloseable {
	def UuidResolver getUuidResolver()

	def CorrespondenceModel getCorrespondenceModel()
	
	def InternalVaveModel getVaveModel()

	/**
	 * Returns the model at the given {@link VURI} if it was already loaded to or created in
	 * the repository. Returns <code>null</code> otherwise.
	 */
	def ModelInstance getModel(VURI modelVuri)

	def void loadExistingModels()

	def void saveOrDeleteModels()

	def void startRecording()

	def Iterable<? extends TransactionalChange> endRecording()
}
