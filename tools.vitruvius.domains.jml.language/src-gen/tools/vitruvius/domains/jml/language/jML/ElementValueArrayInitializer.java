/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Value Array Initializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getElementValueArrayInitializer()
 * @model
 * @generated
 */
public interface ElementValueArrayInitializer extends ElementValue
{
  /**
   * Returns the value of the '<em><b>Elementvalue</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ElementValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elementvalue</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elementvalue</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getElementValueArrayInitializer_Elementvalue()
   * @model containment="true"
   * @generated
   */
  EList<ElementValue> getElementvalue();

} // ElementValueArrayInitializer
