/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.DefaultValue;
import tools.vitruvius.domains.jml.language.jML.ElementValue;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Default Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.DefaultValueImpl#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DefaultValueImpl extends MinimalEObjectImpl.Container implements DefaultValue
{
  /**
   * The cached value of the '{@link #getElementvalue() <em>Elementvalue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElementvalue()
   * @generated
   * @ordered
   */
  protected ElementValue elementvalue;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DefaultValueImpl()
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
    return JMLPackage.Literals.DEFAULT_VALUE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementValue getElementvalue()
  {
    return elementvalue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElementvalue(ElementValue newElementvalue, NotificationChain msgs)
  {
    ElementValue oldElementvalue = elementvalue;
    elementvalue = newElementvalue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.DEFAULT_VALUE__ELEMENTVALUE, oldElementvalue, newElementvalue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElementvalue(ElementValue newElementvalue)
  {
    if (newElementvalue != elementvalue)
    {
      NotificationChain msgs = null;
      if (elementvalue != null)
        msgs = ((InternalEObject)elementvalue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.DEFAULT_VALUE__ELEMENTVALUE, null, msgs);
      if (newElementvalue != null)
        msgs = ((InternalEObject)newElementvalue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.DEFAULT_VALUE__ELEMENTVALUE, null, msgs);
      msgs = basicSetElementvalue(newElementvalue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.DEFAULT_VALUE__ELEMENTVALUE, newElementvalue, newElementvalue));
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
      case JMLPackage.DEFAULT_VALUE__ELEMENTVALUE:
        return basicSetElementvalue(null, msgs);
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
      case JMLPackage.DEFAULT_VALUE__ELEMENTVALUE:
        return getElementvalue();
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
      case JMLPackage.DEFAULT_VALUE__ELEMENTVALUE:
        setElementvalue((ElementValue)newValue);
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
      case JMLPackage.DEFAULT_VALUE__ELEMENTVALUE:
        setElementvalue((ElementValue)null);
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
      case JMLPackage.DEFAULT_VALUE__ELEMENTVALUE:
        return elementvalue != null;
    }
    return super.eIsSet(featureID);
  }

} //DefaultValueImpl
