/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>For Loop Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getDeclaredParam <em>Declared Param</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getForExpression <em>For Expression</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getEachExpression <em>Each Expression</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getForLoopExpression()
 * @model
 * @generated
 */
public interface ForLoopExpression extends Expression
{
  /**
   * Returns the value of the '<em><b>Declared Param</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Declared Param</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Declared Param</em>' containment reference.
   * @see #setDeclaredParam(ValidID)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getForLoopExpression_DeclaredParam()
   * @model containment="true"
   * @generated
   */
  ValidID getDeclaredParam();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getDeclaredParam <em>Declared Param</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Declared Param</em>' containment reference.
   * @see #getDeclaredParam()
   * @generated
   */
  void setDeclaredParam(ValidID value);

  /**
   * Returns the value of the '<em><b>For Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>For Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>For Expression</em>' containment reference.
   * @see #setForExpression(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getForLoopExpression_ForExpression()
   * @model containment="true"
   * @generated
   */
  Expression getForExpression();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getForExpression <em>For Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>For Expression</em>' containment reference.
   * @see #getForExpression()
   * @generated
   */
  void setForExpression(Expression value);

  /**
   * Returns the value of the '<em><b>Each Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Each Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Each Expression</em>' containment reference.
   * @see #setEachExpression(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getForLoopExpression_EachExpression()
   * @model containment="true"
   * @generated
   */
  Expression getEachExpression();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ForLoopExpression#getEachExpression <em>Each Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Each Expression</em>' containment reference.
   * @see #getEachExpression()
   * @generated
   */
  void setEachExpression(Expression value);

} // ForLoopExpression
