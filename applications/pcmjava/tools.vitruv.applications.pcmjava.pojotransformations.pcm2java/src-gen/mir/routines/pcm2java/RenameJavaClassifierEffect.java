package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaClassifierEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      return javaClassifier;
    }
    
    public void update0Element(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      javaClassifier.setName(className);
    }
    
    public EObject getElement2(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      return compilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit) {
      return classSourceElement;
    }
    
    public void update1Element(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      compilationUnit.setName(className);
      EList<String> _namespaces = compilationUnit.getNamespaces();
      _namespaces.clear();
      EList<String> _namespaces_1 = compilationUnit.getNamespaces();
      EList<String> _namespaces_2 = containingPackage.getNamespaces();
      Iterables.<String>addAll(_namespaces_1, _namespaces_2);
      EList<String> _namespaces_3 = compilationUnit.getNamespaces();
      String _name = containingPackage.getName();
      _namespaces_3.add(_name);
      String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(compilationUnit);
      this.persistProjectRelative(classSourceElement, compilationUnit, _buildJavaFilePath);
    }
    
    public EObject getCorrepondenceSourceCompilationUnit(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
      return classSourceElement;
    }
  }
  
  private RenameJavaClassifierEffect.EffectUserExecution userExecution;
  
  public RenameJavaClassifierEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RenameJavaClassifierEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.classSourceElement = classSourceElement;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement classSourceElement;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaClassifierEffect with input:");
    getLogger().debug("   NamedElement: " + this.classSourceElement);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   String: " + this.className);
    
    CompilationUnit compilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompilationUnit(classSourceElement, containingPackage, className), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (compilationUnit == null) {
    	return;
    }
    initializeRetrieveElementState(compilationUnit);
    ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(classSourceElement, containingPackage, className, compilationUnit), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (javaClassifier == null) {
    	return;
    }
    initializeRetrieveElementState(javaClassifier);
    // val updatedElement userExecution.getElement1(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    userExecution.update0Element(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    
    // val updatedElement userExecution.getElement2(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    userExecution.update1Element(classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
