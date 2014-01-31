package edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SyncTransformationProviding;

public class PropagationEngineImpl implements ChangePropagating {

    private final SyncTransformationProviding syncTransformationProviding;

    public PropagationEngineImpl(final SyncTransformationProviding syncTransformationProviding) {
        this.syncTransformationProviding = syncTransformationProviding;
    }

    @Override
    public void propagateChange(final Change change, final ModelInstance sourceModel,
            final CorrespondenceInstance correspondenceInstance, final ModelInstance targetModel) {
        // TODO Auto-generated method stub

    }

}
