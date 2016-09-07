/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CasePart#getTypeGuard <em>Type Guard</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CasePart#getCase <em>Case</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CasePart#getThen <em>Then</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCasePart()
 * @model
 * @generated
 */
public interface CasePart extends EObject
{
  /**
   * Returns the value of the '<em><b>Type Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type Guard</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type Guard</em>' containment reference.
   * @see #setTypeGuard(JvmTypeReference)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCasePart_TypeGuard()
   * @model containment="true"
   * @generated
   */
  JvmTypeReference getTypeGuard();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CasePart#getTypeGuard <em>Type Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type Guard</em>' containment reference.
   * @see #getTypeGuard()
   * @generated
   */
  void setTypeGuard(JvmTypeReference value);

  /**
   * Returns the value of the '<em><b>Case</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Case</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Case</em>' containment reference.
   * @see #setCase(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCasePart_Case()
   * @model containment="true"
   * @generated
   */
  Expression getCase();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CasePart#getCase <em>Case</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Case</em>' containment reference.
   * @see #getCase()
   * @generated
   */
  void setCase(Expression value);

  /**
   * Returns the value of the '<em><b>Then</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Then</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Then</em>' containment reference.
   * @see #setThen(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCasePart_Then()
   * @model containment="true"
   * @generated
   */
  Expression getThen();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CasePart#getThen <em>Then</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Then</em>' containment reference.
   * @see #getThen()
   * @generated
   */
  void setThen(Expression value);

} // CasePart
