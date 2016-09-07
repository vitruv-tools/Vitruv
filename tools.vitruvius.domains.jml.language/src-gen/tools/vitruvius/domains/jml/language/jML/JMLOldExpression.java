/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Old Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLOldExpression#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLOldExpression()
 * @model
 * @generated
 */
public interface JMLOldExpression extends Expression
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLOldExpression_Expr()
   * @model containment="true"
   * @generated
   */
  ParenthesisExpression getExpr();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLOldExpression#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(ParenthesisExpression value);

} // JMLOldExpression
