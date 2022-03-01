package tools.vitruv.testutils.change.processing

import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.propagation.impl.CompositeChangePropagationSpecification
import java.util.Set

class CombinedChangePropagationSpecification extends CompositeChangePropagationSpecification {
	new(Set<String> sourceMetamodelRootNsUris, Set<String> targetMetamodelRoosNsUris,
		Iterable<? extends ChangePropagationSpecification> processors) {
		super(sourceMetamodelRootNsUris, targetMetamodelRoosNsUris);
		processors.forEach[addChangeMainprocessor(it)]
	}
}
