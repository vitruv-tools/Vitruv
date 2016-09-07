package mir.routines.pcm2java;

import tools.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
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
  public AddSuperTypeToDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    super(responseExecutionState, calledBy);
    				this.dataType = dataType;this.innerTypeReference = innerTypeReference;this.superTypeQualifiedName = superTypeQualifiedName;
  }
  
  private DataType dataType;
  
  private TypeReference innerTypeReference;
  
  private String superTypeQualifiedName;
  
  private EObject getElement0(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
    return namespaceClassifier;
  }
  
  private EObject getElement1(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
    return dataType;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddSuperTypeToDataTypeEffect with input:");
    getLogger().debug("   DataType: " + this.dataType);
    getLogger().debug("   TypeReference: " + this.innerTypeReference);
    getLogger().debug("   String: " + this.superTypeQualifiedName);
    
    org.emftext.language.java.classifiers.Class dataTypeImplementation = getCorrespondingElement(
    	getCorrepondenceSourceDataTypeImplementation(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (dataTypeImplementation == null) {
    	return;
    }
    initializeRetrieveElementState(dataTypeImplementation);
    CompilationUnit dataTypeImplementationCU = getCorrespondingElement(
    	getCorrepondenceSourceDataTypeImplementationCU(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (dataTypeImplementationCU == null) {
    	return;
    }
    initializeRetrieveElementState(dataTypeImplementationCU);
    NamespaceClassifierReference namespaceClassifier = TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference();
    initializeCreateElementState(namespaceClassifier);
    
    addCorrespondenceBetween(getElement0(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier), getElement1(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier), "");
    preprocessElementStates();
    new mir.routines.pcm2java.AddSuperTypeToDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceDataTypeImplementationCU(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    return dataType;
  }
  
  private EObject getCorrepondenceSourceDataTypeImplementation(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    return dataType;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
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
