package mir.responses;

import tools.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * The {@link interface tools.vitruvius.framework.change.processing.Change2CommandTransforming} for transformations between the metamodels http://tools.vitruvius.tests.metamodels.allElementTypes and http://tools.vitruvius.tests.metamodels.allElementTypes.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChange2CommandTransformingAllElementTypesToAllElementTypes extends AbstractChange2CommandTransforming {
	public AbstractChange2CommandTransformingAllElementTypesToAllElementTypes() {
		super (VURI.getInstance("http://tools.vitruvius.tests.metamodels.allElementTypes"),
			VURI.getInstance("http://tools.vitruvius.tests.metamodels.allElementTypes"));
	}
	
	/**
	 * Adds the response change processors to this {@link AbstractChange2CommandTransformingAllElementTypesToAllElementTypes}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeProcessor(new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ExecutorAllElementTypesToAllElementTypes(getUserInteracting()));
	}
	
}
