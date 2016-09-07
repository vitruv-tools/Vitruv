/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Assignment;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl#getTypeForVariableDeclaration <em>Type For Variable Declaration</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl#getValue <em>Value</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AssignmentImpl#getAssignable <em>Assignable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssignmentImpl extends ExpressionImpl implements Assignment
{
  /**
   * The default value of the '{@link #getTypeForVariableDeclaration() <em>Type For Variable Declaration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeForVariableDeclaration()
   * @generated
   * @ordered
   */
  protected static final String TYPE_FOR_VARIABLE_DECLARATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTypeForVariableDeclaration() <em>Type For Variable Declaration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeForVariableDeclaration()
   * @generated
   * @ordered
   */
  protected String typeForVariableDeclaration = TYPE_FOR_VARIABLE_DECLARATION_EDEFAULT;

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
   * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected Expression value;

  /**
   * The cached value of the '{@link #getAssignable() <em>Assignable</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssignable()
   * @generated
   * @ordered
   */
  protected Expression assignable;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AssignmentImpl()
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
    return JMLPackage.Literals.ASSIGNMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTypeForVariableDeclaration()
  {
    return typeForVariableDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTypeForVariableDeclaration(String newTypeForVariableDeclaration)
  {
    String oldTypeForVariableDeclaration = typeForVariableDeclaration;
    typeForVariableDeclaration = newTypeForVariableDeclaration;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION, oldTypeForVariableDeclaration, typeForVariableDeclaration));
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ASSIGNMENT__FEATURE, oldFeature, feature));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetValue(Expression newValue, NotificationChain msgs)
  {
    Expression oldValue = value;
    value = newValue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ASSIGNMENT__VALUE, oldValue, newValue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(Expression newValue)
  {
    if (newValue != value)
    {
      NotificationChain msgs = null;
      if (value != null)
        msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ASSIGNMENT__VALUE, null, msgs);
      if (newValue != null)
        msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ASSIGNMENT__VALUE, null, msgs);
      msgs = basicSetValue(newValue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ASSIGNMENT__VALUE, newValue, newValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getAssignable()
  {
    return assignable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAssignable(Expression newAssignable, NotificationChain msgs)
  {
    Expression oldAssignable = assignable;
    assignable = newAssignable;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ASSIGNMENT__ASSIGNABLE, oldAssignable, newAssignable);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAssignable(Expression newAssignable)
  {
    if (newAssignable != assignable)
    {
      NotificationChain msgs = null;
      if (assignable != null)
        msgs = ((InternalEObject)assignable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ASSIGNMENT__ASSIGNABLE, null, msgs);
      if (newAssignable != null)
        msgs = ((InternalEObject)newAssignable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ASSIGNMENT__ASSIGNABLE, null, msgs);
      msgs = basicSetAssignable(newAssignable, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ASSIGNMENT__ASSIGNABLE, newAssignable, newAssignable));
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
      case JMLPackage.ASSIGNMENT__VALUE:
        return basicSetValue(null, msgs);
      case JMLPackage.ASSIGNMENT__ASSIGNABLE:
        return basicSetAssignable(null, msgs);
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
      case JMLPackage.ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION:
        return getTypeForVariableDeclaration();
      case JMLPackage.ASSIGNMENT__FEATURE:
        return getFeature();
      case JMLPackage.ASSIGNMENT__VALUE:
        return getValue();
      case JMLPackage.ASSIGNMENT__ASSIGNABLE:
        return getAssignable();
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
      case JMLPackage.ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION:
        setTypeForVariableDeclaration((String)newValue);
        return;
      case JMLPackage.ASSIGNMENT__FEATURE:
        setFeature((String)newValue);
        return;
      case JMLPackage.ASSIGNMENT__VALUE:
        setValue((Expression)newValue);
        return;
      case JMLPackage.ASSIGNMENT__ASSIGNABLE:
        setAssignable((Expression)newValue);
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
      case JMLPackage.ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION:
        setTypeForVariableDeclaration(TYPE_FOR_VARIABLE_DECLARATION_EDEFAULT);
        return;
      case JMLPackage.ASSIGNMENT__FEATURE:
        setFeature(FEATURE_EDEFAULT);
        return;
      case JMLPackage.ASSIGNMENT__VALUE:
        setValue((Expression)null);
        return;
      case JMLPackage.ASSIGNMENT__ASSIGNABLE:
        setAssignable((Expression)null);
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
      case JMLPackage.ASSIGNMENT__TYPE_FOR_VARIABLE_DECLARATION:
        return TYPE_FOR_VARIABLE_DECLARATION_EDEFAULT == null ? typeForVariableDeclaration != null : !TYPE_FOR_VARIABLE_DECLARATION_EDEFAULT.equals(typeForVariableDeclaration);
      case JMLPackage.ASSIGNMENT__FEATURE:
        return FEATURE_EDEFAULT == null ? feature != null : !FEATURE_EDEFAULT.equals(feature);
      case JMLPackage.ASSIGNMENT__VALUE:
        return value != null;
      case JMLPackage.ASSIGNMENT__ASSIGNABLE:
        return assignable != null;
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
    result.append(" (typeForVariableDeclaration: ");
    result.append(typeForVariableDeclaration);
    result.append(", feature: ");
    result.append(feature);
    result.append(')');
    return result.toString();
  }

} //AssignmentImpl
