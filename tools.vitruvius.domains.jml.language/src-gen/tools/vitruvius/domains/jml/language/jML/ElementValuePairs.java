/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Value Pairs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ElementValuePairs#getElementvaluepair <em>Elementvaluepair</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getElementValuePairs()
 * @model
 * @generated
 */
public interface ElementValuePairs extends EObject
{
  /**
   * Returns the value of the '<em><b>Elementvaluepair</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ElementValuePair}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elementvaluepair</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elementvaluepair</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getElementValuePairs_Elementvaluepair()
   * @model containment="true"
   * @generated
   */
  EList<ElementValuePair> getElementvaluepair();

} // ElementValuePairs
