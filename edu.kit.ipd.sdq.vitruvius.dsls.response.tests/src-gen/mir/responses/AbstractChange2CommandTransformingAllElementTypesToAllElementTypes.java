package mir.responses;

import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.AbstractChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public abstract class AbstractChange2CommandTransformingAllElementTypesToAllElementTypes extends AbstractChange2CommandTransforming {
	public AbstractChange2CommandTransformingAllElementTypesToAllElementTypes() {
		super (new TransformationMetamodelPair(
			VURI.getInstance("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes"),
			VURI.getInstance("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes")));
	}
	
	protected void setup() {
		this.addChangeProcessor(new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ExecutorAllElementTypesToAllElementTypes(getUserInteracting()));
	}
	
}
