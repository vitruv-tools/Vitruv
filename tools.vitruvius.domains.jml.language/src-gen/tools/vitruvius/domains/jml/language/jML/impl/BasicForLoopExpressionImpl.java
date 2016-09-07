/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.BasicForLoopExpression;
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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Basic For Loop Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl#getInitExpressions <em>Init Expressions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl#getUpdateExpressions <em>Update Expressions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.BasicForLoopExpressionImpl#getEachExpression <em>Each Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BasicForLoopExpressionImpl extends ExpressionImpl implements BasicForLoopExpression
{
  /**
   * The cached value of the '{@link #getInitExpressions() <em>Init Expressions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitExpressions()
   * @generated
   * @ordered
   */
  protected EList<Expression> initExpressions;

  /**
   * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpression()
   * @generated
   * @ordered
   */
  protected Expression expression;

  /**
   * The cached value of the '{@link #getUpdateExpressions() <em>Update Expressions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUpdateExpressions()
   * @generated
   * @ordered
   */
  protected EList<Expression> updateExpressions;

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
  protected BasicForLoopExpressionImpl()
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
    return JMLPackage.Literals.BASIC_FOR_LOOP_EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expression> getInitExpressions()
  {
    if (initExpressions == null)
    {
      initExpressions = new EObjectContainmentEList<Expression>(Expression.class, this, JMLPackage.BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS);
    }
    return initExpressions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getExpression()
  {
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs)
  {
    Expression oldExpression = expression;
    expression = newExpression;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION, oldExpression, newExpression);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpression(Expression newExpression)
  {
    if (newExpression != expression)
    {
      NotificationChain msgs = null;
      if (expression != null)
        msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION, null, msgs);
      if (newExpression != null)
        msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION, null, msgs);
      msgs = basicSetExpression(newExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION, newExpression, newExpression));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expression> getUpdateExpressions()
  {
    if (updateExpressions == null)
    {
      updateExpressions = new EObjectContainmentEList<Expression>(Expression.class, this, JMLPackage.BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS);
    }
    return updateExpressions;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION, oldEachExpression, newEachExpression);
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
        msgs = ((InternalEObject)eachExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION, null, msgs);
      if (newEachExpression != null)
        msgs = ((InternalEObject)newEachExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION, null, msgs);
      msgs = basicSetEachExpression(newEachExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION, newEachExpression, newEachExpression));
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
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS:
        return ((InternalEList<?>)getInitExpressions()).basicRemove(otherEnd, msgs);
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION:
        return basicSetExpression(null, msgs);
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS:
        return ((InternalEList<?>)getUpdateExpressions()).basicRemove(otherEnd, msgs);
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
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
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS:
        return getInitExpressions();
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION:
        return getExpression();
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS:
        return getUpdateExpressions();
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        return getEachExpression();
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
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS:
        getInitExpressions().clear();
        getInitExpressions().addAll((Collection<? extends Expression>)newValue);
        return;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION:
        setExpression((Expression)newValue);
        return;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS:
        getUpdateExpressions().clear();
        getUpdateExpressions().addAll((Collection<? extends Expression>)newValue);
        return;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
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
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS:
        getInitExpressions().clear();
        return;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION:
        setExpression((Expression)null);
        return;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS:
        getUpdateExpressions().clear();
        return;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
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
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__INIT_EXPRESSIONS:
        return initExpressions != null && !initExpressions.isEmpty();
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EXPRESSION:
        return expression != null;
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__UPDATE_EXPRESSIONS:
        return updateExpressions != null && !updateExpressions.isEmpty();
      case JMLPackage.BASIC_FOR_LOOP_EXPRESSION__EACH_EXPRESSION:
        return eachExpression != null;
    }
    return super.eIsSet(featureID);
  }

} //BasicForLoopExpressionImpl
