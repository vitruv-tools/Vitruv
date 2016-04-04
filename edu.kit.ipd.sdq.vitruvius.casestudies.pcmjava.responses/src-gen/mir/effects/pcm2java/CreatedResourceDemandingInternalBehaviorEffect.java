package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceComponentClass(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private EObject getCorrepondenceSourceJavaMethod(final CreateNonRootEObjectInList<ResourceDemandingInternalBehaviour> change) {
    ResourceDemandingInternalBehaviour _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect CreatedResourceDemandingInternalBehaviorEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    org.emftext.language.java.classifiers.Class componentClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceComponentClass(change), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    ClassMethod javaMethod = initializeCreateElementState(
    	() -> getCorrepondenceSourceJavaMethod(change), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createClassMethod(), // element creation supplier
    	() -> null, // tag supplier
    	ClassMethod.class);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.CreatedResourceDemandingInternalBehaviorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, componentClass, javaMethod);
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
