package tools.vitruv.applications.jmljava.models;

import tools.vitruv.applications.jmljava.metamodels.JMLMetaModelProvider;

/**
 * A model URI provider for JML models.
 */
public class JMLModelURIProvider extends DirBasedModelURIProvider {

    /**
     * Constructor.
     */
    public JMLModelURIProvider() {
        super(JMLMetaModelProvider.EXTENSIONS);
    }

}
