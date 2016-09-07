/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ConstructorCall;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constructor Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl#getConstructor <em>Constructor</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl#getTypeArguments <em>Type Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl#isExplicitConstructorCall <em>Explicit Constructor Call</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ConstructorCallImpl#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstructorCallImpl extends ExpressionImpl implements ConstructorCall
{
  /**
   * The default value of the '{@link #getConstructor() <em>Constructor</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstructor()
   * @generated
   * @ordered
   */
  protected static final String CONSTRUCTOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getConstructor() <em>Constructor</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstructor()
   * @generated
   * @ordered
   */
  protected String constructor = CONSTRUCTOR_EDEFAULT;

  /**
   * The cached value of the '{@link #getTypeArguments() <em>Type Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeArguments()
   * @generated
   * @ordered
   */
  protected EList<JvmTypeReference> typeArguments;

  /**
   * The default value of the '{@link #isExplicitConstructorCall() <em>Explicit Constructor Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitConstructorCall()
   * @generated
   * @ordered
   */
  protected static final boolean EXPLICIT_CONSTRUCTOR_CALL_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExplicitConstructorCall() <em>Explicit Constructor Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitConstructorCall()
   * @generated
   * @ordered
   */
  protected boolean explicitConstructorCall = EXPLICIT_CONSTRUCTOR_CALL_EDEFAULT;

  /**
   * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArguments()
   * @generated
   * @ordered
   */
  protected EList<Expression> arguments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConstructorCallImpl()
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
    return JMLPackage.Literals.CONSTRUCTOR_CALL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getConstructor()
  {
    return constructor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstructor(String newConstructor)
  {
    String oldConstructor = constructor;
    constructor = newConstructor;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CONSTRUCTOR_CALL__CONSTRUCTOR, oldConstructor, constructor));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<JvmTypeReference> getTypeArguments()
  {
    if (typeArguments == null)
    {
      typeArguments = new EObjectContainmentEList<JvmTypeReference>(JvmTypeReference.class, this, JMLPackage.CONSTRUCTOR_CALL__TYPE_ARGUMENTS);
    }
    return typeArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isExplicitConstructorCall()
  {
    return explicitConstructorCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExplicitConstructorCall(boolean newExplicitConstructorCall)
  {
    boolean oldExplicitConstructorCall = explicitConstructorCall;
    explicitConstructorCall = newExplicitConstructorCall;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL, oldExplicitConstructorCall, explicitConstructorCall));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expression> getArguments()
  {
    if (arguments == null)
    {
      arguments = new EObjectContainmentEList<Expression>(Expression.class, this, JMLPackage.CONSTRUCTOR_CALL__ARGUMENTS);
    }
    return arguments;
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
      case JMLPackage.CONSTRUCTOR_CALL__TYPE_ARGUMENTS:
        return ((InternalEList<?>)getTypeArguments()).basicRemove(otherEnd, msgs);
      case JMLPackage.CONSTRUCTOR_CALL__ARGUMENTS:
        return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.CONSTRUCTOR_CALL__CONSTRUCTOR:
        return getConstructor();
      case JMLPackage.CONSTRUCTOR_CALL__TYPE_ARGUMENTS:
        return getTypeArguments();
      case JMLPackage.CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL:
        return isExplicitConstructorCall();
      case JMLPackage.CONSTRUCTOR_CALL__ARGUMENTS:
        return getArguments();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case JMLPackage.CONSTRUCTOR_CALL__CONSTRUCTOR:
        setConstructor((String)newValue);
        return;
      case JMLPackage.CONSTRUCTOR_CALL__TYPE_ARGUMENTS:
        getTypeArguments().clear();
        getTypeArguments().addAll((Collection<? extends JvmTypeReference>)newValue);
        return;
      case JMLPackage.CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL:
        setExplicitConstructorCall((Boolean)newValue);
        return;
      case JMLPackage.CONSTRUCTOR_CALL__ARGUMENTS:
        getArguments().clear();
        getArguments().addAll((Collection<? extends Expression>)newValue);
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
      case JMLPackage.CONSTRUCTOR_CALL__CONSTRUCTOR:
        setConstructor(CONSTRUCTOR_EDEFAULT);
        return;
      case JMLPackage.CONSTRUCTOR_CALL__TYPE_ARGUMENTS:
        getTypeArguments().clear();
        return;
      case JMLPackage.CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL:
        setExplicitConstructorCall(EXPLICIT_CONSTRUCTOR_CALL_EDEFAULT);
        return;
      case JMLPackage.CONSTRUCTOR_CALL__ARGUMENTS:
        getArguments().clear();
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
      case JMLPackage.CONSTRUCTOR_CALL__CONSTRUCTOR:
        return CONSTRUCTOR_EDEFAULT == null ? constructor != null : !CONSTRUCTOR_EDEFAULT.equals(constructor);
      case JMLPackage.CONSTRUCTOR_CALL__TYPE_ARGUMENTS:
        return typeArguments != null && !typeArguments.isEmpty();
      case JMLPackage.CONSTRUCTOR_CALL__EXPLICIT_CONSTRUCTOR_CALL:
        return explicitConstructorCall != EXPLICIT_CONSTRUCTOR_CALL_EDEFAULT;
      case JMLPackage.CONSTRUCTOR_CALL__ARGUMENTS:
        return arguments != null && !arguments.isEmpty();
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
    result.append(" (constructor: ");
    result.append(constructor);
    result.append(", explicitConstructorCall: ");
    result.append(explicitConstructorCall);
    result.append(')');
    return result.toString();
  }

} //ConstructorCallImpl
