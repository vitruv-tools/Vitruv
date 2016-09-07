/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest;
import tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest;
import tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation Method Or Constant Rest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodOrConstantRestImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodOrConstantRestImpl#getConstant <em>Constant</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationMethodOrConstantRestImpl extends TypedImpl implements AnnotationMethodOrConstantRest
{
  /**
   * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMethod()
   * @generated
   * @ordered
   */
  protected AnnotationMethodRest method;

  /**
   * The cached value of the '{@link #getConstant() <em>Constant</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstant()
   * @generated
   * @ordered
   */
  protected AnnotationConstantRest constant;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AnnotationMethodOrConstantRestImpl()
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
    return JMLPackage.Literals.ANNOTATION_METHOD_OR_CONSTANT_REST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationMethodRest getMethod()
  {
    return method;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMethod(AnnotationMethodRest newMethod, NotificationChain msgs)
  {
    AnnotationMethodRest oldMethod = method;
    method = newMethod;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD, oldMethod, newMethod);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMethod(AnnotationMethodRest newMethod)
  {
    if (newMethod != method)
    {
      NotificationChain msgs = null;
      if (method != null)
        msgs = ((InternalEObject)method).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD, null, msgs);
      if (newMethod != null)
        msgs = ((InternalEObject)newMethod).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD, null, msgs);
      msgs = basicSetMethod(newMethod, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD, newMethod, newMethod));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnotationConstantRest getConstant()
  {
    return constant;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstant(AnnotationConstantRest newConstant, NotificationChain msgs)
  {
    AnnotationConstantRest oldConstant = constant;
    constant = newConstant;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT, oldConstant, newConstant);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstant(AnnotationConstantRest newConstant)
  {
    if (newConstant != constant)
    {
      NotificationChain msgs = null;
      if (constant != null)
        msgs = ((InternalEObject)constant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT, null, msgs);
      if (newConstant != null)
        msgs = ((InternalEObject)newConstant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT, null, msgs);
      msgs = basicSetConstant(newConstant, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT, newConstant, newConstant));
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
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD:
        return basicSetMethod(null, msgs);
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT:
        return basicSetConstant(null, msgs);
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
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD:
        return getMethod();
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT:
        return getConstant();
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
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD:
        setMethod((AnnotationMethodRest)newValue);
        return;
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT:
        setConstant((AnnotationConstantRest)newValue);
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
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD:
        setMethod((AnnotationMethodRest)null);
        return;
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT:
        setConstant((AnnotationConstantRest)null);
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
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__METHOD:
        return method != null;
      case JMLPackage.ANNOTATION_METHOD_OR_CONSTANT_REST__CONSTANT:
        return constant != null;
    }
    return super.eIsSet(featureID);
  }

} //AnnotationMethodOrConstantRestImpl
