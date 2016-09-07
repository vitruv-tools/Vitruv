/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Closure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Closure#getDeclaredFormalParameters <em>Declared Formal Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Closure#isExplicitSyntax <em>Explicit Syntax</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Closure#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getClosure()
 * @model
 * @generated
 */
public interface Closure extends Expression
{
  /**
   * Returns the value of the '<em><b>Declared Formal Parameters</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ValidID}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Declared Formal Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Declared Formal Parameters</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getClosure_DeclaredFormalParameters()
   * @model containment="true"
   * @generated
   */
  EList<ValidID> getDeclaredFormalParameters();

  /**
   * Returns the value of the '<em><b>Explicit Syntax</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Explicit Syntax</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Explicit Syntax</em>' attribute.
   * @see #setExplicitSyntax(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getClosure_ExplicitSyntax()
   * @model
   * @generated
   */
  boolean isExplicitSyntax();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Closure#isExplicitSyntax <em>Explicit Syntax</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Explicit Syntax</em>' attribute.
   * @see #isExplicitSyntax()
   * @generated
   */
  void setExplicitSyntax(boolean value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getClosure_Expression()
   * @model containment="true"
   * @generated
   */
  Expression getExpression();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Closure#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(Expression value);

} // Closure
