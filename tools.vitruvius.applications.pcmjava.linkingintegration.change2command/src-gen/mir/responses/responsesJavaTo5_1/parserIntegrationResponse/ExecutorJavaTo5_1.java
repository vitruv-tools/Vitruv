package mir.responses.responsesJavaTo5_1.parserIntegrationResponse;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruvius.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responsesJavaTo5_1.parserIntegrationResponse.ChangeFieldModifierEventParserResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.ChangeFieldModifierEventParserResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserResponse(userInteracting));
    this.addResponse(mir.responses.responsesJavaTo5_1.parserIntegrationResponse.AddMethodEventParserResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.AddMethodEventParserResponse(userInteracting));
  }
}
