package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
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
        super(modelProviding, changeSynchronizing);
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
    }

    @Override
    public void synchronizeChange(final Change change, final VURI sourceModelURI) {
        ModelInstance sourceModel = this.modelProviding.getModelInstanceOriginal(sourceModelURI);
        Set<CorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getAllCorrespondenceInstances(sourceModelURI);
        if (null == correspondenceInstances || 0 == correspondenceInstances.size()) {
            logger.info("No correspondenceInstance found for model: " + sourceModelURI
                    + ". Change not sychronized with any other model.");
            return;
        }
        for (CorrespondenceInstance correspondenceInstance : correspondenceInstances) {
            this.changePropagating.propagateChange(change, sourceModel, correspondenceInstance);
        }
    }
}
