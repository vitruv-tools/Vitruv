package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

class EMFModelSynchronizer extends ConcreteChangeSynchronizer {

    private static final Logger logger = Logger.getLogger(EMFModelSynchronizer.class.getSimpleName());

    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;

    public EMFModelSynchronizer(final ModelProviding modelProviding, final ChangeSynchronizing changeSynchronizing,
            final ChangePropagating changePropagating, final CorrespondenceProviding correspondenceProviding) {
        super(modelProviding);
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
    }

    @Override
    public ChangeResult synchronizeChange(final Change change) {
        EMFModelChange emfModelChange = (EMFModelChange) change;
        VURI sourceModelURI = emfModelChange.getURI();
        // called in order to create the source model URI if it's not existing already
        this.modelProviding.getAndLoadModelInstanceOriginal(sourceModelURI);
        Set<CorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getAllCorrespondenceInstances(sourceModelURI);
        if (null == correspondenceInstances || 0 == correspondenceInstances.size()) {
            logger.info("No correspondenceInstance found for model: " + sourceModelURI
                    + ". Change not sychronized with any other model.");
            return new EMFChangeResult();
        }
        EMFChangeResult globalEMFChangeResult = new EMFChangeResult();
        for (CorrespondenceInstance correspondenceInstance : correspondenceInstances) {
            ChangeResult currentChangeResult = this.changePropagating.propagateChange(change, correspondenceInstance);
            globalEMFChangeResult.addChangeResult((EMFChangeResult) currentChangeResult);
        }
        return globalEMFChangeResult;
    }
}
