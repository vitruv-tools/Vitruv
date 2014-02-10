package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class FileChange extends Change {

    public enum FileChangeKind {
        CREATE, DELETE
    }

    private FileChangeKind kind;

    public FileChange(final FileChangeKind kind) {
        this.kind = kind;
    }

    public FileChangeKind getFileChangeKind() {
        return this.kind;
    }

}
