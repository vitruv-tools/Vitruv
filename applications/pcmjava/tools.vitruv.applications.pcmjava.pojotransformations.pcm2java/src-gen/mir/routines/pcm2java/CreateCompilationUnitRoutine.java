package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompilationUnitRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompilationUnitRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
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
  
  public CreateCompilationUnitRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.CreateCompilationUnitRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.sourceElementMappedToClass = sourceElementMappedToClass;this.classifier = classifier;this.containingPackage = containingPackage;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private ConcreteClassifier classifier;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompilationUnitRoutine with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   ConcreteClassifier: " + this.classifier);
    getLogger().debug("   Package: " + this.containingPackage);
    
    CompilationUnit compilationUnit = ContainersFactoryImpl.eINSTANCE.createCompilationUnit();
    initializeCreateElementState(compilationUnit);
    userExecution.updateCompilationUnitElement(sourceElementMappedToClass, classifier, containingPackage, compilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), userExecution.getElement2(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), "");
    
    postprocessElementStates();
  }
}
