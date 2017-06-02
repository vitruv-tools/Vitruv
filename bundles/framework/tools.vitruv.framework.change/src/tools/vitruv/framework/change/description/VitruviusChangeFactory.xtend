package tools.vitruv.framework.change.description

import org.apache.log4j.Logger

import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource

import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import tools.vitruv.framework.change.description.impl.LegacyEMFModelChangeImpl
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.util.datatypes.VURI

class VitruviusChangeFactory {
	static val logger = Logger::getLogger(VitruviusChangeFactory)
	static VitruviusChangeFactory instance

	public enum FileChangeKind {
		Create,
		Delete
	}

	private new() {
	}

	static def VitruviusChangeFactory getInstance() {
		if (instance === null) {
			instance = new VitruviusChangeFactory
		}
		return instance
	}

	/**
	 * Generates a change from the given {@link ChangeDescription}. This factory method has to be called when the model
	 * is in the state right before the change described by the recorded {@link ChangeDescription}.
	 */
	def TransactionalChange createEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		val changes = new ChangeDescription2EChangesTransformation(changeDescription).transform
		return new EMFModelChangeImpl(changes, vuri)
	}

	def TransactionalChange createLegacyEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		val changes = new ChangeDescription2EChangesTransformation(changeDescription).transform
		return new LegacyEMFModelChangeImpl(changeDescription, changes, vuri)
	}

	def ConcreteChange createConcreteApplicableChange(EChange change, VURI vuri) {
		return new ConcreteApplicableChangeImpl(change, vuri)
	}

	def ConcreteChange createConcreteChange(EChange change, VURI vuri) {
		return new ConcreteChangeImpl(change, vuri)
	}

	def ConcreteChange createFileChange(FileChangeKind kind, Resource changedFileResource) {
		val vuri = VURI::getInstance(changedFileResource)
		var EChange eChange = null
		if (kind == FileChangeKind::Create) {
			eChange = generateFileCreateChange(changedFileResource)
		} else {
			eChange = generateFileDeleteChange(changedFileResource)
		}
		return new ConcreteChangeImpl(eChange, vuri)
	}

	def CompositeContainerChange createCompositeContainerChange() {
		return new CompositeContainerChangeImpl
	}

	def CompositeTransactionalChange createCompositeTransactionalChange() {
		return new CompositeTransactionalChangeImpl
	}

	def TransactionalChange createEmptyChange(VURI vuri) {
		return new EmptyChangeImpl(vuri)
	}

	def CompositeContainerChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		val compositeChange = new CompositeContainerChangeImpl
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange)
		}
		return compositeChange
	}

	private def EChange generateFileCreateChange(Resource resource) {
		var EObject rootElement = null
		var index = 0
		if (1 == resource.contents.size)
			rootElement = resource.contents.get(0)
		else if (1 < resource.contents.size)
			throw new RuntimeException(
				"The requested model instance resource '" + resource + "' has to contain at most one root element " +
					"in order to be added to the VSUM without an explicit import!")
				else { // resource.contents.size === null --> no element in newModelInstance
					logger.info("Empty model file created: " + VURI::getInstance(resource) +
						". Propagation of 'root element created' not triggered.")
					return null
				}
		val CreateAndInsertRoot<EObject> createRootEObj = TypeInferringCompoundEChangeFactory::instance.
			createCreateAndInsertRootChange(rootElement, resource, index)
		return createRootEObj;
	}

	private def EChange generateFileDeleteChange(Resource resource) {
		if (0 < resource.contents.size) {
			val index = 0
			val EObject rootElement = resource.contents.get(index)
			val RemoveAndDeleteRoot<EObject> deleteRootObj = TypeInferringCompoundEChangeFactory::instance.
				createRemoveAndDeleteRootChange(rootElement, resource, index)
			return deleteRootObj
		}
		logger.info("Deleted resource " + VURI::getInstance(resource) + " did not contain any EObject")
		return null
	}
}
		