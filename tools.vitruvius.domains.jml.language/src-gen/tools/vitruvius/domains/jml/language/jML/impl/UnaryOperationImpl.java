/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.UnaryOperation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unary Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.UnaryOperationImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.UnaryOperationImpl#getOperand <em>Operand</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnaryOperationImpl extends ExpressionImpl implements UnaryOperation
{
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
   * The cached value of the '{@link #getOperand() <em>Operand</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperand()
   * @generated
   * @ordered
   */
  protected Expression operand;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected UnaryOperationImpl()
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
    return JMLPackage.Literals.UNARY_OPERATION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.UNARY_OPERATION__FEATURE, oldFeature, feature));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getOperand()
  {
    return operand;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOperand(Expression newOperand, NotificationChain msgs)
  {
    Expression oldOperand = operand;
    operand = newOperand;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.UNARY_OPERATION__OPERAND, oldOperand, newOperand);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperand(Expression newOperand)
  {
    if (newOperand != operand)
    {
      NotificationChain msgs = null;
      if (operand != null)
        msgs = ((InternalEObject)operand).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.UNARY_OPERATION__OPERAND, null, msgs);
      if (newOperand != null)
        msgs = ((InternalEObject)newOperand).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.UNARY_OPERATION__OPERAND, null, msgs);
      msgs = basicSetOperand(newOperand, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.UNARY_OPERATION__OPERAND, newOperand, newOperand));
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
      case JMLPackage.UNARY_OPERATION__OPERAND:
        return basicSetOperand(null, msgs);
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
      case JMLPackage.UNARY_OPERATION__FEATURE:
        return getFeature();
      case JMLPackage.UNARY_OPERATION__OPERAND:
        return getOperand();
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
      case JMLPackage.UNARY_OPERATION__FEATURE:
        setFeature((String)newValue);
        return;
      case JMLPackage.UNARY_OPERATION__OPERAND:
        setOperand((Expression)newValue);
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
      case JMLPackage.UNARY_OPERATION__FEATURE:
        setFeature(FEATURE_EDEFAULT);
        return;
      case JMLPackage.UNARY_OPERATION__OPERAND:
        setOperand((Expression)null);
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
      case JMLPackage.UNARY_OPERATION__FEATURE:
        return FEATURE_EDEFAULT == null ? feature != null : !FEATURE_EDEFAULT.equals(feature);
      case JMLPackage.UNARY_OPERATION__OPERAND:
        return operand != null;
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
    result.append(" (feature: ");
    result.append(feature);
    result.append(')');
    return result.toString();
  }

} //UnaryOperationImpl
