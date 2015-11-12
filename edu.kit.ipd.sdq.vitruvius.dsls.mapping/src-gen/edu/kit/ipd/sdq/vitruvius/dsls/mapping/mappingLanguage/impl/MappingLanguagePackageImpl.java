/**
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.AttributeEquivalenceExpression;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.BodyConstraintBlock;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintBlock;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintBooleanLiteral;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintExpression;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintLiteral;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintNullLiteral;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintNumberLiteral;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintStringLiteral;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ContextVariable;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.EqualsLiteralExpression;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.FeatureOfContextVariable;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.InExpression;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguageFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.NamedEClass;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMappingPathBase;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMappingPathTail;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.SignatureConstraintBlock;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.VariableRef;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MappingLanguagePackageImpl extends EPackageImpl implements MappingLanguagePackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mappingFileEClass = null;

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
  private EClass mappingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass signatureEClass = null;

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
  private EClass requiredMappingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass signatureConstraintBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bodyConstraintBlockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass featureOfContextVariableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contextVariableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass requiredMappingPathBaseEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass requiredMappingPathTailEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass variableRefEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass inExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass equalsLiteralExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass attributeEquivalenceExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintBooleanLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintNullLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintNumberLiteralEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constraintStringLiteralEClass = null;

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
   * @see edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage#eNS_URI
   * @see #init()
   * @generated
   */
  private MappingLanguagePackageImpl()
  {
    super(eNS_URI, MappingLanguageFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link MappingLanguagePackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static MappingLanguagePackage init()
  {
    if (isInited) return (MappingLanguagePackage)EPackage.Registry.INSTANCE.getEPackage(MappingLanguagePackage.eNS_URI);

    // Obtain or create and register package
    MappingLanguagePackageImpl theMappingLanguagePackage = (MappingLanguagePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MappingLanguagePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MappingLanguagePackageImpl());

    isInited = true;

    // Create package meta-data objects
    theMappingLanguagePackage.createPackageContents();

    // Initialize created meta-data
    theMappingLanguagePackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theMappingLanguagePackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(MappingLanguagePackage.eNS_URI, theMappingLanguagePackage);
    return theMappingLanguagePackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMappingFile()
  {
    return mappingFileEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMappingFile_PluginName()
  {
    return (EAttribute)mappingFileEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMappingFile_Imports()
  {
    return (EReference)mappingFileEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMappingFile_Mappings()
  {
    return (EReference)mappingFileEClass.getEStructuralFeatures().get(2);
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
  public EClass getMapping()
  {
    return mappingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMapping_Name()
  {
    return (EAttribute)mappingEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Requires()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Signature0()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Constraints0()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Signature1()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Constraints1()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_ConstraintsBody()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMapping_Submappings()
  {
    return (EReference)mappingEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSignature()
  {
    return signatureEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSignature_Elements()
  {
    return (EReference)signatureEClass.getEStructuralFeatures().get(0);
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
  public EClass getRequiredMapping()
  {
    return requiredMappingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequiredMapping_Mapping()
  {
    return (EReference)requiredMappingEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRequiredMapping_Name()
  {
    return (EAttribute)requiredMappingEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintBlock()
  {
    return constraintBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConstraintBlock_Expressions()
  {
    return (EReference)constraintBlockEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintExpression()
  {
    return constraintExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSignatureConstraintBlock()
  {
    return signatureConstraintBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBodyConstraintBlock()
  {
    return bodyConstraintBlockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFeatureOfContextVariable()
  {
    return featureOfContextVariableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureOfContextVariable_Context()
  {
    return (EReference)featureOfContextVariableEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFeatureOfContextVariable_Feature()
  {
    return (EReference)featureOfContextVariableEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContextVariable()
  {
    return contextVariableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContextVariable_RequiredMappingPath()
  {
    return (EReference)contextVariableEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContextVariable_TargetClass()
  {
    return (EReference)contextVariableEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRequiredMappingPathBase()
  {
    return requiredMappingPathBaseEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequiredMappingPathBase_RequiredMapping()
  {
    return (EReference)requiredMappingPathBaseEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequiredMappingPathBase_Tail()
  {
    return (EReference)requiredMappingPathBaseEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRequiredMappingPathTail()
  {
    return requiredMappingPathTailEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequiredMappingPathTail_RequiredMapping()
  {
    return (EReference)requiredMappingPathTailEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequiredMappingPathTail_Tail()
  {
    return (EReference)requiredMappingPathTailEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintLiteral()
  {
    return constraintLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVariableRef()
  {
    return variableRefEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVariableRef_Target()
  {
    return (EReference)variableRefEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInExpression()
  {
    return inExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInExpression_Target()
  {
    return (EReference)inExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInExpression_Source()
  {
    return (EReference)inExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEqualsLiteralExpression()
  {
    return equalsLiteralExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEqualsLiteralExpression_Target()
  {
    return (EReference)equalsLiteralExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEqualsLiteralExpression_Value()
  {
    return (EReference)equalsLiteralExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAttributeEquivalenceExpression()
  {
    return attributeEquivalenceExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAttributeEquivalenceExpression_Left()
  {
    return (EReference)attributeEquivalenceExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAttributeEquivalenceExpression_Right()
  {
    return (EReference)attributeEquivalenceExpressionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintBooleanLiteral()
  {
    return constraintBooleanLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstraintBooleanLiteral_IsTrue()
  {
    return (EAttribute)constraintBooleanLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintNullLiteral()
  {
    return constraintNullLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintNumberLiteral()
  {
    return constraintNumberLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstraintNumberLiteral_Value()
  {
    return (EAttribute)constraintNumberLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstraintStringLiteral()
  {
    return constraintStringLiteralEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstraintStringLiteral_Value()
  {
    return (EAttribute)constraintStringLiteralEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MappingLanguageFactory getMappingLanguageFactory()
  {
    return (MappingLanguageFactory)getEFactoryInstance();
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
    mappingFileEClass = createEClass(MAPPING_FILE);
    createEAttribute(mappingFileEClass, MAPPING_FILE__PLUGIN_NAME);
    createEReference(mappingFileEClass, MAPPING_FILE__IMPORTS);
    createEReference(mappingFileEClass, MAPPING_FILE__MAPPINGS);

    importEClass = createEClass(IMPORT);
    createEReference(importEClass, IMPORT__PACKAGE);
    createEAttribute(importEClass, IMPORT__NAME);

    mappingEClass = createEClass(MAPPING);
    createEAttribute(mappingEClass, MAPPING__NAME);
    createEReference(mappingEClass, MAPPING__REQUIRES);
    createEReference(mappingEClass, MAPPING__SIGNATURE0);
    createEReference(mappingEClass, MAPPING__CONSTRAINTS0);
    createEReference(mappingEClass, MAPPING__SIGNATURE1);
    createEReference(mappingEClass, MAPPING__CONSTRAINTS1);
    createEReference(mappingEClass, MAPPING__CONSTRAINTS_BODY);
    createEReference(mappingEClass, MAPPING__SUBMAPPINGS);

    signatureEClass = createEClass(SIGNATURE);
    createEReference(signatureEClass, SIGNATURE__ELEMENTS);

    namedEClassEClass = createEClass(NAMED_ECLASS);
    createEReference(namedEClassEClass, NAMED_ECLASS__TYPE);
    createEAttribute(namedEClassEClass, NAMED_ECLASS__NAME);

    requiredMappingEClass = createEClass(REQUIRED_MAPPING);
    createEReference(requiredMappingEClass, REQUIRED_MAPPING__MAPPING);
    createEAttribute(requiredMappingEClass, REQUIRED_MAPPING__NAME);

    constraintBlockEClass = createEClass(CONSTRAINT_BLOCK);
    createEReference(constraintBlockEClass, CONSTRAINT_BLOCK__EXPRESSIONS);

    constraintExpressionEClass = createEClass(CONSTRAINT_EXPRESSION);

    signatureConstraintBlockEClass = createEClass(SIGNATURE_CONSTRAINT_BLOCK);

    bodyConstraintBlockEClass = createEClass(BODY_CONSTRAINT_BLOCK);

    featureOfContextVariableEClass = createEClass(FEATURE_OF_CONTEXT_VARIABLE);
    createEReference(featureOfContextVariableEClass, FEATURE_OF_CONTEXT_VARIABLE__CONTEXT);
    createEReference(featureOfContextVariableEClass, FEATURE_OF_CONTEXT_VARIABLE__FEATURE);

    contextVariableEClass = createEClass(CONTEXT_VARIABLE);
    createEReference(contextVariableEClass, CONTEXT_VARIABLE__REQUIRED_MAPPING_PATH);
    createEReference(contextVariableEClass, CONTEXT_VARIABLE__TARGET_CLASS);

    requiredMappingPathBaseEClass = createEClass(REQUIRED_MAPPING_PATH_BASE);
    createEReference(requiredMappingPathBaseEClass, REQUIRED_MAPPING_PATH_BASE__REQUIRED_MAPPING);
    createEReference(requiredMappingPathBaseEClass, REQUIRED_MAPPING_PATH_BASE__TAIL);

    requiredMappingPathTailEClass = createEClass(REQUIRED_MAPPING_PATH_TAIL);
    createEReference(requiredMappingPathTailEClass, REQUIRED_MAPPING_PATH_TAIL__REQUIRED_MAPPING);
    createEReference(requiredMappingPathTailEClass, REQUIRED_MAPPING_PATH_TAIL__TAIL);

    constraintLiteralEClass = createEClass(CONSTRAINT_LITERAL);

    variableRefEClass = createEClass(VARIABLE_REF);
    createEReference(variableRefEClass, VARIABLE_REF__TARGET);

    inExpressionEClass = createEClass(IN_EXPRESSION);
    createEReference(inExpressionEClass, IN_EXPRESSION__TARGET);
    createEReference(inExpressionEClass, IN_EXPRESSION__SOURCE);

    equalsLiteralExpressionEClass = createEClass(EQUALS_LITERAL_EXPRESSION);
    createEReference(equalsLiteralExpressionEClass, EQUALS_LITERAL_EXPRESSION__TARGET);
    createEReference(equalsLiteralExpressionEClass, EQUALS_LITERAL_EXPRESSION__VALUE);

    attributeEquivalenceExpressionEClass = createEClass(ATTRIBUTE_EQUIVALENCE_EXPRESSION);
    createEReference(attributeEquivalenceExpressionEClass, ATTRIBUTE_EQUIVALENCE_EXPRESSION__LEFT);
    createEReference(attributeEquivalenceExpressionEClass, ATTRIBUTE_EQUIVALENCE_EXPRESSION__RIGHT);

    constraintBooleanLiteralEClass = createEClass(CONSTRAINT_BOOLEAN_LITERAL);
    createEAttribute(constraintBooleanLiteralEClass, CONSTRAINT_BOOLEAN_LITERAL__IS_TRUE);

    constraintNullLiteralEClass = createEClass(CONSTRAINT_NULL_LITERAL);

    constraintNumberLiteralEClass = createEClass(CONSTRAINT_NUMBER_LITERAL);
    createEAttribute(constraintNumberLiteralEClass, CONSTRAINT_NUMBER_LITERAL__VALUE);

    constraintStringLiteralEClass = createEClass(CONSTRAINT_STRING_LITERAL);
    createEAttribute(constraintStringLiteralEClass, CONSTRAINT_STRING_LITERAL__VALUE);
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

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    signatureConstraintBlockEClass.getESuperTypes().add(this.getConstraintBlock());
    bodyConstraintBlockEClass.getESuperTypes().add(this.getConstraintBlock());
    variableRefEClass.getESuperTypes().add(this.getConstraintExpression());
    inExpressionEClass.getESuperTypes().add(this.getConstraintExpression());
    equalsLiteralExpressionEClass.getESuperTypes().add(this.getConstraintExpression());
    attributeEquivalenceExpressionEClass.getESuperTypes().add(this.getConstraintExpression());
    constraintBooleanLiteralEClass.getESuperTypes().add(this.getConstraintLiteral());
    constraintNullLiteralEClass.getESuperTypes().add(this.getConstraintLiteral());
    constraintNumberLiteralEClass.getESuperTypes().add(this.getConstraintLiteral());
    constraintStringLiteralEClass.getESuperTypes().add(this.getConstraintLiteral());

    // Initialize classes and features; add operations and parameters
    initEClass(mappingFileEClass, MappingFile.class, "MappingFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMappingFile_PluginName(), ecorePackage.getEString(), "pluginName", null, 0, 1, MappingFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMappingFile_Imports(), this.getImport(), null, "imports", null, 0, -1, MappingFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMappingFile_Mappings(), this.getMapping(), null, "mappings", null, 0, -1, MappingFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(importEClass, Import.class, "Import", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getImport_Package(), ecorePackage.getEPackage(), null, "package", null, 0, 1, Import.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getImport_Name(), ecorePackage.getEString(), "name", null, 0, 1, Import.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mappingEClass, Mapping.class, "Mapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMapping_Name(), ecorePackage.getEString(), "name", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Requires(), this.getRequiredMapping(), null, "requires", null, 0, -1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Signature0(), this.getSignature(), null, "signature0", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Constraints0(), this.getSignatureConstraintBlock(), null, "constraints0", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Signature1(), this.getSignature(), null, "signature1", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Constraints1(), this.getSignatureConstraintBlock(), null, "constraints1", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_ConstraintsBody(), this.getBodyConstraintBlock(), null, "constraintsBody", null, 0, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMapping_Submappings(), this.getMapping(), null, "submappings", null, 0, -1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(signatureEClass, Signature.class, "Signature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSignature_Elements(), this.getNamedEClass(), null, "elements", null, 0, -1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(namedEClassEClass, NamedEClass.class, "NamedEClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getNamedEClass_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1, NamedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getNamedEClass_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(requiredMappingEClass, RequiredMapping.class, "RequiredMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRequiredMapping_Mapping(), this.getMapping(), null, "mapping", null, 0, 1, RequiredMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRequiredMapping_Name(), ecorePackage.getEString(), "name", null, 0, 1, RequiredMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constraintBlockEClass, ConstraintBlock.class, "ConstraintBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getConstraintBlock_Expressions(), this.getConstraintExpression(), null, "expressions", null, 0, -1, ConstraintBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constraintExpressionEClass, ConstraintExpression.class, "ConstraintExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(signatureConstraintBlockEClass, SignatureConstraintBlock.class, "SignatureConstraintBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(bodyConstraintBlockEClass, BodyConstraintBlock.class, "BodyConstraintBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(featureOfContextVariableEClass, FeatureOfContextVariable.class, "FeatureOfContextVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFeatureOfContextVariable_Context(), this.getContextVariable(), null, "context", null, 0, 1, FeatureOfContextVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFeatureOfContextVariable_Feature(), ecorePackage.getEStructuralFeature(), null, "feature", null, 0, 1, FeatureOfContextVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(contextVariableEClass, ContextVariable.class, "ContextVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getContextVariable_RequiredMappingPath(), this.getRequiredMappingPathBase(), null, "requiredMappingPath", null, 0, 1, ContextVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContextVariable_TargetClass(), this.getNamedEClass(), null, "targetClass", null, 0, 1, ContextVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(requiredMappingPathBaseEClass, RequiredMappingPathBase.class, "RequiredMappingPathBase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRequiredMappingPathBase_RequiredMapping(), this.getRequiredMapping(), null, "requiredMapping", null, 0, 1, RequiredMappingPathBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRequiredMappingPathBase_Tail(), this.getRequiredMappingPathTail(), null, "tail", null, 0, 1, RequiredMappingPathBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(requiredMappingPathTailEClass, RequiredMappingPathTail.class, "RequiredMappingPathTail", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRequiredMappingPathTail_RequiredMapping(), this.getRequiredMapping(), null, "requiredMapping", null, 0, 1, RequiredMappingPathTail.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRequiredMappingPathTail_Tail(), this.getRequiredMappingPathTail(), null, "tail", null, 0, 1, RequiredMappingPathTail.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constraintLiteralEClass, ConstraintLiteral.class, "ConstraintLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(variableRefEClass, VariableRef.class, "VariableRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVariableRef_Target(), this.getContextVariable(), null, "target", null, 0, 1, VariableRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(inExpressionEClass, InExpression.class, "InExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInExpression_Target(), this.getContextVariable(), null, "target", null, 0, 1, InExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInExpression_Source(), this.getFeatureOfContextVariable(), null, "source", null, 0, 1, InExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(equalsLiteralExpressionEClass, EqualsLiteralExpression.class, "EqualsLiteralExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEqualsLiteralExpression_Target(), this.getFeatureOfContextVariable(), null, "target", null, 0, 1, EqualsLiteralExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEqualsLiteralExpression_Value(), this.getConstraintLiteral(), null, "value", null, 0, 1, EqualsLiteralExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(attributeEquivalenceExpressionEClass, AttributeEquivalenceExpression.class, "AttributeEquivalenceExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAttributeEquivalenceExpression_Left(), this.getFeatureOfContextVariable(), null, "left", null, 0, 1, AttributeEquivalenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAttributeEquivalenceExpression_Right(), this.getFeatureOfContextVariable(), null, "right", null, 0, 1, AttributeEquivalenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constraintBooleanLiteralEClass, ConstraintBooleanLiteral.class, "ConstraintBooleanLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConstraintBooleanLiteral_IsTrue(), ecorePackage.getEBoolean(), "isTrue", null, 0, 1, ConstraintBooleanLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constraintNullLiteralEClass, ConstraintNullLiteral.class, "ConstraintNullLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(constraintNumberLiteralEClass, ConstraintNumberLiteral.class, "ConstraintNumberLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConstraintNumberLiteral_Value(), ecorePackage.getEString(), "value", null, 0, 1, ConstraintNumberLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constraintStringLiteralEClass, ConstraintStringLiteral.class, "ConstraintStringLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConstraintStringLiteral_Value(), ecorePackage.getEString(), "value", null, 0, 1, ConstraintStringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //MappingLanguagePackageImpl
