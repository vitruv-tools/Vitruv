package tools.vitruv.framework.change.description.impl

import java.util.List
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.change.description.TransactionalChange

/**
 * @version 0.2.0
 * @since 2017-06-06
 */
class VitruviusChangeFactoryImpl implements VitruviusChangeFactory {
	static extension Logger = Logger::getLogger(VitruviusChangeFactory)

	private new() {
	}

	static def VitruviusChangeFactory init() {
		level = Level::DEBUG
		new VitruviusChangeFactoryImpl
	}

	/**
	 * Generates a change from the given {@link ChangeDescription}. This factory method has to be called when the model
	 * is in the state right before the change described by the recorded {@link ChangeDescription}.
	 */
	override createEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		val changes = new ChangeDescription2EChangesTransformation(changeDescription).transform
		new EMFModelChangeImpl(changes, vuri)
	}

	override copy(TransactionalChange changeToCopy) {
		val echanges = changeToCopy.EChanges
		if (!echanges.forall[!resolved])
			error("There are some resolved changes!")
		return new EMFModelChangeImpl(echanges, changeToCopy.URI)
	}

	override createEMFModelChangeFromEChanges(List<EChange> echanges, VURI vuri) {
		new EMFModelChangeImpl(echanges, vuri)
	}

	override createLegacyEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		val changes = new ChangeDescription2EChangesTransformation(changeDescription).transform
		new LegacyEMFModelChangeImpl(changeDescription, changes, vuri)
	}

	override createConcreteApplicableChange(EChange change, VURI vuri) {
		new ConcreteApplicableChangeImpl(change, vuri)
	}

	override createConcreteChange(EChange change, VURI vuri) {
		new ConcreteChangeImpl(change, vuri)
	}

	override createFileChange(FileChangeKind kind, Resource changedFileResource) {
		val vuri = VURI::getInstance(changedFileResource)
		val eChange = if (kind == FileChangeKind::Create)
				generateFileCreateChange(changedFileResource)
			else
				generateFileDeleteChange(changedFileResource)
		new ConcreteChangeImpl(eChange, vuri)
	}

	override createCompositeContainerChange() {
		new CompositeContainerChangeImpl
	}

	override createCompositeTransactionalChange() {
		new CompositeTransactionalChangeImpl
	}

	override createEmptyChange(VURI vuri) {
		new EmptyChangeImpl(vuri)
	}

	override createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		val compositeChange = new CompositeContainerChangeImpl
		innerChanges.forEach[compositeChange.addChange(it)]
		compositeChange
	}

	override <T extends VitruviusChange> clone(T originalChange) {
		return new ChangeCloner().clone(originalChange) as T
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
					info("Empty model file created: " + VURI::getInstance(resource) +
						". Propagation of 'root element created' not triggered.")
					return null
				}
				val CreateAndInsertRoot<EObject> createRootEObj = TypeInferringCompoundEChangeFactory::instance.
					createCreateAndInsertRootChange(rootElement, resource, index)
				createRootEObj
			}

			private def EChange generateFileDeleteChange(Resource resource) {
				if (0 < resource.contents.size) {
					val index = 0
					val rootElement = resource.contents.get(index)
					val RemoveAndDeleteRoot<EObject> deleteRootObj = TypeInferringCompoundEChangeFactory::instance.
						createRemoveAndDeleteRootChange(rootElement, resource, index)
					return deleteRootObj
				}
				info("Deleted resource " + VURI::getInstance(resource) + " did not contain any EObject")
				return null
			}

		}
		