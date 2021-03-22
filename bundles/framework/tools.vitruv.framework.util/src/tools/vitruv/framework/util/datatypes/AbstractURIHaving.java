package tools.vitruv.framework.util.datatypes;

import org.eclipse.emf.common.util.URI;

public abstract class AbstractURIHaving implements URIHaving, Comparable<URIHaving> {
    private URI uri;

    public AbstractURIHaving(final URI uri) {
        this.uri = uri;
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public int compareTo(final URIHaving o) {
        return getURI().toString().compareTo(o.getURI().toString());
    }
}
