package tools.vitruv.framework.change.description

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.preparation.ChangeDescription2EChangesTransformation
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeWithUriImpl
import java.util.List

class VitruviusChangeFactory {
	private static val logger = Logger.getLogger(VitruviusChangeFactory);
	private static VitruviusChangeFactory instance;
	
	public enum FileChangeKind {
		Create,
		Delete		
	}
	
	private new() {}
	
	public static def VitruviusChangeFactory getInstance() {
		if (instance === null) {
			instance = new VitruviusChangeFactory();
		}
		return instance;
	}
	
	/**
	 * Generates a change from the given {@link ChangeDescription}. This factory method has to be called when the model
	 * is in the state right before the change described by the recorded {@link ChangeDescription}.
	 */
	public def TransactionalChange createTransactionalChange(ChangeDescription changeDescription) {
		val changes = new ChangeDescription2EChangesTransformation(changeDescription).transform()
		val compositeChange = createCompositeTransactionalChange;
		if (changes.empty) {
			compositeChange.addChange(createEmptyChange(null));
		} else {
			changes.map[createConcreteApplicableChange(it)].forEach[compositeChange.addChange(it)];
		}
		return compositeChange
	}
	
	public def ConcreteChange createConcreteApplicableChange(EChange change) {
		return new ConcreteApplicableChangeImpl(change);
	}
	
	public def ConcreteChange createConcreteChange(EChange change) {
		return new ConcreteChangeImpl(change);
	}
	
	public def ConcreteChange createConcreteChangeWithVuri(EChange change, VURI vuri) {
		return new ConcreteChangeWithUriImpl(vuri, change);
	}
	
	public def List<ConcreteChange> createFileChange(FileChangeKind kind, Resource changedFileResource) {
		if (kind == FileChangeKind.Create) {
			return generateFileCreateChange(changedFileResource).map[new ConcreteChangeImpl(it)];
		} else {
			return generateFileDeleteChange(changedFileResource).map[new ConcreteChangeImpl(it)];
		}
	}
	
	public def CompositeContainerChange createCompositeContainerChange() {
		return new CompositeContainerChangeImpl();
	}
	
	public def CompositeTransactionalChange createCompositeTransactionalChange() {
		return new CompositeTransactionalChangeImpl();
	}
	
	public def TransactionalChange createEmptyChange(VURI vuri) {
		return new EmptyChangeImpl(vuri);
	}
	
	public def CompositeContainerChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		val compositeChange = new CompositeContainerChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
	
	public def <T extends VitruviusChange> T clone(T originalChange) {
		return new ChangeCloner().clone(originalChange) as T;
	}
		
	private def List<EChange> generateFileCreateChange(Resource resource) {
		var EObject rootElement = null;
		var index = 0
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException(
                    "The requested model instance resource '" + resource + "' has to contain at most one root element "
                            + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() === null --> no element in newModelInstance
            logger.info("Empty model file created: " + VURI.getInstance(resource)
                    + ". Propagation of 'root element created' not triggered.");
            return null;
        }
        return TypeInferringCompoundEChangeFactory.
        	instance.createCreateAndInsertRootChange(rootElement, resource, index);
	}
	
	private def List<EChange> generateFileDeleteChange(Resource resource) {
		if (0 < resource.getContents().size()) {
			val index = 0
            val EObject rootElement = resource.getContents().get(index);
            return TypeInferringCompoundEChangeFactory.instance.createRemoveAndDeleteRootChange(rootElement, resource, index);
        }
        logger.info("Deleted resource " + VURI.getInstance(resource) + " did not contain any EObject");
        return null;
	}
}