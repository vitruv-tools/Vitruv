/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JMLFactoryImpl extends EFactoryImpl implements JMLFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static JMLFactory init()
  {
    try
    {
      JMLFactory theJMLFactory = (JMLFactory)EPackage.Registry.INSTANCE.getEFactory(JMLPackage.eNS_URI);
      if (theJMLFactory != null)
      {
        return theJMLFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new JMLFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case JMLPackage.COMPILATION_UNIT: return createCompilationUnit();
      case JMLPackage.PACKAGE_DECLARATION: return createPackageDeclaration();
      case JMLPackage.IMPORT_DECLARATION: return createImportDeclaration();
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER: return createClassifierDeclarationWithModifier();
      case JMLPackage.CLASS_OR_INTERFACE_DECLARATION: return createClassOrInterfaceDeclaration();
      case JMLPackage.MODIFIER: return createModifier();
      case JMLPackage.REGULAR_MODIFIER: return createRegularModifier();
      case JMLPackage.CLASS_DECLARATION: return createClassDeclaration();
      case JMLPackage.NORMAL_CLASS_DECLARATION: return createNormalClassDeclaration();
      case JMLPackage.TYPE_PARAMETERS: return createTypeParameters();
      case JMLPackage.TYPE_PARAMETER: return createTypeParameter();
      case JMLPackage.TYPE_BOUND: return createTypeBound();
      case JMLPackage.ENUM_DECLARATION: return createEnumDeclaration();
      case JMLPackage.ENUM_CONSTANTS: return createEnumConstants();
      case JMLPackage.ENUM_CONSTANT: return createEnumConstant();
      case JMLPackage.ENUM_BODY_DECLARATIONS: return createEnumBodyDeclarations();
      case JMLPackage.ARGUMENTS: return createArguments();
      case JMLPackage.INTERFACE_DECLARATION: return createInterfaceDeclaration();
      case JMLPackage.NORMAL_INTERFACE_DECLARATION: return createNormalInterfaceDeclaration();
      case JMLPackage.CLASS_BODY_DECLARATION: return createClassBodyDeclaration();
      case JMLPackage.STATIC_BLOCK: return createStaticBlock();
      case JMLPackage.JML_SPECIFIED_ELEMENT: return createJMLSpecifiedElement();
      case JMLPackage.JML_MULTILINE_SPEC: return createJMLMultilineSpec();
      case JMLPackage.JML_SINGLELINE_SPEC: return createJMLSinglelineSpec();
      case JMLPackage.JML_EXPRESSION_HAVING: return createJMLExpressionHaving();
      case JMLPackage.VISIBLITY_MODIFIER: return createVisiblityModifier();
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER: return createJMLMethodSpecificationWithModifier();
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR: return createJMLMethodSpecificationWithModifierRegular();
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED: return createJMLMethodSpecificationWithModifierExtended();
      case JMLPackage.JML_METHOD_SPECIFICATION: return createJMLMethodSpecification();
      case JMLPackage.JML_METHOD_BEHAVIOR: return createJMLMethodBehavior();
      case JMLPackage.JML_EXCEPTIONAL_BEHAVIOR_BLOCK: return createJMLExceptionalBehaviorBlock();
      case JMLPackage.JML_NORMAL_BEHAVIOR_BLOCK: return createJMLNormalBehaviorBlock();
      case JMLPackage.JML_BEHAVIOR_BLOCK: return createJMLBehaviorBlock();
      case JMLPackage.JML_METHOD_EXPRESSION: return createJMLMethodExpression();
      case JMLPackage.JML_ENSURES_EXPRESSION: return createJMLEnsuresExpression();
      case JMLPackage.JML_REQUIRES_EXPRESSION: return createJMLRequiresExpression();
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER: return createJMLSpecificationOnlyElementWithModifier();
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT: return createJMLSpecificationOnlyElement();
      case JMLPackage.JML_MODEL_ELEMENT: return createJMLModelElement();
      case JMLPackage.JML_GHOST_ELEMENT: return createJMLGhostElement();
      case JMLPackage.JML_TYPE_EXPRESSION_WITH_MODIFIER: return createJMLTypeExpressionWithModifier();
      case JMLPackage.JML_TYPE_EXPRESSION: return createJMLTypeExpression();
      case JMLPackage.JML_INVARIANT_EXPRESSION: return createJMLInvariantExpression();
      case JMLPackage.JML_CONSTRAINT_EXPRESSION: return createJMLConstraintExpression();
      case JMLPackage.JML_AXIOM_EXPRESSION: return createJMLAxiomExpression();
      case JMLPackage.JML_MEMBER_MODIFIER: return createJMLMemberModifier();
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER: return createMemberDeclWithModifier();
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER_REGULAR: return createMemberDeclWithModifierRegular();
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER_SPEC: return createMemberDeclWithModifierSpec();
      case JMLPackage.MEMBER_DECL: return createMemberDecl();
      case JMLPackage.CONSTRUCTOR: return createConstructor();
      case JMLPackage.MEMBER_DECLARATION: return createMemberDeclaration();
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD: return createGenericMethodOrConstructorDeclOld();
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL: return createGenericMethodOrConstructorDecl();
      case JMLPackage.METHOD_DECLARATION: return createMethodDeclaration();
      case JMLPackage.FIELD_DECLARATION: return createFieldDeclaration();
      case JMLPackage.DECLARED_EXCEPTION: return createDeclaredException();
      case JMLPackage.VARIABLE_DECLARATOR: return createVariableDeclarator();
      case JMLPackage.TYPE: return createType();
      case JMLPackage.CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS: return createClassOrInterfaceTypeWithBrackets();
      case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS: return createPrimitiveTypeWithBrackets();
      case JMLPackage.BRACKETS: return createBrackets();
      case JMLPackage.CLASS_OR_INTERFACE_TYPE: return createClassOrInterfaceType();
      case JMLPackage.CLASSIFIER_TYPE: return createClassifierType();
      case JMLPackage.TYPE_ARGUMENTS: return createTypeArguments();
      case JMLPackage.TYPE_ARGUMENT: return createTypeArgument();
      case JMLPackage.FORMAL_PARAMETER_DECL: return createFormalParameterDecl();
      case JMLPackage.METHOD_BODY: return createMethodBody();
      case JMLPackage.CONSTRUCTOR_BODY: return createConstructorBody();
      case JMLPackage.MODIFIABLE: return createModifiable();
      case JMLPackage.TYPED: return createTyped();
      case JMLPackage.ANNOTATIONS: return createAnnotations();
      case JMLPackage.ANNOTATION: return createAnnotation();
      case JMLPackage.ELEMENT_VALUE_PAIRS: return createElementValuePairs();
      case JMLPackage.ELEMENT_VALUE_PAIR: return createElementValuePair();
      case JMLPackage.ELEMENT_VALUE: return createElementValue();
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER: return createElementValueArrayInitializer();
      case JMLPackage.ANNOTATION_TYPE_DECLARATION: return createAnnotationTypeDeclaration();
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION: return createAnnotationTypeElementDeclaration();
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_REST: return createAnnotationTypeElementRest();
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST: return createAnnotationMethodOrConstantRest();
      case JMLPackage.ANNOTATION_METHOD_REST: return createAnnotationMethodRest();
      case JMLPackage.ANNOTATION_CONSTANT_REST: return createAnnotationConstantRest();
      case JMLPackage.DEFAULT_VALUE: return createDefaultValue();
      case JMLPackage.BLOCK: return createBlock();
      case JMLPackage.BLOCK_STATEMENT: return createBlockStatement();
      case JMLPackage.IDENTIFIER_HAVING: return createIdentifierHaving();
      case JMLPackage.EXPRESSION: return createExpression();
      case JMLPackage.PARENTHESIS_EXPRESSION: return createParenthesisExpression();
      case JMLPackage.COLLECTION_LITERAL: return createCollectionLiteral();
      case JMLPackage.SET_LITERAL: return createSetLiteral();
      case JMLPackage.LIST_LITERAL: return createListLiteral();
      case JMLPackage.CASE_PART: return createCasePart();
      case JMLPackage.VALID_ID: return createValidID();
      case JMLPackage.CATCH_CLAUSE: return createCatchClause();
      case JMLPackage.ASSIGNMENT: return createAssignment();
      case JMLPackage.BINARY_OPERATION: return createBinaryOperation();
      case JMLPackage.INSTANCE_OF_EXPRESSION: return createInstanceOfExpression();
      case JMLPackage.UNARY_OPERATION: return createUnaryOperation();
      case JMLPackage.POSTFIX_OPERATION: return createPostfixOperation();
      case JMLPackage.MEMBER_FEATURE_CALL: return createMemberFeatureCall();
      case JMLPackage.JML_OLD_EXPRESSION: return createJMLOldExpression();
      case JMLPackage.JML_FRESH_EXPRESSION: return createJMLFreshExpression();
      case JMLPackage.JML_RESULT_EXPRESSION: return createJMLResultExpression();
      case JMLPackage.JML_FOR_ALL_EXPRESSION: return createJMLForAllExpression();
      case JMLPackage.CLOSURE: return createClosure();
      case JMLPackage.BLOCK_EXPRESSION: return createBlockExpression();
      case JMLPackage.IF_EXPRESSION: return createIfExpression();
      case JMLPackage.SWITCH_EXPRESSION: return createSwitchExpression();
      case JMLPackage.FOR_LOOP_EXPRESSION: return createForLoopExpression();
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION: return createBasicForLoopExpression();
      case JMLPackage.WHILE_EXPRESSION: return createWhileExpression();
      case JMLPackage.DO_WHILE_EXPRESSION: return createDoWhileExpression();
      case JMLPackage.FEATURE_CALL: return createFeatureCall();
      case JMLPackage.CONSTRUCTOR_CALL: return createConstructorCall();
      case JMLPackage.BOOLEAN_LITERAL: return createBooleanLiteral();
      case JMLPackage.NULL_LITERAL: return createNullLiteral();
      case JMLPackage.NUMBER_LITERAL: return createNumberLiteral();
      case JMLPackage.STRING_LITERAL: return createStringLiteral();
      case JMLPackage.CHAR_LITERAL: return createCharLiteral();
      case JMLPackage.THROW_EXPRESSION: return createThrowExpression();
      case JMLPackage.RETURN_EXPRESSION: return createReturnExpression();
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION: return createTryCatchFinallyExpression();
      case JMLPackage.SYNCHRONIZED_EXPRESSION: return createSynchronizedExpression();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case JMLPackage.MODIFIER_VALUE:
        return createModifierValueFromString(eDataType, initialValue);
      case JMLPackage.VISIBILITY_MODIFIER_VALUE:
        return createVisibilityModifierValueFromString(eDataType, initialValue);
      case JMLPackage.JML_SPEC_MEMBER_MODIFIER:
        return createJMLSpecMemberModifierFromString(eDataType, initialValue);
      case JMLPackage.PRIMITIVE_TYPE:
        return createPrimitiveTypeFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case JMLPackage.MODIFIER_VALUE:
        return convertModifierValueToString(eDataType, instanceValue);
      case JMLPackage.VISIBILITY_MODIFIER_VALUE:
        return convertVisibilityModifierValueToString(eDataType, instanceValue);
      case JMLPackage.JML_SPEC_MEMBER_MODIFIER:
        return convertJMLSpecMemberModifierToString(eDataType, instanceValue);
      case JMLPackage.PRIMITIVE_TYPE:
        return convertPrimitiveTypeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompilationUnit createCompilationUnit()
  {
    CompilationUnitImpl compilationUnit = new CompilationUnitImpl();
    return compilationUnit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PackageDeclaration createPackageDeclaration()
  {
    PackageDeclarationImpl packageDeclaration = new PackageDeclarationImpl();
    return packageDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ImportDeclaration createImportDeclaration()
  {
    ImportDeclarationImpl importDeclaration = new ImportDeclarationImpl();
    return importDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassifierDeclarationWithModifier createClassifierDeclarationWithModifier()
  {
    ClassifierDeclarationWithModifierImpl classifierDeclarationWithModifier = new ClassifierDeclarationWithModifierImpl();
    return classifierDeclarationWithModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassOrInterfaceDeclaration createClassOrInterfaceDeclaration()
  {
    ClassOrInterfaceDeclarationImpl classOrInterfaceDeclaration = new ClassOrInterfaceDeclarationImpl();
    return classOrInterfaceDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Modifier createModifier()
  {
    ModifierImpl modifier = new ModifierImpl();
    return modifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RegularModifier createRegularModifier()
  {
    RegularModifierImpl regularModifier = new RegularModifierImpl();
    return regularModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassDeclaration createClassDeclaration()
  {
    ClassDeclarationImpl classDeclaration = new ClassDeclarationImpl();
    return classDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NormalClassDeclaration createNormalClassDeclaration()
  {
    NormalClassDeclarationImpl normalClassDeclaration = new NormalClassDeclarationImpl();
    return normalClassDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeParameters createTypeParameters()
  {
    TypeParametersImpl typeParameters = new TypeParametersImpl();
    return typeParameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeParameter createTypeParameter()
  {
    TypeParameterImpl typeParameter = new TypeParameterImpl();
    return typeParameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeBound createTypeBound()
  {
    TypeBoundImpl typeBound = new TypeBoundImpl();
    return typeBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumDeclaration createEnumDeclaration()
  {
    EnumDeclarationImpl enumDeclaration = new EnumDeclarationImpl();
    return enumDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumConstants createEnumConstants()
  {
    EnumConstantsImpl enumConstants = new EnumConstantsImpl();
    return enumConstants;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumConstant createEnumConstant()
  {
    EnumConstantImpl enumConstant = new EnumConstantImpl();
    return enumConstant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnumBodyDeclarations createEnumBodyDeclarations()
  {
    EnumBodyDeclarationsImpl enumBodyDeclarations = new EnumBodyDeclarationsImpl();
    return enumBodyDeclarations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Arguments createArguments()
  {
    ArgumentsImpl arguments = new ArgumentsImpl();
    return arguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InterfaceDeclaration createInterfaceDeclaration()
  {
    InterfaceDeclarationImpl interfaceDeclaration = new InterfaceDeclarationImpl();
    return interfaceDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NormalInterfaceDeclaration createNormalInterfaceDeclaration()
  {
    NormalInterfaceDeclarationImpl normalInterfaceDeclaration = new NormalInterfaceDeclarationImpl();
    return normalInterfaceDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassBodyDeclaration createClassBodyDeclaration()
  {
    ClassBodyDeclarationImpl classBodyDeclaration = new ClassBodyDeclarationImpl();
    return classBodyDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StaticBlock createStaticBlock()
  {
    StaticBlockImpl staticBlock = new StaticBlockImpl();
    return staticBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSpecifiedElement createJMLSpecifiedElement()
  {
    JMLSpecifiedElementImpl jmlSpecifiedElement = new JMLSpecifiedElementImpl();
    return jmlSpecifiedElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMultilineSpec createJMLMultilineSpec()
  {
    JMLMultilineSpecImpl jmlMultilineSpec = new JMLMultilineSpecImpl();
    return jmlMultilineSpec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSinglelineSpec createJMLSinglelineSpec()
  {
    JMLSinglelineSpecImpl jmlSinglelineSpec = new JMLSinglelineSpecImpl();
    return jmlSinglelineSpec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLExpressionHaving createJMLExpressionHaving()
  {
    JMLExpressionHavingImpl jmlExpressionHaving = new JMLExpressionHavingImpl();
    return jmlExpressionHaving;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VisiblityModifier createVisiblityModifier()
  {
    VisiblityModifierImpl visiblityModifier = new VisiblityModifierImpl();
    return visiblityModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodSpecificationWithModifier createJMLMethodSpecificationWithModifier()
  {
    JMLMethodSpecificationWithModifierImpl jmlMethodSpecificationWithModifier = new JMLMethodSpecificationWithModifierImpl();
    return jmlMethodSpecificationWithModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodSpecificationWithModifierRegular createJMLMethodSpecificationWithModifierRegular()
  {
    JMLMethodSpecificationWithModifierRegularImpl jmlMethodSpecificationWithModifierRegular = new JMLMethodSpecificationWithModifierRegularImpl();
    return jmlMethodSpecificationWithModifierRegular;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodSpecificationWithModifierExtended createJMLMethodSpecificationWithModifierExtended()
  {
    JMLMethodSpecificationWithModifierExtendedImpl jmlMethodSpecificationWithModifierExtended = new JMLMethodSpecificationWithModifierExtendedImpl();
    return jmlMethodSpecificationWithModifierExtended;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodSpecification createJMLMethodSpecification()
  {
    JMLMethodSpecificationImpl jmlMethodSpecification = new JMLMethodSpecificationImpl();
    return jmlMethodSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodBehavior createJMLMethodBehavior()
  {
    JMLMethodBehaviorImpl jmlMethodBehavior = new JMLMethodBehaviorImpl();
    return jmlMethodBehavior;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLExceptionalBehaviorBlock createJMLExceptionalBehaviorBlock()
  {
    JMLExceptionalBehaviorBlockImpl jmlExceptionalBehaviorBlock = new JMLExceptionalBehaviorBlockImpl();
    return jmlExceptionalBehaviorBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLNormalBehaviorBlock createJMLNormalBehaviorBlock()
  {
    JMLNormalBehaviorBlockImpl jmlNormalBehaviorBlock = new JMLNormalBehaviorBlockImpl();
    return jmlNormalBehaviorBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLBehaviorBlock createJMLBehaviorBlock()
  {
    JMLBehaviorBlockImpl jmlBehaviorBlock = new JMLBehaviorBlockImpl();
    return jmlBehaviorBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodExpression createJMLMethodExpression()
  {
    JMLMethodExpressionImpl jmlMethodExpression = new JMLMethodExpressionImpl();
    return jmlMethodExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLEnsuresExpression createJMLEnsuresExpression()
  {
    JMLEnsuresExpressionImpl jmlEnsuresExpression = new JMLEnsuresExpressionImpl();
    return jmlEnsuresExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLRequiresExpression createJMLRequiresExpression()
  {
    JMLRequiresExpressionImpl jmlRequiresExpression = new JMLRequiresExpressionImpl();
    return jmlRequiresExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSpecificationOnlyElementWithModifier createJMLSpecificationOnlyElementWithModifier()
  {
    JMLSpecificationOnlyElementWithModifierImpl jmlSpecificationOnlyElementWithModifier = new JMLSpecificationOnlyElementWithModifierImpl();
    return jmlSpecificationOnlyElementWithModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSpecificationOnlyElement createJMLSpecificationOnlyElement()
  {
    JMLSpecificationOnlyElementImpl jmlSpecificationOnlyElement = new JMLSpecificationOnlyElementImpl();
    return jmlSpecificationOnlyElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLModelElement createJMLModelElement()
  {
    JMLModelElementImpl jmlModelElement = new JMLModelElementImpl();
    return jmlModelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLGhostElement createJMLGhostElement()
  {
    JMLGhostElementImpl jmlGhostElement = new JMLGhostElementImpl();
    return jmlGhostElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLTypeExpressionWithModifier createJMLTypeExpressionWithModifier()
  {
    JMLTypeExpressionWithModifierImpl jmlTypeExpressionWithModifier = new JMLTypeExpressionWithModifierImpl();
    return jmlTypeExpressionWithModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLTypeExpression createJMLTypeExpression()
  {
    JMLTypeExpressionImpl jmlTypeExpression = new JMLTypeExpressionImpl();
    return jmlTypeExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLInvariantExpression createJMLInvariantExpression()
  {
    JMLInvariantExpressionImpl jmlInvariantExpression = new JMLInvariantExpressionImpl();
    return jmlInvariantExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLConstraintExpression createJMLConstraintExpression()
  {
    JMLConstraintExpressionImpl jmlConstraintExpression = new JMLConstraintExpressionImpl();
    return jmlConstraintExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLAxiomExpression createJMLAxiomExpression()
  {
    JMLAxiomExpressionImpl jmlAxiomExpression = new JMLAxiomExpressionImpl();
    return jmlAxiomExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMemberModifier createJMLMemberModifier()
  {
    JMLMemberModifierImpl jmlMemberModifier = new JMLMemberModifierImpl();
    return jmlMemberModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDeclWithModifier createMemberDeclWithModifier()
  {
    MemberDeclWithModifierImpl memberDeclWithModifier = new MemberDeclWithModifierImpl();
    return memberDeclWithModifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDeclWithModifierRegular createMemberDeclWithModifierRegular()
  {
    MemberDeclWithModifierRegularImpl memberDeclWithModifierRegular = new MemberDeclWithModifierRegularImpl();
    return memberDeclWithModifierRegular;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDeclWithModifierSpec createMemberDeclWithModifierSpec()
  {
    MemberDeclWithModifierSpecImpl memberDeclWithModifierSpec = new MemberDeclWithModifierSpecImpl();
    return memberDeclWithModifierSpec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDecl createMemberDecl()
  {
    MemberDeclImpl memberDecl = new MemberDeclImpl();
    return memberDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Constructor createConstructor()
  {
    ConstructorImpl constructor = new ConstructorImpl();
    return constructor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDeclaration createMemberDeclaration()
  {
    MemberDeclarationImpl memberDeclaration = new MemberDeclarationImpl();
    return memberDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GenericMethodOrConstructorDeclOld createGenericMethodOrConstructorDeclOld()
  {
    GenericMethodOrConstructorDeclOldImpl genericMethodOrConstructorDeclOld = new GenericMethodOrConstructorDeclOldImpl();
    return genericMethodOrConstructorDeclOld;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GenericMethodOrConstructorDecl createGenericMethodOrConstructorDecl()
  {
    GenericMethodOrConstructorDeclImpl genericMethodOrConstructorDecl = new GenericMethodOrConstructorDeclImpl();
    return genericMethodOrConstructorDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MethodDeclaration createMethodDeclaration()
  {
    MethodDeclarationImpl methodDeclaration = new MethodDeclarationImpl();
    return methodDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldDeclaration createFieldDeclaration()
  {
    FieldDeclarationImpl fieldDeclaration = new FieldDeclarationImpl();
    return fieldDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DeclaredException createDeclaredException()
  {
    DeclaredExceptionImpl declaredException = new DeclaredExceptionImpl();
    return declaredException;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableDeclarator createVariableDeclarator()
  {
    VariableDeclaratorImpl variableDeclarator = new VariableDeclaratorImpl();
    return variableDeclarator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type createType()
  {
    TypeImpl type = new TypeImpl();
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassOrInterfaceTypeWithBrackets createClassOrInterfaceTypeWithBrackets()
  {
    ClassOrInterfaceTypeWithBracketsImpl classOrInterfaceTypeWithBrackets = new ClassOrInterfaceTypeWithBracketsImpl();
    return classOrInterfaceTypeWithBrackets;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PrimitiveTypeWithBrackets createPrimitiveTypeWithBrackets()
  {
    PrimitiveTypeWithBracketsImpl primitiveTypeWithBrackets = new PrimitiveTypeWithBracketsImpl();
    return primitiveTypeWithBrackets;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Brackets createBrackets()
  {
    BracketsImpl brackets = new BracketsImpl();
    return brackets;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassOrInterfaceType createClassOrInterfaceType()
  {
    ClassOrInterfaceTypeImpl classOrInterfaceType = new ClassOrInterfaceTypeImpl();
    return classOrInterfaceType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClassifierType createClassifierType()
  {
    ClassifierTypeImpl classifierType = new ClassifierTypeImpl();
    return classifierType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeArguments createTypeArguments()
  {
    TypeArgumentsImpl typeArguments = new TypeArgumentsImpl();
    return typeArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeArgument createTypeArgument()
  {
    TypeArgumentImpl typeArgument = new TypeArgumentImpl();
    return typeArgument;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FormalParameterDecl createFormalParameterDecl()
  {
    FormalParameterDeclImpl formalParameterDecl = new FormalParameterDeclImpl();
    return formalParameterDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MethodBody createMethodBody()
  {
    MethodBodyImpl methodBody = new MethodBodyImpl();
    return methodBody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConstructorBody createConstructorBody()
  {
    ConstructorBodyImpl constructorBody = new ConstructorBodyImpl();
    return constructorBody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Modifiable createModifiable()
  {
    ModifiableImpl modifiable = new ModifiableImpl();
    return modifiable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Typed createTyped()
  {
    TypedImpl typed = new TypedImpl();
    return typed;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Annotations createAnnotations()
  {
    AnnotationsImpl annotations = new AnnotationsImpl();
    return annotations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Annotation createAnnotation()
  {
    AnnotationImpl annotation = new AnnotationImpl();
    return annotation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValuePairs createElementValuePairs()
  {
    ElementValuePairsImpl elementValuePairs = new ElementValuePairsImpl();
    return elementValuePairs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValuePair createElementValuePair()
  {
    ElementValuePairImpl elementValuePair = new ElementValuePairImpl();
    return elementValuePair;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValue createElementValue()
  {
    ElementValueImpl elementValue = new ElementValueImpl();
    return elementValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValueArrayInitializer createElementValueArrayInitializer()
  {
    ElementValueArrayInitializerImpl elementValueArrayInitializer = new ElementValueArrayInitializerImpl();
    return elementValueArrayInitializer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationTypeDeclaration createAnnotationTypeDeclaration()
  {
    AnnotationTypeDeclarationImpl annotationTypeDeclaration = new AnnotationTypeDeclarationImpl();
    return annotationTypeDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationTypeElementDeclaration createAnnotationTypeElementDeclaration()
  {
    AnnotationTypeElementDeclarationImpl annotationTypeElementDeclaration = new AnnotationTypeElementDeclarationImpl();
    return annotationTypeElementDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationTypeElementRest createAnnotationTypeElementRest()
  {
    AnnotationTypeElementRestImpl annotationTypeElementRest = new AnnotationTypeElementRestImpl();
    return annotationTypeElementRest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationMethodOrConstantRest createAnnotationMethodOrConstantRest()
  {
    AnnotationMethodOrConstantRestImpl annotationMethodOrConstantRest = new AnnotationMethodOrConstantRestImpl();
    return annotationMethodOrConstantRest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationMethodRest createAnnotationMethodRest()
  {
    AnnotationMethodRestImpl annotationMethodRest = new AnnotationMethodRestImpl();
    return annotationMethodRest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationConstantRest createAnnotationConstantRest()
  {
    AnnotationConstantRestImpl annotationConstantRest = new AnnotationConstantRestImpl();
    return annotationConstantRest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DefaultValue createDefaultValue()
  {
    DefaultValueImpl defaultValue = new DefaultValueImpl();
    return defaultValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Block createBlock()
  {
    BlockImpl block = new BlockImpl();
    return block;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BlockStatement createBlockStatement()
  {
    BlockStatementImpl blockStatement = new BlockStatementImpl();
    return blockStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IdentifierHaving createIdentifierHaving()
  {
    IdentifierHavingImpl identifierHaving = new IdentifierHavingImpl();
    return identifierHaving;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression createExpression()
  {
    ExpressionImpl expression = new ExpressionImpl();
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ParenthesisExpression createParenthesisExpression()
  {
    ParenthesisExpressionImpl parenthesisExpression = new ParenthesisExpressionImpl();
    return parenthesisExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CollectionLiteral createCollectionLiteral()
  {
    CollectionLiteralImpl collectionLiteral = new CollectionLiteralImpl();
    return collectionLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SetLiteral createSetLiteral()
  {
    SetLiteralImpl setLiteral = new SetLiteralImpl();
    return setLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ListLiteral createListLiteral()
  {
    ListLiteralImpl listLiteral = new ListLiteralImpl();
    return listLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CasePart createCasePart()
  {
    CasePartImpl casePart = new CasePartImpl();
    return casePart;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ValidID createValidID()
  {
    ValidIDImpl validID = new ValidIDImpl();
    return validID;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CatchClause createCatchClause()
  {
    CatchClauseImpl catchClause = new CatchClauseImpl();
    return catchClause;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Assignment createAssignment()
  {
    AssignmentImpl assignment = new AssignmentImpl();
    return assignment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BinaryOperation createBinaryOperation()
  {
    BinaryOperationImpl binaryOperation = new BinaryOperationImpl();
    return binaryOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InstanceOfExpression createInstanceOfExpression()
  {
    InstanceOfExpressionImpl instanceOfExpression = new InstanceOfExpressionImpl();
    return instanceOfExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnaryOperation createUnaryOperation()
  {
    UnaryOperationImpl unaryOperation = new UnaryOperationImpl();
    return unaryOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PostfixOperation createPostfixOperation()
  {
    PostfixOperationImpl postfixOperation = new PostfixOperationImpl();
    return postfixOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberFeatureCall createMemberFeatureCall()
  {
    MemberFeatureCallImpl memberFeatureCall = new MemberFeatureCallImpl();
    return memberFeatureCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLOldExpression createJMLOldExpression()
  {
    JMLOldExpressionImpl jmlOldExpression = new JMLOldExpressionImpl();
    return jmlOldExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLFreshExpression createJMLFreshExpression()
  {
    JMLFreshExpressionImpl jmlFreshExpression = new JMLFreshExpressionImpl();
    return jmlFreshExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLResultExpression createJMLResultExpression()
  {
    JMLResultExpressionImpl jmlResultExpression = new JMLResultExpressionImpl();
    return jmlResultExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLForAllExpression createJMLForAllExpression()
  {
    JMLForAllExpressionImpl jmlForAllExpression = new JMLForAllExpressionImpl();
    return jmlForAllExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Closure createClosure()
  {
    ClosureImpl closure = new ClosureImpl();
    return closure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BlockExpression createBlockExpression()
  {
    BlockExpressionImpl blockExpression = new BlockExpressionImpl();
    return blockExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IfExpression createIfExpression()
  {
    IfExpressionImpl ifExpression = new IfExpressionImpl();
    return ifExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SwitchExpression createSwitchExpression()
  {
    SwitchExpressionImpl switchExpression = new SwitchExpressionImpl();
    return switchExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ForLoopExpression createForLoopExpression()
  {
    ForLoopExpressionImpl forLoopExpression = new ForLoopExpressionImpl();
    return forLoopExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BasicForLoopExpression createBasicForLoopExpression()
  {
    BasicForLoopExpressionImpl basicForLoopExpression = new BasicForLoopExpressionImpl();
    return basicForLoopExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WhileExpression createWhileExpression()
  {
    WhileExpressionImpl whileExpression = new WhileExpressionImpl();
    return whileExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DoWhileExpression createDoWhileExpression()
  {
    DoWhileExpressionImpl doWhileExpression = new DoWhileExpressionImpl();
    return doWhileExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureCall createFeatureCall()
  {
    FeatureCallImpl featureCall = new FeatureCallImpl();
    return featureCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConstructorCall createConstructorCall()
  {
    ConstructorCallImpl constructorCall = new ConstructorCallImpl();
    return constructorCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BooleanLiteral createBooleanLiteral()
  {
    BooleanLiteralImpl booleanLiteral = new BooleanLiteralImpl();
    return booleanLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NullLiteral createNullLiteral()
  {
    NullLiteralImpl nullLiteral = new NullLiteralImpl();
    return nullLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NumberLiteral createNumberLiteral()
  {
    NumberLiteralImpl numberLiteral = new NumberLiteralImpl();
    return numberLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StringLiteral createStringLiteral()
  {
    StringLiteralImpl stringLiteral = new StringLiteralImpl();
    return stringLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CharLiteral createCharLiteral()
  {
    CharLiteralImpl charLiteral = new CharLiteralImpl();
    return charLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ThrowExpression createThrowExpression()
  {
    ThrowExpressionImpl throwExpression = new ThrowExpressionImpl();
    return throwExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReturnExpression createReturnExpression()
  {
    ReturnExpressionImpl returnExpression = new ReturnExpressionImpl();
    return returnExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TryCatchFinallyExpression createTryCatchFinallyExpression()
  {
    TryCatchFinallyExpressionImpl tryCatchFinallyExpression = new TryCatchFinallyExpressionImpl();
    return tryCatchFinallyExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SynchronizedExpression createSynchronizedExpression()
  {
    SynchronizedExpressionImpl synchronizedExpression = new SynchronizedExpressionImpl();
    return synchronizedExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModifierValue createModifierValueFromString(EDataType eDataType, String initialValue)
  {
    ModifierValue result = ModifierValue.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertModifierValueToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VisibilityModifierValue createVisibilityModifierValueFromString(EDataType eDataType, String initialValue)
  {
    VisibilityModifierValue result = VisibilityModifierValue.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertVisibilityModifierValueToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSpecMemberModifier createJMLSpecMemberModifierFromString(EDataType eDataType, String initialValue)
  {
    JMLSpecMemberModifier result = JMLSpecMemberModifier.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertJMLSpecMemberModifierToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PrimitiveType createPrimitiveTypeFromString(EDataType eDataType, String initialValue)
  {
    PrimitiveType result = PrimitiveType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertPrimitiveTypeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLPackage getJMLPackage()
  {
    return (JMLPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static JMLPackage getPackage()
  {
    return JMLPackage.eINSTANCE;
  }

} //JMLFactoryImpl
