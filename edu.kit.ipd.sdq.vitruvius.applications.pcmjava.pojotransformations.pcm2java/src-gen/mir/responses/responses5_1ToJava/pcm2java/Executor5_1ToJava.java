package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractResponseExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractResponseExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedSystemNameResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedSystemNameResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedProvidedRoleFromSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedProvidedRoleFromSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedProvidedRoleFromComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedProvidedRoleFromComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameOperationRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameOperationRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedParameterTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedParameterTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedSEFFResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedSEFFResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletedSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletedSeffResponse(userInteracting));
  }
}
