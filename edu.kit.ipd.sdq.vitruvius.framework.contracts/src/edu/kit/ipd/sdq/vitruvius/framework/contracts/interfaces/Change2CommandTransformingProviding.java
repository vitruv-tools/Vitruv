package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface Change2CommandTransformingProviding {
    public Change2CommandTransforming getChange2CommandTransforming(VURI mmURI1, VURI mmURI2);
}
