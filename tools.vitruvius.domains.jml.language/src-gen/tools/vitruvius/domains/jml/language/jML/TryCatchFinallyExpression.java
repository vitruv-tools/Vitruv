/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Try Catch Finally Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getExpression <em>Expression</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getCatchClauses <em>Catch Clauses</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getFinallyExpression <em>Finally Expression</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTryCatchFinallyExpression()
 * @model
 * @generated
 */
public interface TryCatchFinallyExpression extends Expression
{
  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTryCatchFinallyExpression_Expression()
   * @model containment="true"
   * @generated
   */
  Expression getExpression();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(Expression value);

  /**
   * Returns the value of the '<em><b>Catch Clauses</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.CatchClause}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Catch Clauses</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Catch Clauses</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTryCatchFinallyExpression_CatchClauses()
   * @model containment="true"
   * @generated
   */
  EList<CatchClause> getCatchClauses();

  /**
   * Returns the value of the '<em><b>Finally Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Finally Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Finally Expression</em>' containment reference.
   * @see #setFinallyExpression(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTryCatchFinallyExpression_FinallyExpression()
   * @model containment="true"
   * @generated
   */
  Expression getFinallyExpression();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression#getFinallyExpression <em>Finally Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Finally Expression</em>' containment reference.
   * @see #getFinallyExpression()
   * @generated
   */
  void setFinallyExpression(Expression value);

} // TryCatchFinallyExpression
