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
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedAnnotationForFieldRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreatedAnnotationForFieldRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final Field annotatedField) {
      ConcreteClassifier _containingConcreteClassifier = annotatedField.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public void callRoutine1(final Field annotatedField, final BasicComponent basicComponent, @Extension final RoutinesFacade _routinesFacade) {
      TypeReference _typeReference = annotatedField.getTypeReference();
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
      _routinesFacade.createOperationRequiredRole(basicComponent, opInterface, annotatedField);
    }
  }
  
  public CreatedAnnotationForFieldRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Field annotatedField) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedAnnotationForFieldRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.annotatedField = annotatedField;
  }
  
  private Field annotatedField;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedAnnotationForFieldRoutine with input:");
    getLogger().debug("   Field: " + this.annotatedField);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(annotatedField), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    userExecution.callRoutine1(annotatedField, basicComponent, effectFacade);
    
    postprocessElementStates();
  }
}
