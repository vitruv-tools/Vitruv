/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Catch Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CatchClause#getDeclaredParam <em>Declared Param</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CatchClause#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCatchClause()
 * @model
 * @generated
 */
public interface CatchClause extends EObject
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCatchClause_DeclaredParam()
   * @model containment="true"
   * @generated
   */
  ValidID getDeclaredParam();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CatchClause#getDeclaredParam <em>Declared Param</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Declared Param</em>' containment reference.
   * @see #getDeclaredParam()
   * @generated
   */
  void setDeclaredParam(ValidID value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCatchClause_Expression()
   * @model containment="true"
   * @generated
   */
  Expression getExpression();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CatchClause#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(Expression value);

} // CatchClause
