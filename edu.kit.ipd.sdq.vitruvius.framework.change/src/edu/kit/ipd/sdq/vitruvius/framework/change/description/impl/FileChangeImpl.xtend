package edu.kit.ipd.sdq.vitruvius.framework.change.description.impl

import edu.kit.ipd.sdq.vitruvius.framework.change.description.FileChange
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI

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
