package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.impl.ParametersFactoryImpl;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class AddParameterAndAssignmentToConstructorEffect extends AbstractEffectRealization {
  public AddParameterAndAssignmentToConstructorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    super(responseExecutionState, calledBy);
    				this.parameterCorrespondenceSource = parameterCorrespondenceSource;this.constructor = constructor;this.typeReference = typeReference;this.fieldToBeAssigned = fieldToBeAssigned;this.parameterName = parameterName;
  }
  
  private NamedElement parameterCorrespondenceSource;
  
  private Constructor constructor;
  
  private NamespaceClassifierReference typeReference;
  
  private Field fieldToBeAssigned;
  
  private String parameterName;
  
  private EObject getElement0(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
    return newParameter;
  }
  
  private EObject getElement1(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
    return parameterCorrespondenceSource;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddParameterAndAssignmentToConstructorEffect with input:");
    getLogger().debug("   NamedElement: " + this.parameterCorrespondenceSource);
    getLogger().debug("   Constructor: " + this.constructor);
    getLogger().debug("   NamespaceClassifierReference: " + this.typeReference);
    getLogger().debug("   Field: " + this.fieldToBeAssigned);
    getLogger().debug("   String: " + this.parameterName);
    
    OrdinaryParameter newParameter = ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    initializeCreateElementState(newParameter);
    
    addCorrespondenceBetween(getElement0(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter), getElement1(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter), "");
    preprocessElementStates();
    new mir.routines.pcm2java.AddParameterAndAssignmentToConstructorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName, final OrdinaryParameter newParameter) {
      newParameter.setName(parameterName);
      NamespaceClassifierReference _copy = EcoreUtil.<NamespaceClassifierReference>copy(typeReference);
      newParameter.setTypeReference(_copy);
      EList<Parameter> _parameters = constructor.getParameters();
      _parameters.add(newParameter);
      final Statement asssignment = Pcm2JavaHelper.createAssignmentFromParameterToField(fieldToBeAssigned, newParameter);
      EList<Statement> _statements = constructor.getStatements();
      _statements.add(asssignment);
    }
  }
}
