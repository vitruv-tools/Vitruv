package tools.vitruv.framework.change.processing

import tools.vitruv.framework.util.datatypes.VURI
import java.util.List

interface ChangePropagationSpecificationProvider extends Iterable<ChangeProcessor> {
	public def List<ChangeProcessor> getChangePropagationSpecifications(VURI metamodelVuri);
}