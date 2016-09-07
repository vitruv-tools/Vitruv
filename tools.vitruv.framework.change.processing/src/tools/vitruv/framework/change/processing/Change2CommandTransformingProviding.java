package tools.vitruv.framework.change.processing;

import tools.vitruv.framework.util.datatypes.VURI;

public interface Change2CommandTransformingProviding extends Iterable<Change2CommandTransforming> {
    public Change2CommandTransforming getChange2CommandTransforming(VURI mmURI1, VURI mmURI2);
}
