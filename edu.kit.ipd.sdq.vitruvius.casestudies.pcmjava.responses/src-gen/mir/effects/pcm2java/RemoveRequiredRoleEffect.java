package mir.effects.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
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
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.expressions.AssignmentExpression;
import org.emftext.language.java.expressions.AssignmentExpressionChild;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.Reference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.references.SelfReference;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.Statement;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
public class RemoveRequiredRoleEffect extends AbstractEffectRealization {
  public RemoveRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private RequiredRole requiredRole;
  
  private InterfaceRequiringEntity requiringEntity;
  
  private boolean isRequiredRoleSet;
  
  private boolean isRequiringEntitySet;
  
  public void setRequiredRole(final RequiredRole requiredRole) {
    this.requiredRole = requiredRole;
    this.isRequiredRoleSet = true;
  }
  
  public void setRequiringEntity(final InterfaceRequiringEntity requiringEntity) {
    this.requiringEntity = requiringEntity;
    this.isRequiringEntitySet = true;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceField(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    return requiredRole;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceImport(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    return requiredRole;
  }
  
  public boolean allParametersSet() {
    return isRequiredRoleSet&&isRequiringEntitySet;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    return requiringEntity;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RemoveRequiredRoleEffect with input:");
    getLogger().debug("   RequiredRole: " + this.requiredRole);
    getLogger().debug("   InterfaceRequiringEntity: " + this.requiringEntity);
    
    org.emftext.language.java.classifiers.Class javaClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaClass(requiredRole, requiringEntity), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,	true);
    ClassifierImport requiredInterfaceImport = initializeDeleteElementState(
    	() -> getCorrepondenceSourceRequiredInterfaceImport(requiredRole, requiringEntity), // correspondence source supplier
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ClassifierImport.class,	true);
    Field requiredInterfaceField = initializeDeleteElementState(
    	() -> getCorrepondenceSourceRequiredInterfaceField(requiredRole, requiringEntity), // correspondence source supplier
    	(Field _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Field.class,	true);
    preProcessElements();
    new mir.effects.pcm2java.RemoveRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole, requiringEntity, javaClass, requiredInterfaceImport, requiredInterfaceField);
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
    
    private void executeUserOperations(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
      boolean _equals = Objects.equal(javaClass, null);
      if (_equals) {
        return;
      }
      EList<Member> _members = javaClass.getMembers();
      Iterable<Constructor> _filter = Iterables.<Constructor>filter(_members, Constructor.class);
      for (final Constructor ctor : _filter) {
        {
          this.effectFacade.callRemoveCorrespondingParameterFromConstructor(ctor, requiredRole);
          Statement statementToRemove = null;
          EList<Statement> _statements = ctor.getStatements();
          for (final Statement statement : _statements) {
            if ((statement instanceof ExpressionStatement)) {
              final Expression assignmentExpression = ((ExpressionStatement)statement).getExpression();
              if ((assignmentExpression instanceof AssignmentExpression)) {
                final AssignmentExpressionChild selfReference = ((AssignmentExpression)assignmentExpression).getChild();
                if ((selfReference instanceof SelfReference)) {
                  final Reference fieldReference = ((SelfReference)selfReference).getNext();
                  if ((fieldReference instanceof IdentifierReference)) {
                    final ReferenceableElement field = ((IdentifierReference)fieldReference).getTarget();
                    if ((field instanceof Field)) {
                      String _name = ((Field)field).getName();
                      String _entityName = requiredRole.getEntityName();
                      boolean _equals_1 = _name.equals(_entityName);
                      if (_equals_1) {
                        statementToRemove = statement;
                      }
                    }
                  }
                }
              }
            }
          }
          boolean _notEquals = (!Objects.equal(statementToRemove, null));
          if (_notEquals) {
            EList<Statement> _statements_1 = ctor.getStatements();
            _statements_1.remove(statementToRemove);
          }
        }
      }
    }
  }
}
