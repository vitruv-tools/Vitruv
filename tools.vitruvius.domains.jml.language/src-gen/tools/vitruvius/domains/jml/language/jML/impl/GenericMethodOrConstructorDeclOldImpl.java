/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Constructor;
import tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Method Or Constructor Decl Old</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclOldImpl#getConstructor <em>Constructor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericMethodOrConstructorDeclOldImpl extends MinimalEObjectImpl.Container implements GenericMethodOrConstructorDeclOld
{
  /**
   * The cached value of the '{@link #getConstructor() <em>Constructor</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstructor()
   * @generated
   * @ordered
   */
  protected Constructor constructor;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GenericMethodOrConstructorDeclOldImpl()
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
    return JMLPackage.Literals.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Constructor getConstructor()
  {
    return constructor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstructor(Constructor newConstructor, NotificationChain msgs)
  {
    Constructor oldConstructor = constructor;
    constructor = newConstructor;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR, oldConstructor, newConstructor);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstructor(Constructor newConstructor)
  {
    if (newConstructor != constructor)
    {
      NotificationChain msgs = null;
      if (constructor != null)
        msgs = ((InternalEObject)constructor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR, null, msgs);
      if (newConstructor != null)
        msgs = ((InternalEObject)newConstructor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR, null, msgs);
      msgs = basicSetConstructor(newConstructor, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR, newConstructor, newConstructor));
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR:
        return basicSetConstructor(null, msgs);
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR:
        return getConstructor();
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR:
        setConstructor((Constructor)newValue);
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR:
        setConstructor((Constructor)null);
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL_OLD__CONSTRUCTOR:
        return constructor != null;
    }
    return super.eIsSet(featureID);
  }

} //GenericMethodOrConstructorDeclOldImpl
