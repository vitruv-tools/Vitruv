/**
 */
package edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Value Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ElementValuePair#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ElementValuePair#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getElementValuePair()
 * @model
 * @generated
 */
public interface ElementValuePair extends EObject
{
  /**
   * Returns the value of the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Identifier</em>' attribute.
   * @see #setIdentifier(String)
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getElementValuePair_Identifier()
   * @model
   * @generated
   */
  String getIdentifier();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ElementValuePair#getIdentifier <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Identifier</em>' attribute.
   * @see #getIdentifier()
   * @generated
   */
  void setIdentifier(String value);

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
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getElementValuePair_Elementvalue()
   * @model containment="true"
   * @generated
   */
  ElementValue getElementvalue();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ElementValuePair#getElementvalue <em>Elementvalue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Elementvalue</em>' containment reference.
   * @see #getElementvalue()
   * @generated
   */
  void setElementvalue(ElementValue value);

} // ElementValuePair
