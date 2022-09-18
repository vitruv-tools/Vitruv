package tools.vitruv.variability.vave;

/**
 * Initializes a new empty Virtual Product Model (VSUM) during vave product externalization before the first changes are propagated.
 */
public interface VirtualProductModelInitializer {

	/**
	 * This method is invoked during externalization of a product on the created VSUM before the first changes are propagated.
	 * 
	 * @param virtualProductModel The newly created and still empty Virtual Product Model (VSUM).
	 */
	public void initialize(VirtualProductModel virtualProductModel);

}
