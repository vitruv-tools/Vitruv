package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class RenameJavaClassifierEffect extends AbstractEffectRealization {
  public RenameJavaClassifierEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(responseExecutionState, calledBy);
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
    	getCorrepondenceSourceCompilationUnit(classSourceElement, containingPackage, className), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (compilationUnit == null) {
    	return;
    }
    initializeRetrieveElementState(compilationUnit);
    ConcreteClassifier javaClassifier = getCorrespondingElement(
    	getCorrepondenceSourceJavaClassifier(classSourceElement, containingPackage, className), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (javaClassifier == null) {
    	return;
    }
    initializeRetrieveElementState(javaClassifier);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameJavaClassifierEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    return classSourceElement;
  }
  
  private EObject getCorrepondenceSourceCompilationUnit(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    return classSourceElement;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit, final ConcreteClassifier javaClassifier) {
      javaClassifier.setName(className);
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
  }
}
