package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.internal;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Provider for transformation executings based on @Change2CommandTransformingProvidingImpl
 * TransformationExecutings can be registered via a method in addition to the usual Eclipse
 * extension point mechanism of Eclipse.
 */
public class Change2CommandTransformingProvidingImplCustom extends Change2CommandTransformingProvidingImpl {

    /**
     * Constructor
     */
    public Change2CommandTransformingProvidingImplCustom() {
        super();
    }

    /**
     * Adds a transformation executing to the provider.
     *
     * @param change2CommandTransforming
     *            The transformation executing to be added.
     */
    public void addChange2CommandTransforming(final Change2CommandTransforming change2CommandTransforming) {
        for (final Pair<VURI, VURI> transformableMetamodel : change2CommandTransforming.getTransformableMetamodels()) {
            this.transformationExecuterMap.put(transformableMetamodel, change2CommandTransforming);
        }
    }
}
