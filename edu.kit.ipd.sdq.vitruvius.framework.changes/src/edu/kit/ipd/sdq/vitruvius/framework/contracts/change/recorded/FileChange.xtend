package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URIHaving
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange

interface FileChange extends RecordedChange, URIHaving {
	public enum FileChangeKind {
        CREATE, DELETE
    }
	
	def FileChangeKind getFileChangeKind();
	
}