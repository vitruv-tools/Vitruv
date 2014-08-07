package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public interface EMFModelTransformationExecuting {
    /** The extension point ID. **/
    String ID = "edu.kit.ipd.sdq.vitruvius.framework.contracts.transformationexecuting";

    EMFChangeResult executeTransformation(EMFModelChange change, CorrespondenceInstance correspondenceInstance);

    EMFChangeResult executeTransformation(CompositeChange compositeChange, CorrespondenceInstance correspondenceInstance);

    List<Pair<VURI, VURI>> getTransformableMetamodels();
}
