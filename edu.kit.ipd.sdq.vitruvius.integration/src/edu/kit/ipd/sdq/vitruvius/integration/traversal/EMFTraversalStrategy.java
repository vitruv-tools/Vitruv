package edu.kit.ipd.sdq.vitruvius.integration.traversal;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.ProcessableCompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProcessableChange;

/**
 * This class provides common methods for similar traversal strategies regarding PCM models.
 *
 * @author Sven Leonhardt
 */

public abstract class EMFTraversalStrategy {

    protected Logger logger = Logger.getRootLogger();

    /**
     * Adds a change object to a given list and generates a log entry.
     *
     * @param change
     *            the change to add
     * @param list
     *            the list
     */
    protected void addChange(final ProcessableChange change, final EList<Change> list) {
        list.add(change);
        String changeElement = "";
        if (change instanceof VitruviusChange) {
            changeElement = (((VitruviusChange) change).getEChanges().toString());
            this.logger.info("Element: '" + changeElement + "' added to change list");
        }
        if (change instanceof ProcessableCompositeChange) {
            for (final Change compChange : ((ProcessableCompositeChange) change).getChanges()) {
                if (compChange instanceof EMFModelChange) {
                    changeElement = (((VitruviusChange) compChange).getEChanges().toString());
                    this.logger.info("Element: '" + changeElement + "' added to change list");
                }

            }
        }
    }

}
