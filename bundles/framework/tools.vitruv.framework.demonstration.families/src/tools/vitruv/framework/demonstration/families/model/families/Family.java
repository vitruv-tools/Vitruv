/**
 */
package tools.vitruv.framework.demonstration.families.model.families;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.Family#getLastName <em>Last Name</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.Family#getFather <em>Father</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.Family#getMother <em>Mother</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.Family#getSons <em>Sons</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.Family#getDaughters <em>Daughters</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getFamily()
 * @model
 * @generated
 */
public interface Family extends EObject {
  /**
   * Returns the value of the '<em><b>Last Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Last Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Last Name</em>' attribute.
   * @see #setLastName(String)
   * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getFamily_LastName()
   * @model
   * @generated
   */
  String getLastName();

  /**
   * Sets the value of the '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getLastName <em>Last Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Last Name</em>' attribute.
   * @see #getLastName()
   * @generated
   */
  void setLastName(String value);

  /**
   * Returns the value of the '<em><b>Father</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Father</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Father</em>' containment reference.
   * @see #setFather(Member)
   * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getFamily_Father()
   * @model containment="true" required="true"
   * @generated
   */
  Member getFather();

  /**
   * Sets the value of the '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getFather <em>Father</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Father</em>' containment reference.
   * @see #getFather()
   * @generated
   */
  void setFather(Member value);

  /**
   * Returns the value of the '<em><b>Mother</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mother</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mother</em>' containment reference.
   * @see #setMother(Member)
   * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getFamily_Mother()
   * @model containment="true" required="true"
   * @generated
   */
  Member getMother();

  /**
   * Sets the value of the '{@link tools.vitruv.framework.demonstration.families.model.families.Family#getMother <em>Mother</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mother</em>' containment reference.
   * @see #getMother()
   * @generated
   */
  void setMother(Member value);

  /**
   * Returns the value of the '<em><b>Sons</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruv.framework.demonstration.families.model.families.Member}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sons</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sons</em>' containment reference list.
   * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getFamily_Sons()
   * @model containment="true"
   * @generated
   */
  EList<Member> getSons();

  /**
   * Returns the value of the '<em><b>Daughters</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruv.framework.demonstration.families.model.families.Member}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Daughters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Daughters</em>' containment reference list.
   * @see tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage#getFamily_Daughters()
   * @model containment="true"
   * @generated
   */
  EList<Member> getDaughters();

} // Family
