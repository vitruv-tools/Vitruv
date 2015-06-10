/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFactory;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.xtext.xbase.XbasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MIRPackageImpl extends EPackageImpl implements MIRPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mirFileEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass importEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bundleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mappingBodyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mappingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typedElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typedElementRefEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass classMappingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass featureMappingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass namedEClassEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass featureCallEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private MIRPackageImpl()
  {
    super(eNS_URI, MIRFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link MIRPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static MIRPackage init()
  {
    if (isInited) return (MIRPackage)EPackage.Registry.INSTANCE.getEPackage(MIRPackage.eNS_URI);

    // Obtain or create and register package
    MIRPackageImpl theMIRPackage = (MIRPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MIRPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MIRPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XbasePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theMIRPackage.createPackageContents();

    // Initialize created meta-data
    theMIRPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theMIRPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(MIRPackage.eNS_URI, theMIRPackage);
    return theMIRPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMIRFile()
  {
    return mirFileEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMIRFile_GeneratedPackage()
  {
    return (EAttribute)mirFileEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMIRFile_GeneratedClass()
  {
    return (EAttribute)mirFileEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMIRFile_Bundles()
  {
    return (EReference)mirFileEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMIRFile_Imports()
  {
    return (EReference)mirFileEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMIRFile_Mappings()
  {
    return (EReference)mirFileEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getImport()
  {
    return importEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getImport_Package()
  {
    return (EReference)importEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getImport_Name()
  {
    return (EAttribute)importEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBundle()
  {
    return bundleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBundle_BundleFQN()
  {
    return (EAttribute)bundleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMappingBody()
  {
    return mappingBodyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMappingBody_Whenwhere()
  {
    return (EReference)mappingBodyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMappingBody_WithBlocks()
  {
    return (EReference)mappingBodyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMappingBody_Withs()
  {
    return (EReference)mappingBodyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMapping()
  {
    return mappingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_MappedElements()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Constraints()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypedElement()
  {
    return typedElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTypedElementRef()
  {
    return typedElementRefEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTypedElementRef_Ref()
  {
    return (EReference)typedElementRefEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClassMapping()
  {
    return classMappingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFeatureMapping()
  {
    return featureMappingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNamedEClass()
  {
    return namedEClassEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getNamedEClass_Type()
  {
    return (EReference)namedEClassEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNamedEClass_Name()
  {
    return (EAttribute)namedEClassEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFeatureCall()
  {
    return featureCallEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureCall_Ref()
  {
    return (EReference)featureCallEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureCall_Tail()
  {
    return (EReference)featureCallEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureCall_Type()
  {
    return (EReference)featureCallEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFeatureCall_Name()
  {
    return (EAttribute)featureCallEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MIRFactory getMIRFactory()
  {
    return (MIRFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    mirFileEClass = createEClass(MIR_FILE);
    createEAttribute(mirFileEClass, MIR_FILE__GENERATED_PACKAGE);
    createEAttribute(mirFileEClass, MIR_FILE__GENERATED_CLASS);
    createEReference(mirFileEClass, MIR_FILE__BUNDLES);
    createEReference(mirFileEClass, MIR_FILE__IMPORTS);
    createEReference(mirFileEClass, MIR_FILE__MAPPINGS);

    importEClass = createEClass(IMPORT);
    createEReference(importEClass, IMPORT__PACKAGE);
    createEAttribute(importEClass, IMPORT__NAME);

    bundleEClass = createEClass(BUNDLE);
    createEAttribute(bundleEClass, BUNDLE__BUNDLE_FQN);

    mappingBodyEClass = createEClass(MAPPING_BODY);
    createEReference(mappingBodyEClass, MAPPING_BODY__WHENWHERE);
    createEReference(mappingBodyEClass, MAPPING_BODY__WITH_BLOCKS);
    createEReference(mappingBodyEClass, MAPPING_BODY__WITHS);

    mappingEClass = createEClass(MAPPING);
    createEReference(mappingEClass, MAPPING__MAPPED_ELEMENTS);
    createEReference(mappingEClass, MAPPING__CONSTRAINTS);

    typedElementEClass = createEClass(TYPED_ELEMENT);

    typedElementRefEClass = createEClass(TYPED_ELEMENT_REF);
    createEReference(typedElementRefEClass, TYPED_ELEMENT_REF__REF);

    classMappingEClass = createEClass(CLASS_MAPPING);

    featureMappingEClass = createEClass(FEATURE_MAPPING);

    namedEClassEClass = createEClass(NAMED_ECLASS);
    createEReference(namedEClassEClass, NAMED_ECLASS__TYPE);
    createEAttribute(namedEClassEClass, NAMED_ECLASS__NAME);

    featureCallEClass = createEClass(FEATURE_CALL);
    createEReference(featureCallEClass, FEATURE_CALL__REF);
    createEReference(featureCallEClass, FEATURE_CALL__TAIL);
    createEReference(featureCallEClass, FEATURE_CALL__TYPE);
    createEAttribute(featureCallEClass, FEATURE_CALL__NAME);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XbasePackage theXbasePackage = (XbasePackage)EPackage.Registry.INSTANCE.getEPackage(XbasePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    typedElementRefEClass.getESuperTypes().add(this.getTypedElement());
    classMappingEClass.getESuperTypes().add(this.getMapping());
    featureMappingEClass.getESuperTypes().add(this.getMapping());
    namedEClassEClass.getESuperTypes().add(this.getTypedElement());
    featureCallEClass.getESuperTypes().add(this.getTypedElement());

    // Initialize classes and features; add operations and parameters
    initEClass(mirFileEClass, MIRFile.class, "MIRFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMIRFile_GeneratedPackage(), ecorePackage.getEString(), "generatedPackage", null, 0, 1, MIRFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMIRFile_GeneratedClass(), ecorePackage.getEString(), "generatedClass", null, 0, 1, MIRFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMIRFile_Bundles(), this.getBundle(), null, "bundles", null, 0, -1, MIRFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMIRFile_Imports(), this.getImport(), null, "imports", null, 0, -1, MIRFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMIRFile_Mappings(), this.getMapping(), null, "mappings", null, 0, -1, MIRFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(importEClass, Import.class, "Import", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getImport_Package(), ecorePackage.getEPackage(), null, "package", null, 0, 1, Import.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getImport_Name(), ecorePackage.getEString(), "name", null, 0, 1, Import.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(bundleEClass, Bundle.class, "Bundle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBundle_BundleFQN(), ecorePackage.getEString(), "bundleFQN", null, 0, 1, Bundle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mappingBodyEClass, MappingBody.class, "MappingBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMappingBody_Whenwhere(), theXbasePackage.getXExpression(), null, "whenwhere", null, 0, 1, MappingBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMappingBody_WithBlocks(), theXbasePackage.getXExpression(), null, "withBlocks", null, 0, -1, MappingBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMappingBody_Withs(), this.getMapping(), null, "withs", null, 0, -1, MappingBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mappingEClass, Mapping.class, "Mapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMapping_MappedElements(), this.getTypedElement(), null, "mappedElements", null, 0, -1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Constraints(), this.getMappingBody(), null, "constraints", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typedElementEClass, TypedElement.class, "TypedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(typedElementRefEClass, TypedElementRef.class, "TypedElementRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTypedElementRef_Ref(), this.getTypedElement(), null, "ref", null, 0, 1, TypedElementRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(classMappingEClass, ClassMapping.class, "ClassMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(featureMappingEClass, FeatureMapping.class, "FeatureMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(namedEClassEClass, NamedEClass.class, "NamedEClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getNamedEClass_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1, NamedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getNamedEClass_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(featureCallEClass, FeatureCall.class, "FeatureCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFeatureCall_Ref(), this.getTypedElement(), null, "ref", null, 0, 1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFeatureCall_Tail(), ecorePackage.getEStructuralFeature(), null, "tail", null, 0, 1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFeatureCall_Type(), ecorePackage.getEClassifier(), null, "type", null, 0, 1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFeatureCall_Name(), ecorePackage.getEString(), "name", null, 0, 1, FeatureCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //MIRPackageImpl
