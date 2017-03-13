/**
 */
package tools.vitruv.framework.demonstration.persons.model.persons.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import tools.vitruv.framework.demonstration.persons.model.persons.Person;
import tools.vitruv.framework.demonstration.persons.model.persons.PersonsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.demonstration.persons.model.persons.impl.PersonImpl#getFullName <em>Full Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PersonImpl extends MinimalEObjectImpl.Container implements Person {
  /**
   * The default value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFullName()
   * @generated
   * @ordered
   */
  protected static final String FULL_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFullName()
   * @generated
   * @ordered
   */
  protected String fullName = FULL_NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PersonImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PersonsPackage.Literals.PERSON;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFullName(String newFullName) {
    String oldFullName = fullName;
    fullName = newFullName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PersonsPackage.PERSON__FULL_NAME, oldFullName, fullName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case PersonsPackage.PERSON__FULL_NAME:
        return getFullName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case PersonsPackage.PERSON__FULL_NAME:
        setFullName((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case PersonsPackage.PERSON__FULL_NAME:
        setFullName(FULL_NAME_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case PersonsPackage.PERSON__FULL_NAME:
        return FULL_NAME_EDEFAULT == null ? fullName != null : !FULL_NAME_EDEFAULT.equals(fullName);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (fullName: ");
    result.append(fullName);
    result.append(')');
    return result.toString();
  }

} //PersonImpl
