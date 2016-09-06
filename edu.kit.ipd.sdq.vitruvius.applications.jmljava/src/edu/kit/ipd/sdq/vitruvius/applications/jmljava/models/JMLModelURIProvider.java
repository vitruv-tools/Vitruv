package edu.kit.ipd.sdq.vitruvius.applications.jmljava.models;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JMLMetaModelProvider;

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
