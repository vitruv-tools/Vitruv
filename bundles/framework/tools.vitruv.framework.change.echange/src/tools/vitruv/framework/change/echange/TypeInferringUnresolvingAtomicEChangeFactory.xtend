package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.impl.TypeInferringUnresolvingAtomicEChangeFactoryImpl

/**
 * Factory singleton class for elements of change models.
 * Creates only changes which EObjects are replaced by proxy elements.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
interface TypeInferringUnresolvingAtomicEChangeFactory extends TypeInferringAtomicEChangeFactory {
	static TypeInferringUnresolvingAtomicEChangeFactory factoryInstance = TypeInferringUnresolvingAtomicEChangeFactoryImpl::init

	/**
	 * Get the singleton instance of the factory.
	 * @return The singleton instance.
	 */
	def static TypeInferringUnresolvingAtomicEChangeFactory getInstance() {
		factoryInstance
	}
}
