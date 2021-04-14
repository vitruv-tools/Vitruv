package tools.vitruv.framework.tests.echange

import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.id.IdResolverAndRepository

package final class TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	
	new(IdResolverAndRepository idResolverAndRepository) {
		super(new TypeInferringUnresolvingAtomicEChangeFactory(idResolverAndRepository));
	}
	
}
