package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.FileChange
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.preparation.ChangeToEChangeConverter

class FileChangeImpl extends AbstractConcreteChange implements FileChange {
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

	override prepare(ChangeToEChangeConverter converter) {
		this.eChanges.clear();
		this.eChanges.addAll(converter.calculateEChangesForChange(this));
		setPrepated();
	}	
}
