/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.CatchClause;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.ValidID;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Catch Clause</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CatchClauseImpl#getDeclaredParam <em>Declared Param</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CatchClauseImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CatchClauseImpl extends MinimalEObjectImpl.Container implements CatchClause
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
   * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpression()
   * @generated
   * @ordered
   */
  protected Expression expression;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CatchClauseImpl()
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
    return JMLPackage.Literals.CATCH_CLAUSE;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CATCH_CLAUSE__DECLARED_PARAM, oldDeclaredParam, newDeclaredParam);
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
        msgs = ((InternalEObject)declaredParam).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CATCH_CLAUSE__DECLARED_PARAM, null, msgs);
      if (newDeclaredParam != null)
        msgs = ((InternalEObject)newDeclaredParam).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CATCH_CLAUSE__DECLARED_PARAM, null, msgs);
      msgs = basicSetDeclaredParam(newDeclaredParam, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CATCH_CLAUSE__DECLARED_PARAM, newDeclaredParam, newDeclaredParam));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CATCH_CLAUSE__EXPRESSION, oldExpression, newExpression);
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
        msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CATCH_CLAUSE__EXPRESSION, null, msgs);
      if (newExpression != null)
        msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CATCH_CLAUSE__EXPRESSION, null, msgs);
      msgs = basicSetExpression(newExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CATCH_CLAUSE__EXPRESSION, newExpression, newExpression));
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
      case JMLPackage.CATCH_CLAUSE__DECLARED_PARAM:
        return basicSetDeclaredParam(null, msgs);
      case JMLPackage.CATCH_CLAUSE__EXPRESSION:
        return basicSetExpression(null, msgs);
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
      case JMLPackage.CATCH_CLAUSE__DECLARED_PARAM:
        return getDeclaredParam();
      case JMLPackage.CATCH_CLAUSE__EXPRESSION:
        return getExpression();
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
      case JMLPackage.CATCH_CLAUSE__DECLARED_PARAM:
        setDeclaredParam((ValidID)newValue);
        return;
      case JMLPackage.CATCH_CLAUSE__EXPRESSION:
        setExpression((Expression)newValue);
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
      case JMLPackage.CATCH_CLAUSE__DECLARED_PARAM:
        setDeclaredParam((ValidID)null);
        return;
      case JMLPackage.CATCH_CLAUSE__EXPRESSION:
        setExpression((Expression)null);
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
      case JMLPackage.CATCH_CLAUSE__DECLARED_PARAM:
        return declaredParam != null;
      case JMLPackage.CATCH_CLAUSE__EXPRESSION:
        return expression != null;
    }
    return super.eIsSet(featureID);
  }

} //CatchClauseImpl
