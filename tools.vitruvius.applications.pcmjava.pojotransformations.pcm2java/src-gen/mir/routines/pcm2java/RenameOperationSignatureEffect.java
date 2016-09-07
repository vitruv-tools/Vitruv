package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

import java.io.IOException;
import java.util.HashSet;
import java.util.function.Consumer;
import mir.routines.pcm2java.RoutinesFacade;
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
  public RenameOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<OperationSignature, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<OperationSignature, String> change;
  
  private EObject getCorrepondenceSourceInterfaceMethod(final ReplaceSingleValuedEAttribute<OperationSignature, String> change) {
    OperationSignature _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameOperationSignatureEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<OperationSignature, String> change, final InterfaceMethod interfaceMethod) {
      OperationSignature _affectedEObject = change.getAffectedEObject();
      final OperationSignature operationSignature = ((OperationSignature) _affectedEObject);
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
