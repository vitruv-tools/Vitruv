package edu.kit.ipd.sdq.vitruvius.integration.traversal;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;

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
    protected void addChange(final Change change, final EList<Change> list) {
        list.add(change);
        String changeElement = "";
        if (change instanceof EMFModelChange) {
            changeElement = (((EMFModelChange) change).getEChange().toString());
            this.logger.info("Element: '" + changeElement + "' added to change list");
        }
        if (change instanceof CompositeChange) {
            for (final Change compChange : ((CompositeChange) change).getChanges()) {
                if (compChange instanceof EMFModelChange) {
                    changeElement = (((EMFModelChange) compChange).getEChange().toString());
                    this.logger.info("Element: '" + changeElement + "' added to change list");
                }

            }
        }
    }

}
