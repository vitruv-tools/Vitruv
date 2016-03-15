package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.interfaces.IResponseRealization
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

abstract class AbstractResponseRealization implements IResponseRealization {
	protected val UserInteracting userInteracting;

	public new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	protected abstract def Logger getLogger();
	
}