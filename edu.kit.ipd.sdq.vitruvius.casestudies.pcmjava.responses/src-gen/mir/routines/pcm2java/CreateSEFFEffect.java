package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class CreateSEFFEffect extends AbstractEffectRealization {
  public CreateSEFFEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private ServiceEffectSpecification seff;
  
  private boolean isSeffSet;
  
  public void setSeff(final ServiceEffectSpecification seff) {
    this.seff = seff;
    this.isSeffSet = true;
  }
  
  private EObject getElement0(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
    return classMethod;
  }
  
  private EObject getElement1(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
    return seff;
  }
  
  public boolean allParametersSet() {
    return isSeffSet;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final ServiceEffectSpecification seff) {
    Signature _describedService__SEFF = seff.getDescribedService__SEFF();
    return _describedService__SEFF;
  }
  
  private boolean checkMatcherPrecondition(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod) {
    Signature _describedService__SEFF = seff.getDescribedService__SEFF();
    return (_describedService__SEFF instanceof OperationSignature);
  }
  
  private EObject getCorrepondenceSourceComponentClass(final ServiceEffectSpecification seff) {
    BasicComponent _basicComponent_ServiceEffectSpecification = seff.getBasicComponent_ServiceEffectSpecification();
    return _basicComponent_ServiceEffectSpecification;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateSEFFEffect with input:");
    getLogger().debug("   ServiceEffectSpecification: " + this.seff);
    
    org.emftext.language.java.classifiers.Class componentClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceComponentClass(seff), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	false, true, false);
    InterfaceMethod interfaceMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceInterfaceMethod(seff), // correspondence source supplier
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	InterfaceMethod.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    if (!checkMatcherPrecondition(seff, componentClass, interfaceMethod)) {
    	return;
    }
    ClassMethod classMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(classMethod);
    
    addCorrespondenceBetween(getElement0(seff, componentClass, interfaceMethod, classMethod), getElement1(seff, componentClass, interfaceMethod, classMethod), "");
    preProcessElements();
    new mir.routines.pcm2java.CreateSEFFEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	seff, componentClass, interfaceMethod, classMethod);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      Pcm2JavaHelper.initializeClassMethod(classMethod, interfaceMethod, true);
      ClassMethod correspondingClassMethod = Pcm2JavaHelper.findMethodInClass(componentClass, classMethod);
      boolean _equals = Objects.equal(null, correspondingClassMethod);
      if (_equals) {
        EList<Member> _members = componentClass.getMembers();
        _members.add(classMethod);
        correspondingClassMethod = classMethod;
      } else {
        String _name = interfaceMethod.getName();
        correspondingClassMethod.setName(_name);
      }
    }
  }
}
