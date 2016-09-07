/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.Type;
import tools.vitruvius.domains.jml.language.jML.Typed;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Formal Parameter Decl</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl#isVarargs <em>Varargs</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.FormalParameterDeclImpl#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormalParameterDeclImpl extends ModifiableImpl implements FormalParameterDecl
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
   * The default value of the '{@link #isVarargs() <em>Varargs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isVarargs()
   * @generated
   * @ordered
   */
  protected static final boolean VARARGS_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isVarargs() <em>Varargs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isVarargs()
   * @generated
   * @ordered
   */
  protected boolean varargs = VARARGS_EDEFAULT;

  /**
   * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIdentifier()
   * @generated
   * @ordered
   */
  protected static final String IDENTIFIER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIdentifier()
   * @generated
   * @ordered
   */
  protected String identifier = IDENTIFIER_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FormalParameterDeclImpl()
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
    return JMLPackage.Literals.FORMAL_PARAMETER_DECL;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.FORMAL_PARAMETER_DECL__TYPE, oldType, newType);
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
        msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FORMAL_PARAMETER_DECL__TYPE, null, msgs);
      if (newType != null)
        msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FORMAL_PARAMETER_DECL__TYPE, null, msgs);
      msgs = basicSetType(newType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.FORMAL_PARAMETER_DECL__TYPE, newType, newType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isVarargs()
  {
    return varargs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVarargs(boolean newVarargs)
  {
    boolean oldVarargs = varargs;
    varargs = newVarargs;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.FORMAL_PARAMETER_DECL__VARARGS, oldVarargs, varargs));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getIdentifier()
  {
    return identifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIdentifier(String newIdentifier)
  {
    String oldIdentifier = identifier;
    identifier = newIdentifier;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.FORMAL_PARAMETER_DECL__IDENTIFIER, oldIdentifier, identifier));
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
      case JMLPackage.FORMAL_PARAMETER_DECL__TYPE:
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
      case JMLPackage.FORMAL_PARAMETER_DECL__TYPE:
        return getType();
      case JMLPackage.FORMAL_PARAMETER_DECL__VARARGS:
        return isVarargs();
      case JMLPackage.FORMAL_PARAMETER_DECL__IDENTIFIER:
        return getIdentifier();
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
      case JMLPackage.FORMAL_PARAMETER_DECL__TYPE:
        setType((Type)newValue);
        return;
      case JMLPackage.FORMAL_PARAMETER_DECL__VARARGS:
        setVarargs((Boolean)newValue);
        return;
      case JMLPackage.FORMAL_PARAMETER_DECL__IDENTIFIER:
        setIdentifier((String)newValue);
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
      case JMLPackage.FORMAL_PARAMETER_DECL__TYPE:
        setType((Type)null);
        return;
      case JMLPackage.FORMAL_PARAMETER_DECL__VARARGS:
        setVarargs(VARARGS_EDEFAULT);
        return;
      case JMLPackage.FORMAL_PARAMETER_DECL__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
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
      case JMLPackage.FORMAL_PARAMETER_DECL__TYPE:
        return type != null;
      case JMLPackage.FORMAL_PARAMETER_DECL__VARARGS:
        return varargs != VARARGS_EDEFAULT;
      case JMLPackage.FORMAL_PARAMETER_DECL__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
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
        case JMLPackage.FORMAL_PARAMETER_DECL__TYPE: return JMLPackage.TYPED__TYPE;
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
        case JMLPackage.TYPED__TYPE: return JMLPackage.FORMAL_PARAMETER_DECL__TYPE;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
    result.append(" (varargs: ");
    result.append(varargs);
    result.append(", identifier: ");
    result.append(identifier);
    result.append(')');
    return result.toString();
  }

} //FormalParameterDeclImpl
