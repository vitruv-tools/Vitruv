package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class FileChange extends Change implements URIHaving {

    public enum FileChangeKind {
        CREATE, DELETE
    }

    private VURI vuri;
    private FileChangeKind kind;

    public FileChange(final FileChangeKind kind, final VURI vuri) {
        this.vuri = vuri;
        this.kind = kind;
    }

    public FileChangeKind getFileChangeKind() {
        return this.kind;
    }

    @Override
    public edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI getURI() {
        return this.vuri;
    }

}
