package mir.effects.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import java.util.HashSet;
import java.util.function.Consumer;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class RenameOperationSignatureEffect extends AbstractEffectRealization {
  public RenameOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedEAttribute<String> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RenameOperationSignatureEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    InterfaceMethod interfaceMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	InterfaceMethod.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    preProcessElements();
    new mir.effects.pcm2java.RenameOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final InterfaceMethod interfaceMethod) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      final OperationSignature operationSignature = ((OperationSignature) _newAffectedEObject);
      final OperationInterface operationInterface = operationSignature.getInterface__OperationSignature();
      String _newValue = change.getNewValue();
      interfaceMethod.setName(_newValue);
      final HashSet<InterfaceProvidingEntity> implementingComponents = Sets.<InterfaceProvidingEntity>newHashSet();
      Repository _repository__Interface = operationInterface.getRepository__Interface();
      EList<RepositoryComponent> _components__Repository = _repository__Interface.getComponents__Repository();
      final Consumer<RepositoryComponent> _function = (RepositoryComponent comp) -> {
        EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = comp.getProvidedRoles_InterfaceProvidingEntity();
        final Iterable<OperationProvidedRole> opProvRoles = Iterables.<OperationProvidedRole>filter(_providedRoles_InterfaceProvidingEntity, OperationProvidedRole.class);
        final Function1<OperationProvidedRole, Boolean> _function_1 = (OperationProvidedRole it) -> {
          OperationInterface _providedInterface__OperationProvidedRole = it.getProvidedInterface__OperationProvidedRole();
          String _id = _providedInterface__OperationProvidedRole.getId();
          String _id_1 = operationInterface.getId();
          return Boolean.valueOf(Objects.equal(_id, _id_1));
        };
        Iterable<OperationProvidedRole> _filter = IterableExtensions.<OperationProvidedRole>filter(opProvRoles, _function_1);
        final Consumer<OperationProvidedRole> _function_2 = (OperationProvidedRole opProRole) -> {
          InterfaceProvidingEntity _providingEntity_ProvidedRole = opProRole.getProvidingEntity_ProvidedRole();
          implementingComponents.add(_providingEntity_ProvidedRole);
        };
        _filter.forEach(_function_2);
      };
      _components__Repository.forEach(_function);
      final Iterable<BasicComponent> basicComponents = Iterables.<BasicComponent>filter(implementingComponents, BasicComponent.class);
      final Consumer<BasicComponent> _function_1 = (BasicComponent it) -> {
        EList<ServiceEffectSpecification> _serviceEffectSpecifications__BasicComponent = it.getServiceEffectSpecifications__BasicComponent();
        final Consumer<ServiceEffectSpecification> _function_2 = (ServiceEffectSpecification it_1) -> {
          this.effectFacade.callUpdateSEFFImplementingMethodName(it_1);
        };
        _serviceEffectSpecifications__BasicComponent.forEach(_function_2);
      };
      basicComponents.forEach(_function_1);
    }
  }
}
