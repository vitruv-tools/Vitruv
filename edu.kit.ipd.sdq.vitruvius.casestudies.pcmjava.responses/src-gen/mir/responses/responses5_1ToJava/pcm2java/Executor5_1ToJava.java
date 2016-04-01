package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractResponseExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatePackagesForRepositoryResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatePackagesForRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedRepositoryPackagesResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenamedRepositoryPackagesResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedSystemResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatedSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedSystemNameResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangedSystemNameResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreateComponentResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreateComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameComponentResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenameComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeleteComponentClassResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.DeleteComponentClassResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletePackageForBasicComponentResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.DeletePackageForBasicComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreateInterfaceResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreateInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedInterfaceResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenamedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedCompositeDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedCompositeDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenamedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedCompositeDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedCollectionDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedCollectionDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenamedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedCollectionDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedInnerDeclarationToCompositeDataTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.AddedInnerDeclarationToCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedNameOfInnerDeclarationResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangedNameOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedProvidedRoleInterfaceProvidingEntityResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.AddedProvidedRoleInterfaceProvidingEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromSystemResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromComponentResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddRequiredRoleToInterfaceRequiringEntityResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.AddRequiredRoleToInterfaceRequiringEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameOperationRequiredRoleResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenameOperationRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddOperationSignatureResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.AddOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameOperationSignatureResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenameOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeleteOperationSignatureResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.DeleteOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedParameterResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedParameterNameResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangedParameterNameResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedParameterTypeResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangedParameterTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedParameterResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedResourceDemandingInternalBehaviorResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedSEFFResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.CreatedSEFFResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedSeffResponse.getTrigger(), new mir.responses.responses5_1ToJava.pcm2java.RemovedSeffResponse(userInteracting));
  }
}
