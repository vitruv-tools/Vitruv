package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public abstract class AbstractURIHaving implements URIHaving {
    private VURI uri;

    public AbstractURIHaving(final VURI uri) {
        this.uri = uri;
    }

    @Override
    public VURI getURI() {
        return this.uri;
    }

}
