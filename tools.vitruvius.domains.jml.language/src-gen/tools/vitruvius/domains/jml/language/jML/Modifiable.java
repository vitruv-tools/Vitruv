/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Modifiable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Modifiable#getModifiers <em>Modifiers</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getModifiable()
 * @model
 * @generated
 */
public interface Modifiable extends EObject
{
  /**
   * Returns the value of the '<em><b>Modifiers</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.Modifier}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Modifiers</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Modifiers</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getModifiable_Modifiers()
   * @model containment="true"
   * @generated
   */
  EList<Modifier> getModifiers();

} // Modifiable
