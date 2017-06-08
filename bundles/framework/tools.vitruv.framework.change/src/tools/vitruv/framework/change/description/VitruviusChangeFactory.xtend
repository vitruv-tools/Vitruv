package tools.vitruv.framework.change.description

import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.VitruviusChangeFactoryImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

/**
 * 
 * @version
 * @since 
 * @author
 */
interface VitruviusChangeFactory {
	VitruviusChangeFactory instance = VitruviusChangeFactoryImpl::init

	public enum FileChangeKind {
		Create,
		Delete
	}

	/**
	 * Generates a change from the given {@link ChangeDescription}. This factory method has to be called when the model
	 * is in the state right before the change described by the recorded {@link ChangeDescription}.
	 */
	def TransactionalChange createEMFModelChange(ChangeDescription changeDescription, VURI vuri)

	/**
	 * 
	 * @param changeToCopy
	 * @param vuri
	 * @return
	 */
	def TransactionalChange createEMFModelChange(EMFModelChangeImpl changeToCopy, VURI vuri)

	def TransactionalChange createLegacyEMFModelChange(ChangeDescription changeDescription, VURI vuri)

	def ConcreteChange createConcreteApplicableChange(EChange change, VURI vuri)

	def ConcreteChange createConcreteChange(EChange change, VURI vuri)

	def ConcreteChange createFileChange(FileChangeKind kind, Resource changedFileResource)

	def CompositeContainerChange createCompositeContainerChange()

	def CompositeTransactionalChange createCompositeTransactionalChange()

	def TransactionalChange createEmptyChange(VURI vuri)

	def CompositeContainerChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges)

}
