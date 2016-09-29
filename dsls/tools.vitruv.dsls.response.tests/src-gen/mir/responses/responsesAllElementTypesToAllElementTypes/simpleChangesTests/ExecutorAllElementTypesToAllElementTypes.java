package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes2.AllElementTypes2Package;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.datatypes.MetamodelPair;

@SuppressWarnings("all")
public class ExecutorAllElementTypesToAllElementTypes extends AbstractResponseExecutor {
  private final MetamodelPair metamodelPair;
	
  public ExecutorAllElementTypesToAllElementTypes(final UserInteracting userInteracting) {
    super(userInteracting);
	this.metamodelPair = new MetamodelPair(AllElementTypes2Package.eNS_URI, AllElementTypes2Package.eNS_URI);
  }
  
  @Override
  public MetamodelPair getMetamodelPair() {
    return metamodelPair;
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedSingleValuedEAttributeResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedSingleValuedEAttributeResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedNonRootIdResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedNonRootIdResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.CreatedNonRootEObjectInListResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.CreatedNonRootEObjectInListResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.DeletedNonRootEObjectInListResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.DeletedNonRootEObjectInListResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedNonRootEObjectSingleResponseResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedNonRootEObjectSingleResponseResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedSingleValuedNonContainmentEReferenceResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.ReplacedSingleValuedNonContainmentEReferenceResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.InsertedEAttributeValueResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.InsertedEAttributeValueResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.RemovedEAttributeValueResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.RemovedEAttributeValueResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.InsertedNonContainmentEReferenceResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.InsertedNonContainmentEReferenceResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.RemoveedNonContainmentEReferenceResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.RemoveedNonContainmentEReferenceResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.CreateRootTestResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.CreateRootTestResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.DeleteRootTestResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.DeleteRootTestResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationResponse(userInteracting));
    this.addResponse(mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.HelperResponseForNonRootObjectContainerContentsInitializationResponse.getExpectedChangeType(), new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.HelperResponseForNonRootObjectContainerContentsInitializationResponse(userInteracting));
  }
}
