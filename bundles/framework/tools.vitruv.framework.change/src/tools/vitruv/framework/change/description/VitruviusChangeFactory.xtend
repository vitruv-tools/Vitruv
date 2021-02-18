package tools.vitruv.framework.change.description

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeWithUriImpl
import java.util.List

class VitruviusChangeFactory {
	static val logger = Logger.getLogger(VitruviusChangeFactory);
	static VitruviusChangeFactory instance;
	
	enum FileChangeKind {
		Create,
		Delete		
	}
	
	private new() {}
	
	static def VitruviusChangeFactory getInstance() {
		if (instance === null) {
			instance = new VitruviusChangeFactory();
		}
		return instance;
	}
	
	def ConcreteChange createConcreteApplicableChange(EChange change) {
		return new ConcreteApplicableChangeImpl(change);
	}
	
	def ConcreteChange createConcreteChange(EChange change) {
		return new ConcreteChangeImpl(change);
	}
	
	def ConcreteChange createConcreteChangeWithVuri(EChange change, VURI vuri) {
		return new ConcreteChangeWithUriImpl(vuri, change);
	}
	
	def List<ConcreteChange> createFileChange(FileChangeKind kind, Resource changedFileResource) {
		if (kind == FileChangeKind.Create) {
			return generateFileCreateChange(changedFileResource).map[new ConcreteChangeImpl(it)];
		} else {
			return generateFileDeleteChange(changedFileResource).map[new ConcreteChangeImpl(it)];
		}
	}
	
	def CompositeContainerChange createCompositeContainerChange() {
		return new CompositeContainerChangeImpl();
	}
	
	def CompositeTransactionalChange createCompositeTransactionalChange() {
		return new CompositeTransactionalChangeImpl();
	}
	
	def CompositeTransactionalChange createCompositeTransactionalChange(Iterable<? extends TransactionalChange> innerChanges) {
		val compositeChange = new CompositeTransactionalChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
	
	def TransactionalChange createEmptyChange(VURI vuri) {
		return new EmptyChangeImpl(vuri);
	}
	
	def CompositeContainerChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		val compositeChange = new CompositeContainerChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
	
	def <T extends VitruviusChange> T clone(T originalChange) {
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