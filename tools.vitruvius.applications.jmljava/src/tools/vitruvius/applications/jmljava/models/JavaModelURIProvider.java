package tools.vitruvius.applications.jmljava.models;

import tools.vitruvius.applications.jmljava.metamodels.JaMoPPMetaModelProvider;

/**
 * A provider for Java model URIs.
 */
public class JavaModelURIProvider extends DirBasedModelURIProvider {

    /**
     * Constructor.
     */
    public JavaModelURIProvider() {
        super(JaMoPPMetaModelProvider.EXTENSIONS);
    }
 
}
