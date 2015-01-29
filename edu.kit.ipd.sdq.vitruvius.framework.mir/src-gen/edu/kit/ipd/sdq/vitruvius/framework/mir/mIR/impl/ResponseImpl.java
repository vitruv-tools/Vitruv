/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl#getAction <em>Action</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl#getContext <em>Context</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl#getInv <em>Inv</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.ResponseImpl#getRestoreAction <em>Restore Action</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResponseImpl extends MinimalEObjectImpl.Container implements Response
{
  /**
   * The default value of the '{@link #getAction() <em>Action</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAction()
   * @generated
   * @ordered
   */
  protected static final ResponseAction ACTION_EDEFAULT = ResponseAction.ANY;

  /**
   * The cached value of the '{@link #getAction() <em>Action</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAction()
   * @generated
   * @ordered
   */
  protected ResponseAction action = ACTION_EDEFAULT;

  /**
   * The cached value of the '{@link #getContext() <em>Context</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContext()
   * @generated
   * @ordered
   */
  protected TypedElement context;

  /**
   * The cached value of the '{@link #getInv() <em>Inv</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInv()
   * @generated
   * @ordered
   */
  protected Invariant inv;

  /**
   * The cached value of the '{@link #getRestoreAction() <em>Restore Action</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRestoreAction()
   * @generated
   * @ordered
   */
  protected XExpression restoreAction;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ResponseImpl()
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
    return MIRPackage.Literals.RESPONSE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResponseAction getAction()
  {
    return action;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAction(ResponseAction newAction)
  {
    ResponseAction oldAction = action;
    action = newAction == null ? ACTION_EDEFAULT : newAction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__ACTION, oldAction, action));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypedElement getContext()
  {
    return context;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetContext(TypedElement newContext, NotificationChain msgs)
  {
    TypedElement oldContext = context;
    context = newContext;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__CONTEXT, oldContext, newContext);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setContext(TypedElement newContext)
  {
    if (newContext != context)
    {
      NotificationChain msgs = null;
      if (context != null)
        msgs = ((InternalEObject)context).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__CONTEXT, null, msgs);
      if (newContext != null)
        msgs = ((InternalEObject)newContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__CONTEXT, null, msgs);
      msgs = basicSetContext(newContext, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__CONTEXT, newContext, newContext));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Invariant getInv()
  {
    if (inv != null && inv.eIsProxy())
    {
      InternalEObject oldInv = (InternalEObject)inv;
      inv = (Invariant)eResolveProxy(oldInv);
      if (inv != oldInv)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRPackage.RESPONSE__INV, oldInv, inv));
      }
    }
    return inv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Invariant basicGetInv()
  {
    return inv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInv(Invariant newInv)
  {
    Invariant oldInv = inv;
    inv = newInv;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__INV, oldInv, inv));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XExpression getRestoreAction()
  {
    return restoreAction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRestoreAction(XExpression newRestoreAction, NotificationChain msgs)
  {
    XExpression oldRestoreAction = restoreAction;
    restoreAction = newRestoreAction;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__RESTORE_ACTION, oldRestoreAction, newRestoreAction);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRestoreAction(XExpression newRestoreAction)
  {
    if (newRestoreAction != restoreAction)
    {
      NotificationChain msgs = null;
      if (restoreAction != null)
        msgs = ((InternalEObject)restoreAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__RESTORE_ACTION, null, msgs);
      if (newRestoreAction != null)
        msgs = ((InternalEObject)newRestoreAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__RESTORE_ACTION, null, msgs);
      msgs = basicSetRestoreAction(newRestoreAction, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__RESTORE_ACTION, newRestoreAction, newRestoreAction));
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
      case MIRPackage.RESPONSE__CONTEXT:
        return basicSetContext(null, msgs);
      case MIRPackage.RESPONSE__RESTORE_ACTION:
        return basicSetRestoreAction(null, msgs);
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
      case MIRPackage.RESPONSE__ACTION:
        return getAction();
      case MIRPackage.RESPONSE__CONTEXT:
        return getContext();
      case MIRPackage.RESPONSE__INV:
        if (resolve) return getInv();
        return basicGetInv();
      case MIRPackage.RESPONSE__RESTORE_ACTION:
        return getRestoreAction();
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
      case MIRPackage.RESPONSE__ACTION:
        setAction((ResponseAction)newValue);
        return;
      case MIRPackage.RESPONSE__CONTEXT:
        setContext((TypedElement)newValue);
        return;
      case MIRPackage.RESPONSE__INV:
        setInv((Invariant)newValue);
        return;
      case MIRPackage.RESPONSE__RESTORE_ACTION:
        setRestoreAction((XExpression)newValue);
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
      case MIRPackage.RESPONSE__ACTION:
        setAction(ACTION_EDEFAULT);
        return;
      case MIRPackage.RESPONSE__CONTEXT:
        setContext((TypedElement)null);
        return;
      case MIRPackage.RESPONSE__INV:
        setInv((Invariant)null);
        return;
      case MIRPackage.RESPONSE__RESTORE_ACTION:
        setRestoreAction((XExpression)null);
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
      case MIRPackage.RESPONSE__ACTION:
        return action != ACTION_EDEFAULT;
      case MIRPackage.RESPONSE__CONTEXT:
        return context != null;
      case MIRPackage.RESPONSE__INV:
        return inv != null;
      case MIRPackage.RESPONSE__RESTORE_ACTION:
        return restoreAction != null;
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
    result.append(" (action: ");
    result.append(action);
    result.append(')');
    return result.toString();
  }

} //ResponseImpl
