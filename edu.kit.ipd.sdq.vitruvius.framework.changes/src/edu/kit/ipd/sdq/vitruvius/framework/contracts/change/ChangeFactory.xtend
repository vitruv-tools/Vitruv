package edu.kit.ipd.sdq.vitruvius.framework.contracts.change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.impl.EMFModelChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.impl.FileChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.impl.CompositeChangeImpl
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.impl.GeneralChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change

class ChangeFactory {
	private static ChangeFactory instance;
	
	private new() {}
	
	public static def ChangeFactory getInstance() {
		if (instance == null) {
			instance = new ChangeFactory();
		}
		return instance;
	}
	
	public def EMFModelChange createEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		return new EMFModelChangeImpl(changeDescription, vuri);
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
	
	public def CompositeChange createCompositeChange(Iterable<? extends Change> innerChanges) {
		val compositeChange = new CompositeChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
}