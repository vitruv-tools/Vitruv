package edu.kit.ipd.sdq.vitruvius.framework.synctransprovider;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecutingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EclipseBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

/**
 * The class @SyncTransformationProviderImpl provides the interface
 * 
 * @link(TransformationExecutingProviding) hence it enable users of the class to find the correct
 *                                         synchronisation Transformation for two meta models. In
 *                                         the first iteration SyncTransformationProviderImpl
 *                                         directly knows the syncTransformations instead of looking
 *                                         it up via an extension point mechanism.
 * 
 * @author Langhamm
 * 
 */

public class TransformationExecutingProvidingImpl implements TransformationExecutingProviding {

    private ClaimableMap<Pair<VURI, VURI>, TransformationExecuting> transformationExecuterMap;

    public TransformationExecutingProvidingImpl() {
        this.transformationExecuterMap = new ClaimableHashMap<Pair<VURI, VURI>, TransformationExecuting>();
        List<TransformationExecuting> transformationExecutingList = EclipseBridge.getRegisteredExtensions(
                TransformationExecuting.ID, VitruviusConstants.getExtensionPropertyName(),
                TransformationExecuting.class);
        for (final TransformationExecuting transformationExecuting : transformationExecutingList) {
            // TODO if third party extensions are used call all extensions in protected mode
            // EclipseBridge.callInProtectedMode(callable);
            List<Pair<VURI, VURI>> transformableMetamodels = transformationExecuting.getTransformableMetamodels();
            for (Pair<VURI, VURI> transformableMetamodel : transformableMetamodels) {
                this.transformationExecuterMap.put(transformableMetamodel, transformationExecuting);
            }
        }
    }

    @Override
    public SyncTransformation getSyncTransformation(final VURI mmURI1, final Change change, final VURI mmURI2) {
        throw new RuntimeException("getSyncTransformation is not implemented yet");
    }

    @Override
    public TransformationExecuting getTransformationExecuting(final VURI mmURI1, final VURI mmURI2) {
        Pair<VURI, VURI> vuriPair = new Pair<VURI, VURI>(mmURI1, mmURI2);
        return this.transformationExecuterMap.claimValueForKey(vuriPair);
    }
}
