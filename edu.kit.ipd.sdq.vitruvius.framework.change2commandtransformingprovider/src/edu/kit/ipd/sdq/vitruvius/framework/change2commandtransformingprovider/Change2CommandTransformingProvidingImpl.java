package edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;

// TODO ML the documentation is not correct any more?
/**
 * The class provides the interface @link(Change2CommandTransformingProviding) hence it enable users
 * of the class to find the correct synchronisation Transformation for two meta models. In the first
 * iteration SyncTransformationProviderImpl directly knows the syncTransformations instead of
 * looking it up via an extension point mechanism.
 *
 * @author Langhamm
 *
 */
public class Change2CommandTransformingProvidingImpl extends AbstractChange2CommandTransformingProviding {

    @Override
    protected void setup() {
        List<Change2CommandTransforming> transformationExecutingList = EclipseBridge.getRegisteredExtensions(
                Change2CommandTransforming.ID, VitruviusConstants.getExtensionPropertyName(),
                Change2CommandTransforming.class);
        for (final Change2CommandTransforming transformationExecuting : transformationExecutingList) {
            // TODO if third party extensions are used call all extensions in protected mode
            // EclipseBridge.callInProtectedMode(callable);
            addChange2CommandTransforming(transformationExecuting);
        }
    }

}
