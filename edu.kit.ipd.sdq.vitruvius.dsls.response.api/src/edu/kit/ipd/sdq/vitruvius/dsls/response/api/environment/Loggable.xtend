package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import org.apache.log4j.Logger

class Loggable {
	private val Logger LOGGER;
	
	public new() {
		LOGGER = Logger.getLogger(this.class);
	}
	
	protected def Logger getLogger() {
		return LOGGER;
	}
}