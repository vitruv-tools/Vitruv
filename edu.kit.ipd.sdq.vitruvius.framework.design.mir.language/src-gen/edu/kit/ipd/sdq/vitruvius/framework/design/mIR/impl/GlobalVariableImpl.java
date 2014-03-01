/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.GlobalVariableImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.GlobalVariableImpl#getJvmTypeReference <em>Jvm Type Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalVariableImpl extends MinimalEObjectImpl.Container implements GlobalVariable
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getJvmTypeReference() <em>Jvm Type Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJvmTypeReference()
   * @generated
   * @ordered
   */
  protected JvmTypeReference jvmTypeReference;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GlobalVariableImpl()
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
    return MIRPackage.Literals.GLOBAL_VARIABLE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.GLOBAL_VARIABLE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JvmTypeReference getJvmTypeReference()
  {
    return jvmTypeReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetJvmTypeReference(JvmTypeReference newJvmTypeReference, NotificationChain msgs)
  {
    JvmTypeReference oldJvmTypeReference = jvmTypeReference;
    jvmTypeReference = newJvmTypeReference;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE, oldJvmTypeReference, newJvmTypeReference);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJvmTypeReference(JvmTypeReference newJvmTypeReference)
  {
    if (newJvmTypeReference != jvmTypeReference)
    {
      NotificationChain msgs = null;
      if (jvmTypeReference != null)
        msgs = ((InternalEObject)jvmTypeReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE, null, msgs);
      if (newJvmTypeReference != null)
        msgs = ((InternalEObject)newJvmTypeReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE, null, msgs);
      msgs = basicSetJvmTypeReference(newJvmTypeReference, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE, newJvmTypeReference, newJvmTypeReference));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE:
        return basicSetJvmTypeReference(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case MIRPackage.GLOBAL_VARIABLE__NAME:
        return getName();
      case MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE:
        return getJvmTypeReference();
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
      case MIRPackage.GLOBAL_VARIABLE__NAME:
        setName((String)newValue);
        return;
      case MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE:
        setJvmTypeReference((JvmTypeReference)newValue);
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
      case MIRPackage.GLOBAL_VARIABLE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE:
        setJvmTypeReference((JvmTypeReference)null);
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
      case MIRPackage.GLOBAL_VARIABLE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case MIRPackage.GLOBAL_VARIABLE__JVM_TYPE_REFERENCE:
        return jvmTypeReference != null;
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
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //GlobalVariableImpl
