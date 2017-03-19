package tools.vitruv.framework.change.description

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvedAtomicEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.util.datatypes.VURI

class VitruviusChangeFactory {
	private static val logger = Logger.getLogger(VitruviusChangeFactory);
	private static VitruviusChangeFactory instance;
	
	public enum FileChangeKind {
		Create,
		Delete		
	}
	
	private new() {}
	
	public static def VitruviusChangeFactory getInstance() {
		if (instance == null) {
			instance = new VitruviusChangeFactory();
		}
		return instance;
	}
	
	/**
	 * Generates a change from the given {@link ChangeDescription}. This factory method has to be called when the model
	 * is in the state right before the change described by the recorded {@link ChangeDescription}.
	 */
	public def TransactionalChange createEMFModelChange(ChangeDescription changeDescription, VURI vuri, boolean unresolve) {
		return new EMFModelChangeImpl(changeDescription, vuri, unresolve);
	}
	
	public def ConcreteChange createConcreteChange(EChange change, VURI vuri) {
		return new ConcreteChangeImpl(change, vuri);
	}
	
	public def ConcreteChange createFileChange(FileChangeKind kind, Resource changedFileResource, boolean unresolve) {
		val vuri = VURI.getInstance(changedFileResource);
		if (kind == FileChangeKind.Create) {
			return new ConcreteChangeImpl(generateFileCreateChange(changedFileResource, unresolve), vuri);
		} else {
			return new ConcreteChangeImpl(generateFileDeleteChange(changedFileResource, unresolve), vuri);
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
		
	private def EChange generateFileCreateChange(Resource resource, boolean unresolve) {
		var EObject rootElement = null;
		var index = 0 // TODO Stefan: Added for working EChange implementation + boolean: unresolve
        if (1 == resource.getContents().size()) {
            rootElement = resource.getContents().get(0);
        } else if (1 < resource.getContents().size()) {
            throw new RuntimeException(
                    "The requested model instance resource '" + resource + "' has to contain at most one root element "
                            + "in order to be added to the VSUM without an explicit import!");
        } else { // resource.getContents().size() == null --> no element in newModelInstance
            logger.info("Empty model file created: " + VURI.getInstance(resource)
                    + ". Propagation of 'root element created' not triggered.");
            return null;
        }
		var TypeInferringCompoundEChangeFactory factory
        if (unresolve) {
        	factory = new TypeInferringCompoundEChangeFactory(new TypeInferringUnresolvedAtomicEChangeFactory)
        } else {
        	factory = new TypeInferringCompoundEChangeFactory(new TypeInferringUnresolvedAtomicEChangeFactory)
        }
        val CreateAndInsertRoot<EObject> createRootEObj = factory.createCreateAndInsertRootChange(rootElement, resource.URI.toString, index);
        return createRootEObj; 
	}
	
	private def generateFileDeleteChange(Resource resource, boolean unresolve) {
		if (0 < resource.getContents().size()) {
			val index = 0 // TODO Stefan: Added for working EChange implementation + boolean: unresolve
            val EObject rootElement = resource.getContents().get(index);
            var TypeInferringCompoundEChangeFactory factory
       		if (unresolve) {
        		factory = new TypeInferringCompoundEChangeFactory(new TypeInferringUnresolvedAtomicEChangeFactory)
        	} else {
        		factory = new TypeInferringCompoundEChangeFactory(new TypeInferringUnresolvedAtomicEChangeFactory)
        	}
            val RemoveAndDeleteRoot<EObject> deleteRootObj = factory.createRemoveAndDeleteRootChange(rootElement, resource.URI.toString, index);
            return deleteRootObj;
        }
        logger.info("Deleted resource " + VURI.getInstance(resource) + " did not contain any EObject");
        return null;
	}
}