package tools.vitruvius.framework.change.processing.impl;

import java.util.List;

import tools.vitruvius.framework.change.processing.Change2CommandTransforming;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import tools.vitruvius.framework.userinteraction.impl.UserInteractor;
import tools.vitruvius.framework.util.VitruviusConstants;
import tools.vitruvius.framework.util.bridges.EclipseBridge;

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

    /**
     * Defines the user interacting that is used for all Change2CommandTransforming implementations.
     * For now, we use the same user interactor for all Change2CommandTransforming. In future work
     * this should be extended to allow the use of different user interactors depending on the used
     * metamodels.
     */
    private final UserInteracting userInteracting;

    public Change2CommandTransformingProvidingImpl() {
        this.userInteracting = new UserInteractor();
    }

    @Override
    protected void setup() {
        List<Change2CommandTransforming> transformationExecutingList = EclipseBridge.getRegisteredExtensions(
                Change2CommandTransforming.ExtensionPointID, VitruviusConstants.getExtensionPropertyName(),
                Change2CommandTransforming.class);
        for (final Change2CommandTransforming transformationExecuting : transformationExecutingList) {
            // TODO if third party extensions are used call all extensions in protected mode
            // EclipseBridge.callInProtectedMode(callable);
            addChange2CommandTransforming(transformationExecuting);
            transformationExecuting.setUserInteracting(this.userInteracting);
        }
    }

}
