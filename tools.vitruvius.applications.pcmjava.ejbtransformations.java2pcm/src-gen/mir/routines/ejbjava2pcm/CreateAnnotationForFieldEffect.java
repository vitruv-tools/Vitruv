package mir.routines.ejbjava2pcm;

import com.google.common.collect.Iterables;
import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;

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
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreateAnnotationForFieldEffect extends AbstractEffectRealization {
  public CreateAnnotationForFieldEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Field, AnnotationInstanceOrModifier> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Field, AnnotationInstanceOrModifier> change;
  
  private EObject getCorrepondenceSourceBasicComponent(final InsertEReference<Field, AnnotationInstanceOrModifier> change) {
    Field _affectedEObject = change.getAffectedEObject();
    ConcreteClassifier _containingConcreteClassifier = ((Field) _affectedEObject).getContainingConcreteClassifier();
    return _containingConcreteClassifier;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAnnotationForFieldEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	getCorrepondenceSourceBasicComponent(change), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateAnnotationForFieldEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, basicComponent);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Field, AnnotationInstanceOrModifier> change, final BasicComponent basicComponent) {
      Field _affectedEObject = change.getAffectedEObject();
      TypeReference _typeReference = ((Field) _affectedEObject).getTypeReference();
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
      Field _affectedEObject_1 = change.getAffectedEObject();
      this.effectFacade.callCreateOperationRequiredRole(basicComponent, opInterface, ((Field) _affectedEObject_1));
    }
  }
}
