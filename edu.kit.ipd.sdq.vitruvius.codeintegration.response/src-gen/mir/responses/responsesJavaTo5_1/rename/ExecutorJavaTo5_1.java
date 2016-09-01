package mir.responses.responsesJavaTo5_1.rename;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractResponseExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responsesJavaTo5_1.rename.RenameMethodResponse.getExpectedChangeType(), new mir.responses.responsesJavaTo5_1.rename.RenameMethodResponse(userInteracting));
  }
}
