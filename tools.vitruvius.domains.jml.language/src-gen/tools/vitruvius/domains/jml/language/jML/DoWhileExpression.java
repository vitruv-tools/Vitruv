/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Do While Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getBody <em>Body</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getPredicate <em>Predicate</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getDoWhileExpression()
 * @model
 * @generated
 */
public interface DoWhileExpression extends Expression
{
  /**
   * Returns the value of the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body</em>' containment reference.
   * @see #setBody(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getDoWhileExpression_Body()
   * @model containment="true"
   * @generated
   */
  Expression getBody();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getBody <em>Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Body</em>' containment reference.
   * @see #getBody()
   * @generated
   */
  void setBody(Expression value);

  /**
   * Returns the value of the '<em><b>Predicate</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Predicate</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Predicate</em>' containment reference.
   * @see #setPredicate(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getDoWhileExpression_Predicate()
   * @model containment="true"
   * @generated
   */
  Expression getPredicate();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.DoWhileExpression#getPredicate <em>Predicate</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Predicate</em>' containment reference.
   * @see #getPredicate()
   * @generated
   */
  void setPredicate(Expression value);

} // DoWhileExpression
