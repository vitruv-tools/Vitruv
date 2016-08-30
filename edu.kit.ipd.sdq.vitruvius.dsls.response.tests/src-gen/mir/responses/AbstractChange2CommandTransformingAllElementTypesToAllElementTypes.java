package mir.responses;

import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

/**
 * The {@link interface edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransforming} for transformations between the metamodels http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes and http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChange2CommandTransformingAllElementTypesToAllElementTypes extends AbstractChange2CommandTransforming {
	public AbstractChange2CommandTransformingAllElementTypesToAllElementTypes() {
		super (new TransformationMetamodelPair(
			VURI.getInstance("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes"),
			VURI.getInstance("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes")));
	}
	
	/**
	 * Adds the response change processors to this {@link AbstractChange2CommandTransformingAllElementTypesToAllElementTypes}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeProcessor(new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ExecutorAllElementTypesToAllElementTypes(getUserInteracting()));
	}
	
}
