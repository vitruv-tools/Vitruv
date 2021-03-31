package tools.vitruv.framework.tests.echange

import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory

package final class TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	
	new(UuidGeneratorAndResolver uuidProviderAndResolver) {
		super(new TypeInferringUnresolvingAtomicEChangeFactory(uuidProviderAndResolver));
	}
	
}
