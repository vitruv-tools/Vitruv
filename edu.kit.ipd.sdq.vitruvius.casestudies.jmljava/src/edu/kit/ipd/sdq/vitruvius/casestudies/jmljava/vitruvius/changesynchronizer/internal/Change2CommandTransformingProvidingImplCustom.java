package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.internal;

import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;

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

}
