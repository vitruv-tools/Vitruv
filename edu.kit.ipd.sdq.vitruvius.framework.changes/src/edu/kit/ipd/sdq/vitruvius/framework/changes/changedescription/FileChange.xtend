package edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription

interface FileChange extends ConcreteChange {
	public enum FileChangeKind {
        CREATE, DELETE
    }
	
	def FileChangeKind getFileChangeKind();
	
}