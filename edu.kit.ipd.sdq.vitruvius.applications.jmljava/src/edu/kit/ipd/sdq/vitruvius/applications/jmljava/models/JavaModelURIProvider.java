package edu.kit.ipd.sdq.vitruvius.applications.jmljava.models;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JaMoPPMetaModelProvider;

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
