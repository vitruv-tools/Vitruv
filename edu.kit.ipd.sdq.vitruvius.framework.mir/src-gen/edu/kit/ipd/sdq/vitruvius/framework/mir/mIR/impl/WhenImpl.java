/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>When</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhenImpl#getPredicate <em>Predicate</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.WhenImpl#getOppositeExpression <em>Opposite Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WhenImpl extends MinimalEObjectImpl.Container implements When
{
  /**
   * The cached value of the '{@link #getPredicate() <em>Predicate</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPredicate()
   * @generated
   * @ordered
   */
  protected EObject predicate;

  /**
   * The cached value of the '{@link #getOppositeExpression() <em>Opposite Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOppositeExpression()
   * @generated
   * @ordered
   */
  protected EObject oppositeExpression;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WhenImpl()
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
    return MIRPackage.Literals.WHEN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EObject getPredicate()
  {
    return predicate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPredicate(EObject newPredicate, NotificationChain msgs)
  {
    EObject oldPredicate = predicate;
    predicate = newPredicate;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.WHEN__PREDICATE, oldPredicate, newPredicate);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPredicate(EObject newPredicate)
  {
    if (newPredicate != predicate)
    {
      NotificationChain msgs = null;
      if (predicate != null)
        msgs = ((InternalEObject)predicate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.WHEN__PREDICATE, null, msgs);
      if (newPredicate != null)
        msgs = ((InternalEObject)newPredicate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.WHEN__PREDICATE, null, msgs);
      msgs = basicSetPredicate(newPredicate, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.WHEN__PREDICATE, newPredicate, newPredicate));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EObject getOppositeExpression()
  {
    return oppositeExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOppositeExpression(EObject newOppositeExpression, NotificationChain msgs)
  {
    EObject oldOppositeExpression = oppositeExpression;
    oppositeExpression = newOppositeExpression;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.WHEN__OPPOSITE_EXPRESSION, oldOppositeExpression, newOppositeExpression);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOppositeExpression(EObject newOppositeExpression)
  {
    if (newOppositeExpression != oppositeExpression)
    {
      NotificationChain msgs = null;
      if (oppositeExpression != null)
        msgs = ((InternalEObject)oppositeExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.WHEN__OPPOSITE_EXPRESSION, null, msgs);
      if (newOppositeExpression != null)
        msgs = ((InternalEObject)newOppositeExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.WHEN__OPPOSITE_EXPRESSION, null, msgs);
      msgs = basicSetOppositeExpression(newOppositeExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.WHEN__OPPOSITE_EXPRESSION, newOppositeExpression, newOppositeExpression));
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
      case MIRPackage.WHEN__PREDICATE:
        return basicSetPredicate(null, msgs);
      case MIRPackage.WHEN__OPPOSITE_EXPRESSION:
        return basicSetOppositeExpression(null, msgs);
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
      case MIRPackage.WHEN__PREDICATE:
        return getPredicate();
      case MIRPackage.WHEN__OPPOSITE_EXPRESSION:
        return getOppositeExpression();
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
      case MIRPackage.WHEN__PREDICATE:
        setPredicate((EObject)newValue);
        return;
      case MIRPackage.WHEN__OPPOSITE_EXPRESSION:
        setOppositeExpression((EObject)newValue);
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
      case MIRPackage.WHEN__PREDICATE:
        setPredicate((EObject)null);
        return;
      case MIRPackage.WHEN__OPPOSITE_EXPRESSION:
        setOppositeExpression((EObject)null);
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
      case MIRPackage.WHEN__PREDICATE:
        return predicate != null;
      case MIRPackage.WHEN__OPPOSITE_EXPRESSION:
        return oppositeExpression != null;
    }
    return super.eIsSet(featureID);
  }

} //WhenImpl
