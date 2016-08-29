package edu.kit.ipd.sdq.vitruvius.framework.change.description

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.EMFModelChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.change.description.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.FileChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.CompositeChangeImpl
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange
import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.GeneralChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange
import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.TransactionalChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.change.description.impl.EmptyChangeImpl

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
	
	public def GeneralChange createGeneralChange(EChange change, VURI vuri) {
		return new GeneralChangeImpl(#[change], vuri);
	}
	
	public def GeneralChange createGeneralChange(List<EChange> changes, VURI vuri) {
		return new GeneralChangeImpl(changes, vuri);
	}
	
	public def FileChange createFileChange(FileChangeKind kind, VURI vuri) {
		return new FileChangeImpl(kind, vuri);
	}
	
	public def CompositeChange createCompositeChange() {
		return new CompositeChangeImpl();
	}
	
	public def TransactionalChange createTransactionalChange() {
		return new TransactionalChangeImpl();
	}
	
	public def ConcreteChange createEmptyChange(VURI vuri) {
		return new EmptyChangeImpl(vuri);
	}
	
	public def CompositeChange createCompositeChange(Iterable<? extends VitruviusChange> innerChanges) {
		val compositeChange = new CompositeChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
}