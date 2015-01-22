/**
 */
package edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Value Pairs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ElementValuePairs#getElementvaluepair <em>Elementvaluepair</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage#getElementValuePairs()
 * @model
 * @generated
 */
public interface ElementValuePairs extends EObject
{
  /**
   * Returns the value of the '<em><b>Elementvaluepair</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.ElementValuePair}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elementvaluepair</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elementvaluepair</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage#getElementValuePairs_Elementvaluepair()
   * @model containment="true"
   * @generated
   */
  EList<ElementValuePair> getElementvaluepair();

} // ElementValuePairs
