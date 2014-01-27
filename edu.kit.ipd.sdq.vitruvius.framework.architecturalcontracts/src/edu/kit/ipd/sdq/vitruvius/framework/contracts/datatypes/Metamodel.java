package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class Metamodel extends AbstractURIHaving {

    private String[] fileExtensions;

    public Metamodel(final VURI uri, final String... fileExtensions) {
        super(uri);
        this.fileExtensions = fileExtensions;
    }

    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

}
