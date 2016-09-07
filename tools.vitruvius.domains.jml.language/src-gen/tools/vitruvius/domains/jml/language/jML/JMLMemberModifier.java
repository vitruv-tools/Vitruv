/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Member Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLMemberModifier#getModifier <em>Modifier</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMemberModifier()
 * @model
 * @generated
 */
public interface JMLMemberModifier extends EObject
{
  /**
   * Returns the value of the '<em><b>Modifier</b></em>' attribute.
   * The literals are from the enumeration {@link tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modifier</em>' attribute.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
   * @see #setModifier(JMLSpecMemberModifier)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMemberModifier_Modifier()
   * @model
   * @generated
   */
  JMLSpecMemberModifier getModifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLMemberModifier#getModifier <em>Modifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Modifier</em>' attribute.
   * @see tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
   * @see #getModifier()
   * @generated
   */
  void setModifier(JMLSpecMemberModifier value);

} // JMLMemberModifier
