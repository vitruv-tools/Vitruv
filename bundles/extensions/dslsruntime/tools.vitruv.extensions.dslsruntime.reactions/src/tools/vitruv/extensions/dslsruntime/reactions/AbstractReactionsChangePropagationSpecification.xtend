package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.propagation.impl.CompositeChangePropagationSpecification
import java.util.Set

/**
 * A {@link CompositeChangePropagationSpecification} that contains the reactions change processor.
 * To add further change processors extend the implementing class and override the setup method.
 */
abstract class AbstractReactionsChangePropagationSpecification extends CompositeChangePropagationSpecification {

	new(Set<String> sourceMetamodelRootNsUris, Set<String> targetMetamodelRootNsUris) {
		super(sourceMetamodelRootNsUris, targetMetamodelRootNsUris);
		this.setup();
	}

	/**
	 * Adds the reactions change processor to this {@link CompositeChangePropagationSpecification}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected abstract def void setup();
}
