/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named EClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedEClassImpl#getRepresentedEClass <em>Represented EClass</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamedEClassImpl extends MappableElementImpl implements NamedEClass
{
  /**
   * The cached value of the '{@link #getRepresentedEClass() <em>Represented EClass</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRepresentedEClass()
   * @generated
   * @ordered
   */
  protected EClass representedEClass;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NamedEClassImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return MIRPackage.Literals.NAMED_ECLASS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRepresentedEClass()
  {
    if (representedEClass != null && representedEClass.eIsProxy())
    {
      InternalEObject oldRepresentedEClass = (InternalEObject)representedEClass;
      representedEClass = (EClass)eResolveProxy(oldRepresentedEClass);
      if (representedEClass != oldRepresentedEClass)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRPackage.NAMED_ECLASS__REPRESENTED_ECLASS, oldRepresentedEClass, representedEClass));
      }
    }
    return representedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass basicGetRepresentedEClass()
  {
    return representedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRepresentedEClass(EClass newRepresentedEClass)
  {
    EClass oldRepresentedEClass = representedEClass;
    representedEClass = newRepresentedEClass;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.NAMED_ECLASS__REPRESENTED_ECLASS, oldRepresentedEClass, representedEClass));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case MIRPackage.NAMED_ECLASS__REPRESENTED_ECLASS:
        if (resolve) return getRepresentedEClass();
        return basicGetRepresentedEClass();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case MIRPackage.NAMED_ECLASS__REPRESENTED_ECLASS:
        setRepresentedEClass((EClass)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case MIRPackage.NAMED_ECLASS__REPRESENTED_ECLASS:
        setRepresentedEClass((EClass)null);
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case MIRPackage.NAMED_ECLASS__REPRESENTED_ECLASS:
        return representedEClass != null;
    }
    return super.eIsSet(featureID);
  }

} //NamedEClassImpl
