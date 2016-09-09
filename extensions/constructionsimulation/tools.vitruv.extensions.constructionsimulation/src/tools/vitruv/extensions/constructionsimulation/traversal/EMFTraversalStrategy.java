package tools.vitruv.extensions.constructionsimulation.traversal;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import tools.vitruv.framework.change.description.CompositeContainerChange;
import tools.vitruv.framework.change.description.ConcreteChange;
import tools.vitruv.framework.change.description.EMFModelChange;
import tools.vitruv.framework.change.description.VitruviusChange;

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
    protected void addChange(final VitruviusChange change, final EList<VitruviusChange> list) {
        list.add(change);
        String changeElement = "";
        if (change instanceof ConcreteChange) {
            changeElement = change.getEChanges().toString();
            this.logger.info("Element: '" + changeElement + "' added to change list");
        }
        if (change instanceof CompositeContainerChange) {
            for (final VitruviusChange compChange : ((CompositeContainerChange) change).getChanges()) {
                if (compChange instanceof EMFModelChange) {
                    changeElement = (((EMFModelChange) compChange).getEChanges().toString());
                    this.logger.info("Element: '" + changeElement + "' added to change list");
                }

            }
        }
    }

}
