package mir.responses.responsesJavaTo5_1.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.AddImportResponseResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.AddImportResponseResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveImportResponseResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveImportResponseResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.RenameMethodResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.RenameMethodResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveMethodEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveMethodEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.AddMethodEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.AddMethodEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.CreateMetodParameterEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.CreateMetodParameterEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.MethodParameterNameChangeEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.MethodParameterNameChangeEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeMethodTypeEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeMethodTypeEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveFieldEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveFieldEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.AddFieldEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.AddFieldEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeFieldTypeEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeFieldTypeEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeFieldModifierEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeFieldModifierEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveFieldModifierEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.RemoveFieldModifierEventResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeMethodModifierEventResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.packageMappingIntegration.ChangeMethodModifierEventResponse(userInteracting));
  }
}
