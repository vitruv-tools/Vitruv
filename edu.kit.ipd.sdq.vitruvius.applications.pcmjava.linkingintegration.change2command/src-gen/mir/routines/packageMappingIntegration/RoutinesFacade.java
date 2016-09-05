package mir.routines.packageMappingIntegration;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectsFacade;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractEffectsFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void callCreateOperationSignature(final OperationInterface opInterface, final Method newMethod) {
    mir.routines.packageMappingIntegration.CreateOperationSignatureEffect effect = new mir.routines.packageMappingIntegration.CreateOperationSignatureEffect(this.executionState, calledBy,
    	opInterface, newMethod);
    effect.applyRoutine();
  }
  
  public void callCreateRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    mir.routines.packageMappingIntegration.CreateRequiredRoleEffect effect = new mir.routines.packageMappingIntegration.CreateRequiredRoleEffect(this.executionState, calledBy,
    	basicComponent, opInterface, field);
    effect.applyRoutine();
  }
  
  public void callRemoveRequiredRoleAndCorrespondence(final OperationRequiredRole orr, final Field field) {
    mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceEffect effect = new mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceEffect(this.executionState, calledBy,
    	orr, field);
    effect.applyRoutine();
  }
}
