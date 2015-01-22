/**
 */
package edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Regular Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.RegularModifier#getModifier <em>Modifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage#getRegularModifier()
 * @model
 * @generated
 */
public interface RegularModifier extends Modifier
{
  /**
   * Returns the value of the '<em><b>Modifier</b></em>' attribute.
   * The literals are from the enumeration {@link edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ModifierValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modifier</em>' attribute.
   * @see edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ModifierValue
   * @see #setModifier(ModifierValue)
   * @see edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage#getRegularModifier_Modifier()
   * @model
   * @generated
   */
  ModifierValue getModifier();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.RegularModifier#getModifier <em>Modifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Modifier</em>' attribute.
   * @see edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ModifierValue
   * @see #getModifier()
   * @generated
   */
  void setModifier(ModifierValue value);

} // RegularModifier
