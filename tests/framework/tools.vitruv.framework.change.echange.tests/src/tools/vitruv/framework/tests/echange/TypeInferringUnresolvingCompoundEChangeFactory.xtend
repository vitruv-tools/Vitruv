package tools.vitruv.framework.tests.echange

import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.id.IdResolver

package final class TypeInferringUnresolvingCompoundEChangeFactory extends TypeInferringCompoundEChangeFactory {
	
	new(IdResolver idResolver) {
		super(new TypeInferringUnresolvingAtomicEChangeFactory(idResolver));
	}
	
}
