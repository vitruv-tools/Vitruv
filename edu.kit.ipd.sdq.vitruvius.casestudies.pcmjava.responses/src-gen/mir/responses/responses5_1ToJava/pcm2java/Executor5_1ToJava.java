package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToJava extends AbstractResponseExecutor {
  public Executor5_1ToJava(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  protected void setup() {
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatePackagesForRepositoryResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatePackagesForRepositoryResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedRepositoryPackagesResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedRepositoryPackagesResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedSystemNameResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedSystemNameResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreateComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreateComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeleteComponentClassResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeleteComponentClassResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeletePackageForBasicComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeletePackageForBasicComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreateInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreateInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenamedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenamedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedCollectionDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedCollectionDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedInnerDeclarationToCompositeDataTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.AddedInnerDeclarationToCompositeDataTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedNameOfInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedNameOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeTypeOfInnerDeclarationResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddedProvidedRoleInterfaceProvidingEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.AddedProvidedRoleInterfaceProvidingEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedProvidedInterfaceOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedProvidingEntityOfProvidedRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromSystemResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromSystemResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromComponentResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedProvidedRoleFromComponentResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddRequiredRoleToInterfaceRequiringEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.AddRequiredRoleToInterfaceRequiringEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameOperationRequiredRoleResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameOperationRequiredRoleResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleEntityResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationRequiredRoleInterfaceResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.AddOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.AddOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureReturnTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.DeleteOperationSignatureResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.DeleteOperationSignatureResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedParameterNameResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedParameterNameResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangedParameterTypeResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangedParameterTypeResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedParameterResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedParameterResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RenameResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedResourceDemandingInternalBehaviorResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedResourceDemandingInternalBehaviorResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.CreatedSEFFResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.CreatedSEFFResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.ChangeOperationSignatureOfSeffResponse(userInteracting));
    this.addResponse(mir.responses.responses5_1ToJava.pcm2java.RemovedSeffResponse.getExpectedChangeType(), new mir.responses.responses5_1ToJava.pcm2java.RemovedSeffResponse(userInteracting));
  }
}
