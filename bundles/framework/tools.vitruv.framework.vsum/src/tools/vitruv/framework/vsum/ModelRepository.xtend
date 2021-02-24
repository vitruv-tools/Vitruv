package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.ResourceAccess
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.correspondence.CorrespondenceModel

interface ModelRepository extends ResourceAccess, AutoCloseable {
	def UuidGeneratorAndResolver getUuidGeneratorAndResolver()
	def CorrespondenceModel getCorrespondenceModel()
	def ModelInstance getModel(VURI modelVuri)
    def void saveOrDeleteModels()
    def void startRecording()
    def Iterable<? extends TransactionalChange> endRecording()
}
