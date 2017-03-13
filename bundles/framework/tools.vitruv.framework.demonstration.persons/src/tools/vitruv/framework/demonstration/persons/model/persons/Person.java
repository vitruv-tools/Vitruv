/**
 */
package tools.vitruv.framework.demonstration.persons.model.persons;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.demonstration.persons.model.persons.Person#getFullName <em>Full Name</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.demonstration.persons.model.persons.PersonsPackage#getPerson()
 * @model abstract="true"
 * @generated
 */
public interface Person extends EObject {

  /**
   * Returns the value of the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Full Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Full Name</em>' attribute.
   * @see #setFullName(String)
   * @see tools.vitruv.framework.demonstration.persons.model.persons.PersonsPackage#getPerson_FullName()
   * @model
   * @generated
   */
  String getFullName();

  /**
   * Sets the value of the '{@link tools.vitruv.framework.demonstration.persons.model.persons.Person#getFullName <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Full Name</em>' attribute.
   * @see #getFullName()
   * @generated
   */
  void setFullName(String value);
} // Person
