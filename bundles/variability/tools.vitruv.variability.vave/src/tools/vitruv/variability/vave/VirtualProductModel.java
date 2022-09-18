package tools.vitruv.variability.vave;

import java.util.Collection;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.variability.vave.model.vave.Configuration;

/**
 * Extends the internal virtual model into a Virtual Product Model. 
 */
public interface VirtualProductModel extends InternalVirtualModel {

	/**
	 * Returns the configuration of the Virtual Product Model.
	 * 
	 * @return The configuration stored in the Virtual Product Model.
	 */
	public Configuration getConfiguration();

	/**
	 * Returns a collection of Vitruvius Changes that were applied to the Virtual Product Model.
	 * 
	 * @return a collection of Vitruvius Changes.
	 */
	public Collection<VitruviusChange> getDeltas();

	/**
	 * Clears the Vitruvius Changes stored in the Virtual Product Model.
	 */
	public void clearDeltas();

}
