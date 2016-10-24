package mir.reactions.reactionsJavaTo5_1.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddImportResponseReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddImportResponseReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveImportResponseReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveImportResponseReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RenameMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RenameMethodReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveMethodEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveMethodEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddMethodEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddMethodEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.CreateMetodParameterEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.CreateMetodParameterEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.MethodParameterNameChangeEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.MethodParameterNameChangeEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodTypeEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodTypeEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddFieldEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.AddFieldEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldTypeEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldTypeEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldModifierEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeFieldModifierEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldModifierEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.RemoveFieldModifierEventReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodModifierEventReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.packageMappingIntegration.ChangeMethodModifierEventReaction(userInteracting));
  }
}
