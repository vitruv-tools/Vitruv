/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Member Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getMethod <em>Method</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getField <em>Field</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberDeclaration()
 * @model
 * @generated
 */
public interface MemberDeclaration extends MemberDecl, Typed
{
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberDeclaration_Method()
   * @model containment="true"
   * @generated
   */
  MethodDeclaration getMethod();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getMethod <em>Method</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Method</em>' containment reference.
   * @see #getMethod()
   * @generated
   */
  void setMethod(MethodDeclaration value);

  /**
   * Returns the value of the '<em><b>Field</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field</em>' containment reference.
   * @see #setField(FieldDeclaration)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMemberDeclaration_Field()
   * @model containment="true"
   * @generated
   */
  FieldDeclaration getField();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MemberDeclaration#getField <em>Field</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' containment reference.
   * @see #getField()
   * @generated
   */
  void setField(FieldDeclaration value);

} // MemberDeclaration
