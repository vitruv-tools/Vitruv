package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  public AddParameterAndAssignmentToConstructorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private NamedElement parameterCorrespondenceSource;
  
  private Constructor constructor;
  
  private NamespaceClassifierReference typeReference;
  
  private Field fieldToBeAssigned;
  
  private String parameterName;
  
  private boolean isParameterCorrespondenceSourceSet;
  
  private boolean isConstructorSet;
  
  private boolean isTypeReferenceSet;
  
  private boolean isFieldToBeAssignedSet;
  
  private boolean isParameterNameSet;
  
  public void setParameterCorrespondenceSource(final NamedElement parameterCorrespondenceSource) {
    this.parameterCorrespondenceSource = parameterCorrespondenceSource;
    this.isParameterCorrespondenceSourceSet = true;
  }
  
  public void setConstructor(final Constructor constructor) {
    this.constructor = constructor;
    this.isConstructorSet = true;
  }
  
  public void setTypeReference(final NamespaceClassifierReference typeReference) {
    this.typeReference = typeReference;
    this.isTypeReferenceSet = true;
  }
  
  public void setFieldToBeAssigned(final Field fieldToBeAssigned) {
    this.fieldToBeAssigned = fieldToBeAssigned;
    this.isFieldToBeAssignedSet = true;
  }
  
  public void setParameterName(final String parameterName) {
    this.parameterName = parameterName;
    this.isParameterNameSet = true;
  }
  
  private EObject getCorrepondenceSourceNewParameter(final NamedElement parameterCorrespondenceSource, final Constructor constructor, final NamespaceClassifierReference typeReference, final Field fieldToBeAssigned, final String parameterName) {
    return parameterCorrespondenceSource;
  }
  
  public boolean allParametersSet() {
    return isParameterCorrespondenceSourceSet&&isConstructorSet&&isTypeReferenceSet&&isFieldToBeAssignedSet&&isParameterNameSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddParameterAndAssignmentToConstructorEffect with input:");
    getLogger().debug("   NamedElement: " + this.parameterCorrespondenceSource);
    getLogger().debug("   Constructor: " + this.constructor);
    getLogger().debug("   NamespaceClassifierReference: " + this.typeReference);
    getLogger().debug("   Field: " + this.fieldToBeAssigned);
    getLogger().debug("   String: " + this.parameterName);
    
    OrdinaryParameter newParameter = initializeCreateElementState(
    	() -> getCorrepondenceSourceNewParameter(parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName), // correspondence source supplier
    	() -> ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter(), // element creation supplier
    	() -> null, // tag supplier
    	OrdinaryParameter.class);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.AddParameterAndAssignmentToConstructorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	parameterCorrespondenceSource, constructor, typeReference, fieldToBeAssigned, parameterName, newParameter);
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
