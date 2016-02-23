package edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider;

import java.util.Iterator;
import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * The class provides the interface @link(Change2CommandTransformingProviding) hence it enable users
 * of the class to find the correct synchronisation Transformation for two meta models. In the first
 * iteration SyncTransformationProviderImpl directly knows the syncTransformations instead of
 * looking it up via an extension point mechanism.
 *
 * @author Langhamm
 *
 */

public class Change2CommandTransformingProvidingImpl implements Change2CommandTransformingProviding {

    protected ClaimableMap<Pair<VURI, VURI>, Change2CommandTransforming> transformationExecuterMap;

    public Change2CommandTransformingProvidingImpl() {
        this.transformationExecuterMap = new ClaimableHashMap<Pair<VURI, VURI>, Change2CommandTransforming>();
        List<Change2CommandTransforming> transformationExecutingList = EclipseBridge.getRegisteredExtensions(
                Change2CommandTransforming.ID, VitruviusConstants.getExtensionPropertyName(),
                Change2CommandTransforming.class);
        for (final Change2CommandTransforming transformationExecuting : transformationExecutingList) {
            // TODO if third party extensions are used call all extensions in protected mode
            // EclipseBridge.callInProtectedMode(callable);
            List<Pair<VURI, VURI>> transformableMetamodels = transformationExecuting.getTransformableMetamodels();
            for (Pair<VURI, VURI> transformableMetamodel : transformableMetamodels) {
                this.transformationExecuterMap.put(transformableMetamodel, transformationExecuting);
            }
        }
    }

    @Override
    public Change2CommandTransforming getChange2CommandTransforming(final VURI mmURI1, final VURI mmURI2) {
        Pair<VURI, VURI> vuriPair = new Pair<VURI, VURI>(mmURI1, mmURI2);
        return this.transformationExecuterMap.claimValueForKey(vuriPair);
    }

    @Override
    public Iterator<Change2CommandTransforming> iterator() {
        return this.transformationExecuterMap.values().iterator();
    }
}
