/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specification Only Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#isInstance <em>Instance</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#getElement <em>Element</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLSpecificationOnlyElement()
 * @model
 * @generated
 */
public interface JMLSpecificationOnlyElement extends EObject
{
  /**
   * Returns the value of the '<em><b>Instance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Instance</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Instance</em>' attribute.
   * @see #setInstance(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLSpecificationOnlyElement_Instance()
   * @model
   * @generated
   */
  boolean isInstance();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#isInstance <em>Instance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Instance</em>' attribute.
   * @see #isInstance()
   * @generated
   */
  void setInstance(boolean value);

  /**
   * Returns the value of the '<em><b>Element</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Element</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Element</em>' containment reference.
   * @see #setElement(MemberDeclWithModifierSpec)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLSpecificationOnlyElement_Element()
   * @model containment="true"
   * @generated
   */
  MemberDeclWithModifierSpec getElement();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement#getElement <em>Element</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Element</em>' containment reference.
   * @see #getElement()
   * @generated
   */
  void setElement(MemberDeclWithModifierSpec value);

} // JMLSpecificationOnlyElement
