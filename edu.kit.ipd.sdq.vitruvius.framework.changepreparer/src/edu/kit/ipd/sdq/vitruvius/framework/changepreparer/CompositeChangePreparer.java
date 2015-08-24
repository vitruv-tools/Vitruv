package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;

public class CompositeChangePreparer extends ConcreteChangePreparer {

    private static Logger logger = Logger.getLogger(CompositeChangePreparer.class.getSimpleName());
    private final CorrespondenceProviding correspondenceProviding;

    public CompositeChangePreparer(final CorrespondenceProviding correspondenceProviding) {
        this.correspondenceProviding = correspondenceProviding;
    }

    @Override
    Change prepareChange(final Change change) {
        final CompositeChange compositeChange = (CompositeChange) change;
        this.validateCompositeChange(compositeChange);
        if (compositeChange.getChanges().isEmpty()) {
            logger.info("Empty CompositeChange. Return empty EMFChangeResult");
            return null;
        }
        final EMFModelChange emfModelChange = (EMFModelChange) compositeChange.getChanges().get(0);
        final Set<InternalCorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getOrCreateAllCorrespondenceInstances(emfModelChange.getURI());
        if (null == correspondenceInstances || 0 == correspondenceInstances.size()) {
            logger.info("No correspondenceInstance found for model: " + emfModelChange.getURI()
                    + ". Change not sychronized with any other model.");
            return null;
        }
        return change;
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
            logger.warn(
                    "CompositeChange (" + compositeChange + ") is not valid, because it does not contain any changes.");
            return;

        }
        EMFModelChange emfModelChange;
        String fileExtension = null;
        for (final Change change : compositeChange.getChanges()) {
            if (change instanceof CompositeChange) {
                this.validateCompositeChange((CompositeChange) change);
                continue;
            }
            if (false == change instanceof EMFModelChange) {
                throw new RuntimeException(
                        "composite change is not valid, because the change (" + change + ") is no EMFModelChange.");
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
