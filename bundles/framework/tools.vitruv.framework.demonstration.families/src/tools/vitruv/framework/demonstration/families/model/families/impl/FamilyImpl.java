/**
 */
package tools.vitruv.framework.demonstration.families.model.families.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.framework.demonstration.families.model.families.FamiliesPackage;
import tools.vitruv.framework.demonstration.families.model.families.Family;
import tools.vitruv.framework.demonstration.families.model.families.Member;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl#getFather <em>Father</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl#getMother <em>Mother</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl#getSons <em>Sons</em>}</li>
 *   <li>{@link tools.vitruv.framework.demonstration.families.model.families.impl.FamilyImpl#getDaughters <em>Daughters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FamilyImpl extends MinimalEObjectImpl.Container implements Family {
  /**
   * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLastName()
   * @generated
   * @ordered
   */
  protected static final String LAST_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLastName()
   * @generated
   * @ordered
   */
  protected String lastName = LAST_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getFather() <em>Father</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFather()
   * @generated
   * @ordered
   */
  protected Member father;

  /**
   * The cached value of the '{@link #getMother() <em>Mother</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMother()
   * @generated
   * @ordered
   */
  protected Member mother;

  /**
   * The cached value of the '{@link #getSons() <em>Sons</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSons()
   * @generated
   * @ordered
   */
  protected EList<Member> sons;

  /**
   * The cached value of the '{@link #getDaughters() <em>Daughters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDaughters()
   * @generated
   * @ordered
   */
  protected EList<Member> daughters;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FamilyImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return FamiliesPackage.Literals.FAMILY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLastName(String newLastName) {
    String oldLastName = lastName;
    lastName = newLastName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__LAST_NAME, oldLastName, lastName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Member getFather() {
    return father;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFather(Member newFather, NotificationChain msgs) {
    Member oldFather = father;
    father = newFather;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__FATHER, oldFather, newFather);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFather(Member newFather) {
    if (newFather != father) {
      NotificationChain msgs = null;
      if (father != null)
        msgs = ((InternalEObject)father).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FamiliesPackage.FAMILY__FATHER, null, msgs);
      if (newFather != null)
        msgs = ((InternalEObject)newFather).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FamiliesPackage.FAMILY__FATHER, null, msgs);
      msgs = basicSetFather(newFather, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__FATHER, newFather, newFather));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Member getMother() {
    return mother;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMother(Member newMother, NotificationChain msgs) {
    Member oldMother = mother;
    mother = newMother;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__MOTHER, oldMother, newMother);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMother(Member newMother) {
    if (newMother != mother) {
      NotificationChain msgs = null;
      if (mother != null)
        msgs = ((InternalEObject)mother).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FamiliesPackage.FAMILY__MOTHER, null, msgs);
      if (newMother != null)
        msgs = ((InternalEObject)newMother).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FamiliesPackage.FAMILY__MOTHER, null, msgs);
      msgs = basicSetMother(newMother, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__MOTHER, newMother, newMother));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Member> getSons() {
    if (sons == null) {
      sons = new EObjectContainmentEList<Member>(Member.class, this, FamiliesPackage.FAMILY__SONS);
    }
    return sons;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Member> getDaughters() {
    if (daughters == null) {
      daughters = new EObjectContainmentEList<Member>(Member.class, this, FamiliesPackage.FAMILY__DAUGHTERS);
    }
    return daughters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case FamiliesPackage.FAMILY__FATHER:
        return basicSetFather(null, msgs);
      case FamiliesPackage.FAMILY__MOTHER:
        return basicSetMother(null, msgs);
      case FamiliesPackage.FAMILY__SONS:
        return ((InternalEList<?>)getSons()).basicRemove(otherEnd, msgs);
      case FamiliesPackage.FAMILY__DAUGHTERS:
        return ((InternalEList<?>)getDaughters()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case FamiliesPackage.FAMILY__LAST_NAME:
        return getLastName();
      case FamiliesPackage.FAMILY__FATHER:
        return getFather();
      case FamiliesPackage.FAMILY__MOTHER:
        return getMother();
      case FamiliesPackage.FAMILY__SONS:
        return getSons();
      case FamiliesPackage.FAMILY__DAUGHTERS:
        return getDaughters();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case FamiliesPackage.FAMILY__LAST_NAME:
        setLastName((String)newValue);
        return;
      case FamiliesPackage.FAMILY__FATHER:
        setFather((Member)newValue);
        return;
      case FamiliesPackage.FAMILY__MOTHER:
        setMother((Member)newValue);
        return;
      case FamiliesPackage.FAMILY__SONS:
        getSons().clear();
        getSons().addAll((Collection<? extends Member>)newValue);
        return;
      case FamiliesPackage.FAMILY__DAUGHTERS:
        getDaughters().clear();
        getDaughters().addAll((Collection<? extends Member>)newValue);
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
      case FamiliesPackage.FAMILY__LAST_NAME:
        setLastName(LAST_NAME_EDEFAULT);
        return;
      case FamiliesPackage.FAMILY__FATHER:
        setFather((Member)null);
        return;
      case FamiliesPackage.FAMILY__MOTHER:
        setMother((Member)null);
        return;
      case FamiliesPackage.FAMILY__SONS:
        getSons().clear();
        return;
      case FamiliesPackage.FAMILY__DAUGHTERS:
        getDaughters().clear();
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
      case FamiliesPackage.FAMILY__LAST_NAME:
        return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
      case FamiliesPackage.FAMILY__FATHER:
        return father != null;
      case FamiliesPackage.FAMILY__MOTHER:
        return mother != null;
      case FamiliesPackage.FAMILY__SONS:
        return sons != null && !sons.isEmpty();
      case FamiliesPackage.FAMILY__DAUGHTERS:
        return daughters != null && !daughters.isEmpty();
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
    result.append(" (lastName: ");
    result.append(lastName);
    result.append(')');
    return result.toString();
  }

} //FamilyImpl
