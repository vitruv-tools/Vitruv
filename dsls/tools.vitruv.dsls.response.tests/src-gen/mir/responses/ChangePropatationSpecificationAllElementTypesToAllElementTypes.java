package mir.responses;

import tools.vitruv.framework.change.processing.impl.CompositeChangeProcessor;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangeProcessor} for transformations between the metamodels http://tools.vitruv.tests.metamodels.allElementTypes and http://tools.vitruv.tests.metamodels.allElementTypes.
 * To add further change processors overwrite the setup method.
 */
public abstract class ChangePropatationSpecificationAllElementTypesToAllElementTypes extends CompositeChangeProcessor {
	private final tools.vitruv.framework.util.datatypes.MetamodelPair metamodelPair;
	
	public ChangePropatationSpecificationAllElementTypesToAllElementTypes() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor());
		this.metamodelPair = new tools.vitruv.framework.util.datatypes.MetamodelPair("http://tools.vitruv.tests.metamodels.allElementTypes", "http://tools.vitruv.tests.metamodels.allElementTypes");
		setup();
	}
	
	public tools.vitruv.framework.util.datatypes.MetamodelPair getMetamodelPair() {
		return metamodelPair;
	}	
	
	/**
	 * Adds the response change processors to this {@link ChangePropatationSpecificationAllElementTypesToAllElementTypes}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ExecutorAllElementTypesToAllElementTypes(getUserInteracting()));
	}
	
}
