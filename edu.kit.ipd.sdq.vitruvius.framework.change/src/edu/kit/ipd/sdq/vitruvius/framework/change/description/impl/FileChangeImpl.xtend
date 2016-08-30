package edu.kit.ipd.sdq.vitruvius.framework.change.description.impl

import edu.kit.ipd.sdq.vitruvius.framework.change.description.FileChange
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource

class FileChangeImpl extends ConcreteChangeImpl implements FileChange {
    FileChangeKind kind;
	Resource fileResource;
	
    new(FileChangeKind kind, Resource changedFileResource) {
    	super(VURI.getInstance(changedFileResource));
        this.kind = kind;
        this.fileResource = changedFileResource;
    }

    override FileChangeKind getFileChangeKind() {
        return this.kind;
    }
				
	override getChangedFileResource() {
		return this.fileResource;
	}

}
