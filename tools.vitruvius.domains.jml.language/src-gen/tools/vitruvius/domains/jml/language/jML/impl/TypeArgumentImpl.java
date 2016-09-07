/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.TypeArgument;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Argument</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl#isExtends <em>Extends</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentImpl#isSuper <em>Super</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeArgumentImpl extends MinimalEObjectImpl.Container implements TypeArgument
{
  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected Type type;

  /**
   * The default value of the '{@link #isWildcard() <em>Wildcard</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isWildcard()
   * @generated
   * @ordered
   */
  protected static final boolean WILDCARD_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isWildcard() <em>Wildcard</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isWildcard()
   * @generated
   * @ordered
   */
  protected boolean wildcard = WILDCARD_EDEFAULT;

  /**
   * The default value of the '{@link #isExtends() <em>Extends</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExtends()
   * @generated
   * @ordered
   */
  protected static final boolean EXTENDS_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExtends() <em>Extends</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExtends()
   * @generated
   * @ordered
   */
  protected boolean extends_ = EXTENDS_EDEFAULT;

  /**
   * The default value of the '{@link #isSuper() <em>Super</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSuper()
   * @generated
   * @ordered
   */
  protected static final boolean SUPER_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isSuper() <em>Super</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSuper()
   * @generated
   * @ordered
   */
  protected boolean super_ = SUPER_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TypeArgumentImpl()
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
    return JMLPackage.Literals.TYPE_ARGUMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type getType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetType(Type newType, NotificationChain msgs)
  {
    Type oldType = type;
    type = newType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_ARGUMENT__TYPE, oldType, newType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(Type newType)
  {
    if (newType != type)
    {
      NotificationChain msgs = null;
      if (type != null)
        msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TYPE_ARGUMENT__TYPE, null, msgs);
      if (newType != null)
        msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TYPE_ARGUMENT__TYPE, null, msgs);
      msgs = basicSetType(newType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_ARGUMENT__TYPE, newType, newType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isWildcard()
  {
    return wildcard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWildcard(boolean newWildcard)
  {
    boolean oldWildcard = wildcard;
    wildcard = newWildcard;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_ARGUMENT__WILDCARD, oldWildcard, wildcard));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isExtends()
  {
    return extends_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExtends(boolean newExtends)
  {
    boolean oldExtends = extends_;
    extends_ = newExtends;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_ARGUMENT__EXTENDS, oldExtends, extends_));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSuper()
  {
    return super_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSuper(boolean newSuper)
  {
    boolean oldSuper = super_;
    super_ = newSuper;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TYPE_ARGUMENT__SUPER, oldSuper, super_));
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
      case JMLPackage.TYPE_ARGUMENT__TYPE:
        return basicSetType(null, msgs);
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
      case JMLPackage.TYPE_ARGUMENT__TYPE:
        return getType();
      case JMLPackage.TYPE_ARGUMENT__WILDCARD:
        return isWildcard();
      case JMLPackage.TYPE_ARGUMENT__EXTENDS:
        return isExtends();
      case JMLPackage.TYPE_ARGUMENT__SUPER:
        return isSuper();
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
      case JMLPackage.TYPE_ARGUMENT__TYPE:
        setType((Type)newValue);
        return;
      case JMLPackage.TYPE_ARGUMENT__WILDCARD:
        setWildcard((Boolean)newValue);
        return;
      case JMLPackage.TYPE_ARGUMENT__EXTENDS:
        setExtends((Boolean)newValue);
        return;
      case JMLPackage.TYPE_ARGUMENT__SUPER:
        setSuper((Boolean)newValue);
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
      case JMLPackage.TYPE_ARGUMENT__TYPE:
        setType((Type)null);
        return;
      case JMLPackage.TYPE_ARGUMENT__WILDCARD:
        setWildcard(WILDCARD_EDEFAULT);
        return;
      case JMLPackage.TYPE_ARGUMENT__EXTENDS:
        setExtends(EXTENDS_EDEFAULT);
        return;
      case JMLPackage.TYPE_ARGUMENT__SUPER:
        setSuper(SUPER_EDEFAULT);
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
      case JMLPackage.TYPE_ARGUMENT__TYPE:
        return type != null;
      case JMLPackage.TYPE_ARGUMENT__WILDCARD:
        return wildcard != WILDCARD_EDEFAULT;
      case JMLPackage.TYPE_ARGUMENT__EXTENDS:
        return extends_ != EXTENDS_EDEFAULT;
      case JMLPackage.TYPE_ARGUMENT__SUPER:
        return super_ != SUPER_EDEFAULT;
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
    result.append(" (wildcard: ");
    result.append(wildcard);
    result.append(", extends: ");
    result.append(extends_);
    result.append(", super: ");
    result.append(super_);
    result.append(')');
    return result.toString();
  }

} //TypeArgumentImpl
