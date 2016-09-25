package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompilationUnitEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
      return compilationUnit;
    }
    
    public void updateCompilationUnitElement(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
      EList<String> _namespaces = compilationUnit.getNamespaces();
      EList<String> _namespaces_1 = containingPackage.getNamespaces();
      Iterables.<String>addAll(_namespaces, _namespaces_1);
      EList<String> _namespaces_2 = compilationUnit.getNamespaces();
      String _name = containingPackage.getName();
      _namespaces_2.add(_name);
      String _name_1 = classifier.getName();
      compilationUnit.setName(_name_1);
      EList<ConcreteClassifier> _classifiers = compilationUnit.getClassifiers();
      _classifiers.add(classifier);
      String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(compilationUnit);
      this.persistProjectRelative(sourceElementMappedToClass, compilationUnit, _buildJavaFilePath);
    }
    
    public EObject getElement2(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
      return sourceElementMappedToClass;
    }
  }
  
  private CreateCompilationUnitEffect.EffectUserExecution userExecution;
  
  public CreateCompilationUnitEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateCompilationUnitEffect.EffectUserExecution(getExecutionState(), this);
    				this.sourceElementMappedToClass = sourceElementMappedToClass;this.classifier = classifier;this.containingPackage = containingPackage;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private ConcreteClassifier classifier;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompilationUnitEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   ConcreteClassifier: " + this.classifier);
    getLogger().debug("   Package: " + this.containingPackage);
    
    CompilationUnit compilationUnit = ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    initializeCreateElementState(compilationUnit);
    userExecution.updateCompilationUnitElement(sourceElementMappedToClass, classifier, containingPackage, compilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), userExecution.getElement2(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), "");
    
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
