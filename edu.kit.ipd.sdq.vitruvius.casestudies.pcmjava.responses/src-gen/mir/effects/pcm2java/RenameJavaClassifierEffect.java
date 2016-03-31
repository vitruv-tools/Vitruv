package mir.effects.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
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
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class RenameJavaClassifierEffect extends AbstractEffectRealization {
  public RenameJavaClassifierEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private NamedElement classSourceElement;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  private boolean isClassSourceElementSet;
  
  private boolean isContainingPackageSet;
  
  private boolean isClassNameSet;
  
  public void setClassSourceElement(final NamedElement classSourceElement) {
    this.classSourceElement = classSourceElement;
    this.isClassSourceElementSet = true;
  }
  
  public void setContainingPackage(final org.emftext.language.java.containers.Package containingPackage) {
    this.containingPackage = containingPackage;
    this.isContainingPackageSet = true;
  }
  
  public void setClassName(final String className) {
    this.className = className;
    this.isClassNameSet = true;
  }
  
  public boolean allParametersSet() {
    return isClassSourceElementSet&&isContainingPackageSet&&isClassNameSet;
  }
  
  private String getModelPath(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className, final CompilationUnit compilationUnit) {
    String _buildJavaFilePath = Pcm2JavaHelper.buildJavaFilePath(compilationUnit);
    return _buildJavaFilePath;
  }
  
  private EObject getCorrepondenceSourceJavaClassifier(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    return classSourceElement;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RenameJavaClassifierEffect with input:");
    getLogger().debug("   NamedElement: " + this.classSourceElement);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   String: " + this.className);
    
    CompilationUnit compilationUnit = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceCompilationUnit(classSourceElement, containingPackage, className), // correspondence source supplier
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	CompilationUnit.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    setPersistenceInformation(compilationUnit, () -> getModelPath(classSourceElement, containingPackage, className, compilationUnit), false);
    ConcreteClassifier javaClassifier = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaClassifier(classSourceElement, containingPackage, className), // correspondence source supplier
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ConcreteClassifier.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    preProcessElements();
    new mir.effects.pcm2java.RenameJavaClassifierEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	classSourceElement, containingPackage, className, compilationUnit, javaClassifier);
    postProcessElements();
  }
  
  private EObject getCorrepondenceSourceCompilationUnit(final NamedElement classSourceElement, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    return classSourceElement;
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
    }
  }
}
