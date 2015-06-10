/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bundle</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.BundleImpl#getBundleFQN <em>Bundle FQN</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BundleImpl extends MinimalEObjectImpl.Container implements Bundle
{
  /**
   * The default value of the '{@link #getBundleFQN() <em>Bundle FQN</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBundleFQN()
   * @generated
   * @ordered
   */
  protected static final String BUNDLE_FQN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBundleFQN() <em>Bundle FQN</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBundleFQN()
   * @generated
   * @ordered
   */
  protected String bundleFQN = BUNDLE_FQN_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BundleImpl()
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
    return MIRPackage.Literals.BUNDLE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getBundleFQN()
  {
    return bundleFQN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBundleFQN(String newBundleFQN)
  {
    String oldBundleFQN = bundleFQN;
    bundleFQN = newBundleFQN;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.BUNDLE__BUNDLE_FQN, oldBundleFQN, bundleFQN));
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
      case MIRPackage.BUNDLE__BUNDLE_FQN:
        return getBundleFQN();
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
      case MIRPackage.BUNDLE__BUNDLE_FQN:
        setBundleFQN((String)newValue);
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
      case MIRPackage.BUNDLE__BUNDLE_FQN:
        setBundleFQN(BUNDLE_FQN_EDEFAULT);
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
      case MIRPackage.BUNDLE__BUNDLE_FQN:
        return BUNDLE_FQN_EDEFAULT == null ? bundleFQN != null : !BUNDLE_FQN_EDEFAULT.equals(bundleFQN);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (bundleFQN: ");
    result.append(bundleFQN);
    result.append(')');
    return result.toString();
  }

} //BundleImpl
