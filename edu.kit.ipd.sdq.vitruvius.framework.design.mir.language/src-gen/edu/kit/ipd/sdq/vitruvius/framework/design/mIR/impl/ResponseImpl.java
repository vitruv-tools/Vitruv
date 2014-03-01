/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.InvariantRestriction;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.OperationRestriction;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl#getOperationRestriction <em>Operation Restriction</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl#getInvariantRestriction <em>Invariant Restriction</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ResponseImpl#getXtendStmt <em>Xtend Stmt</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResponseImpl extends MinimalEObjectImpl.Container implements Response
{
  /**
   * The cached value of the '{@link #getOperationRestriction() <em>Operation Restriction</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperationRestriction()
   * @generated
   * @ordered
   */
  protected OperationRestriction operationRestriction;

  /**
   * The cached value of the '{@link #getInvariantRestriction() <em>Invariant Restriction</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvariantRestriction()
   * @generated
   * @ordered
   */
  protected InvariantRestriction invariantRestriction;

  /**
   * The default value of the '{@link #getXtendStmt() <em>Xtend Stmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXtendStmt()
   * @generated
   * @ordered
   */
  protected static final String XTEND_STMT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getXtendStmt() <em>Xtend Stmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXtendStmt()
   * @generated
   * @ordered
   */
  protected String xtendStmt = XTEND_STMT_EDEFAULT;

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
  public OperationRestriction getOperationRestriction()
  {
    return operationRestriction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOperationRestriction(OperationRestriction newOperationRestriction, NotificationChain msgs)
  {
    OperationRestriction oldOperationRestriction = operationRestriction;
    operationRestriction = newOperationRestriction;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__OPERATION_RESTRICTION, oldOperationRestriction, newOperationRestriction);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperationRestriction(OperationRestriction newOperationRestriction)
  {
    if (newOperationRestriction != operationRestriction)
    {
      NotificationChain msgs = null;
      if (operationRestriction != null)
        msgs = ((InternalEObject)operationRestriction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__OPERATION_RESTRICTION, null, msgs);
      if (newOperationRestriction != null)
        msgs = ((InternalEObject)newOperationRestriction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__OPERATION_RESTRICTION, null, msgs);
      msgs = basicSetOperationRestriction(newOperationRestriction, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__OPERATION_RESTRICTION, newOperationRestriction, newOperationRestriction));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvariantRestriction getInvariantRestriction()
  {
    return invariantRestriction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInvariantRestriction(InvariantRestriction newInvariantRestriction, NotificationChain msgs)
  {
    InvariantRestriction oldInvariantRestriction = invariantRestriction;
    invariantRestriction = newInvariantRestriction;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__INVARIANT_RESTRICTION, oldInvariantRestriction, newInvariantRestriction);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInvariantRestriction(InvariantRestriction newInvariantRestriction)
  {
    if (newInvariantRestriction != invariantRestriction)
    {
      NotificationChain msgs = null;
      if (invariantRestriction != null)
        msgs = ((InternalEObject)invariantRestriction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__INVARIANT_RESTRICTION, null, msgs);
      if (newInvariantRestriction != null)
        msgs = ((InternalEObject)newInvariantRestriction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.RESPONSE__INVARIANT_RESTRICTION, null, msgs);
      msgs = basicSetInvariantRestriction(newInvariantRestriction, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__INVARIANT_RESTRICTION, newInvariantRestriction, newInvariantRestriction));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getXtendStmt()
  {
    return xtendStmt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setXtendStmt(String newXtendStmt)
  {
    String oldXtendStmt = xtendStmt;
    xtendStmt = newXtendStmt;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.RESPONSE__XTEND_STMT, oldXtendStmt, xtendStmt));
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
      case MIRPackage.RESPONSE__OPERATION_RESTRICTION:
        return basicSetOperationRestriction(null, msgs);
      case MIRPackage.RESPONSE__INVARIANT_RESTRICTION:
        return basicSetInvariantRestriction(null, msgs);
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
      case MIRPackage.RESPONSE__OPERATION_RESTRICTION:
        return getOperationRestriction();
      case MIRPackage.RESPONSE__INVARIANT_RESTRICTION:
        return getInvariantRestriction();
      case MIRPackage.RESPONSE__XTEND_STMT:
        return getXtendStmt();
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
      case MIRPackage.RESPONSE__OPERATION_RESTRICTION:
        setOperationRestriction((OperationRestriction)newValue);
        return;
      case MIRPackage.RESPONSE__INVARIANT_RESTRICTION:
        setInvariantRestriction((InvariantRestriction)newValue);
        return;
      case MIRPackage.RESPONSE__XTEND_STMT:
        setXtendStmt((String)newValue);
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
      case MIRPackage.RESPONSE__OPERATION_RESTRICTION:
        setOperationRestriction((OperationRestriction)null);
        return;
      case MIRPackage.RESPONSE__INVARIANT_RESTRICTION:
        setInvariantRestriction((InvariantRestriction)null);
        return;
      case MIRPackage.RESPONSE__XTEND_STMT:
        setXtendStmt(XTEND_STMT_EDEFAULT);
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
      case MIRPackage.RESPONSE__OPERATION_RESTRICTION:
        return operationRestriction != null;
      case MIRPackage.RESPONSE__INVARIANT_RESTRICTION:
        return invariantRestriction != null;
      case MIRPackage.RESPONSE__XTEND_STMT:
        return XTEND_STMT_EDEFAULT == null ? xtendStmt != null : !XTEND_STMT_EDEFAULT.equals(xtendStmt);
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
    result.append(" (xtendStmt: ");
    result.append(xtendStmt);
    result.append(')');
    return result.toString();
  }

} //ResponseImpl
