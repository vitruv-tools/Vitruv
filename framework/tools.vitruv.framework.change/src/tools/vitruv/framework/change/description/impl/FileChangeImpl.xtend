package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.FileChange
import tools.vitruv.framework.util.datatypes.VURI
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
