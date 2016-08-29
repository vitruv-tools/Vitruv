package edu.kit.ipd.sdq.vitruvius.framework.change.description

interface FileChange extends ConcreteChange {
	public enum FileChangeKind {
        CREATE, DELETE
    }
	
	def FileChangeKind getFileChangeKind();
	
}