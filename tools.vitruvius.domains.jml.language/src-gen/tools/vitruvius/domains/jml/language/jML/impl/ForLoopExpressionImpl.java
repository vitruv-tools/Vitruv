/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.ForLoopExpression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.ValidID;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>For Loop Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl#getDeclaredParam <em>Declared Param</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl#getForExpression <em>For Expression</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ForLoopExpressionImpl#getEachExpression <em>Each Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForLoopExpressionImpl extends ExpressionImpl implements ForLoopExpression
{
  /**
   * The cached value of the '{@link #getDeclaredParam() <em>Declared Param</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDeclaredParam()
   * @generated
   * @ordered
   */
  protected ValidID declaredParam;

  /**
   * The cached value of the '{@link #getForExpression() <em>For Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getForExpression()
   * @generated
   * @ordered
   */
  protected Expression forExpression;

  /**
   * The cached value of the '{@link #getEachExpression() <em>Each Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEachExpression()
   * @generated
   * @ordered
   */
  protected Expression eachExpression;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ForLoopExpressionImpl()
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
    return JMLPackage.Literals.FOR_LOOP_EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ValidID getDeclaredParam()
  {
    return declaredParam;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDeclaredParam(ValidID newDeclaredParam, NotificationChain msgs)
  {
    ValidID oldDeclaredParam = declaredParam;
    declaredParam = newDeclaredParam;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM, oldDeclaredParam, newDeclaredParam);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDeclaredParam(ValidID newDeclaredParam)
  {
    if (newDeclaredParam != declaredParam)
    {
      NotificationChain msgs = null;
      if (declaredParam != null)
        msgs = ((InternalEObject)declaredParam).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM, null, msgs);
      if (newDeclaredParam != null)
        msgs = ((InternalEObject)newDeclaredParam).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM, null, msgs);
      msgs = basicSetDeclaredParam(newDeclaredParam, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM, newDeclaredParam, newDeclaredParam));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getForExpression()
  {
    return forExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetForExpression(Expression newForExpression, NotificationChain msgs)
  {
    Expression oldForExpression = forExpression;
    forExpression = newForExpression;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION, oldForExpression, newForExpression);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setForExpression(Expression newForExpression)
  {
    if (newForExpression != forExpression)
    {
      NotificationChain msgs = null;
      if (forExpression != null)
        msgs = ((InternalEObject)forExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION, null, msgs);
      if (newForExpression != null)
        msgs = ((InternalEObject)newForExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION, null, msgs);
      msgs = basicSetForExpression(newForExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION, newForExpression, newForExpression));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getEachExpression()
  {
    return eachExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetEachExpression(Expression newEachExpression, NotificationChain msgs)
  {
    Expression oldEachExpression = eachExpression;
    eachExpression = newEachExpression;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION, oldEachExpression, newEachExpression);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEachExpression(Expression newEachExpression)
  {
    if (newEachExpression != eachExpression)
    {
      NotificationChain msgs = null;
      if (eachExpression != null)
        msgs = ((InternalEObject)eachExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION, null, msgs);
      if (newEachExpression != null)
        msgs = ((InternalEObject)newEachExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION, null, msgs);
      msgs = basicSetEachExpression(newEachExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION, newEachExpression, newEachExpression));
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
      case JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM:
        return basicSetDeclaredParam(null, msgs);
      case JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION:
        return basicSetForExpression(null, msgs);
      case JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        return basicSetEachExpression(null, msgs);
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
      case JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM:
        return getDeclaredParam();
      case JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION:
        return getForExpression();
      case JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        return getEachExpression();
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
      case JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM:
        setDeclaredParam((ValidID)newValue);
        return;
      case JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION:
        setForExpression((Expression)newValue);
        return;
      case JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        setEachExpression((Expression)newValue);
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
      case JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM:
        setDeclaredParam((ValidID)null);
        return;
      case JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION:
        setForExpression((Expression)null);
        return;
      case JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        setEachExpression((Expression)null);
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
      case JMLPackage.FOR_LOOP_EXPRESSION__DECLARED_PARAM:
        return declaredParam != null;
      case JMLPackage.FOR_LOOP_EXPRESSION__FOR_EXPRESSION:
        return forExpression != null;
      case JMLPackage.FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        return eachExpression != null;
    }
    return super.eIsSet(featureID);
  }

} //ForLoopExpressionImpl
