/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Expression With Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getModifier <em>Modifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getSpec <em>Spec</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLTypeExpressionWithModifier()
 * @model
 * @generated
 */
public interface JMLTypeExpressionWithModifier extends EObject
{
  /**
   * Returns the value of the '<em><b>Modifier</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.VisiblityModifier}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modifier</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modifier</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLTypeExpressionWithModifier_Modifier()
   * @model containment="true"
   * @generated
   */
  EList<VisiblityModifier> getModifier();

  /**
   * Returns the value of the '<em><b>Spec</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Spec</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Spec</em>' containment reference.
   * @see #setSpec(JMLTypeExpression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLTypeExpressionWithModifier_Spec()
   * @model containment="true"
   * @generated
   */
  JMLTypeExpression getSpec();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier#getSpec <em>Spec</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Spec</em>' containment reference.
   * @see #getSpec()
   * @generated
   */
  void setSpec(JMLTypeExpression value);

} // JMLTypeExpressionWithModifier
