/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.FieldDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.Typed;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Member Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberDeclarationImpl#getField <em>Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MemberDeclarationImpl extends MemberDeclImpl implements MemberDeclaration
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
   * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMethod()
   * @generated
   * @ordered
   */
  protected MethodDeclaration method;

  /**
   * The cached value of the '{@link #getField() <em>Field</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getField()
   * @generated
   * @ordered
   */
  protected FieldDeclaration field;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MemberDeclarationImpl()
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
    return JMLPackage.Literals.MEMBER_DECLARATION;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECLARATION__TYPE, oldType, newType);
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
        msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECLARATION__TYPE, null, msgs);
      if (newType != null)
        msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECLARATION__TYPE, null, msgs);
      msgs = basicSetType(newType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECLARATION__TYPE, newType, newType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MethodDeclaration getMethod()
  {
    return method;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMethod(MethodDeclaration newMethod, NotificationChain msgs)
  {
    MethodDeclaration oldMethod = method;
    method = newMethod;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECLARATION__METHOD, oldMethod, newMethod);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMethod(MethodDeclaration newMethod)
  {
    if (newMethod != method)
    {
      NotificationChain msgs = null;
      if (method != null)
        msgs = ((InternalEObject)method).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECLARATION__METHOD, null, msgs);
      if (newMethod != null)
        msgs = ((InternalEObject)newMethod).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECLARATION__METHOD, null, msgs);
      msgs = basicSetMethod(newMethod, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECLARATION__METHOD, newMethod, newMethod));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldDeclaration getField()
  {
    return field;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetField(FieldDeclaration newField, NotificationChain msgs)
  {
    FieldDeclaration oldField = field;
    field = newField;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECLARATION__FIELD, oldField, newField);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setField(FieldDeclaration newField)
  {
    if (newField != field)
    {
      NotificationChain msgs = null;
      if (field != null)
        msgs = ((InternalEObject)field).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECLARATION__FIELD, null, msgs);
      if (newField != null)
        msgs = ((InternalEObject)newField).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_DECLARATION__FIELD, null, msgs);
      msgs = basicSetField(newField, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_DECLARATION__FIELD, newField, newField));
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
      case JMLPackage.MEMBER_DECLARATION__TYPE:
        return basicSetType(null, msgs);
      case JMLPackage.MEMBER_DECLARATION__METHOD:
        return basicSetMethod(null, msgs);
      case JMLPackage.MEMBER_DECLARATION__FIELD:
        return basicSetField(null, msgs);
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
      case JMLPackage.MEMBER_DECLARATION__TYPE:
        return getType();
      case JMLPackage.MEMBER_DECLARATION__METHOD:
        return getMethod();
      case JMLPackage.MEMBER_DECLARATION__FIELD:
        return getField();
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
      case JMLPackage.MEMBER_DECLARATION__TYPE:
        setType((Type)newValue);
        return;
      case JMLPackage.MEMBER_DECLARATION__METHOD:
        setMethod((MethodDeclaration)newValue);
        return;
      case JMLPackage.MEMBER_DECLARATION__FIELD:
        setField((FieldDeclaration)newValue);
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
      case JMLPackage.MEMBER_DECLARATION__TYPE:
        setType((Type)null);
        return;
      case JMLPackage.MEMBER_DECLARATION__METHOD:
        setMethod((MethodDeclaration)null);
        return;
      case JMLPackage.MEMBER_DECLARATION__FIELD:
        setField((FieldDeclaration)null);
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
      case JMLPackage.MEMBER_DECLARATION__TYPE:
        return type != null;
      case JMLPackage.MEMBER_DECLARATION__METHOD:
        return method != null;
      case JMLPackage.MEMBER_DECLARATION__FIELD:
        return field != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == Typed.class)
    {
      switch (derivedFeatureID)
      {
        case JMLPackage.MEMBER_DECLARATION__TYPE: return JMLPackage.TYPED__TYPE;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == Typed.class)
    {
      switch (baseFeatureID)
      {
        case JMLPackage.TYPED__TYPE: return JMLPackage.MEMBER_DECLARATION__TYPE;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

} //MemberDeclarationImpl
