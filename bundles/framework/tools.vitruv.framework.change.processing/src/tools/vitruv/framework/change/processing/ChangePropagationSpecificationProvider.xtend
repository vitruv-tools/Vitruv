package tools.vitruv.framework.change.processing

import tools.vitruv.framework.util.datatypes.VURI
import java.util.List

interface ChangePropagationSpecificationProvider extends Iterable<ChangePropagationSpecification> {
	public def List<ChangePropagationSpecification> getChangePropagationSpecifications(VURI metamodelVuri);
}