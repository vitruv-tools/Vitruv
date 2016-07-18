package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
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
  public RemoveRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    super(responseExecutionState, calledBy);
    				this.requiredRole = requiredRole;this.requiringEntity = requiringEntity;
  }
  
  private RequiredRole requiredRole;
  
  private InterfaceRequiringEntity requiringEntity;
  
  private EObject getCorrepondenceSourceRequiredInterfaceField(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    return requiredRole;
  }
  
  private EObject getElement0(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
    return requiredInterfaceImport;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceImport(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    return requiredRole;
  }
  
  private EObject getElement1(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
    return requiredInterfaceField;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    return requiringEntity;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleEffect with input:");
    getLogger().debug("   RequiredRole: " + this.requiredRole);
    getLogger().debug("   InterfaceRequiringEntity: " + this.requiringEntity);
    
    ClassifierImport requiredInterfaceImport = getCorrespondingElement(
    	getCorrepondenceSourceRequiredInterfaceImport(requiredRole, requiringEntity), // correspondence source supplier
    	ClassifierImport.class,
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(requiredInterfaceImport);
    Field requiredInterfaceField = getCorrespondingElement(
    	getCorrepondenceSourceRequiredInterfaceField(requiredRole, requiringEntity), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(requiredInterfaceField);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	getCorrepondenceSourceJavaClass(requiredRole, requiringEntity), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(javaClass);
    deleteObject(getElement0(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    deleteObject(getElement1(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    preprocessElementStates();
    new mir.routines.pcm2java.RemoveRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
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
