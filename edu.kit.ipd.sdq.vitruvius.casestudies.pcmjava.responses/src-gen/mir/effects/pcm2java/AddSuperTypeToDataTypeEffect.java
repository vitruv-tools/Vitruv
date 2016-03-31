package mir.effects.pcm2java;

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
import org.emftext.language.java.generics.GenericsFactory;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.TypeArgument;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.impl.TypesFactoryImpl;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class AddSuperTypeToDataTypeEffect extends AbstractEffectRealization {
  public AddSuperTypeToDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private DataType dataType;
  
  private TypeReference innerTypeReference;
  
  private String superTypeQualifiedName;
  
  private boolean isDataTypeSet;
  
  private boolean isInnerTypeReferenceSet;
  
  private boolean isSuperTypeQualifiedNameSet;
  
  public void setDataType(final DataType dataType) {
    this.dataType = dataType;
    this.isDataTypeSet = true;
  }
  
  public void setInnerTypeReference(final TypeReference innerTypeReference) {
    this.innerTypeReference = innerTypeReference;
    this.isInnerTypeReferenceSet = true;
  }
  
  public void setSuperTypeQualifiedName(final String superTypeQualifiedName) {
    this.superTypeQualifiedName = superTypeQualifiedName;
    this.isSuperTypeQualifiedNameSet = true;
  }
  
  public boolean allParametersSet() {
    return isDataTypeSet&&isInnerTypeReferenceSet&&isSuperTypeQualifiedNameSet;
  }
  
  private EObject getCorrepondenceSourceDataTypeImplementationCU(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    return dataType;
  }
  
  private EObject getCorrepondenceSourceDataTypeImplementation(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    return dataType;
  }
  
  private EObject getCorrepondenceSourceNamespaceClassifier(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    return dataType;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddSuperTypeToDataTypeEffect with input:");
    getLogger().debug("   DataType: " + this.dataType);
    getLogger().debug("   TypeReference: " + this.innerTypeReference);
    getLogger().debug("   String: " + this.superTypeQualifiedName);
    
    NamespaceClassifierReference namespaceClassifier = initializeCreateElementState(
    	() -> getCorrepondenceSourceNamespaceClassifier(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	() -> TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference(), // element creation supplier
    	() -> null, // tag supplier
    	NamespaceClassifierReference.class);
    org.emftext.language.java.classifiers.Class dataTypeImplementation = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceDataTypeImplementation(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,	false);
    CompilationUnit dataTypeImplementationCU = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceDataTypeImplementationCU(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	CompilationUnit.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.AddSuperTypeToDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	dataType, innerTypeReference, superTypeQualifiedName, namespaceClassifier, dataTypeImplementation, dataTypeImplementationCU);
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
    
    private void executeUserOperations(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final NamespaceClassifierReference namespaceClassifier, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU) {
      final ClassifierImport collectionTypeClassImport = Pcm2JavaHelper.getJavaClassImport(superTypeQualifiedName);
      EList<Import> _imports = dataTypeImplementationCU.getImports();
      _imports.add(collectionTypeClassImport);
      ConcreteClassifier _classifier = collectionTypeClassImport.getClassifier();
      Pcm2JavaHelper.createNamespaceClassifierReference(namespaceClassifier, _classifier);
      final QualifiedTypeArgument qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
      qualifiedTypeArgument.setTypeReference(innerTypeReference);
      EList<ClassifierReference> _classifierReferences = namespaceClassifier.getClassifierReferences();
      ClassifierReference _get = _classifierReferences.get(0);
      EList<TypeArgument> _typeArguments = _get.getTypeArguments();
      _typeArguments.add(qualifiedTypeArgument);
      dataTypeImplementation.setExtends(namespaceClassifier);
    }
  }
}
