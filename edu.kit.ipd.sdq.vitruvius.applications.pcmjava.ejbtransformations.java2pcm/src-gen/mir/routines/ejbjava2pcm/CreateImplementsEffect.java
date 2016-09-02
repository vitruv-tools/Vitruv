package mir.routines.ejbjava2pcm;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.EJBJava2PcmHelper;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
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

@SuppressWarnings("all")
public class CreateImplementsEffect extends AbstractEffectRealization {
  public CreateImplementsEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<org.emftext.language.java.classifiers.Class, TypeReference> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<org.emftext.language.java.classifiers.Class, TypeReference> change;
  
  private EObject getCorrepondenceSourceBasicComponent(final InsertEReference<org.emftext.language.java.classifiers.Class, TypeReference> change) {
    org.emftext.language.java.classifiers.Class _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateImplementsEffect with input:");
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
    OperationProvidedRole opr = RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    initializeCreateElementState(opr);
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateImplementsEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, basicComponent, opr);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<org.emftext.language.java.classifiers.Class, TypeReference> change, final BasicComponent basicComponent, final OperationProvidedRole opr) {
      Repository _repository__RepositoryComponent = basicComponent.getRepository__RepositoryComponent();
      EList<Interface> _interfaces__Repository = _repository__RepositoryComponent.getInterfaces__Repository();
      Iterable<OperationInterface> _filter = Iterables.<OperationInterface>filter(_interfaces__Repository, OperationInterface.class);
      final Function1<OperationInterface, Boolean> _function = (OperationInterface it) -> {
        String _entityName = it.getEntityName();
        TypeReference _newValue = change.getNewValue();
        Classifier _classifier = EJBJava2PcmHelper.getClassifier(_newValue);
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
        TypeReference _newValue = change.getNewValue();
        List<EObject> _singletonList_1 = Collections.<EObject>singletonList(_newValue);
        this.correspondenceModel.createAndAddCorrespondence(_singletonList, _singletonList_1);
      }
    }
  }
}
