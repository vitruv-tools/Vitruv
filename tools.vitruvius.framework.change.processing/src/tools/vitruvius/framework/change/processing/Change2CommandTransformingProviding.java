package tools.vitruvius.framework.change.processing;

import tools.vitruvius.framework.util.datatypes.VURI;

public interface Change2CommandTransformingProviding extends Iterable<Change2CommandTransforming> {
    public Change2CommandTransforming getChange2CommandTransforming(VURI mmURI1, VURI mmURI2);
}
