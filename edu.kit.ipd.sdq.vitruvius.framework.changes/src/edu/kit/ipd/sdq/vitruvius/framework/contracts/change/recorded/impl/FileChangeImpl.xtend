package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.FileChange

class FileChangeImpl extends RecordedConcreteChangeImpl implements FileChange {
    FileChangeKind kind;

    new(FileChangeKind kind, VURI vuri) {
    	super(vuri);
        this.kind = kind;
    }

    override FileChangeKind getFileChangeKind() {
        return this.kind;
    }

	override containsConcreteChange() {
		return true;
	}
	
}
