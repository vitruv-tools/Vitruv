/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.CatchClause;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.TryCatchFinallyExpression;

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
 * An implementation of the model object '<em><b>Try Catch Finally Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl#getCatchClauses <em>Catch Clauses</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TryCatchFinallyExpressionImpl#getFinallyExpression <em>Finally Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TryCatchFinallyExpressionImpl extends ExpressionImpl implements TryCatchFinallyExpression
{
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
   * The cached value of the '{@link #getCatchClauses() <em>Catch Clauses</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCatchClauses()
   * @generated
   * @ordered
   */
  protected EList<CatchClause> catchClauses;

  /**
   * The cached value of the '{@link #getFinallyExpression() <em>Finally Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFinallyExpression()
   * @generated
   * @ordered
   */
  protected Expression finallyExpression;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TryCatchFinallyExpressionImpl()
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
    return JMLPackage.Literals.TRY_CATCH_FINALLY_EXPRESSION;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION, oldExpression, newExpression);
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
        msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION, null, msgs);
      if (newExpression != null)
        msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION, null, msgs);
      msgs = basicSetExpression(newExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION, newExpression, newExpression));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<CatchClause> getCatchClauses()
  {
    if (catchClauses == null)
    {
      catchClauses = new EObjectContainmentEList<CatchClause>(CatchClause.class, this, JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES);
    }
    return catchClauses;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getFinallyExpression()
  {
    return finallyExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFinallyExpression(Expression newFinallyExpression, NotificationChain msgs)
  {
    Expression oldFinallyExpression = finallyExpression;
    finallyExpression = newFinallyExpression;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION, oldFinallyExpression, newFinallyExpression);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFinallyExpression(Expression newFinallyExpression)
  {
    if (newFinallyExpression != finallyExpression)
    {
      NotificationChain msgs = null;
      if (finallyExpression != null)
        msgs = ((InternalEObject)finallyExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION, null, msgs);
      if (newFinallyExpression != null)
        msgs = ((InternalEObject)newFinallyExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION, null, msgs);
      msgs = basicSetFinallyExpression(newFinallyExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION, newFinallyExpression, newFinallyExpression));
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
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION:
        return basicSetExpression(null, msgs);
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES:
        return ((InternalEList<?>)getCatchClauses()).basicRemove(otherEnd, msgs);
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION:
        return basicSetFinallyExpression(null, msgs);
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
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION:
        return getExpression();
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES:
        return getCatchClauses();
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION:
        return getFinallyExpression();
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
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION:
        setExpression((Expression)newValue);
        return;
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES:
        getCatchClauses().clear();
        getCatchClauses().addAll((Collection<? extends CatchClause>)newValue);
        return;
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION:
        setFinallyExpression((Expression)newValue);
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
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION:
        setExpression((Expression)null);
        return;
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES:
        getCatchClauses().clear();
        return;
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION:
        setFinallyExpression((Expression)null);
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
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__EXPRESSION:
        return expression != null;
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__CATCH_CLAUSES:
        return catchClauses != null && !catchClauses.isEmpty();
      case JMLPackage.TRY_CATCH_FINALLY_EXPRESSION__FINALLY_EXPRESSION:
        return finallyExpression != null;
    }
    return super.eIsSet(featureID);
  }

} //TryCatchFinallyExpressionImpl
