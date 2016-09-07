package tools.vitruv.applications.jmljava.models;

import tools.vitruv.applications.jmljava.metamodels.JaMoPPMetaModelProvider;

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
