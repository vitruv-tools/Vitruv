package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.impl.EMFModelChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.RecordedCompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.impl.FileChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.impl.RecordedCompositeChangeImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange

class RecordedChangeFactory {
	private static RecordedChangeFactory instance;
	
	private new() {}
	
	public static def RecordedChangeFactory getInstance() {
		if (instance == null) {
			instance = new RecordedChangeFactory();
		}
		return instance;
	}
	
	public def EMFModelChange createEMFModelChange(ChangeDescription changeDescription, VURI vuri) {
		return new EMFModelChangeImpl(changeDescription, vuri);
	}
	
	public def FileChange createFileChange(FileChangeKind kind, VURI vuri) {
		return new FileChangeImpl(kind, vuri);
	}
	
	public def RecordedCompositeChange createRecordedCompositeChange() {
		return new RecordedCompositeChangeImpl();
	}
	
	public def RecordedCompositeChange createRecordedCompositeChange(Iterable<? extends RecordedChange> innerChanges) {
		val compositeChange = new RecordedCompositeChangeImpl();
		for (innerChange : innerChanges) {
			compositeChange.addChange(innerChange);
		}
		return compositeChange;
	}
}