package mir.reactions.reactions5_1ToJava.pcm2java;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractResponseExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedRepositoryReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenamedRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenamedRepositoryReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedSystemReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedSystemReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedSystemReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedSystemReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangedSystemNameReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangedSystemNameReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedComponentReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenameComponentReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedComponentReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedInterfaceReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenamedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenamedInterfaceReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedCompositeDataTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenamedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenamedCompositeDataTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedCompositeDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedCompositeDataTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedCollectionDataTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenamedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenamedCollectionDataTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedCollectionDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedCollectionDataTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedInnerDeclarationReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenameInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenameInnerDeclarationReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedProvidedRoleReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedProvidedRoleFromSystemReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedProvidedRoleFromSystemReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedProvidedRoleFromComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedProvidedRoleFromComponentReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedRequiredRoleReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedRequiredRoleReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenameOperationRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenameOperationRequiredRoleReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedOperationSignatureReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenameOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenameOperationSignatureReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedOperationSignatureReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedOperationSignatureReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedParameterReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenamedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenamedParameterReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangedParameterTypeReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedParameterReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedDemandingInternalBehaviorReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedDemandingInternalBehaviorReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.CreatedSEFFReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.CreatedSEFFReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffReaction(userInteracting));
    this.addResponse(mir.reactions.reactions5_1ToJava.pcm2java.DeletedSeffReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToJava.pcm2java.DeletedSeffReaction(userInteracting));
  }
}
