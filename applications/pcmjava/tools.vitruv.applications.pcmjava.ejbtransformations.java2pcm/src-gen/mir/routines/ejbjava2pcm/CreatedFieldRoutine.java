package mir.routines.ejbjava2pcm;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedFieldRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedFieldRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final Field field) {
      return field;
    }
    
    public void callRoutine1(final Field field, final BasicComponent basicComponent, @Extension final RoutinesFacade _routinesFacade) {
      TypeReference _typeReference = field.getTypeReference();
      Classifier _classifier = EJBJava2PcmHelper.getClassifier(_typeReference);
      final String interfaceName = _classifier.getName();
      Repository _repository__RepositoryComponent = basicComponent.getRepository__RepositoryComponent();
      EList<Interface> _interfaces__Repository = _repository__RepositoryComponent.getInterfaces__Repository();
      Iterable<OperationInterface> _filter = Iterables.<OperationInterface>filter(_interfaces__Repository, OperationInterface.class);
      final Function1<OperationInterface, Boolean> _function = (OperationInterface it) -> {
        String _entityName = it.getEntityName();
        return Boolean.valueOf(_entityName.equals(interfaceName));
      };
      final OperationInterface opInterface = IterableExtensions.<OperationInterface>findFirst(_filter, _function);
      _routinesFacade.createOperationRequiredRole(basicComponent, opInterface, field);
    }
  }
  
  public CreatedFieldRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedFieldRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.field = field;
  }
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedFieldRoutine with input:");
    getLogger().debug("   Field: " + this.field);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(field), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    userExecution.callRoutine1(field, basicComponent, actionsFacade);
    
    postprocessElementStates();
  }
}
