/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Default Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.DefaultValue#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getDefaultValue()
 * @model
 * @generated
 */
public interface DefaultValue extends EObject
{
  /**
   * Returns the value of the '<em><b>Elementvalue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elementvalue</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elementvalue</em>' containment reference.
   * @see #setElementvalue(ElementValue)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getDefaultValue_Elementvalue()
   * @model containment="true"
   * @generated
   */
  ElementValue getElementvalue();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.DefaultValue#getElementvalue <em>Elementvalue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Elementvalue</em>' containment reference.
   * @see #getElementvalue()
   * @generated
   */
  void setElementvalue(ElementValue value);

} // DefaultValue
