package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaTo5_1 extends AbstractResponseExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedFirstPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedFirstPackageReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedClassReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassAnnotationReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassAnnotationReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreatedInterfaceReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateInterfaceAnnotationReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateInterfaceAnnotationReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateAnnotationForFieldReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateAnnotationForFieldReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateFieldReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateFieldReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateImplementsReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateImplementsReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateInterfaceMethodReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateParameterInInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateParameterInInterfaceMethodReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.ReturnTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.ReturnTypeCreatedReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateFieldInDatatypeClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateFieldInDatatypeClassReaction(userInteracting));
    this.addResponse(mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassReaction(userInteracting));
  }
}
