package tools.vitruvius.framework.change.processing.impl;

import java.util.Iterator;
import java.util.List;

import tools.vitruvius.framework.change.processing.Change2CommandTransforming;
import tools.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import tools.vitruvius.framework.util.datatypes.ClaimableHashMap;
import tools.vitruvius.framework.util.datatypes.ClaimableMap;
import tools.vitruvius.framework.util.datatypes.MetamodelPair;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * This abstract implementation of the {@link Change2CommandTransformingProviding} provides a
 * default implementation for the handling of {@link Change2CommandTransforming}s. A concrete
 * {@link Change2CommandTransformingProviding} can be derived from this class adding the needed
 * transformation executors.
 *
 * @author Heiko Klare
 */
public abstract class AbstractChange2CommandTransformingProviding implements Change2CommandTransformingProviding {
    private ClaimableMap<MetamodelPair, Change2CommandTransforming> transformationExecuterMap;

    public AbstractChange2CommandTransformingProviding() {
        this.transformationExecuterMap = new ClaimableHashMap<MetamodelPair, Change2CommandTransforming>();
        setup();
    }

    public void addChange2CommandTransforming(final Change2CommandTransforming change2CommandTransforming) {
        this.transformationExecuterMap.put(change2CommandTransforming.getTransformableMetamodels(),
                change2CommandTransforming);
    }

    @Override
    public Change2CommandTransforming getChange2CommandTransforming(final VURI mmURI1, final VURI mmURI2) {
    	MetamodelPair vuriPair = new MetamodelPair(mmURI1, mmURI2);
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
