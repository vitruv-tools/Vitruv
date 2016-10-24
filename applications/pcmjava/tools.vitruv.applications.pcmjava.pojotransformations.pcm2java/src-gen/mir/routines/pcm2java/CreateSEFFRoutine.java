package mir.routines.pcm2java;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSEFFRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateSEFFRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      return classMethod;
    }
    
    public void update0Element(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
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
    
    public EObject getCorrepondenceSourceInterfaceMethod(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      return _describedService__SEFF;
    }
    
    public EObject getCorrepondenceSourceComponentClass(final ServiceEffectSpecification seff) {
      BasicComponent _basicComponent_ServiceEffectSpecification = seff.getBasicComponent_ServiceEffectSpecification();
      return _basicComponent_ServiceEffectSpecification;
    }
    
    public EObject getElement2(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      return seff;
    }
    
    public void updateClassMethodElement(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      Pcm2JavaHelper.initializeClassMethod(classMethod, interfaceMethod, true);
    }
    
    public EObject getElement3(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      return componentClass;
    }
    
    public boolean checkMatcherPrecondition1(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      return (_describedService__SEFF instanceof OperationSignature);
    }
  }
  
  public CreateSEFFRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ServiceEffectSpecification seff) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.CreateSEFFRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.seff = seff;
  }
  
  private ServiceEffectSpecification seff;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFRoutine with input:");
    getLogger().debug("   ServiceEffectSpecification: " + this.seff);
    
    org.emftext.language.java.classifiers.Class componentClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentClass(seff), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (componentClass == null) {
    	return;
    }
    initializeRetrieveElementState(componentClass);
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(seff, componentClass), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    if (!userExecution.checkMatcherPrecondition1(seff, componentClass, interfaceMethod)) {
    	return;
    }
    ClassMethod classMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(classMethod);
    userExecution.updateClassMethodElement(seff, componentClass, interfaceMethod, classMethod);
    
    addCorrespondenceBetween(userExecution.getElement1(seff, componentClass, interfaceMethod, classMethod), userExecution.getElement2(seff, componentClass, interfaceMethod, classMethod), "");
    
    // val updatedElement userExecution.getElement3(seff, componentClass, interfaceMethod, classMethod);
    userExecution.update0Element(seff, componentClass, interfaceMethod, classMethod);
    
    postprocessElementStates();
  }
}
