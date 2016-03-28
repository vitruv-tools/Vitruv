package mir.effects.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.PCM2JavaHelper;
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
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.impl.ImportsFactoryImpl;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class AddRequiredRoleEffect extends AbstractEffectRealization {
  public AddRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OperationRequiredRole requiredRole;
  
  private boolean isRequiredRoleSet;
  
  public void setRequiredRole(final OperationRequiredRole requiredRole) {
    this.requiredRole = requiredRole;
    this.isRequiredRoleSet = true;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceField(final OperationRequiredRole requiredRole) {
    return requiredRole;
  }
  
  private EObject getCorrepondenceSourceRequiredInterface(final OperationRequiredRole requiredRole) {
    OperationInterface _requiredInterface__OperationRequiredRole = requiredRole.getRequiredInterface__OperationRequiredRole();
    return _requiredInterface__OperationRequiredRole;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceImport(final OperationRequiredRole requiredRole) {
    return requiredRole;
  }
  
  public boolean allParametersSet() {
    return isRequiredRoleSet;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final OperationRequiredRole requiredRole) {
    InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
    return _requiringEntity_RequiredRole;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    ClassifierImport requiredInterfaceImport = initializeCreateElementState(
    	() -> getCorrepondenceSourceRequiredInterfaceImport(requiredRole), // correspondence source supplier
    	() -> ImportsFactoryImpl.eINSTANCE.createClassifierImport(), // element creation supplier
    	() -> null, // tag supplier
    	ClassifierImport.class);
    Field requiredInterfaceField = initializeCreateElementState(
    	() -> getCorrepondenceSourceRequiredInterfaceField(requiredRole), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createField(), // element creation supplier
    	() -> null, // tag supplier
    	Field.class);
    Interface requiredInterface = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRequiredInterface(requiredRole), // correspondence source supplier
    	(Interface _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Interface.class,	false);
    org.emftext.language.java.classifiers.Class javaClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaClass(requiredRole), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.AddRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole, requiredInterfaceImport, requiredInterfaceField, requiredInterface, javaClass);
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
    
    private void executeUserOperations(final OperationRequiredRole requiredRole, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass) {
      final NamespaceClassifierReference typeRef = PCM2JavaHelper.createNamespaceClassifierReference(requiredInterface);
      PCM2JavaHelper.addImportToCompilationUnitOfClassifier(requiredInterfaceImport, javaClass, requiredInterface);
      final String requiredRoleName = requiredRole.getEntityName();
      NamespaceClassifierReference _copy = EcoreUtil.<NamespaceClassifierReference>copy(typeRef);
      PCM2JavaHelper.createPrivateField(requiredInterfaceField, _copy, requiredRoleName);
      EList<Member> _members = javaClass.getMembers();
      _members.add(requiredInterfaceField);
      EList<Member> _members_1 = javaClass.getMembers();
      Iterable<Constructor> _filter = Iterables.<Constructor>filter(_members_1, Constructor.class);
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_filter);
      if (_isNullOrEmpty) {
        PCM2JavaHelper.addConstructorToClass(javaClass);
      }
      EList<Member> _members_2 = javaClass.getMembers();
      Iterable<Constructor> _filter_1 = Iterables.<Constructor>filter(_members_2, Constructor.class);
      for (final Constructor ctor : _filter_1) {
        {
          NamespaceClassifierReference _copy_1 = EcoreUtil.<NamespaceClassifierReference>copy(typeRef);
          final Parameter newParam = PCM2JavaHelper.createOrdinaryParameter(_copy_1, requiredRoleName);
          EList<Parameter> _parameters = ctor.getParameters();
          _parameters.add(newParam);
          final Statement asssignment = PCM2JavaHelper.createAssignmentFromParameterToField(requiredInterfaceField, newParam);
          EList<Statement> _statements = ctor.getStatements();
          _statements.add(asssignment);
        }
      }
    }
  }
}
