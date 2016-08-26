package edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider;

import java.util.Iterator;
import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;

/**
 * This abstract implementation of the {@link Change2CommandTransformingProviding} provides a
 * default implementation for the handling of {@link Change2CommandTransforming}s. A concrete
 * {@link Change2CommandTransformingProviding} can be derived from this class adding the needed
 * transformation executors.
 *
 * @author Heiko Klare
 */
public abstract class AbstractChange2CommandTransformingProviding implements Change2CommandTransformingProviding {
    private ClaimableMap<TransformationMetamodelPair, Change2CommandTransforming> transformationExecuterMap;

    public AbstractChange2CommandTransformingProviding() {
        this.transformationExecuterMap = new ClaimableHashMap<TransformationMetamodelPair, Change2CommandTransforming>();
        setup();
    }

    public void addChange2CommandTransforming(final Change2CommandTransforming change2CommandTransforming) {
        this.transformationExecuterMap.put(change2CommandTransforming.getTransformableMetamodels(),
                change2CommandTransforming);
    }

    @Override
    public Change2CommandTransforming getChange2CommandTransforming(final VURI mmURI1, final VURI mmURI2) {
        TransformationMetamodelPair vuriPair = new TransformationMetamodelPair(mmURI1, mmURI2);
        return this.transformationExecuterMap.claimValueForKey(vuriPair);
    }

    @Override
    public Iterator<Change2CommandTransforming> iterator() {
        return this.transformationExecuterMap.values().iterator();
    }

    protected abstract void setup();

    public static Change2CommandTransformingProviding createChange2CommandTransformingProviding(
            final List<Change2CommandTransforming> change2CommandTransformings) {
        return new AbstractChange2CommandTransformingProviding() {
            @Override
            public void setup() {
                for (Change2CommandTransforming transformer : change2CommandTransformings) {
                    addChange2CommandTransforming(transformer);
                }
            }
        };
    }
}
