package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

public class CompositeChangeSynchronizer extends ConcreteChangeSynchronizer {

    private static Logger logger = Logger.getLogger(CompositeChangeSynchronizer.class.getSimpleName());
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;

    public CompositeChangeSynchronizer(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding) {
        super(modelProviding);
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
    }

    @Override
    ChangeResult synchronizeChange(final Change change) {
        CompositeChange compositeChange = (CompositeChange) change;
        validateCompositeChange(compositeChange);
        EMFModelChange emfModelChange = (EMFModelChange) compositeChange.getChanges().get(0);
        Set<CorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getAllCorrespondenceInstances(emfModelChange.getURI());
        if (null == correspondenceInstances || 0 == correspondenceInstances.size()) {
            logger.info("No correspondenceInstance found for model: " + emfModelChange.getURI()
                    + ". Change not sychronized with any other model.");
            return new EMFChangeResult();
        }
        EMFChangeResult emfChangeResult = new EMFChangeResult();
        for (CorrespondenceInstance correspondenceInstance : correspondenceInstances) {
            ChangeResult currentChangeResult = this.changePropagating.propagateChange(compositeChange,
                    correspondenceInstance);
            emfChangeResult.addChangeResult((EMFChangeResult) currentChangeResult);
        }
        return emfChangeResult;
    }

    /**
     * checks whether a composite Change is valid In our implementation a composite change is valid
     * iff i) it contains only EMFModelChanges and CompositeChanges ii) all changes occured in the
     * same source meta model iii) it contains at least one change
     * 
     * @param compositeChange
     */
    private void validateCompositeChange(final CompositeChange compositeChange) {
        if (compositeChange.getChanges().isEmpty()) {
            throw new RuntimeException("CompositeChange (" + compositeChange
                    + ") is not valid, because it does not contain any changes.");
        }
        EMFModelChange emfModelChange;
        String fileExtension = null;
        for (Change change : compositeChange.getChanges()) {
            if (change instanceof CompositeChange) {
                validateCompositeChange((CompositeChange) change);
                continue;
            }
            if (false == change instanceof EMFModelChange) {
                throw new RuntimeException("composite change is not valid, because the change (" + change
                        + ") is no EMFModelChange.");
            }
            emfModelChange = (EMFModelChange) change;
            if (null == fileExtension) {
                fileExtension = emfModelChange.getURI().getFileExtension();
                continue;
            }
            if (!fileExtension.equals(emfModelChange.getURI().getFileExtension())) {
                throw new RuntimeException("composite change is not valid, because the change (" + change
                        + ") does not have the file extension " + fileExtension);
            }
        }
    }
}
