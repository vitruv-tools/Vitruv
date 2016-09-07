/**
 */
package tools.vitruvius.domains.jml.language.jML.util;

import tools.vitruvius.domains.jml.language.jML.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage
 * @generated
 */
public class JMLAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static JMLPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = JMLPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLSwitch<Adapter> modelSwitch =
    new JMLSwitch<Adapter>()
    {
      @Override
      public Adapter caseCompilationUnit(CompilationUnit object)
      {
        return createCompilationUnitAdapter();
      }
      @Override
      public Adapter casePackageDeclaration(PackageDeclaration object)
      {
        return createPackageDeclarationAdapter();
      }
      @Override
      public Adapter caseImportDeclaration(ImportDeclaration object)
      {
        return createImportDeclarationAdapter();
      }
      @Override
      public Adapter caseClassifierDeclarationWithModifier(ClassifierDeclarationWithModifier object)
      {
        return createClassifierDeclarationWithModifierAdapter();
      }
      @Override
      public Adapter caseClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration object)
      {
        return createClassOrInterfaceDeclarationAdapter();
      }
      @Override
      public Adapter caseModifier(Modifier object)
      {
        return createModifierAdapter();
      }
      @Override
      public Adapter caseRegularModifier(RegularModifier object)
      {
        return createRegularModifierAdapter();
      }
      @Override
      public Adapter caseClassDeclaration(ClassDeclaration object)
      {
        return createClassDeclarationAdapter();
      }
      @Override
      public Adapter caseNormalClassDeclaration(NormalClassDeclaration object)
      {
        return createNormalClassDeclarationAdapter();
      }
      @Override
      public Adapter caseTypeParameters(TypeParameters object)
      {
        return createTypeParametersAdapter();
      }
      @Override
      public Adapter caseTypeParameter(TypeParameter object)
      {
        return createTypeParameterAdapter();
      }
      @Override
      public Adapter caseTypeBound(TypeBound object)
      {
        return createTypeBoundAdapter();
      }
      @Override
      public Adapter caseEnumDeclaration(EnumDeclaration object)
      {
        return createEnumDeclarationAdapter();
      }
      @Override
      public Adapter caseEnumConstants(EnumConstants object)
      {
        return createEnumConstantsAdapter();
      }
      @Override
      public Adapter caseEnumConstant(EnumConstant object)
      {
        return createEnumConstantAdapter();
      }
      @Override
      public Adapter caseEnumBodyDeclarations(EnumBodyDeclarations object)
      {
        return createEnumBodyDeclarationsAdapter();
      }
      @Override
      public Adapter caseArguments(Arguments object)
      {
        return createArgumentsAdapter();
      }
      @Override
      public Adapter caseInterfaceDeclaration(InterfaceDeclaration object)
      {
        return createInterfaceDeclarationAdapter();
      }
      @Override
      public Adapter caseNormalInterfaceDeclaration(NormalInterfaceDeclaration object)
      {
        return createNormalInterfaceDeclarationAdapter();
      }
      @Override
      public Adapter caseClassBodyDeclaration(ClassBodyDeclaration object)
      {
        return createClassBodyDeclarationAdapter();
      }
      @Override
      public Adapter caseStaticBlock(StaticBlock object)
      {
        return createStaticBlockAdapter();
      }
      @Override
      public Adapter caseJMLSpecifiedElement(JMLSpecifiedElement object)
      {
        return createJMLSpecifiedElementAdapter();
      }
      @Override
      public Adapter caseJMLMultilineSpec(JMLMultilineSpec object)
      {
        return createJMLMultilineSpecAdapter();
      }
      @Override
      public Adapter caseJMLSinglelineSpec(JMLSinglelineSpec object)
      {
        return createJMLSinglelineSpecAdapter();
      }
      @Override
      public Adapter caseJMLExpressionHaving(JMLExpressionHaving object)
      {
        return createJMLExpressionHavingAdapter();
      }
      @Override
      public Adapter caseVisiblityModifier(VisiblityModifier object)
      {
        return createVisiblityModifierAdapter();
      }
      @Override
      public Adapter caseJMLMethodSpecificationWithModifier(JMLMethodSpecificationWithModifier object)
      {
        return createJMLMethodSpecificationWithModifierAdapter();
      }
      @Override
      public Adapter caseJMLMethodSpecificationWithModifierRegular(JMLMethodSpecificationWithModifierRegular object)
      {
        return createJMLMethodSpecificationWithModifierRegularAdapter();
      }
      @Override
      public Adapter caseJMLMethodSpecificationWithModifierExtended(JMLMethodSpecificationWithModifierExtended object)
      {
        return createJMLMethodSpecificationWithModifierExtendedAdapter();
      }
      @Override
      public Adapter caseJMLMethodSpecification(JMLMethodSpecification object)
      {
        return createJMLMethodSpecificationAdapter();
      }
      @Override
      public Adapter caseJMLMethodBehavior(JMLMethodBehavior object)
      {
        return createJMLMethodBehaviorAdapter();
      }
      @Override
      public Adapter caseJMLExceptionalBehaviorBlock(JMLExceptionalBehaviorBlock object)
      {
        return createJMLExceptionalBehaviorBlockAdapter();
      }
      @Override
      public Adapter caseJMLNormalBehaviorBlock(JMLNormalBehaviorBlock object)
      {
        return createJMLNormalBehaviorBlockAdapter();
      }
      @Override
      public Adapter caseJMLBehaviorBlock(JMLBehaviorBlock object)
      {
        return createJMLBehaviorBlockAdapter();
      }
      @Override
      public Adapter caseJMLMethodExpression(JMLMethodExpression object)
      {
        return createJMLMethodExpressionAdapter();
      }
      @Override
      public Adapter caseJMLEnsuresExpression(JMLEnsuresExpression object)
      {
        return createJMLEnsuresExpressionAdapter();
      }
      @Override
      public Adapter caseJMLRequiresExpression(JMLRequiresExpression object)
      {
        return createJMLRequiresExpressionAdapter();
      }
      @Override
      public Adapter caseJMLSpecificationOnlyElementWithModifier(JMLSpecificationOnlyElementWithModifier object)
      {
        return createJMLSpecificationOnlyElementWithModifierAdapter();
      }
      @Override
      public Adapter caseJMLSpecificationOnlyElement(JMLSpecificationOnlyElement object)
      {
        return createJMLSpecificationOnlyElementAdapter();
      }
      @Override
      public Adapter caseJMLModelElement(JMLModelElement object)
      {
        return createJMLModelElementAdapter();
      }
      @Override
      public Adapter caseJMLGhostElement(JMLGhostElement object)
      {
        return createJMLGhostElementAdapter();
      }
      @Override
      public Adapter caseJMLTypeExpressionWithModifier(JMLTypeExpressionWithModifier object)
      {
        return createJMLTypeExpressionWithModifierAdapter();
      }
      @Override
      public Adapter caseJMLTypeExpression(JMLTypeExpression object)
      {
        return createJMLTypeExpressionAdapter();
      }
      @Override
      public Adapter caseJMLInvariantExpression(JMLInvariantExpression object)
      {
        return createJMLInvariantExpressionAdapter();
      }
      @Override
      public Adapter caseJMLConstraintExpression(JMLConstraintExpression object)
      {
        return createJMLConstraintExpressionAdapter();
      }
      @Override
      public Adapter caseJMLAxiomExpression(JMLAxiomExpression object)
      {
        return createJMLAxiomExpressionAdapter();
      }
      @Override
      public Adapter caseJMLMemberModifier(JMLMemberModifier object)
      {
        return createJMLMemberModifierAdapter();
      }
      @Override
      public Adapter caseMemberDeclWithModifier(MemberDeclWithModifier object)
      {
        return createMemberDeclWithModifierAdapter();
      }
      @Override
      public Adapter caseMemberDeclWithModifierRegular(MemberDeclWithModifierRegular object)
      {
        return createMemberDeclWithModifierRegularAdapter();
      }
      @Override
      public Adapter caseMemberDeclWithModifierSpec(MemberDeclWithModifierSpec object)
      {
        return createMemberDeclWithModifierSpecAdapter();
      }
      @Override
      public Adapter caseMemberDecl(MemberDecl object)
      {
        return createMemberDeclAdapter();
      }
      @Override
      public Adapter caseConstructor(Constructor object)
      {
        return createConstructorAdapter();
      }
      @Override
      public Adapter caseMemberDeclaration(MemberDeclaration object)
      {
        return createMemberDeclarationAdapter();
      }
      @Override
      public Adapter caseGenericMethodOrConstructorDeclOld(GenericMethodOrConstructorDeclOld object)
      {
        return createGenericMethodOrConstructorDeclOldAdapter();
      }
      @Override
      public Adapter caseGenericMethodOrConstructorDecl(GenericMethodOrConstructorDecl object)
      {
        return createGenericMethodOrConstructorDeclAdapter();
      }
      @Override
      public Adapter caseMethodDeclaration(MethodDeclaration object)
      {
        return createMethodDeclarationAdapter();
      }
      @Override
      public Adapter caseFieldDeclaration(FieldDeclaration object)
      {
        return createFieldDeclarationAdapter();
      }
      @Override
      public Adapter caseDeclaredException(DeclaredException object)
      {
        return createDeclaredExceptionAdapter();
      }
      @Override
      public Adapter caseVariableDeclarator(VariableDeclarator object)
      {
        return createVariableDeclaratorAdapter();
      }
      @Override
      public Adapter caseType(Type object)
      {
        return createTypeAdapter();
      }
      @Override
      public Adapter caseClassOrInterfaceTypeWithBrackets(ClassOrInterfaceTypeWithBrackets object)
      {
        return createClassOrInterfaceTypeWithBracketsAdapter();
      }
      @Override
      public Adapter casePrimitiveTypeWithBrackets(PrimitiveTypeWithBrackets object)
      {
        return createPrimitiveTypeWithBracketsAdapter();
      }
      @Override
      public Adapter caseBrackets(Brackets object)
      {
        return createBracketsAdapter();
      }
      @Override
      public Adapter caseClassOrInterfaceType(ClassOrInterfaceType object)
      {
        return createClassOrInterfaceTypeAdapter();
      }
      @Override
      public Adapter caseClassifierType(ClassifierType object)
      {
        return createClassifierTypeAdapter();
      }
      @Override
      public Adapter caseTypeArguments(TypeArguments object)
      {
        return createTypeArgumentsAdapter();
      }
      @Override
      public Adapter caseTypeArgument(TypeArgument object)
      {
        return createTypeArgumentAdapter();
      }
      @Override
      public Adapter caseFormalParameterDecl(FormalParameterDecl object)
      {
        return createFormalParameterDeclAdapter();
      }
      @Override
      public Adapter caseMethodBody(MethodBody object)
      {
        return createMethodBodyAdapter();
      }
      @Override
      public Adapter caseConstructorBody(ConstructorBody object)
      {
        return createConstructorBodyAdapter();
      }
      @Override
      public Adapter caseModifiable(Modifiable object)
      {
        return createModifiableAdapter();
      }
      @Override
      public Adapter caseTyped(Typed object)
      {
        return createTypedAdapter();
      }
      @Override
      public Adapter caseAnnotations(Annotations object)
      {
        return createAnnotationsAdapter();
      }
      @Override
      public Adapter caseAnnotation(Annotation object)
      {
        return createAnnotationAdapter();
      }
      @Override
      public Adapter caseElementValuePairs(ElementValuePairs object)
      {
        return createElementValuePairsAdapter();
      }
      @Override
      public Adapter caseElementValuePair(ElementValuePair object)
      {
        return createElementValuePairAdapter();
      }
      @Override
      public Adapter caseElementValue(ElementValue object)
      {
        return createElementValueAdapter();
      }
      @Override
      public Adapter caseElementValueArrayInitializer(ElementValueArrayInitializer object)
      {
        return createElementValueArrayInitializerAdapter();
      }
      @Override
      public Adapter caseAnnotationTypeDeclaration(AnnotationTypeDeclaration object)
      {
        return createAnnotationTypeDeclarationAdapter();
      }
      @Override
      public Adapter caseAnnotationTypeElementDeclaration(AnnotationTypeElementDeclaration object)
      {
        return createAnnotationTypeElementDeclarationAdapter();
      }
      @Override
      public Adapter caseAnnotationTypeElementRest(AnnotationTypeElementRest object)
      {
        return createAnnotationTypeElementRestAdapter();
      }
      @Override
      public Adapter caseAnnotationMethodOrConstantRest(AnnotationMethodOrConstantRest object)
      {
        return createAnnotationMethodOrConstantRestAdapter();
      }
      @Override
      public Adapter caseAnnotationMethodRest(AnnotationMethodRest object)
      {
        return createAnnotationMethodRestAdapter();
      }
      @Override
      public Adapter caseAnnotationConstantRest(AnnotationConstantRest object)
      {
        return createAnnotationConstantRestAdapter();
      }
      @Override
      public Adapter caseDefaultValue(DefaultValue object)
      {
        return createDefaultValueAdapter();
      }
      @Override
      public Adapter caseBlock(Block object)
      {
        return createBlockAdapter();
      }
      @Override
      public Adapter caseBlockStatement(BlockStatement object)
      {
        return createBlockStatementAdapter();
      }
      @Override
      public Adapter caseIdentifierHaving(IdentifierHaving object)
      {
        return createIdentifierHavingAdapter();
      }
      @Override
      public Adapter caseExpression(Expression object)
      {
        return createExpressionAdapter();
      }
      @Override
      public Adapter caseParenthesisExpression(ParenthesisExpression object)
      {
        return createParenthesisExpressionAdapter();
      }
      @Override
      public Adapter caseCollectionLiteral(CollectionLiteral object)
      {
        return createCollectionLiteralAdapter();
      }
      @Override
      public Adapter caseSetLiteral(SetLiteral object)
      {
        return createSetLiteralAdapter();
      }
      @Override
      public Adapter caseListLiteral(ListLiteral object)
      {
        return createListLiteralAdapter();
      }
      @Override
      public Adapter caseCasePart(CasePart object)
      {
        return createCasePartAdapter();
      }
      @Override
      public Adapter caseValidID(ValidID object)
      {
        return createValidIDAdapter();
      }
      @Override
      public Adapter caseCatchClause(CatchClause object)
      {
        return createCatchClauseAdapter();
      }
      @Override
      public Adapter caseAssignment(Assignment object)
      {
        return createAssignmentAdapter();
      }
      @Override
      public Adapter caseBinaryOperation(BinaryOperation object)
      {
        return createBinaryOperationAdapter();
      }
      @Override
      public Adapter caseInstanceOfExpression(InstanceOfExpression object)
      {
        return createInstanceOfExpressionAdapter();
      }
      @Override
      public Adapter caseUnaryOperation(UnaryOperation object)
      {
        return createUnaryOperationAdapter();
      }
      @Override
      public Adapter casePostfixOperation(PostfixOperation object)
      {
        return createPostfixOperationAdapter();
      }
      @Override
      public Adapter caseMemberFeatureCall(MemberFeatureCall object)
      {
        return createMemberFeatureCallAdapter();
      }
      @Override
      public Adapter caseJMLOldExpression(JMLOldExpression object)
      {
        return createJMLOldExpressionAdapter();
      }
      @Override
      public Adapter caseJMLFreshExpression(JMLFreshExpression object)
      {
        return createJMLFreshExpressionAdapter();
      }
      @Override
      public Adapter caseJMLResultExpression(JMLResultExpression object)
      {
        return createJMLResultExpressionAdapter();
      }
      @Override
      public Adapter caseJMLForAllExpression(JMLForAllExpression object)
      {
        return createJMLForAllExpressionAdapter();
      }
      @Override
      public Adapter caseClosure(Closure object)
      {
        return createClosureAdapter();
      }
      @Override
      public Adapter caseBlockExpression(BlockExpression object)
      {
        return createBlockExpressionAdapter();
      }
      @Override
      public Adapter caseIfExpression(IfExpression object)
      {
        return createIfExpressionAdapter();
      }
      @Override
      public Adapter caseSwitchExpression(SwitchExpression object)
      {
        return createSwitchExpressionAdapter();
      }
      @Override
      public Adapter caseForLoopExpression(ForLoopExpression object)
      {
        return createForLoopExpressionAdapter();
      }
      @Override
      public Adapter caseBasicForLoopExpression(BasicForLoopExpression object)
      {
        return createBasicForLoopExpressionAdapter();
      }
      @Override
      public Adapter caseWhileExpression(WhileExpression object)
      {
        return createWhileExpressionAdapter();
      }
      @Override
      public Adapter caseDoWhileExpression(DoWhileExpression object)
      {
        return createDoWhileExpressionAdapter();
      }
      @Override
      public Adapter caseFeatureCall(FeatureCall object)
      {
        return createFeatureCallAdapter();
      }
      @Override
      public Adapter caseConstructorCall(ConstructorCall object)
      {
        return createConstructorCallAdapter();
      }
      @Override
      public Adapter caseBooleanLiteral(BooleanLiteral object)
      {
        return createBooleanLiteralAdapter();
      }
      @Override
      public Adapter caseNullLiteral(NullLiteral object)
      {
        return createNullLiteralAdapter();
      }
      @Override
      public Adapter caseNumberLiteral(NumberLiteral object)
      {
        return createNumberLiteralAdapter();
      }
      @Override
      public Adapter caseStringLiteral(StringLiteral object)
      {
        return createStringLiteralAdapter();
      }
      @Override
      public Adapter caseCharLiteral(CharLiteral object)
      {
        return createCharLiteralAdapter();
      }
      @Override
      public Adapter caseThrowExpression(ThrowExpression object)
      {
        return createThrowExpressionAdapter();
      }
      @Override
      public Adapter caseReturnExpression(ReturnExpression object)
      {
        return createReturnExpressionAdapter();
      }
      @Override
      public Adapter caseTryCatchFinallyExpression(TryCatchFinallyExpression object)
      {
        return createTryCatchFinallyExpressionAdapter();
      }
      @Override
      public Adapter caseSynchronizedExpression(SynchronizedExpression object)
      {
        return createSynchronizedExpressionAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit <em>Compilation Unit</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.CompilationUnit
   * @generated
   */
  public Adapter createCompilationUnitAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.PackageDeclaration <em>Package Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.PackageDeclaration
   * @generated
   */
  public Adapter createPackageDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration <em>Import Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ImportDeclaration
   * @generated
   */
  public Adapter createImportDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier <em>Classifier Declaration With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
   * @generated
   */
  public Adapter createClassifierDeclarationWithModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration <em>Class Or Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
   * @generated
   */
  public Adapter createClassOrInterfaceDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Modifier <em>Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Modifier
   * @generated
   */
  public Adapter createModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.RegularModifier <em>Regular Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.RegularModifier
   * @generated
   */
  public Adapter createRegularModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassDeclaration <em>Class Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassDeclaration
   * @generated
   */
  public Adapter createClassDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration <em>Normal Class Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration
   * @generated
   */
  public Adapter createNormalClassDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters <em>Type Parameters</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameters
   * @generated
   */
  public Adapter createTypeParametersAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.TypeParameter <em>Type Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.TypeParameter
   * @generated
   */
  public Adapter createTypeParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.TypeBound <em>Type Bound</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.TypeBound
   * @generated
   */
  public Adapter createTypeBoundAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration <em>Enum Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.EnumDeclaration
   * @generated
   */
  public Adapter createEnumDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.EnumConstants <em>Enum Constants</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstants
   * @generated
   */
  public Adapter createEnumConstantsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.EnumConstant <em>Enum Constant</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.EnumConstant
   * @generated
   */
  public Adapter createEnumConstantAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations <em>Enum Body Declarations</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations
   * @generated
   */
  public Adapter createEnumBodyDeclarationsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Arguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Arguments
   * @generated
   */
  public Adapter createArgumentsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration <em>Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration
   * @generated
   */
  public Adapter createInterfaceDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration <em>Normal Interface Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration
   * @generated
   */
  public Adapter createNormalInterfaceDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration <em>Class Body Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration
   * @generated
   */
  public Adapter createClassBodyDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.StaticBlock <em>Static Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.StaticBlock
   * @generated
   */
  public Adapter createStaticBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement <em>Specified Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
   * @generated
   */
  public Adapter createJMLSpecifiedElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec <em>Multiline Spec</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec
   * @generated
   */
  public Adapter createJMLMultilineSpecAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec <em>Singleline Spec</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec
   * @generated
   */
  public Adapter createJMLSinglelineSpecAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving <em>Expression Having</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLExpressionHaving
   * @generated
   */
  public Adapter createJMLExpressionHavingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.VisiblityModifier <em>Visiblity Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.VisiblityModifier
   * @generated
   */
  public Adapter createVisiblityModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier <em>Method Specification With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier
   * @generated
   */
  public Adapter createJMLMethodSpecificationWithModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierRegular <em>Method Specification With Modifier Regular</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierRegular
   * @generated
   */
  public Adapter createJMLMethodSpecificationWithModifierRegularAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierExtended <em>Method Specification With Modifier Extended</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifierExtended
   * @generated
   */
  public Adapter createJMLMethodSpecificationWithModifierExtendedAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecification <em>Method Specification</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodSpecification
   * @generated
   */
  public Adapter createJMLMethodSpecificationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior <em>Method Behavior</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior
   * @generated
   */
  public Adapter createJMLMethodBehaviorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLExceptionalBehaviorBlock <em>Exceptional Behavior Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLExceptionalBehaviorBlock
   * @generated
   */
  public Adapter createJMLExceptionalBehaviorBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLNormalBehaviorBlock <em>Normal Behavior Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLNormalBehaviorBlock
   * @generated
   */
  public Adapter createJMLNormalBehaviorBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLBehaviorBlock <em>Behavior Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLBehaviorBlock
   * @generated
   */
  public Adapter createJMLBehaviorBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodExpression <em>Method Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMethodExpression
   * @generated
   */
  public Adapter createJMLMethodExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLEnsuresExpression <em>Ensures Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLEnsuresExpression
   * @generated
   */
  public Adapter createJMLEnsuresExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLRequiresExpression <em>Requires Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLRequiresExpression
   * @generated
   */
  public Adapter createJMLRequiresExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier <em>Specification Only Element With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier
   * @generated
   */
  public Adapter createJMLSpecificationOnlyElementWithModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement <em>Specification Only Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement
   * @generated
   */
  public Adapter createJMLSpecificationOnlyElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLModelElement <em>Model Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLModelElement
   * @generated
   */
  public Adapter createJMLModelElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLGhostElement <em>Ghost Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLGhostElement
   * @generated
   */
  public Adapter createJMLGhostElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier <em>Type Expression With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier
   * @generated
   */
  public Adapter createJMLTypeExpressionWithModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpression <em>Type Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLTypeExpression
   * @generated
   */
  public Adapter createJMLTypeExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression <em>Invariant Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression
   * @generated
   */
  public Adapter createJMLInvariantExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLConstraintExpression <em>Constraint Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLConstraintExpression
   * @generated
   */
  public Adapter createJMLConstraintExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLAxiomExpression <em>Axiom Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLAxiomExpression
   * @generated
   */
  public Adapter createJMLAxiomExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLMemberModifier <em>Member Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLMemberModifier
   * @generated
   */
  public Adapter createJMLMemberModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier <em>Member Decl With Modifier</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
   * @generated
   */
  public Adapter createMemberDeclWithModifierAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular <em>Member Decl With Modifier Regular</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular
   * @generated
   */
  public Adapter createMemberDeclWithModifierRegularAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec <em>Member Decl With Modifier Spec</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec
   * @generated
   */
  public Adapter createMemberDeclWithModifierSpecAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MemberDecl <em>Member Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDecl
   * @generated
   */
  public Adapter createMemberDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Constructor <em>Constructor</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Constructor
   * @generated
   */
  public Adapter createConstructorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration <em>Member Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MemberDeclaration
   * @generated
   */
  public Adapter createMemberDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld <em>Generic Method Or Constructor Decl Old</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld
   * @generated
   */
  public Adapter createGenericMethodOrConstructorDeclOldAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl <em>Generic Method Or Constructor Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl
   * @generated
   */
  public Adapter createGenericMethodOrConstructorDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration <em>Method Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MethodDeclaration
   * @generated
   */
  public Adapter createMethodDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.FieldDeclaration <em>Field Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.FieldDeclaration
   * @generated
   */
  public Adapter createFieldDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.DeclaredException <em>Declared Exception</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.DeclaredException
   * @generated
   */
  public Adapter createDeclaredExceptionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.VariableDeclarator <em>Variable Declarator</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.VariableDeclarator
   * @generated
   */
  public Adapter createVariableDeclaratorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Type <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Type
   * @generated
   */
  public Adapter createTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets <em>Class Or Interface Type With Brackets</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets
   * @generated
   */
  public Adapter createClassOrInterfaceTypeWithBracketsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets <em>Primitive Type With Brackets</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets
   * @generated
   */
  public Adapter createPrimitiveTypeWithBracketsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Brackets <em>Brackets</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Brackets
   * @generated
   */
  public Adapter createBracketsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType <em>Class Or Interface Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceType
   * @generated
   */
  public Adapter createClassOrInterfaceTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ClassifierType <em>Classifier Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ClassifierType
   * @generated
   */
  public Adapter createClassifierTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.TypeArguments <em>Type Arguments</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArguments
   * @generated
   */
  public Adapter createTypeArgumentsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.TypeArgument <em>Type Argument</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.TypeArgument
   * @generated
   */
  public Adapter createTypeArgumentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl <em>Formal Parameter Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.FormalParameterDecl
   * @generated
   */
  public Adapter createFormalParameterDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MethodBody <em>Method Body</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MethodBody
   * @generated
   */
  public Adapter createMethodBodyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ConstructorBody <em>Constructor Body</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorBody
   * @generated
   */
  public Adapter createConstructorBodyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Modifiable <em>Modifiable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Modifiable
   * @generated
   */
  public Adapter createModifiableAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Typed <em>Typed</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Typed
   * @generated
   */
  public Adapter createTypedAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Annotations <em>Annotations</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Annotations
   * @generated
   */
  public Adapter createAnnotationsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Annotation <em>Annotation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Annotation
   * @generated
   */
  public Adapter createAnnotationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePairs <em>Element Value Pairs</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePairs
   * @generated
   */
  public Adapter createElementValuePairsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ElementValuePair <em>Element Value Pair</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValuePair
   * @generated
   */
  public Adapter createElementValuePairAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ElementValue <em>Element Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValue
   * @generated
   */
  public Adapter createElementValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer <em>Element Value Array Initializer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer
   * @generated
   */
  public Adapter createElementValueArrayInitializerAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration <em>Annotation Type Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration
   * @generated
   */
  public Adapter createAnnotationTypeDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration <em>Annotation Type Element Declaration</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementDeclaration
   * @generated
   */
  public Adapter createAnnotationTypeElementDeclarationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest <em>Annotation Type Element Rest</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationTypeElementRest
   * @generated
   */
  public Adapter createAnnotationTypeElementRestAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest <em>Annotation Method Or Constant Rest</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest
   * @generated
   */
  public Adapter createAnnotationMethodOrConstantRestAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest <em>Annotation Method Rest</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest
   * @generated
   */
  public Adapter createAnnotationMethodRestAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest <em>Annotation Constant Rest</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest
   * @generated
   */
  public Adapter createAnnotationConstantRestAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.DefaultValue <em>Default Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.DefaultValue
   * @generated
   */
  public Adapter createDefaultValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Block <em>Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Block
   * @generated
   */
  public Adapter createBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.BlockStatement <em>Block Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.BlockStatement
   * @generated
   */
  public Adapter createBlockStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.IdentifierHaving <em>Identifier Having</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.IdentifierHaving
   * @generated
   */
  public Adapter createIdentifierHavingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Expression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Expression
   * @generated
   */
  public Adapter createExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ParenthesisExpression <em>Parenthesis Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ParenthesisExpression
   * @generated
   */
  public Adapter createParenthesisExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.CollectionLiteral <em>Collection Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.CollectionLiteral
   * @generated
   */
  public Adapter createCollectionLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.SetLiteral <em>Set Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.SetLiteral
   * @generated
   */
  public Adapter createSetLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ListLiteral <em>List Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ListLiteral
   * @generated
   */
  public Adapter createListLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.CasePart <em>Case Part</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.CasePart
   * @generated
   */
  public Adapter createCasePartAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ValidID <em>Valid ID</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ValidID
   * @generated
   */
  public Adapter createValidIDAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.CatchClause <em>Catch Clause</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.CatchClause
   * @generated
   */
  public Adapter createCatchClauseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Assignment <em>Assignment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Assignment
   * @generated
   */
  public Adapter createAssignmentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.BinaryOperation <em>Binary Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.BinaryOperation
   * @generated
   */
  public Adapter createBinaryOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.InstanceOfExpression <em>Instance Of Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.InstanceOfExpression
   * @generated
   */
  public Adapter createInstanceOfExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.UnaryOperation <em>Unary Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.UnaryOperation
   * @generated
   */
  public Adapter createUnaryOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.PostfixOperation <em>Postfix Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.PostfixOperation
   * @generated
   */
  public Adapter createPostfixOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.MemberFeatureCall <em>Member Feature Call</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.MemberFeatureCall
   * @generated
   */
  public Adapter createMemberFeatureCallAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLOldExpression <em>Old Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLOldExpression
   * @generated
   */
  public Adapter createJMLOldExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLFreshExpression <em>Fresh Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLFreshExpression
   * @generated
   */
  public Adapter createJMLFreshExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLResultExpression <em>Result Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLResultExpression
   * @generated
   */
  public Adapter createJMLResultExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.JMLForAllExpression <em>For All Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.JMLForAllExpression
   * @generated
   */
  public Adapter createJMLForAllExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.Closure <em>Closure</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.Closure
   * @generated
   */
  public Adapter createClosureAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.BlockExpression <em>Block Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.BlockExpression
   * @generated
   */
  public Adapter createBlockExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.IfExpression <em>If Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.IfExpression
   * @generated
   */
  public Adapter createIfExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.SwitchExpression <em>Switch Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.SwitchExpression
   * @generated
   */
  public Adapter createSwitchExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression <em>For Loop Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ForLoopExpression
   * @generated
   */
  public Adapter createForLoopExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression <em>Basic For Loop Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression
   * @generated
   */
  public Adapter createBasicForLoopExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.WhileExpression <em>While Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.WhileExpression
   * @generated
   */
  public Adapter createWhileExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression <em>Do While Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.DoWhileExpression
   * @generated
   */
  public Adapter createDoWhileExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.FeatureCall <em>Feature Call</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.FeatureCall
   * @generated
   */
  public Adapter createFeatureCallAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ConstructorCall <em>Constructor Call</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ConstructorCall
   * @generated
   */
  public Adapter createConstructorCallAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.BooleanLiteral <em>Boolean Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.BooleanLiteral
   * @generated
   */
  public Adapter createBooleanLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.NullLiteral <em>Null Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.NullLiteral
   * @generated
   */
  public Adapter createNullLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.NumberLiteral <em>Number Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.NumberLiteral
   * @generated
   */
  public Adapter createNumberLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.StringLiteral <em>String Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.StringLiteral
   * @generated
   */
  public Adapter createStringLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.CharLiteral <em>Char Literal</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.CharLiteral
   * @generated
   */
  public Adapter createCharLiteralAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ThrowExpression <em>Throw Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ThrowExpression
   * @generated
   */
  public Adapter createThrowExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.ReturnExpression <em>Return Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.ReturnExpression
   * @generated
   */
  public Adapter createReturnExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression <em>Try Catch Finally Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression
   * @generated
   */
  public Adapter createTryCatchFinallyExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link tools.vitruvius.domains.jml.language.jML.SynchronizedExpression <em>Synchronized Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see tools.vitruvius.domains.jml.language.jML.SynchronizedExpression
   * @generated
   */
  public Adapter createSynchronizedExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //JMLAdapterFactory
