package edu.kit.ipd.sdq.vitruvius.framework.contracts.change

interface FileChange extends ConcreteChange {
	public enum FileChangeKind {
        CREATE, DELETE
    }
	
	def FileChangeKind getFileChangeKind();
	
}