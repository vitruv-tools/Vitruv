/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fresh Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLFreshExpression#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLFreshExpression()
 * @model
 * @generated
 */
public interface JMLFreshExpression extends Expression
{
  /**
   * Returns the value of the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' containment reference.
   * @see #setExpr(ParenthesisExpression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLFreshExpression_Expr()
   * @model containment="true"
   * @generated
   */
  ParenthesisExpression getExpr();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLFreshExpression#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(ParenthesisExpression value);

} // JMLFreshExpression
