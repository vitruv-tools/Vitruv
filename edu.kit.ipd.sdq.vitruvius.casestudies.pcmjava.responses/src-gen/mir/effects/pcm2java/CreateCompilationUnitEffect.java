package mir.effects.pcm2java;

import com.google.common.collect.Iterables;
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
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class CreateCompilationUnitEffect extends AbstractEffectRealization {
  public CreateCompilationUnitEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private ConcreteClassifier classifier;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private boolean isSourceElementMappedToClassSet;
  
  private boolean isClassifierSet;
  
  private boolean isContainingPackageSet;
  
  public void setSourceElementMappedToClass(final NamedElement sourceElementMappedToClass) {
    this.sourceElementMappedToClass = sourceElementMappedToClass;
    this.isSourceElementMappedToClassSet = true;
  }
  
  public void setClassifier(final ConcreteClassifier classifier) {
    this.classifier = classifier;
    this.isClassifierSet = true;
  }
  
  public void setContainingPackage(final org.emftext.language.java.containers.Package containingPackage) {
    this.containingPackage = containingPackage;
    this.isContainingPackageSet = true;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToClassSet&&isClassifierSet&&isContainingPackageSet;
  }
  
  private String getModelPath(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
    String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(compilationUnit);
    return _buildJavaFilePath;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect CreateCompilationUnitEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   ConcreteClassifier: " + this.classifier);
    getLogger().debug("   Package: " + this.containingPackage);
    
    CompilationUnit compilationUnit = initializeCreateElementState(
    	() -> getCorrepondenceSourceCompilationUnit(sourceElementMappedToClass, classifier, containingPackage), // correspondence source supplier
    	() -> ContainersFactoryImpl.eINSTANCE.createCompilationUnit(), // element creation supplier
    	() -> null, // tag supplier
    	CompilationUnit.class);
    setPersistenceInformation(compilationUnit, () -> getModelPath(sourceElementMappedToClass, classifier, containingPackage, compilationUnit), false);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.CreateCompilationUnitEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToClass, classifier, containingPackage, compilationUnit);
    postProcessElements();
  }
  
  private EObject getCorrepondenceSourceCompilationUnit(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage) {
    return sourceElementMappedToClass;
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
    
    private void executeUserOperations(final NamedElement sourceElementMappedToClass, final ConcreteClassifier classifier, final org.emftext.language.java.containers.Package containingPackage, final CompilationUnit compilationUnit) {
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
    }
  }
}
