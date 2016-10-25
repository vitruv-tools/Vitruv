package mir.reactions.reactionsJavaTo5_1.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractReactionsExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddImportReactionReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddImportReactionReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveImportReactionReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveImportReactionReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RenameMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RenameMethodReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveMethodEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveMethodEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddMethodEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddMethodEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.CreateMetodParameterEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.CreateMetodParameterEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.MethodParameterNameChangeEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.MethodParameterNameChangeEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodTypeEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodTypeEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddFieldEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddFieldEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldTypeEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldTypeEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldModifierEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldModifierEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldModifierEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldModifierEventReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodModifierEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodModifierEventReaction(userInteracting));
  }
}
