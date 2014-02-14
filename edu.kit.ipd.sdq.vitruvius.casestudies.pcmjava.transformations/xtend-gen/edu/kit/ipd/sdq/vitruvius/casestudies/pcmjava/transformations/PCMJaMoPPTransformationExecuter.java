package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecuting;

public class PCMJaMoPPTransformationExecuter implements TransformationExecuting {

    private final ChangeSynchronizer changeSynchronizer;

    public PCMJaMoPPTransformationExecuter() {
        this.changeSynchronizer = new ChangeSynchronizer();
    }

    @Override
    public void executeTransformation(final Change change, final ModelInstance sourceModel,
            final CorrespondenceInstance correspondenceInstance) {
        final EMFModelChange emfModelChange = (EMFModelChange) change;
        this.changeSynchronizer.setCorrespondenceInstance(correspondenceInstance);
        this.changeSynchronizer.synchronizeChange(emfModelChange.getEChange());
    }

}
