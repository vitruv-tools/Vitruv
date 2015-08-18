package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public interface Change2CommandTransforming {
    /** The extension point ID. **/
    String ID = "edu.kit.ipd.sdq.vitruvius.framework.contracts.change2commandtransforming";

    void transformChanges2Commands(Blackboard blackboard);

    List<Pair<VURI, VURI>> getTransformableMetamodels();
}
