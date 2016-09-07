package tools.vitruvius.framework.util.datatypes;

public abstract class AbstractURIHaving implements URIHaving, Comparable<URIHaving> {
    private VURI uri;

    public AbstractURIHaving(final VURI uri) {
        this.uri = uri;
    }

    @Override
    public VURI getURI() {
        return this.uri;
    }

    @Override
    public int compareTo(final URIHaving o) {
        return getURI().compareTo(o.getURI());
    }
}
