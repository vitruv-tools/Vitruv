package tools.vitruv.framework.change.description

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.FileChange.FileChangeKind
import tools.vitruv.framework.change.description.impl.FileChangeImpl
import tools.vitruv.framework.change.description.impl.CompositeChangeImpl
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.impl.TransactionalChangeImpl
import tools.vitruv.framework.change.description.impl.EmptyChangeImpl
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.impl.ConcreteChangeImpl

class VitruviusChangeFactory {
	private static VitruviusChangeFactory instance;
	
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
		return new ConcreteChangeImpl(#[change], vuri);
	}
	
	public def ConcreteChange createConcreteChange(List<EChange> changes, VURI vuri) {
		return new ConcreteChangeImpl(changes, vuri);
	}
	
	public def FileChange createFileChange(FileChangeKind kind, Resource changedFileResource) {
		return new FileChangeImpl(kind, changedFileResource);
	}
	
	public def CompositeContainerChange createCompositeContainerChange() {
		return new CompositeChangeImpl();
	}
	
	public def CompositeTransactionalChange createCompositeTransactionalChange() {
		return new TransactionalChangeImpl();
	}
	
	public def ConcreteChange createEmptyChange(VURI vuri) {
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