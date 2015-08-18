package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

/**
 * Prepares a model change.
 *
 * @author Langhamm
 *
 */
abstract class ConcreteChangePreparer {
    abstract Change prepareChange(Change change);
}
