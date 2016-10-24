package mir.routines.ejbjava2pcm;

import java.io.IOException;
import java.util.Set;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;

@SuppressWarnings("all")
public class CreatedClassMethodInEJBClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedClassMethodInEJBClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
      return clazz;
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod, final BasicComponent basicComponent, @Extension final RoutinesFacade _routinesFacade) {
      final Method method = EJBJava2PcmHelper.getOoverridenInterfaceMethod(classMethod, clazz);
      final Set<OperationSignature> correspondingEObjects = CorrespondenceModelUtil.<OperationSignature, Correspondence>getCorrespondingEObjectsByType(this.correspondenceModel, method, OperationSignature.class);
      OperationSignature opSignature = null;
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(correspondingEObjects);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        OperationSignature _get = ((OperationSignature[])Conversions.unwrapArray(correspondingEObjects, OperationSignature.class))[0];
        opSignature = _get;
      }
      _routinesFacade.createSEFFForClassMethod(basicComponent, opSignature, classMethod);
    }
  }
  
  public CreatedClassMethodInEJBClassRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedClassMethodInEJBClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.classMethod = classMethod;
  }
  
  private org.emftext.language.java.classifiers.Class clazz;
  
  private ClassMethod classMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedClassMethodInEJBClassRoutine with input:");
    getLogger().debug("   Class: " + this.clazz);
    getLogger().debug("   ClassMethod: " + this.classMethod);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(clazz, classMethod), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    userExecution.callRoutine1(clazz, classMethod, basicComponent, actionsFacade);
    
    postprocessElementStates();
  }
}
