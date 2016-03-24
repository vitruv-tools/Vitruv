package edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider;

import java.util.Iterator;
import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * This abstract implementation of the {@link Change2CommandTransformingProviding} provides a
 * default implementation for the handling of {@link Change2CommandTransforming}s. A concrete
 * {@link Change2CommandTransformingProviding} can be derived from this class adding the needed
 * transformation executors.
 *
 * @author Heiko Klare
 */
public abstract class AbstractChange2CommandTransformingProviding implements Change2CommandTransformingProviding {
    private ClaimableMap<Pair<VURI, VURI>, Change2CommandTransforming> transformationExecuterMap;

    public AbstractChange2CommandTransformingProviding() {
        this.transformationExecuterMap = new ClaimableHashMap<Pair<VURI, VURI>, Change2CommandTransforming>();
    }

    public void addChange2CommandTransforming(final Change2CommandTransforming change2CommandTransforming) {
        List<Pair<VURI, VURI>> transformableMetamodels = change2CommandTransforming.getTransformableMetamodels();
        for (Pair<VURI, VURI> transformableMetamodel : transformableMetamodels) {
            this.transformationExecuterMap.put(transformableMetamodel, change2CommandTransforming);
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
