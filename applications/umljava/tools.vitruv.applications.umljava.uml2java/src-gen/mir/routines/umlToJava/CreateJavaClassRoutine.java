package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass) {
      return umlClass;
    }
    
    public void updateJavaClassElement(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass) {
      String _name = umlClass.getName();
      javaClass.setName(_name);
    }
    
    public EObject getElement4(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass) {
      return javaClass;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      return umlClass;
    }
    
    public void updateJavaCompilationUnitElement(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      String _name = umlClass.getName();
      javaCompilationUnit.setName(_name);
      EList<ConcreteClassifier> _classifiers = javaCompilationUnit.getClassifiers();
      _classifiers.add(javaClass);
      String _buildJavaFilePath = JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit);
      this.persistProjectRelative(umlClass, javaCompilationUnit, _buildJavaFilePath);
    }
  }
  
  public CreateJavaClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    org.emftext.language.java.classifiers.Class javaClass = ClassifiersFactoryImpl.eINSTANCE.createClass();
    initializeCreateElementState(javaClass);
    userExecution.updateJavaClassElement(umlClass, javaClass);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClass, javaClass), userExecution.getElement2(umlClass, javaClass), "");
    
    CompilationUnit javaCompilationUnit = ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    initializeCreateElementState(javaCompilationUnit);
    userExecution.updateJavaCompilationUnitElement(umlClass, javaClass, javaCompilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement3(umlClass, javaClass, javaCompilationUnit), userExecution.getElement4(umlClass, javaClass, javaCompilationUnit), "");
    
    postprocessElementStates();
  }
}
