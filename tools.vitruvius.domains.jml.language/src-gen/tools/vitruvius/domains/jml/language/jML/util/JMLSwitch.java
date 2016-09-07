/**
 */
package tools.vitruvius.domains.jml.language.jML.util;

import tools.vitruvius.domains.jml.language.jML.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage
 * @generated
 */
public class JMLSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static JMLPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = JMLPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case JMLPackage.COMPILATION_UNIT:
      {
        CompilationUnit compilationUnit = (CompilationUnit)theEObject;
        T result = caseCompilationUnit(compilationUnit);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.PACKAGE_DECLARATION:
      {
        PackageDeclaration packageDeclaration = (PackageDeclaration)theEObject;
        T result = casePackageDeclaration(packageDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.IMPORT_DECLARATION:
      {
        ImportDeclaration importDeclaration = (ImportDeclaration)theEObject;
        T result = caseImportDeclaration(importDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASSIFIER_DECLARATION_WITH_MODIFIER:
      {
        ClassifierDeclarationWithModifier classifierDeclarationWithModifier = (ClassifierDeclarationWithModifier)theEObject;
        T result = caseClassifierDeclarationWithModifier(classifierDeclarationWithModifier);
        if (result == null) result = caseModifiable(classifierDeclarationWithModifier);
        if (result == null) result = caseBlockStatement(classifierDeclarationWithModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASS_OR_INTERFACE_DECLARATION:
      {
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration)theEObject;
        T result = caseClassOrInterfaceDeclaration(classOrInterfaceDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MODIFIER:
      {
        Modifier modifier = (Modifier)theEObject;
        T result = caseModifier(modifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.REGULAR_MODIFIER:
      {
        RegularModifier regularModifier = (RegularModifier)theEObject;
        T result = caseRegularModifier(regularModifier);
        if (result == null) result = caseModifier(regularModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASS_DECLARATION:
      {
        ClassDeclaration classDeclaration = (ClassDeclaration)theEObject;
        T result = caseClassDeclaration(classDeclaration);
        if (result == null) result = caseClassOrInterfaceDeclaration(classDeclaration);
        if (result == null) result = caseMemberDecl(classDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.NORMAL_CLASS_DECLARATION:
      {
        NormalClassDeclaration normalClassDeclaration = (NormalClassDeclaration)theEObject;
        T result = caseNormalClassDeclaration(normalClassDeclaration);
        if (result == null) result = caseClassDeclaration(normalClassDeclaration);
        if (result == null) result = caseAnnotationTypeElementRest(normalClassDeclaration);
        if (result == null) result = caseClassOrInterfaceDeclaration(normalClassDeclaration);
        if (result == null) result = caseMemberDecl(normalClassDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPE_PARAMETERS:
      {
        TypeParameters typeParameters = (TypeParameters)theEObject;
        T result = caseTypeParameters(typeParameters);
        if (result == null) result = caseGenericMethodOrConstructorDeclOld(typeParameters);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPE_PARAMETER:
      {
        TypeParameter typeParameter = (TypeParameter)theEObject;
        T result = caseTypeParameter(typeParameter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPE_BOUND:
      {
        TypeBound typeBound = (TypeBound)theEObject;
        T result = caseTypeBound(typeBound);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ENUM_DECLARATION:
      {
        EnumDeclaration enumDeclaration = (EnumDeclaration)theEObject;
        T result = caseEnumDeclaration(enumDeclaration);
        if (result == null) result = caseClassDeclaration(enumDeclaration);
        if (result == null) result = caseAnnotationTypeElementRest(enumDeclaration);
        if (result == null) result = caseIdentifierHaving(enumDeclaration);
        if (result == null) result = caseClassOrInterfaceDeclaration(enumDeclaration);
        if (result == null) result = caseMemberDecl(enumDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ENUM_CONSTANTS:
      {
        EnumConstants enumConstants = (EnumConstants)theEObject;
        T result = caseEnumConstants(enumConstants);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ENUM_CONSTANT:
      {
        EnumConstant enumConstant = (EnumConstant)theEObject;
        T result = caseEnumConstant(enumConstant);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ENUM_BODY_DECLARATIONS:
      {
        EnumBodyDeclarations enumBodyDeclarations = (EnumBodyDeclarations)theEObject;
        T result = caseEnumBodyDeclarations(enumBodyDeclarations);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ARGUMENTS:
      {
        Arguments arguments = (Arguments)theEObject;
        T result = caseArguments(arguments);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.INTERFACE_DECLARATION:
      {
        InterfaceDeclaration interfaceDeclaration = (InterfaceDeclaration)theEObject;
        T result = caseInterfaceDeclaration(interfaceDeclaration);
        if (result == null) result = caseClassOrInterfaceDeclaration(interfaceDeclaration);
        if (result == null) result = caseMemberDecl(interfaceDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.NORMAL_INTERFACE_DECLARATION:
      {
        NormalInterfaceDeclaration normalInterfaceDeclaration = (NormalInterfaceDeclaration)theEObject;
        T result = caseNormalInterfaceDeclaration(normalInterfaceDeclaration);
        if (result == null) result = caseInterfaceDeclaration(normalInterfaceDeclaration);
        if (result == null) result = caseAnnotationTypeElementRest(normalInterfaceDeclaration);
        if (result == null) result = caseClassOrInterfaceDeclaration(normalInterfaceDeclaration);
        if (result == null) result = caseMemberDecl(normalInterfaceDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASS_BODY_DECLARATION:
      {
        ClassBodyDeclaration classBodyDeclaration = (ClassBodyDeclaration)theEObject;
        T result = caseClassBodyDeclaration(classBodyDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.STATIC_BLOCK:
      {
        StaticBlock staticBlock = (StaticBlock)theEObject;
        T result = caseStaticBlock(staticBlock);
        if (result == null) result = caseClassBodyDeclaration(staticBlock);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_SPECIFIED_ELEMENT:
      {
        JMLSpecifiedElement jmlSpecifiedElement = (JMLSpecifiedElement)theEObject;
        T result = caseJMLSpecifiedElement(jmlSpecifiedElement);
        if (result == null) result = caseClassBodyDeclaration(jmlSpecifiedElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_MULTILINE_SPEC:
      {
        JMLMultilineSpec jmlMultilineSpec = (JMLMultilineSpec)theEObject;
        T result = caseJMLMultilineSpec(jmlMultilineSpec);
        if (result == null) result = caseJMLSpecifiedElement(jmlMultilineSpec);
        if (result == null) result = caseClassBodyDeclaration(jmlMultilineSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_SINGLELINE_SPEC:
      {
        JMLSinglelineSpec jmlSinglelineSpec = (JMLSinglelineSpec)theEObject;
        T result = caseJMLSinglelineSpec(jmlSinglelineSpec);
        if (result == null) result = caseJMLSpecifiedElement(jmlSinglelineSpec);
        if (result == null) result = caseClassBodyDeclaration(jmlSinglelineSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_EXPRESSION_HAVING:
      {
        JMLExpressionHaving jmlExpressionHaving = (JMLExpressionHaving)theEObject;
        T result = caseJMLExpressionHaving(jmlExpressionHaving);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.VISIBLITY_MODIFIER:
      {
        VisiblityModifier visiblityModifier = (VisiblityModifier)theEObject;
        T result = caseVisiblityModifier(visiblityModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER:
      {
        JMLMethodSpecificationWithModifier jmlMethodSpecificationWithModifier = (JMLMethodSpecificationWithModifier)theEObject;
        T result = caseJMLMethodSpecificationWithModifier(jmlMethodSpecificationWithModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER_REGULAR:
      {
        JMLMethodSpecificationWithModifierRegular jmlMethodSpecificationWithModifierRegular = (JMLMethodSpecificationWithModifierRegular)theEObject;
        T result = caseJMLMethodSpecificationWithModifierRegular(jmlMethodSpecificationWithModifierRegular);
        if (result == null) result = caseJMLMethodSpecificationWithModifier(jmlMethodSpecificationWithModifierRegular);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER_EXTENDED:
      {
        JMLMethodSpecificationWithModifierExtended jmlMethodSpecificationWithModifierExtended = (JMLMethodSpecificationWithModifierExtended)theEObject;
        T result = caseJMLMethodSpecificationWithModifierExtended(jmlMethodSpecificationWithModifierExtended);
        if (result == null) result = caseJMLMethodSpecificationWithModifier(jmlMethodSpecificationWithModifierExtended);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_METHOD_SPECIFICATION:
      {
        JMLMethodSpecification jmlMethodSpecification = (JMLMethodSpecification)theEObject;
        T result = caseJMLMethodSpecification(jmlMethodSpecification);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_METHOD_BEHAVIOR:
      {
        JMLMethodBehavior jmlMethodBehavior = (JMLMethodBehavior)theEObject;
        T result = caseJMLMethodBehavior(jmlMethodBehavior);
        if (result == null) result = caseJMLMethodSpecification(jmlMethodBehavior);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_EXCEPTIONAL_BEHAVIOR_BLOCK:
      {
        JMLExceptionalBehaviorBlock jmlExceptionalBehaviorBlock = (JMLExceptionalBehaviorBlock)theEObject;
        T result = caseJMLExceptionalBehaviorBlock(jmlExceptionalBehaviorBlock);
        if (result == null) result = caseJMLMethodBehavior(jmlExceptionalBehaviorBlock);
        if (result == null) result = caseJMLMethodSpecification(jmlExceptionalBehaviorBlock);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_NORMAL_BEHAVIOR_BLOCK:
      {
        JMLNormalBehaviorBlock jmlNormalBehaviorBlock = (JMLNormalBehaviorBlock)theEObject;
        T result = caseJMLNormalBehaviorBlock(jmlNormalBehaviorBlock);
        if (result == null) result = caseJMLMethodBehavior(jmlNormalBehaviorBlock);
        if (result == null) result = caseJMLMethodSpecification(jmlNormalBehaviorBlock);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_BEHAVIOR_BLOCK:
      {
        JMLBehaviorBlock jmlBehaviorBlock = (JMLBehaviorBlock)theEObject;
        T result = caseJMLBehaviorBlock(jmlBehaviorBlock);
        if (result == null) result = caseJMLMethodBehavior(jmlBehaviorBlock);
        if (result == null) result = caseJMLMethodSpecification(jmlBehaviorBlock);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_METHOD_EXPRESSION:
      {
        JMLMethodExpression jmlMethodExpression = (JMLMethodExpression)theEObject;
        T result = caseJMLMethodExpression(jmlMethodExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlMethodExpression);
        if (result == null) result = caseJMLMethodSpecification(jmlMethodExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_ENSURES_EXPRESSION:
      {
        JMLEnsuresExpression jmlEnsuresExpression = (JMLEnsuresExpression)theEObject;
        T result = caseJMLEnsuresExpression(jmlEnsuresExpression);
        if (result == null) result = caseJMLMethodExpression(jmlEnsuresExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlEnsuresExpression);
        if (result == null) result = caseJMLMethodSpecification(jmlEnsuresExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_REQUIRES_EXPRESSION:
      {
        JMLRequiresExpression jmlRequiresExpression = (JMLRequiresExpression)theEObject;
        T result = caseJMLRequiresExpression(jmlRequiresExpression);
        if (result == null) result = caseJMLMethodExpression(jmlRequiresExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlRequiresExpression);
        if (result == null) result = caseJMLMethodSpecification(jmlRequiresExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER:
      {
        JMLSpecificationOnlyElementWithModifier jmlSpecificationOnlyElementWithModifier = (JMLSpecificationOnlyElementWithModifier)theEObject;
        T result = caseJMLSpecificationOnlyElementWithModifier(jmlSpecificationOnlyElementWithModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT:
      {
        JMLSpecificationOnlyElement jmlSpecificationOnlyElement = (JMLSpecificationOnlyElement)theEObject;
        T result = caseJMLSpecificationOnlyElement(jmlSpecificationOnlyElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_MODEL_ELEMENT:
      {
        JMLModelElement jmlModelElement = (JMLModelElement)theEObject;
        T result = caseJMLModelElement(jmlModelElement);
        if (result == null) result = caseJMLSpecificationOnlyElement(jmlModelElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_GHOST_ELEMENT:
      {
        JMLGhostElement jmlGhostElement = (JMLGhostElement)theEObject;
        T result = caseJMLGhostElement(jmlGhostElement);
        if (result == null) result = caseJMLSpecificationOnlyElement(jmlGhostElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_TYPE_EXPRESSION_WITH_MODIFIER:
      {
        JMLTypeExpressionWithModifier jmlTypeExpressionWithModifier = (JMLTypeExpressionWithModifier)theEObject;
        T result = caseJMLTypeExpressionWithModifier(jmlTypeExpressionWithModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_TYPE_EXPRESSION:
      {
        JMLTypeExpression jmlTypeExpression = (JMLTypeExpression)theEObject;
        T result = caseJMLTypeExpression(jmlTypeExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlTypeExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_INVARIANT_EXPRESSION:
      {
        JMLInvariantExpression jmlInvariantExpression = (JMLInvariantExpression)theEObject;
        T result = caseJMLInvariantExpression(jmlInvariantExpression);
        if (result == null) result = caseJMLTypeExpression(jmlInvariantExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlInvariantExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_CONSTRAINT_EXPRESSION:
      {
        JMLConstraintExpression jmlConstraintExpression = (JMLConstraintExpression)theEObject;
        T result = caseJMLConstraintExpression(jmlConstraintExpression);
        if (result == null) result = caseJMLTypeExpression(jmlConstraintExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlConstraintExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_AXIOM_EXPRESSION:
      {
        JMLAxiomExpression jmlAxiomExpression = (JMLAxiomExpression)theEObject;
        T result = caseJMLAxiomExpression(jmlAxiomExpression);
        if (result == null) result = caseJMLTypeExpression(jmlAxiomExpression);
        if (result == null) result = caseJMLExpressionHaving(jmlAxiomExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_MEMBER_MODIFIER:
      {
        JMLMemberModifier jmlMemberModifier = (JMLMemberModifier)theEObject;
        T result = caseJMLMemberModifier(jmlMemberModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER:
      {
        MemberDeclWithModifier memberDeclWithModifier = (MemberDeclWithModifier)theEObject;
        T result = caseMemberDeclWithModifier(memberDeclWithModifier);
        if (result == null) result = caseModifiable(memberDeclWithModifier);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER_REGULAR:
      {
        MemberDeclWithModifierRegular memberDeclWithModifierRegular = (MemberDeclWithModifierRegular)theEObject;
        T result = caseMemberDeclWithModifierRegular(memberDeclWithModifierRegular);
        if (result == null) result = caseMemberDeclWithModifier(memberDeclWithModifierRegular);
        if (result == null) result = caseModifiable(memberDeclWithModifierRegular);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MEMBER_DECL_WITH_MODIFIER_SPEC:
      {
        MemberDeclWithModifierSpec memberDeclWithModifierSpec = (MemberDeclWithModifierSpec)theEObject;
        T result = caseMemberDeclWithModifierSpec(memberDeclWithModifierSpec);
        if (result == null) result = caseMemberDeclWithModifier(memberDeclWithModifierSpec);
        if (result == null) result = caseModifiable(memberDeclWithModifierSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MEMBER_DECL:
      {
        MemberDecl memberDecl = (MemberDecl)theEObject;
        T result = caseMemberDecl(memberDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CONSTRUCTOR:
      {
        Constructor constructor = (Constructor)theEObject;
        T result = caseConstructor(constructor);
        if (result == null) result = caseMemberDecl(constructor);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MEMBER_DECLARATION:
      {
        MemberDeclaration memberDeclaration = (MemberDeclaration)theEObject;
        T result = caseMemberDeclaration(memberDeclaration);
        if (result == null) result = caseMemberDecl(memberDeclaration);
        if (result == null) result = caseTyped(memberDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD:
      {
        GenericMethodOrConstructorDeclOld genericMethodOrConstructorDeclOld = (GenericMethodOrConstructorDeclOld)theEObject;
        T result = caseGenericMethodOrConstructorDeclOld(genericMethodOrConstructorDeclOld);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL:
      {
        GenericMethodOrConstructorDecl genericMethodOrConstructorDecl = (GenericMethodOrConstructorDecl)theEObject;
        T result = caseGenericMethodOrConstructorDecl(genericMethodOrConstructorDecl);
        if (result == null) result = caseMemberDecl(genericMethodOrConstructorDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.METHOD_DECLARATION:
      {
        MethodDeclaration methodDeclaration = (MethodDeclaration)theEObject;
        T result = caseMethodDeclaration(methodDeclaration);
        if (result == null) result = caseIdentifierHaving(methodDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.FIELD_DECLARATION:
      {
        FieldDeclaration fieldDeclaration = (FieldDeclaration)theEObject;
        T result = caseFieldDeclaration(fieldDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.DECLARED_EXCEPTION:
      {
        DeclaredException declaredException = (DeclaredException)theEObject;
        T result = caseDeclaredException(declaredException);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.VARIABLE_DECLARATOR:
      {
        VariableDeclarator variableDeclarator = (VariableDeclarator)theEObject;
        T result = caseVariableDeclarator(variableDeclarator);
        if (result == null) result = caseIdentifierHaving(variableDeclarator);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPE:
      {
        Type type = (Type)theEObject;
        T result = caseType(type);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASS_OR_INTERFACE_TYPE_WITH_BRACKETS:
      {
        ClassOrInterfaceTypeWithBrackets classOrInterfaceTypeWithBrackets = (ClassOrInterfaceTypeWithBrackets)theEObject;
        T result = caseClassOrInterfaceTypeWithBrackets(classOrInterfaceTypeWithBrackets);
        if (result == null) result = caseType(classOrInterfaceTypeWithBrackets);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS:
      {
        PrimitiveTypeWithBrackets primitiveTypeWithBrackets = (PrimitiveTypeWithBrackets)theEObject;
        T result = casePrimitiveTypeWithBrackets(primitiveTypeWithBrackets);
        if (result == null) result = caseType(primitiveTypeWithBrackets);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BRACKETS:
      {
        Brackets brackets = (Brackets)theEObject;
        T result = caseBrackets(brackets);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASS_OR_INTERFACE_TYPE:
      {
        ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType)theEObject;
        T result = caseClassOrInterfaceType(classOrInterfaceType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLASSIFIER_TYPE:
      {
        ClassifierType classifierType = (ClassifierType)theEObject;
        T result = caseClassifierType(classifierType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPE_ARGUMENTS:
      {
        TypeArguments typeArguments = (TypeArguments)theEObject;
        T result = caseTypeArguments(typeArguments);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPE_ARGUMENT:
      {
        TypeArgument typeArgument = (TypeArgument)theEObject;
        T result = caseTypeArgument(typeArgument);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.FORMAL_PARAMETER_DECL:
      {
        FormalParameterDecl formalParameterDecl = (FormalParameterDecl)theEObject;
        T result = caseFormalParameterDecl(formalParameterDecl);
        if (result == null) result = caseModifiable(formalParameterDecl);
        if (result == null) result = caseTyped(formalParameterDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.METHOD_BODY:
      {
        MethodBody methodBody = (MethodBody)theEObject;
        T result = caseMethodBody(methodBody);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CONSTRUCTOR_BODY:
      {
        ConstructorBody constructorBody = (ConstructorBody)theEObject;
        T result = caseConstructorBody(constructorBody);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MODIFIABLE:
      {
        Modifiable modifiable = (Modifiable)theEObject;
        T result = caseModifiable(modifiable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TYPED:
      {
        Typed typed = (Typed)theEObject;
        T result = caseTyped(typed);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATIONS:
      {
        Annotations annotations = (Annotations)theEObject;
        T result = caseAnnotations(annotations);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION:
      {
        Annotation annotation = (Annotation)theEObject;
        T result = caseAnnotation(annotation);
        if (result == null) result = caseModifier(annotation);
        if (result == null) result = caseElementValue(annotation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ELEMENT_VALUE_PAIRS:
      {
        ElementValuePairs elementValuePairs = (ElementValuePairs)theEObject;
        T result = caseElementValuePairs(elementValuePairs);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ELEMENT_VALUE_PAIR:
      {
        ElementValuePair elementValuePair = (ElementValuePair)theEObject;
        T result = caseElementValuePair(elementValuePair);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ELEMENT_VALUE:
      {
        ElementValue elementValue = (ElementValue)theEObject;
        T result = caseElementValue(elementValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER:
      {
        ElementValueArrayInitializer elementValueArrayInitializer = (ElementValueArrayInitializer)theEObject;
        T result = caseElementValueArrayInitializer(elementValueArrayInitializer);
        if (result == null) result = caseElementValue(elementValueArrayInitializer);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION_TYPE_DECLARATION:
      {
        AnnotationTypeDeclaration annotationTypeDeclaration = (AnnotationTypeDeclaration)theEObject;
        T result = caseAnnotationTypeDeclaration(annotationTypeDeclaration);
        if (result == null) result = caseInterfaceDeclaration(annotationTypeDeclaration);
        if (result == null) result = caseAnnotationTypeElementRest(annotationTypeDeclaration);
        if (result == null) result = caseClassOrInterfaceDeclaration(annotationTypeDeclaration);
        if (result == null) result = caseMemberDecl(annotationTypeDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_DECLARATION:
      {
        AnnotationTypeElementDeclaration annotationTypeElementDeclaration = (AnnotationTypeElementDeclaration)theEObject;
        T result = caseAnnotationTypeElementDeclaration(annotationTypeElementDeclaration);
        if (result == null) result = caseModifiable(annotationTypeElementDeclaration);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION_TYPE_ELEMENT_REST:
      {
        AnnotationTypeElementRest annotationTypeElementRest = (AnnotationTypeElementRest)theEObject;
        T result = caseAnnotationTypeElementRest(annotationTypeElementRest);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST:
      {
        AnnotationMethodOrConstantRest annotationMethodOrConstantRest = (AnnotationMethodOrConstantRest)theEObject;
        T result = caseAnnotationMethodOrConstantRest(annotationMethodOrConstantRest);
        if (result == null) result = caseTyped(annotationMethodOrConstantRest);
        if (result == null) result = caseAnnotationTypeElementRest(annotationMethodOrConstantRest);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION_METHOD_REST:
      {
        AnnotationMethodRest annotationMethodRest = (AnnotationMethodRest)theEObject;
        T result = caseAnnotationMethodRest(annotationMethodRest);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ANNOTATION_CONSTANT_REST:
      {
        AnnotationConstantRest annotationConstantRest = (AnnotationConstantRest)theEObject;
        T result = caseAnnotationConstantRest(annotationConstantRest);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.DEFAULT_VALUE:
      {
        DefaultValue defaultValue = (DefaultValue)theEObject;
        T result = caseDefaultValue(defaultValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BLOCK:
      {
        Block block = (Block)theEObject;
        T result = caseBlock(block);
        if (result == null) result = caseMethodBody(block);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BLOCK_STATEMENT:
      {
        BlockStatement blockStatement = (BlockStatement)theEObject;
        T result = caseBlockStatement(blockStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.IDENTIFIER_HAVING:
      {
        IdentifierHaving identifierHaving = (IdentifierHaving)theEObject;
        T result = caseIdentifierHaving(identifierHaving);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.EXPRESSION:
      {
        Expression expression = (Expression)theEObject;
        T result = caseExpression(expression);
        if (result == null) result = caseBlockStatement(expression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.PARENTHESIS_EXPRESSION:
      {
        ParenthesisExpression parenthesisExpression = (ParenthesisExpression)theEObject;
        T result = caseParenthesisExpression(parenthesisExpression);
        if (result == null) result = caseExpression(parenthesisExpression);
        if (result == null) result = caseBlockStatement(parenthesisExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.COLLECTION_LITERAL:
      {
        CollectionLiteral collectionLiteral = (CollectionLiteral)theEObject;
        T result = caseCollectionLiteral(collectionLiteral);
        if (result == null) result = caseExpression(collectionLiteral);
        if (result == null) result = caseBlockStatement(collectionLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.SET_LITERAL:
      {
        SetLiteral setLiteral = (SetLiteral)theEObject;
        T result = caseSetLiteral(setLiteral);
        if (result == null) result = caseCollectionLiteral(setLiteral);
        if (result == null) result = caseExpression(setLiteral);
        if (result == null) result = caseBlockStatement(setLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.LIST_LITERAL:
      {
        ListLiteral listLiteral = (ListLiteral)theEObject;
        T result = caseListLiteral(listLiteral);
        if (result == null) result = caseCollectionLiteral(listLiteral);
        if (result == null) result = caseExpression(listLiteral);
        if (result == null) result = caseBlockStatement(listLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CASE_PART:
      {
        CasePart casePart = (CasePart)theEObject;
        T result = caseCasePart(casePart);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.VALID_ID:
      {
        ValidID validID = (ValidID)theEObject;
        T result = caseValidID(validID);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CATCH_CLAUSE:
      {
        CatchClause catchClause = (CatchClause)theEObject;
        T result = caseCatchClause(catchClause);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.ASSIGNMENT:
      {
        Assignment assignment = (Assignment)theEObject;
        T result = caseAssignment(assignment);
        if (result == null) result = caseExpression(assignment);
        if (result == null) result = caseBlockStatement(assignment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BINARY_OPERATION:
      {
        BinaryOperation binaryOperation = (BinaryOperation)theEObject;
        T result = caseBinaryOperation(binaryOperation);
        if (result == null) result = caseExpression(binaryOperation);
        if (result == null) result = caseBlockStatement(binaryOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.INSTANCE_OF_EXPRESSION:
      {
        InstanceOfExpression instanceOfExpression = (InstanceOfExpression)theEObject;
        T result = caseInstanceOfExpression(instanceOfExpression);
        if (result == null) result = caseExpression(instanceOfExpression);
        if (result == null) result = caseBlockStatement(instanceOfExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.UNARY_OPERATION:
      {
        UnaryOperation unaryOperation = (UnaryOperation)theEObject;
        T result = caseUnaryOperation(unaryOperation);
        if (result == null) result = caseExpression(unaryOperation);
        if (result == null) result = caseBlockStatement(unaryOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.POSTFIX_OPERATION:
      {
        PostfixOperation postfixOperation = (PostfixOperation)theEObject;
        T result = casePostfixOperation(postfixOperation);
        if (result == null) result = caseExpression(postfixOperation);
        if (result == null) result = caseBlockStatement(postfixOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.MEMBER_FEATURE_CALL:
      {
        MemberFeatureCall memberFeatureCall = (MemberFeatureCall)theEObject;
        T result = caseMemberFeatureCall(memberFeatureCall);
        if (result == null) result = caseExpression(memberFeatureCall);
        if (result == null) result = caseBlockStatement(memberFeatureCall);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_OLD_EXPRESSION:
      {
        JMLOldExpression jmlOldExpression = (JMLOldExpression)theEObject;
        T result = caseJMLOldExpression(jmlOldExpression);
        if (result == null) result = caseExpression(jmlOldExpression);
        if (result == null) result = caseBlockStatement(jmlOldExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_FRESH_EXPRESSION:
      {
        JMLFreshExpression jmlFreshExpression = (JMLFreshExpression)theEObject;
        T result = caseJMLFreshExpression(jmlFreshExpression);
        if (result == null) result = caseExpression(jmlFreshExpression);
        if (result == null) result = caseBlockStatement(jmlFreshExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_RESULT_EXPRESSION:
      {
        JMLResultExpression jmlResultExpression = (JMLResultExpression)theEObject;
        T result = caseJMLResultExpression(jmlResultExpression);
        if (result == null) result = caseExpression(jmlResultExpression);
        if (result == null) result = caseBlockStatement(jmlResultExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.JML_FOR_ALL_EXPRESSION:
      {
        JMLForAllExpression jmlForAllExpression = (JMLForAllExpression)theEObject;
        T result = caseJMLForAllExpression(jmlForAllExpression);
        if (result == null) result = caseExpression(jmlForAllExpression);
        if (result == null) result = caseBlockStatement(jmlForAllExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CLOSURE:
      {
        Closure closure = (Closure)theEObject;
        T result = caseClosure(closure);
        if (result == null) result = caseExpression(closure);
        if (result == null) result = caseBlockStatement(closure);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BLOCK_EXPRESSION:
      {
        BlockExpression blockExpression = (BlockExpression)theEObject;
        T result = caseBlockExpression(blockExpression);
        if (result == null) result = caseExpression(blockExpression);
        if (result == null) result = caseBlockStatement(blockExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.IF_EXPRESSION:
      {
        IfExpression ifExpression = (IfExpression)theEObject;
        T result = caseIfExpression(ifExpression);
        if (result == null) result = caseExpression(ifExpression);
        if (result == null) result = caseBlockStatement(ifExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.SWITCH_EXPRESSION:
      {
        SwitchExpression switchExpression = (SwitchExpression)theEObject;
        T result = caseSwitchExpression(switchExpression);
        if (result == null) result = caseExpression(switchExpression);
        if (result == null) result = caseBlockStatement(switchExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.FOR_LOOP_EXPRESSION:
      {
        ForLoopExpression forLoopExpression = (ForLoopExpression)theEObject;
        T result = caseForLoopExpression(forLoopExpression);
        if (result == null) result = caseExpression(forLoopExpression);
        if (result == null) result = caseBlockStatement(forLoopExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION:
      {
        BasicForLoopExpression basicForLoopExpression = (BasicForLoopExpression)theEObject;
        T result = caseBasicForLoopExpression(basicForLoopExpression);
        if (result == null) result = caseExpression(basicForLoopExpression);
        if (result == null) result = caseBlockStatement(basicForLoopExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.WHILE_EXPRESSION:
      {
        WhileExpression whileExpression = (WhileExpression)theEObject;
        T result = caseWhileExpression(whileExpression);
        if (result == null) result = caseExpression(whileExpression);
        if (result == null) result = caseBlockStatement(whileExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.DO_WHILE_EXPRESSION:
      {
        DoWhileExpression doWhileExpression = (DoWhileExpression)theEObject;
        T result = caseDoWhileExpression(doWhileExpression);
        if (result == null) result = caseExpression(doWhileExpression);
        if (result == null) result = caseBlockStatement(doWhileExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.FEATURE_CALL:
      {
        FeatureCall featureCall = (FeatureCall)theEObject;
        T result = caseFeatureCall(featureCall);
        if (result == null) result = caseExpression(featureCall);
        if (result == null) result = caseBlockStatement(featureCall);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CONSTRUCTOR_CALL:
      {
        ConstructorCall constructorCall = (ConstructorCall)theEObject;
        T result = caseConstructorCall(constructorCall);
        if (result == null) result = caseExpression(constructorCall);
        if (result == null) result = caseBlockStatement(constructorCall);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.BOOLEAN_LITERAL:
      {
        BooleanLiteral booleanLiteral = (BooleanLiteral)theEObject;
        T result = caseBooleanLiteral(booleanLiteral);
        if (result == null) result = caseExpression(booleanLiteral);
        if (result == null) result = caseBlockStatement(booleanLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.NULL_LITERAL:
      {
        NullLiteral nullLiteral = (NullLiteral)theEObject;
        T result = caseNullLiteral(nullLiteral);
        if (result == null) result = caseExpression(nullLiteral);
        if (result == null) result = caseBlockStatement(nullLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.NUMBER_LITERAL:
      {
        NumberLiteral numberLiteral = (NumberLiteral)theEObject;
        T result = caseNumberLiteral(numberLiteral);
        if (result == null) result = caseExpression(numberLiteral);
        if (result == null) result = caseBlockStatement(numberLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.STRING_LITERAL:
      {
        StringLiteral stringLiteral = (StringLiteral)theEObject;
        T result = caseStringLiteral(stringLiteral);
        if (result == null) result = caseExpression(stringLiteral);
        if (result == null) result = caseBlockStatement(stringLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.CHAR_LITERAL:
      {
        CharLiteral charLiteral = (CharLiteral)theEObject;
        T result = caseCharLiteral(charLiteral);
        if (result == null) result = caseExpression(charLiteral);
        if (result == null) result = caseBlockStatement(charLiteral);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.THROW_EXPRESSION:
      {
        ThrowExpression throwExpression = (ThrowExpression)theEObject;
        T result = caseThrowExpression(throwExpression);
        if (result == null) result = caseExpression(throwExpression);
        if (result == null) result = caseBlockStatement(throwExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.RETURN_EXPRESSION:
      {
        ReturnExpression returnExpression = (ReturnExpression)theEObject;
        T result = caseReturnExpression(returnExpression);
        if (result == null) result = caseExpression(returnExpression);
        if (result == null) result = caseBlockStatement(returnExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION:
      {
        TryCatchFinallyExpression tryCatchFinallyExpression = (TryCatchFinallyExpression)theEObject;
        T result = caseTryCatchFinallyExpression(tryCatchFinallyExpression);
        if (result == null) result = caseExpression(tryCatchFinallyExpression);
        if (result == null) result = caseBlockStatement(tryCatchFinallyExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JMLPackage.SYNCHRONIZED_EXPRESSION:
      {
        SynchronizedExpression synchronizedExpression = (SynchronizedExpression)theEObject;
        T result = caseSynchronizedExpression(synchronizedExpression);
        if (result == null) result = caseExpression(synchronizedExpression);
        if (result == null) result = caseBlockStatement(synchronizedExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Compilation Unit</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Compilation Unit</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompilationUnit(CompilationUnit object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Package Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Package Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePackageDeclaration(PackageDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Import Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Import Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseImportDeclaration(ImportDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Classifier Declaration With Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Classifier Declaration With Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassifierDeclarationWithModifier(ClassifierDeclarationWithModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Class Or Interface Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Class Or Interface Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModifier(Modifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Regular Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Regular Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRegularModifier(RegularModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Class Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Class Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassDeclaration(ClassDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Normal Class Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Normal Class Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNormalClassDeclaration(NormalClassDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Parameters</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Parameters</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeParameters(TypeParameters object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Parameter</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Parameter</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeParameter(TypeParameter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Bound</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Bound</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeBound(TypeBound object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enum Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enum Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnumDeclaration(EnumDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enum Constants</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enum Constants</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnumConstants(EnumConstants object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enum Constant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enum Constant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnumConstant(EnumConstant object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enum Body Declarations</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enum Body Declarations</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnumBodyDeclarations(EnumBodyDeclarations object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Arguments</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Arguments</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseArguments(Arguments object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interface Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interface Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterfaceDeclaration(InterfaceDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Normal Interface Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Normal Interface Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNormalInterfaceDeclaration(NormalInterfaceDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Class Body Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Class Body Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassBodyDeclaration(ClassBodyDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Static Block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Static Block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStaticBlock(StaticBlock object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Specified Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Specified Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLSpecifiedElement(JMLSpecifiedElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Multiline Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Multiline Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMultilineSpec(JMLMultilineSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Singleline Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Singleline Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLSinglelineSpec(JMLSinglelineSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression Having</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression Having</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLExpressionHaving(JMLExpressionHaving object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Visiblity Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Visiblity Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVisiblityModifier(VisiblityModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Specification With Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Specification With Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMethodSpecificationWithModifier(JMLMethodSpecificationWithModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Specification With Modifier Regular</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Specification With Modifier Regular</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMethodSpecificationWithModifierRegular(JMLMethodSpecificationWithModifierRegular object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Specification With Modifier Extended</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Specification With Modifier Extended</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMethodSpecificationWithModifierExtended(JMLMethodSpecificationWithModifierExtended object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Specification</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Specification</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMethodSpecification(JMLMethodSpecification object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Behavior</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Behavior</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMethodBehavior(JMLMethodBehavior object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Exceptional Behavior Block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Exceptional Behavior Block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLExceptionalBehaviorBlock(JMLExceptionalBehaviorBlock object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Normal Behavior Block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Normal Behavior Block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLNormalBehaviorBlock(JMLNormalBehaviorBlock object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Behavior Block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Behavior Block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLBehaviorBlock(JMLBehaviorBlock object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMethodExpression(JMLMethodExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ensures Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ensures Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLEnsuresExpression(JMLEnsuresExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Requires Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Requires Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLRequiresExpression(JMLRequiresExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Specification Only Element With Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Specification Only Element With Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLSpecificationOnlyElementWithModifier(JMLSpecificationOnlyElementWithModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Specification Only Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Specification Only Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLSpecificationOnlyElement(JMLSpecificationOnlyElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLModelElement(JMLModelElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ghost Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ghost Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLGhostElement(JMLGhostElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Expression With Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Expression With Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLTypeExpressionWithModifier(JMLTypeExpressionWithModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLTypeExpression(JMLTypeExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Invariant Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Invariant Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLInvariantExpression(JMLInvariantExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constraint Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constraint Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLConstraintExpression(JMLConstraintExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Axiom Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Axiom Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLAxiomExpression(JMLAxiomExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLMemberModifier(JMLMemberModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Decl With Modifier</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Decl With Modifier</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMemberDeclWithModifier(MemberDeclWithModifier object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Decl With Modifier Regular</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Decl With Modifier Regular</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMemberDeclWithModifierRegular(MemberDeclWithModifierRegular object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Decl With Modifier Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Decl With Modifier Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMemberDeclWithModifierSpec(MemberDeclWithModifierSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMemberDecl(MemberDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constructor</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constructor</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConstructor(Constructor object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMemberDeclaration(MemberDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Generic Method Or Constructor Decl Old</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Generic Method Or Constructor Decl Old</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGenericMethodOrConstructorDeclOld(GenericMethodOrConstructorDeclOld object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Generic Method Or Constructor Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Generic Method Or Constructor Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGenericMethodOrConstructorDecl(GenericMethodOrConstructorDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMethodDeclaration(MethodDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFieldDeclaration(FieldDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Declared Exception</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Declared Exception</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDeclaredException(DeclaredException object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Variable Declarator</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Variable Declarator</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVariableDeclarator(VariableDeclarator object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseType(Type object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Class Or Interface Type With Brackets</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Class Or Interface Type With Brackets</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassOrInterfaceTypeWithBrackets(ClassOrInterfaceTypeWithBrackets object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Primitive Type With Brackets</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Primitive Type With Brackets</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePrimitiveTypeWithBrackets(PrimitiveTypeWithBrackets object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Brackets</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Brackets</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBrackets(Brackets object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Class Or Interface Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Class Or Interface Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassOrInterfaceType(ClassOrInterfaceType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Classifier Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Classifier Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClassifierType(ClassifierType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Arguments</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Arguments</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeArguments(TypeArguments object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Argument</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Argument</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeArgument(TypeArgument object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Formal Parameter Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Formal Parameter Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFormalParameterDecl(FormalParameterDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Method Body</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Method Body</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMethodBody(MethodBody object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constructor Body</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constructor Body</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConstructorBody(ConstructorBody object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Modifiable</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Modifiable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModifiable(Modifiable object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Typed</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Typed</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTyped(Typed object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotations</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotations</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotations(Annotations object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotation(Annotation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Element Value Pairs</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Element Value Pairs</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseElementValuePairs(ElementValuePairs object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Element Value Pair</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Element Value Pair</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseElementValuePair(ElementValuePair object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Element Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Element Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseElementValue(ElementValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Element Value Array Initializer</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Element Value Array Initializer</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseElementValueArrayInitializer(ElementValueArrayInitializer object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Type Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Type Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationTypeDeclaration(AnnotationTypeDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Type Element Declaration</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Type Element Declaration</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationTypeElementDeclaration(AnnotationTypeElementDeclaration object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Type Element Rest</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Type Element Rest</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationTypeElementRest(AnnotationTypeElementRest object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Method Or Constant Rest</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Method Or Constant Rest</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationMethodOrConstantRest(AnnotationMethodOrConstantRest object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Method Rest</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Method Rest</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationMethodRest(AnnotationMethodRest object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annotation Constant Rest</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annotation Constant Rest</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnotationConstantRest(AnnotationConstantRest object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Default Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Default Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDefaultValue(DefaultValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBlock(Block object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Block Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Block Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBlockStatement(BlockStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Identifier Having</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Identifier Having</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIdentifierHaving(IdentifierHaving object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExpression(Expression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parenthesis Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parenthesis Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParenthesisExpression(ParenthesisExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Collection Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Collection Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCollectionLiteral(CollectionLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Set Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Set Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSetLiteral(SetLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>List Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>List Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseListLiteral(ListLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Case Part</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Case Part</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCasePart(CasePart object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Valid ID</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Valid ID</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseValidID(ValidID object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Catch Clause</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Catch Clause</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCatchClause(CatchClause object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Assignment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Assignment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAssignment(Assignment object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Binary Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Binary Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBinaryOperation(BinaryOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Instance Of Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Instance Of Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInstanceOfExpression(InstanceOfExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unary Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unary Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnaryOperation(UnaryOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Postfix Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Postfix Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePostfixOperation(PostfixOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Member Feature Call</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Member Feature Call</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMemberFeatureCall(MemberFeatureCall object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Old Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Old Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLOldExpression(JMLOldExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fresh Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fresh Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLFreshExpression(JMLFreshExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Result Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Result Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLResultExpression(JMLResultExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>For All Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>For All Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJMLForAllExpression(JMLForAllExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Closure</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Closure</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClosure(Closure object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Block Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Block Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBlockExpression(BlockExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>If Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>If Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIfExpression(IfExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Switch Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Switch Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSwitchExpression(SwitchExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>For Loop Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>For Loop Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseForLoopExpression(ForLoopExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Basic For Loop Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Basic For Loop Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBasicForLoopExpression(BasicForLoopExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>While Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>While Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseWhileExpression(WhileExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Do While Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Do While Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDoWhileExpression(DoWhileExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Feature Call</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Feature Call</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFeatureCall(FeatureCall object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constructor Call</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constructor Call</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConstructorCall(ConstructorCall object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Boolean Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Boolean Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBooleanLiteral(BooleanLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Null Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Null Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNullLiteral(NullLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Number Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Number Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNumberLiteral(NumberLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>String Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>String Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStringLiteral(StringLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Char Literal</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Char Literal</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCharLiteral(CharLiteral object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Throw Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Throw Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseThrowExpression(ThrowExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Return Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Return Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReturnExpression(ReturnExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Try Catch Finally Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Try Catch Finally Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTryCatchFinallyExpression(TryCatchFinallyExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Synchronized Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Synchronized Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSynchronizedExpression(SynchronizedExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //JMLSwitch
