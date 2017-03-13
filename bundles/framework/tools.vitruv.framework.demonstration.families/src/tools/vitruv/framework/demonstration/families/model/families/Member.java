/**
 */
package tools.vitruv.framework.demonstration.families.model.families;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Member</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.Member#getFirstName <em>First Name</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getMember()
 * @model
 * @generated
 */
public interface Member extends EObject {
  /**
   * Returns the value of the '<em><b>First Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>First Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>First Name</em>' attribute.
   * @see #setFirstName(String)
   * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getMember_FirstName()
   * @model
   * @generated
   */
  String getFirstName();

  /**
   * Sets the value of the '{@link tools.vitruv.framework.demonstration.families.model.families.Member#getFirstName <em>First Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>First Name</em>' attribute.
   * @see #getFirstName()
   * @generated
   */
  void setFirstName(String value);

} // Member
