package tools.vitruv.framework.change.echange

import tools.vitruv.framework.uuid.UuidGeneratorAndResolver

final class TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	
	new(UuidGeneratorAndResolver uuidProviderAndResolver) {
		super(new TypeInferringUnresolvingAtomicEChangeFactory(uuidProviderAndResolver));
	}
	
}
