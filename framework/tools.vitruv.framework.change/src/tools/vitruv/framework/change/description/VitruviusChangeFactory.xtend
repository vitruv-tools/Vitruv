package tools.vitruv.framework.change.description

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl
import tools.vitruv.framework.change.description.impl.FileCreateChangeImpl
import tools.vitruv.framework.change.description.impl.FileDeleteChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeTransactionalChangeImpl

class VitruviusChangeFactory {
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
	
	public def EMFModelChange createEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		return new EMFModelChangeImpl(changeDescription, vuri);
	}
	
	public def ConcreteChange createConcreteChange(EChange change, VURI vuri) {
		return new ConcreteChangeImpl(change, vuri);
	}
	
	public def ConcreteChange createFileChange(FileChangeKind kind, Resource changedFileResource) {
		if (kind == FileChangeKind.Create) {
			return new FileCreateChangeImpl(changedFileResource);
		} else {
			return new FileDeleteChangeImpl(changedFileResource);		
		}
	}
	
	public def CompositeContainerChange createCompositeContainerChange() {
		return new CompositeChangeImpl();
	}
	
	public def CompositeTransactionalChange createCompositeTransactionalChange() {
		return new CompositeTransactionalChangeImpl();
	}
	
	public def TransactionalChange createEmptyChange(VURI vuri) {
		return new EmptyChangeImpl(vuri);
	}
	
	public def CompositeContainerChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		val compositeChange = new CompositeChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
}