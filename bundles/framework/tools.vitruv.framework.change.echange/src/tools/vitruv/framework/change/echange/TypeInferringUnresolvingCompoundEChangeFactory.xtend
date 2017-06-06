package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.impl.TypeInferringUnresolvingCompoundEChangeFactoryImpl

interface TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	static TypeInferringUnresolvingCompoundEChangeFactory factoryInstance = TypeInferringUnresolvingCompoundEChangeFactoryImpl::
		init

	/**
	 * Get the singleton instance of the factory.
	 * @return The singleton instance.
	 */
	def static TypeInferringUnresolvingCompoundEChangeFactory getInstance() {
		factoryInstance
	}
}
