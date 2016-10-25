package mir.routines.ejbjava2pcm;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedImplementsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedImplementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz) {
      return clazz;
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz, final BasicComponent basicComponent, final OperationProvidedRole opr) {
      return opr;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz, final BasicComponent basicComponent, final OperationProvidedRole opr) {
      Repository _repository__RepositoryComponent = basicComponent.getRepository__RepositoryComponent();
      EList<Interface> _interfaces__Repository = _repository__RepositoryComponent.getInterfaces__Repository();
      Iterable<OperationInterface> _filter = Iterables.<OperationInterface>filter(_interfaces__Repository, OperationInterface.class);
      final Function1<OperationInterface, Boolean> _function = (OperationInterface it) -> {
        String _entityName = it.getEntityName();
        Classifier _classifier = EJBJava2PcmHelper.getClassifier(implementz);
        String _name = _classifier.getName();
        return Boolean.valueOf(_entityName.equals(_name));
      };
      final OperationInterface opInterface = IterableExtensions.<OperationInterface>findFirst(_filter, _function);
      boolean _notEquals = (!Objects.equal(null, opInterface));
      if (_notEquals) {
        opr.setProvidedInterface__OperationProvidedRole(opInterface);
        opr.setProvidingEntity_ProvidedRole(basicComponent);
        String _entityName = basicComponent.getEntityName();
        String _plus = (_entityName + "_provides_");
        String _entityName_1 = opInterface.getEntityName();
        String _plus_1 = (_plus + _entityName_1);
        opr.setEntityName(_plus_1);
        List<EObject> _singletonList = Collections.<EObject>singletonList(opr);
        List<EObject> _singletonList_1 = Collections.<EObject>singletonList(implementz);
        this.correspondenceModel.createAndAddCorrespondence(_singletonList, _singletonList_1);
      }
    }
  }
  
  public CreatedImplementsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedImplementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.implementz = implementz;
  }
  
  private org.emftext.language.java.classifiers.Class clazz;
  
  private TypeReference implementz;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedImplementsRoutine with input:");
    getLogger().debug("   Class: " + this.clazz);
    getLogger().debug("   TypeReference: " + this.implementz);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(clazz, implementz), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    OperationProvidedRole opr = RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    initializeCreateElementState(opr);
    
    // val updatedElement userExecution.getElement1(clazz, implementz, basicComponent, opr);
    userExecution.update0Element(clazz, implementz, basicComponent, opr);
    
    postprocessElementStates();
  }
}
