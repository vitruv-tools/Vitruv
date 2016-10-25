package mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractReactionsExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction.ChangeFieldModifierEventParserReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction.ChangeFieldModifierEventParserReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction.RemoveFieldModifierEventParserReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction.RemoveFieldModifierEventParserReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction.AddMethodEventParserReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.parserIntegrationReaction.AddMethodEventParserReaction(userInteracting));
  }
}
