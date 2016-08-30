package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreatedFirstPackageResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreatedFirstPackageResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreatedClassResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreatedClassResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateClassAnnotationResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateClassAnnotationResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreatedInterfaceResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreatedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateInterfaceAnnotationResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateInterfaceAnnotationResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateAnnotationForFieldResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateAnnotationForFieldResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateFieldResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateFieldResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateImplementsResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateImplementsResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateInterfaceMethodResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateInterfaceMethodResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateParameterInInterfaceMethodResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateParameterInInterfaceMethodResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.ReturnTypeCreatedResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.ReturnTypeCreatedResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateFieldInDatatypeClassResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateFieldInDatatypeClassResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassResponse(userInteracting));
  }
}
