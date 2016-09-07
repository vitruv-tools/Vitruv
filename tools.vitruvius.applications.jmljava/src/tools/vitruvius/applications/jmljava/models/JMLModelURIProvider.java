package tools.vitruvius.applications.jmljava.models;

import tools.vitruvius.applications.jmljava.metamodels.JMLMetaModelProvider;

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
