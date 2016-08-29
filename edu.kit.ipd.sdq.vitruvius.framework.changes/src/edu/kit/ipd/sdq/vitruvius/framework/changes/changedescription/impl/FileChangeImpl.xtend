package edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.impl

import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.FileChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI

class FileChangeImpl extends ConcreteChangeImpl implements FileChange {
    FileChangeKind kind;

    new(FileChangeKind kind, VURI vuri) {
    	super(vuri);
        this.kind = kind;
    }

    override FileChangeKind getFileChangeKind() {
        return this.kind;
    }

}
