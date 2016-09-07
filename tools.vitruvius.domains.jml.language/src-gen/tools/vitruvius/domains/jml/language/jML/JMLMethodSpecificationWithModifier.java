/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Specification With Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getModifier <em>Modifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getSpec <em>Spec</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMethodSpecificationWithModifier()
 * @model
 * @generated
 */
public interface JMLMethodSpecificationWithModifier extends EObject
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMethodSpecificationWithModifier_Modifier()
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
   * @see #setSpec(JMLMethodSpecification)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMethodSpecificationWithModifier_Spec()
   * @model containment="true"
   * @generated
   */
  JMLMethodSpecification getSpec();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier#getSpec <em>Spec</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Spec</em>' containment reference.
   * @see #getSpec()
   * @generated
   */
  void setSpec(JMLMethodSpecification value);

} // JMLMethodSpecificationWithModifier
