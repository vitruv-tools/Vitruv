/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Method Or Constructor Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getTypeParameters <em>Type Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getMethod <em>Method</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getConstructor <em>Constructor</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDecl()
 * @model
 * @generated
 */
public interface GenericMethodOrConstructorDecl extends MemberDecl
{
  /**
   * Returns the value of the '<em><b>Type Parameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type Parameters</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type Parameters</em>' containment reference.
   * @see #setTypeParameters(TypeParameters)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDecl_TypeParameters()
   * @model containment="true"
   * @generated
   */
  TypeParameters getTypeParameters();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getTypeParameters <em>Type Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type Parameters</em>' containment reference.
   * @see #getTypeParameters()
   * @generated
   */
  void setTypeParameters(TypeParameters value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(Type)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDecl_Type()
   * @model containment="true"
   * @generated
   */
  Type getType();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(Type value);

  /**
   * Returns the value of the '<em><b>Method</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Method</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Method</em>' containment reference.
   * @see #setMethod(MethodDeclaration)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDecl_Method()
   * @model containment="true"
   * @generated
   */
  MethodDeclaration getMethod();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getMethod <em>Method</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Method</em>' containment reference.
   * @see #getMethod()
   * @generated
   */
  void setMethod(MethodDeclaration value);

  /**
   * Returns the value of the '<em><b>Constructor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constructor</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constructor</em>' containment reference.
   * @see #setConstructor(Constructor)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDecl_Constructor()
   * @model containment="true"
   * @generated
   */
  Constructor getConstructor();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl#getConstructor <em>Constructor</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constructor</em>' containment reference.
   * @see #getConstructor()
   * @generated
   */
  void setConstructor(Constructor value);

} // GenericMethodOrConstructorDecl
