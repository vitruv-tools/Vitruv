/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Regular Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.RegularModifier#getModifier <em>Modifier</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getRegularModifier()
 * @model
 * @generated
 */
public interface RegularModifier extends Modifier
{
  /**
   * Returns the value of the '<em><b>Modifier</b></em>' attribute.
   * The literals are from the enumeration {@link tools.vitruvius.domains.jml.language.jML.ModifierValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modifier</em>' attribute.
   * @see tools.vitruvius.domains.jml.language.jML.ModifierValue
   * @see #setModifier(ModifierValue)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getRegularModifier_Modifier()
   * @model
   * @generated
   */
  ModifierValue getModifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.RegularModifier#getModifier <em>Modifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Modifier</em>' attribute.
   * @see tools.vitruvius.domains.jml.language.jML.ModifierValue
   * @see #getModifier()
   * @generated
   */
  void setModifier(ModifierValue value);

} // RegularModifier
