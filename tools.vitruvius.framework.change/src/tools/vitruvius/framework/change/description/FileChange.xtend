package tools.vitruvius.framework.change.description

import org.eclipse.emf.ecore.resource.Resource

interface FileChange extends ConcreteChange {
	public enum FileChangeKind {
        CREATE, DELETE
    }
	
	def FileChangeKind getFileChangeKind();
	
	def Resource getChangedFileResource();
	
}