package tools.vitruv.extensions.dslsruntime.reactions.structure

import org.apache.log4j.Logger
import org.apache.log4j.Level

class Loggable {
	val Logger LOGGER

	new() {
		LOGGER = Logger::getLogger(this.class)
		LOGGER.level = Level::DEBUG
	}

	protected def Logger getLogger() {
		LOGGER
	}
}
