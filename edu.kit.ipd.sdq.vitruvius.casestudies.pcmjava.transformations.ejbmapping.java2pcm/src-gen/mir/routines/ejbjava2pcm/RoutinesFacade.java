package mir.routines.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectsFacade;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractEffectsFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void callCreateBasicComponent(final Repository repo, final NamedElement namedElement) {
    mir.routines.ejbjava2pcm.CreateBasicComponentEffect effect = new mir.routines.ejbjava2pcm.CreateBasicComponentEffect(this.executionState, calledBy,
    	repo, namedElement);
    effect.applyRoutine();
  }
  
  public void callCreateOperationInterface(final Repository repo, final NamedElement namedElement) {
    mir.routines.ejbjava2pcm.CreateOperationInterfaceEffect effect = new mir.routines.ejbjava2pcm.CreateOperationInterfaceEffect(this.executionState, calledBy,
    	repo, namedElement);
    effect.applyRoutine();
  }
  
  public void callCreateOperationRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    mir.routines.ejbjava2pcm.CreateOperationRequiredRoleEffect effect = new mir.routines.ejbjava2pcm.CreateOperationRequiredRoleEffect(this.executionState, calledBy,
    	basicComponent, opInterface, field);
    effect.applyRoutine();
  }
  
  public void callCreateOperationSignature(final InterfaceMethod interfaceMethod, final OperationInterface opInterface) {
    mir.routines.ejbjava2pcm.CreateOperationSignatureEffect effect = new mir.routines.ejbjava2pcm.CreateOperationSignatureEffect(this.executionState, calledBy,
    	interfaceMethod, opInterface);
    effect.applyRoutine();
  }
  
  public void callCreatePCMParameter(final Parameter jaMoPPParam, final OperationSignature opSignature) {
    mir.routines.ejbjava2pcm.CreatePCMParameterEffect effect = new mir.routines.ejbjava2pcm.CreatePCMParameterEffect(this.executionState, calledBy,
    	jaMoPPParam, opSignature);
    effect.applyRoutine();
  }
  
  public void callCreatePCMReturnType(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
    mir.routines.ejbjava2pcm.CreatePCMReturnTypeEffect effect = new mir.routines.ejbjava2pcm.CreatePCMReturnTypeEffect(this.executionState, calledBy,
    	returnType, opSignature, javaMethod);
    effect.applyRoutine();
  }
  
  public void callCreateSEFFForClassMethod(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    mir.routines.ejbjava2pcm.CreateSEFFForClassMethodEffect effect = new mir.routines.ejbjava2pcm.CreateSEFFForClassMethodEffect(this.executionState, calledBy,
    	basicComponent, opSignature, classMethod);
    effect.applyRoutine();
  }
}
