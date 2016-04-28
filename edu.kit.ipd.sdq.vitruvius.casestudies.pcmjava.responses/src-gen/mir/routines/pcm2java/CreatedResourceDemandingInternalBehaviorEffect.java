package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypesFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;

@SuppressWarnings("all")
public class CreatedResourceDemandingInternalBehaviorEffect extends AbstractEffectRealization {
  public CreatedResourceDemandingInternalBehaviorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
    return javaMethod;
  }
  
  private EObject getElement1(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
    ResourceDemandingInternalBehaviour _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceComponentClass(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreatedResourceDemandingInternalBehaviorEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    org.emftext.language.java.classifiers.Class componentClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceComponentClass(change), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    ClassMethod javaMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(javaMethod);
    
    addCorrespondenceBetween(getElement0(change, componentClass, javaMethod), getElement1(change, componentClass, javaMethod), "");
    preProcessElements();
    new mir.routines.pcm2java.CreatedResourceDemandingInternalBehaviorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, componentClass, javaMethod);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      ResourceDemandingInternalBehaviour _newValue = change.getNewValue();
      String _entityName = _newValue.getEntityName();
      javaMethod.setName(_entityName);
      org.emftext.language.java.types.Void _createVoid = TypesFactory.eINSTANCE.createVoid();
      javaMethod.setTypeReference(_createVoid);
      EList<Member> _members = componentClass.getMembers();
      _members.add(javaMethod);
    }
  }
}
