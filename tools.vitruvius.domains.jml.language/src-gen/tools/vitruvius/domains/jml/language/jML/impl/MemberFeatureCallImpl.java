/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.MemberFeatureCall;

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
 * An implementation of the model object '<em><b>Member Feature Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#getMemberCallTarget <em>Member Call Target</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#isNullSafe <em>Null Safe</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#isExplicitStatic <em>Explicit Static</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#getTypeArguments <em>Type Arguments</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#isExplicitOperationCall <em>Explicit Operation Call</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.MemberFeatureCallImpl#getMemberCallArguments <em>Member Call Arguments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MemberFeatureCallImpl extends ExpressionImpl implements MemberFeatureCall
{
  /**
   * The cached value of the '{@link #getMemberCallTarget() <em>Member Call Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMemberCallTarget()
   * @generated
   * @ordered
   */
  protected Expression memberCallTarget;

  /**
   * The default value of the '{@link #isNullSafe() <em>Null Safe</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isNullSafe()
   * @generated
   * @ordered
   */
  protected static final boolean NULL_SAFE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isNullSafe() <em>Null Safe</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isNullSafe()
   * @generated
   * @ordered
   */
  protected boolean nullSafe = NULL_SAFE_EDEFAULT;

  /**
   * The default value of the '{@link #isExplicitStatic() <em>Explicit Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitStatic()
   * @generated
   * @ordered
   */
  protected static final boolean EXPLICIT_STATIC_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExplicitStatic() <em>Explicit Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitStatic()
   * @generated
   * @ordered
   */
  protected boolean explicitStatic = EXPLICIT_STATIC_EDEFAULT;

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
   * The default value of the '{@link #getFeature() <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFeature()
   * @generated
   * @ordered
   */
  protected static final String FEATURE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFeature() <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFeature()
   * @generated
   * @ordered
   */
  protected String feature = FEATURE_EDEFAULT;

  /**
   * The default value of the '{@link #isExplicitOperationCall() <em>Explicit Operation Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitOperationCall()
   * @generated
   * @ordered
   */
  protected static final boolean EXPLICIT_OPERATION_CALL_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExplicitOperationCall() <em>Explicit Operation Call</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitOperationCall()
   * @generated
   * @ordered
   */
  protected boolean explicitOperationCall = EXPLICIT_OPERATION_CALL_EDEFAULT;

  /**
   * The cached value of the '{@link #getMemberCallArguments() <em>Member Call Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMemberCallArguments()
   * @generated
   * @ordered
   */
  protected EList<Expression> memberCallArguments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MemberFeatureCallImpl()
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
    return JMLPackage.Literals.MEMBER_FEATURE_CALL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getMemberCallTarget()
  {
    return memberCallTarget;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMemberCallTarget(Expression newMemberCallTarget, NotificationChain msgs)
  {
    Expression oldMemberCallTarget = memberCallTarget;
    memberCallTarget = newMemberCallTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET, oldMemberCallTarget, newMemberCallTarget);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMemberCallTarget(Expression newMemberCallTarget)
  {
    if (newMemberCallTarget != memberCallTarget)
    {
      NotificationChain msgs = null;
      if (memberCallTarget != null)
        msgs = ((InternalEObject)memberCallTarget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET, null, msgs);
      if (newMemberCallTarget != null)
        msgs = ((InternalEObject)newMemberCallTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET, null, msgs);
      msgs = basicSetMemberCallTarget(newMemberCallTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET, newMemberCallTarget, newMemberCallTarget));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isNullSafe()
  {
    return nullSafe;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNullSafe(boolean newNullSafe)
  {
    boolean oldNullSafe = nullSafe;
    nullSafe = newNullSafe;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_FEATURE_CALL__NULL_SAFE, oldNullSafe, nullSafe));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isExplicitStatic()
  {
    return explicitStatic;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExplicitStatic(boolean newExplicitStatic)
  {
    boolean oldExplicitStatic = explicitStatic;
    explicitStatic = newExplicitStatic;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_STATIC, oldExplicitStatic, explicitStatic));
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
      typeArguments = new EObjectContainmentEList<JvmTypeReference>(JvmTypeReference.class, this, JMLPackage.MEMBER_FEATURE_CALL__TYPE_ARGUMENTS);
    }
    return typeArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFeature()
  {
    return feature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFeature(String newFeature)
  {
    String oldFeature = feature;
    feature = newFeature;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_FEATURE_CALL__FEATURE, oldFeature, feature));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isExplicitOperationCall()
  {
    return explicitOperationCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExplicitOperationCall(boolean newExplicitOperationCall)
  {
    boolean oldExplicitOperationCall = explicitOperationCall;
    explicitOperationCall = newExplicitOperationCall;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL, oldExplicitOperationCall, explicitOperationCall));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expression> getMemberCallArguments()
  {
    if (memberCallArguments == null)
    {
      memberCallArguments = new EObjectContainmentEList<Expression>(Expression.class, this, JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS);
    }
    return memberCallArguments;
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
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET:
        return basicSetMemberCallTarget(null, msgs);
      case JMLPackage.MEMBER_FEATURE_CALL__TYPE_ARGUMENTS:
        return ((InternalEList<?>)getTypeArguments()).basicRemove(otherEnd, msgs);
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS:
        return ((InternalEList<?>)getMemberCallArguments()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET:
        return getMemberCallTarget();
      case JMLPackage.MEMBER_FEATURE_CALL__NULL_SAFE:
        return isNullSafe();
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_STATIC:
        return isExplicitStatic();
      case JMLPackage.MEMBER_FEATURE_CALL__TYPE_ARGUMENTS:
        return getTypeArguments();
      case JMLPackage.MEMBER_FEATURE_CALL__FEATURE:
        return getFeature();
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL:
        return isExplicitOperationCall();
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS:
        return getMemberCallArguments();
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
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET:
        setMemberCallTarget((Expression)newValue);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__NULL_SAFE:
        setNullSafe((Boolean)newValue);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_STATIC:
        setExplicitStatic((Boolean)newValue);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__TYPE_ARGUMENTS:
        getTypeArguments().clear();
        getTypeArguments().addAll((Collection<? extends JvmTypeReference>)newValue);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__FEATURE:
        setFeature((String)newValue);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL:
        setExplicitOperationCall((Boolean)newValue);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS:
        getMemberCallArguments().clear();
        getMemberCallArguments().addAll((Collection<? extends Expression>)newValue);
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
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET:
        setMemberCallTarget((Expression)null);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__NULL_SAFE:
        setNullSafe(NULL_SAFE_EDEFAULT);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_STATIC:
        setExplicitStatic(EXPLICIT_STATIC_EDEFAULT);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__TYPE_ARGUMENTS:
        getTypeArguments().clear();
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__FEATURE:
        setFeature(FEATURE_EDEFAULT);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL:
        setExplicitOperationCall(EXPLICIT_OPERATION_CALL_EDEFAULT);
        return;
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS:
        getMemberCallArguments().clear();
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
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_TARGET:
        return memberCallTarget != null;
      case JMLPackage.MEMBER_FEATURE_CALL__NULL_SAFE:
        return nullSafe != NULL_SAFE_EDEFAULT;
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_STATIC:
        return explicitStatic != EXPLICIT_STATIC_EDEFAULT;
      case JMLPackage.MEMBER_FEATURE_CALL__TYPE_ARGUMENTS:
        return typeArguments != null && !typeArguments.isEmpty();
      case JMLPackage.MEMBER_FEATURE_CALL__FEATURE:
        return FEATURE_EDEFAULT == null ? feature != null : !FEATURE_EDEFAULT.equals(feature);
      case JMLPackage.MEMBER_FEATURE_CALL__EXPLICIT_OPERATION_CALL:
        return explicitOperationCall != EXPLICIT_OPERATION_CALL_EDEFAULT;
      case JMLPackage.MEMBER_FEATURE_CALL__MEMBER_CALL_ARGUMENTS:
        return memberCallArguments != null && !memberCallArguments.isEmpty();
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
    result.append(" (nullSafe: ");
    result.append(nullSafe);
    result.append(", explicitStatic: ");
    result.append(explicitStatic);
    result.append(", feature: ");
    result.append(feature);
    result.append(", explicitOperationCall: ");
    result.append(explicitOperationCall);
    result.append(')');
    return result.toString();
  }

} //MemberFeatureCallImpl
