package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners.CompositeChangeRefinerResult;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange;

/**
 * Refiner for a composite changes. If it matches a given composite change it can be used to refine
 * the change. This means the whole composite change is converted to separate change operations or a
 * complex transformation. Anyway the result will be an result object, which can be applied. The
 * necessary actions are hidden from the user.
 */
public interface CompositeChangeRefiner {

    /**
     * Indicates if the refiner can be used with the given composite change.
     * 
     * @param change
     *            The composite change to test.
     * @return True if the refiner can be applied.
     */
    boolean match(CompositeChange change);

    /**
     * Refines the composite changes and creates a list of ordered model changes.
     * 
     * @param change
     *            The composite change to refine.
     * @return A result, which can be applied.
     */
    CompositeChangeRefinerResult refine(CompositeChange change);

}