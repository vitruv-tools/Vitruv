package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

/**
 * The interface for the change listeners created from a MIR specification.
 * 
 * A change listener only creates a list of proposed changes that follow a
 * {@link EChange}. Whether all changes are applied and how the changes are
 * propagated (e.g. to another EChangeListener) is handled by the caller. 
 * 
 * @author Dominik Werle
 *
 */
public interface EChangeListener {
	/**
	 * Handles a list of {@link EChange EChanges} and returns the
	 * actions that are to be performed as a reaction.
	 * @param change the change to handle
	 * @return the response actions for the change
	 */
	public List<EChange> handleChange(EChange change);
}
