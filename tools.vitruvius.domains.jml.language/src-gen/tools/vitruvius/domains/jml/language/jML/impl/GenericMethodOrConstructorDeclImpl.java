/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Constructor;
import tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.TypeParameters;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Method Or Constructor Decl</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl#getTypeParameters <em>Type Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.GenericMethodOrConstructorDeclImpl#getConstructor <em>Constructor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericMethodOrConstructorDeclImpl extends MemberDeclImpl implements GenericMethodOrConstructorDecl
{
  /**
   * The cached value of the '{@link #getTypeParameters() <em>Type Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeParameters()
   * @generated
   * @ordered
   */
  protected TypeParameters typeParameters;

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
  protected GenericMethodOrConstructorDeclImpl()
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
    return JMLPackage.Literals.GENERIC_METHOD_OR_CONSTRUCTOR_DECL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeParameters getTypeParameters()
  {
    return typeParameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTypeParameters(TypeParameters newTypeParameters, NotificationChain msgs)
  {
    TypeParameters oldTypeParameters = typeParameters;
    typeParameters = newTypeParameters;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS, oldTypeParameters, newTypeParameters);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTypeParameters(TypeParameters newTypeParameters)
  {
    if (newTypeParameters != typeParameters)
    {
      NotificationChain msgs = null;
      if (typeParameters != null)
        msgs = ((InternalEObject)typeParameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS, null, msgs);
      if (newTypeParameters != null)
        msgs = ((InternalEObject)newTypeParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS, null, msgs);
      msgs = basicSetTypeParameters(newTypeParameters, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS, newTypeParameters, newTypeParameters));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE, oldType, newType);
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
        msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE, null, msgs);
      if (newType != null)
        msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE, null, msgs);
      msgs = basicSetType(newType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE, newType, newType));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD, oldMethod, newMethod);
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
        msgs = ((InternalEObject)method).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD, null, msgs);
      if (newMethod != null)
        msgs = ((InternalEObject)newMethod).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD, null, msgs);
      msgs = basicSetMethod(newMethod, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD, newMethod, newMethod));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR, oldConstructor, newConstructor);
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
        msgs = ((InternalEObject)constructor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR, null, msgs);
      if (newConstructor != null)
        msgs = ((InternalEObject)newConstructor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR, null, msgs);
      msgs = basicSetConstructor(newConstructor, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR, newConstructor, newConstructor));
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS:
        return basicSetTypeParameters(null, msgs);
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE:
        return basicSetType(null, msgs);
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD:
        return basicSetMethod(null, msgs);
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR:
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS:
        return getTypeParameters();
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE:
        return getType();
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD:
        return getMethod();
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR:
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS:
        setTypeParameters((TypeParameters)newValue);
        return;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE:
        setType((Type)newValue);
        return;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD:
        setMethod((MethodDeclaration)newValue);
        return;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR:
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS:
        setTypeParameters((TypeParameters)null);
        return;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE:
        setType((Type)null);
        return;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD:
        setMethod((MethodDeclaration)null);
        return;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR:
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
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE_PARAMETERS:
        return typeParameters != null;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__TYPE:
        return type != null;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__METHOD:
        return method != null;
      case JMLPackage.GENERIC_METHOD_OR_CONSTRUCTOR_DECL__CONSTRUCTOR:
        return constructor != null;
    }
    return super.eIsSet(featureID);
  }

} //GenericMethodOrConstructorDeclImpl
