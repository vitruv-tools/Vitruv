/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Closure;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.ValidID;

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
 * An implementation of the model object '<em><b>Closure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl#getDeclaredFormalParameters <em>Declared Formal Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl#isExplicitSyntax <em>Explicit Syntax</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ClosureImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClosureImpl extends ExpressionImpl implements Closure
{
  /**
   * The cached value of the '{@link #getDeclaredFormalParameters() <em>Declared Formal Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDeclaredFormalParameters()
   * @generated
   * @ordered
   */
  protected EList<ValidID> declaredFormalParameters;

  /**
   * The default value of the '{@link #isExplicitSyntax() <em>Explicit Syntax</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitSyntax()
   * @generated
   * @ordered
   */
  protected static final boolean EXPLICIT_SYNTAX_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExplicitSyntax() <em>Explicit Syntax</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExplicitSyntax()
   * @generated
   * @ordered
   */
  protected boolean explicitSyntax = EXPLICIT_SYNTAX_EDEFAULT;

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
  protected ClosureImpl()
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
    return JMLPackage.Literals.CLOSURE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ValidID> getDeclaredFormalParameters()
  {
    if (declaredFormalParameters == null)
    {
      declaredFormalParameters = new EObjectContainmentEList<ValidID>(ValidID.class, this, JMLPackage.CLOSURE__DECLARED_FORMAL_PARAMETERS);
    }
    return declaredFormalParameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isExplicitSyntax()
  {
    return explicitSyntax;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExplicitSyntax(boolean newExplicitSyntax)
  {
    boolean oldExplicitSyntax = explicitSyntax;
    explicitSyntax = newExplicitSyntax;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CLOSURE__EXPLICIT_SYNTAX, oldExplicitSyntax, explicitSyntax));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CLOSURE__EXPRESSION, oldExpression, newExpression);
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
        msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CLOSURE__EXPRESSION, null, msgs);
      if (newExpression != null)
        msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CLOSURE__EXPRESSION, null, msgs);
      msgs = basicSetExpression(newExpression, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CLOSURE__EXPRESSION, newExpression, newExpression));
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
      case JMLPackage.CLOSURE__DECLARED_FORMAL_PARAMETERS:
        return ((InternalEList<?>)getDeclaredFormalParameters()).basicRemove(otherEnd, msgs);
      case JMLPackage.CLOSURE__EXPRESSION:
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
      case JMLPackage.CLOSURE__DECLARED_FORMAL_PARAMETERS:
        return getDeclaredFormalParameters();
      case JMLPackage.CLOSURE__EXPLICIT_SYNTAX:
        return isExplicitSyntax();
      case JMLPackage.CLOSURE__EXPRESSION:
        return getExpression();
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
      case JMLPackage.CLOSURE__DECLARED_FORMAL_PARAMETERS:
        getDeclaredFormalParameters().clear();
        getDeclaredFormalParameters().addAll((Collection<? extends ValidID>)newValue);
        return;
      case JMLPackage.CLOSURE__EXPLICIT_SYNTAX:
        setExplicitSyntax((Boolean)newValue);
        return;
      case JMLPackage.CLOSURE__EXPRESSION:
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
      case JMLPackage.CLOSURE__DECLARED_FORMAL_PARAMETERS:
        getDeclaredFormalParameters().clear();
        return;
      case JMLPackage.CLOSURE__EXPLICIT_SYNTAX:
        setExplicitSyntax(EXPLICIT_SYNTAX_EDEFAULT);
        return;
      case JMLPackage.CLOSURE__EXPRESSION:
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
      case JMLPackage.CLOSURE__DECLARED_FORMAL_PARAMETERS:
        return declaredFormalParameters != null && !declaredFormalParameters.isEmpty();
      case JMLPackage.CLOSURE__EXPLICIT_SYNTAX:
        return explicitSyntax != EXPLICIT_SYNTAX_EDEFAULT;
      case JMLPackage.CLOSURE__EXPRESSION:
        return expression != null;
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
    result.append(" (explicitSyntax: ");
    result.append(explicitSyntax);
    result.append(')');
    return result.toString();
  }

} //ClosureImpl
