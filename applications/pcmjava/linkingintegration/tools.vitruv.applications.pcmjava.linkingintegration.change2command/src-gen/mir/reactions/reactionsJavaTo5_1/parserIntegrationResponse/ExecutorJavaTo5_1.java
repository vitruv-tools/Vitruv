package mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addResponse(mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.ChangeFieldModifierEventParserReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.ChangeFieldModifierEventParserReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.AddMethodEventParserReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.AddMethodEventParserReaction(userInteracting));
  }
}
