/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.CasePart;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl#getTypeGuard <em>Type Guard</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl#getCase <em>Case</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CasePartImpl#getThen <em>Then</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CasePartImpl extends MinimalEObjectImpl.Container implements CasePart
{
  /**
   * The cached value of the '{@link #getTypeGuard() <em>Type Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeGuard()
   * @generated
   * @ordered
   */
  protected JvmTypeReference typeGuard;

  /**
   * The cached value of the '{@link #getCase() <em>Case</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCase()
   * @generated
   * @ordered
   */
  protected Expression case_;

  /**
   * The cached value of the '{@link #getThen() <em>Then</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getThen()
   * @generated
   * @ordered
   */
  protected Expression then;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CasePartImpl()
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
    return JMLPackage.Literals.CASE_PART;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JvmTypeReference getTypeGuard()
  {
    return typeGuard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTypeGuard(JvmTypeReference newTypeGuard, NotificationChain msgs)
  {
    JvmTypeReference oldTypeGuard = typeGuard;
    typeGuard = newTypeGuard;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CASE_PART__TYPE_GUARD, oldTypeGuard, newTypeGuard);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTypeGuard(JvmTypeReference newTypeGuard)
  {
    if (newTypeGuard != typeGuard)
    {
      NotificationChain msgs = null;
      if (typeGuard != null)
        msgs = ((InternalEObject)typeGuard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CASE_PART__TYPE_GUARD, null, msgs);
      if (newTypeGuard != null)
        msgs = ((InternalEObject)newTypeGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CASE_PART__TYPE_GUARD, null, msgs);
      msgs = basicSetTypeGuard(newTypeGuard, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CASE_PART__TYPE_GUARD, newTypeGuard, newTypeGuard));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getCase()
  {
    return case_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCase(Expression newCase, NotificationChain msgs)
  {
    Expression oldCase = case_;
    case_ = newCase;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CASE_PART__CASE, oldCase, newCase);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCase(Expression newCase)
  {
    if (newCase != case_)
    {
      NotificationChain msgs = null;
      if (case_ != null)
        msgs = ((InternalEObject)case_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CASE_PART__CASE, null, msgs);
      if (newCase != null)
        msgs = ((InternalEObject)newCase).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CASE_PART__CASE, null, msgs);
      msgs = basicSetCase(newCase, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CASE_PART__CASE, newCase, newCase));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getThen()
  {
    return then;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetThen(Expression newThen, NotificationChain msgs)
  {
    Expression oldThen = then;
    then = newThen;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.CASE_PART__THEN, oldThen, newThen);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setThen(Expression newThen)
  {
    if (newThen != then)
    {
      NotificationChain msgs = null;
      if (then != null)
        msgs = ((InternalEObject)then).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CASE_PART__THEN, null, msgs);
      if (newThen != null)
        msgs = ((InternalEObject)newThen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.CASE_PART__THEN, null, msgs);
      msgs = basicSetThen(newThen, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.CASE_PART__THEN, newThen, newThen));
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
      case JMLPackage.CASE_PART__TYPE_GUARD:
        return basicSetTypeGuard(null, msgs);
      case JMLPackage.CASE_PART__CASE:
        return basicSetCase(null, msgs);
      case JMLPackage.CASE_PART__THEN:
        return basicSetThen(null, msgs);
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
      case JMLPackage.CASE_PART__TYPE_GUARD:
        return getTypeGuard();
      case JMLPackage.CASE_PART__CASE:
        return getCase();
      case JMLPackage.CASE_PART__THEN:
        return getThen();
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
      case JMLPackage.CASE_PART__TYPE_GUARD:
        setTypeGuard((JvmTypeReference)newValue);
        return;
      case JMLPackage.CASE_PART__CASE:
        setCase((Expression)newValue);
        return;
      case JMLPackage.CASE_PART__THEN:
        setThen((Expression)newValue);
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
      case JMLPackage.CASE_PART__TYPE_GUARD:
        setTypeGuard((JvmTypeReference)null);
        return;
      case JMLPackage.CASE_PART__CASE:
        setCase((Expression)null);
        return;
      case JMLPackage.CASE_PART__THEN:
        setThen((Expression)null);
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
      case JMLPackage.CASE_PART__TYPE_GUARD:
        return typeGuard != null;
      case JMLPackage.CASE_PART__CASE:
        return case_ != null;
      case JMLPackage.CASE_PART__THEN:
        return then != null;
    }
    return super.eIsSet(featureID);
  }

} //CasePartImpl
