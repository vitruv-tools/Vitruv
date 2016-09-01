/**
 */
package edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visiblity Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VisiblityModifier#getModifier <em>Modifier</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getVisiblityModifier()
 * @model
 * @generated
 */
public interface VisiblityModifier extends EObject
{
  /**
   * Returns the value of the '<em><b>Modifier</b></em>' attribute.
   * The literals are from the enumeration {@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VisibilityModifierValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modifier</em>' attribute.
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VisibilityModifierValue
   * @see #setModifier(VisibilityModifierValue)
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getVisiblityModifier_Modifier()
   * @model
   * @generated
   */
  VisibilityModifierValue getModifier();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VisiblityModifier#getModifier <em>Modifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Modifier</em>' attribute.
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VisibilityModifierValue
   * @see #getModifier()
   * @generated
   */
  void setModifier(VisibilityModifierValue value);

} // VisiblityModifier
