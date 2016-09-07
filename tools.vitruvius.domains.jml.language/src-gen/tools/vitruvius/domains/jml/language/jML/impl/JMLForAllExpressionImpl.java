/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLForAllExpression;
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
 * An implementation of the model object '<em><b>For All Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl#getInitExpressions <em>Init Expressions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLForAllExpressionImpl#getUpdateExpressions <em>Update Expressions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLForAllExpressionImpl extends ExpressionImpl implements JMLForAllExpression
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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLForAllExpressionImpl()
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
    return JMLPackage.Literals.JML_FOR_ALL_EXPRESSION;
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
      initExpressions = new EObjectContainmentEList<Expression>(Expression.class, this, JMLPackage.JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS);
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION, oldExpression, newExpression);
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
        msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION, null, msgs);
      if (newExpression != null)
        msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION, null, msgs);
      msgs = basicSetExpression(newExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION, newExpression, newExpression));
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
      updateExpressions = new EObjectContainmentEList<Expression>(Expression.class, this, JMLPackage.JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS);
    }
    return updateExpressions;
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
      case JMLPackage.JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS:
        return ((InternalEList<?>)getInitExpressions()).basicRemove(otherEnd, msgs);
      case JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION:
        return basicSetExpression(null, msgs);
      case JMLPackage.JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS:
        return ((InternalEList<?>)getUpdateExpressions()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS:
        return getInitExpressions();
      case JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION:
        return getExpression();
      case JMLPackage.JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS:
        return getUpdateExpressions();
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
      case JMLPackage.JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS:
        getInitExpressions().clear();
        getInitExpressions().addAll((Collection<? extends Expression>)newValue);
        return;
      case JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION:
        setExpression((Expression)newValue);
        return;
      case JMLPackage.JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS:
        getUpdateExpressions().clear();
        getUpdateExpressions().addAll((Collection<? extends Expression>)newValue);
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
      case JMLPackage.JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS:
        getInitExpressions().clear();
        return;
      case JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION:
        setExpression((Expression)null);
        return;
      case JMLPackage.JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS:
        getUpdateExpressions().clear();
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
      case JMLPackage.JML_FOR_ALL_EXPRESSION__INIT_EXPRESSIONS:
        return initExpressions != null && !initExpressions.isEmpty();
      case JMLPackage.JML_FOR_ALL_EXPRESSION__EXPRESSION:
        return expression != null;
      case JMLPackage.JML_FOR_ALL_EXPRESSION__UPDATE_EXPRESSIONS:
        return updateExpressions != null && !updateExpressions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //JMLForAllExpressionImpl
