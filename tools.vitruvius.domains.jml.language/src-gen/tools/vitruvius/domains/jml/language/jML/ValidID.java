/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Valid ID</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ValidID#getParameterType <em>Parameter Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ValidID#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getValidID()
 * @model
 * @generated
 */
public interface ValidID extends EObject
{
  /**
   * Returns the value of the '<em><b>Parameter Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameter Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter Type</em>' attribute.
   * @see #setParameterType(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getValidID_ParameterType()
   * @model
   * @generated
   */
  String getParameterType();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ValidID#getParameterType <em>Parameter Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameter Type</em>' attribute.
   * @see #getParameterType()
   * @generated
   */
  void setParameterType(String value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getValidID_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ValidID#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // ValidID
