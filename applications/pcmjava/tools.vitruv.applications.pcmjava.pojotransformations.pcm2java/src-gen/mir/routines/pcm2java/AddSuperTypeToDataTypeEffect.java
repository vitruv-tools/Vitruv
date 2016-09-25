package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
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
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddSuperTypeToDataTypeEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU) {
      return dataTypeImplementationCU;
    }
    
    public void update0Element(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU) {
      final ClassifierImport collectionTypeClassImport = Pcm2JavaHelper.getJavaClassImport(superTypeQualifiedName);
      EList<Import> _imports = dataTypeImplementationCU.getImports();
      _imports.add(collectionTypeClassImport);
    }
    
    public EObject getElement4(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      return dataTypeImplementation;
    }
    
    public void updateNamespaceClassifierElement(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      EList<Import> _imports = dataTypeImplementationCU.getImports();
      Iterable<ClassifierImport> _filter = Iterables.<ClassifierImport>filter(_imports, ClassifierImport.class);
      ClassifierImport _last = IterableExtensions.<ClassifierImport>last(_filter);
      ConcreteClassifier _classifier = _last.getClassifier();
      Pcm2JavaHelper.createNamespaceClassifierReference(namespaceClassifier, _classifier);
      final QualifiedTypeArgument qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
      qualifiedTypeArgument.setTypeReference(innerTypeReference);
      EList<ClassifierReference> _classifierReferences = namespaceClassifier.getClassifierReferences();
      ClassifierReference _get = _classifierReferences.get(0);
      EList<TypeArgument> _typeArguments = _get.getTypeArguments();
      _typeArguments.add(qualifiedTypeArgument);
    }
    
    public EObject getElement2(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      return namespaceClassifier;
    }
    
    public EObject getCorrepondenceSourceDataTypeImplementationCU(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation) {
      return dataType;
    }
    
    public EObject getElement3(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      return dataType;
    }
    
    public EObject getCorrepondenceSourceDataTypeImplementation(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
      return dataType;
    }
    
    public void update1Element(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      dataTypeImplementation.setExtends(namespaceClassifier);
    }
  }
  
  private AddSuperTypeToDataTypeEffect.EffectUserExecution userExecution;
  
  public AddSuperTypeToDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.AddSuperTypeToDataTypeEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.dataType = dataType;this.innerTypeReference = innerTypeReference;this.superTypeQualifiedName = superTypeQualifiedName;
  }
  
  private DataType dataType;
  
  private TypeReference innerTypeReference;
  
  private String superTypeQualifiedName;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddSuperTypeToDataTypeEffect with input:");
    getLogger().debug("   DataType: " + this.dataType);
    getLogger().debug("   TypeReference: " + this.innerTypeReference);
    getLogger().debug("   String: " + this.superTypeQualifiedName);
    
    org.emftext.language.java.classifiers.Class dataTypeImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeImplementation(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (dataTypeImplementation == null) {
    	return;
    }
    initializeRetrieveElementState(dataTypeImplementation);
    CompilationUnit dataTypeImplementationCU = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeImplementationCU(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (dataTypeImplementationCU == null) {
    	return;
    }
    initializeRetrieveElementState(dataTypeImplementationCU);
    // val updatedElement userExecution.getElement1(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU);
    userExecution.update0Element(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU);
    
    NamespaceClassifierReference namespaceClassifier = TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference();
    initializeCreateElementState(namespaceClassifier);
    userExecution.updateNamespaceClassifierElement(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    
    addCorrespondenceBetween(userExecution.getElement2(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier), userExecution.getElement3(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier), "");
    
    // val updatedElement userExecution.getElement4(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    userExecution.update1Element(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    
    preprocessElementStates();
    postprocessElementStates();
  }
}
