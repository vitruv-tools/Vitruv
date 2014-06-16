package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;
import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

public interface TransformationExecuting {
    /** The extension point ID. **/
    String ID = "edu.kit.ipd.sdq.vitruvius.framework.contracts.transformationexecuting";

    Set<VURI> executeTransformation(Change change, ModelInstance sourceModel, CorrespondenceInstance correspondenceInstance);

    List<Pair<VURI, VURI>> getTransformableMetamodels();
}
