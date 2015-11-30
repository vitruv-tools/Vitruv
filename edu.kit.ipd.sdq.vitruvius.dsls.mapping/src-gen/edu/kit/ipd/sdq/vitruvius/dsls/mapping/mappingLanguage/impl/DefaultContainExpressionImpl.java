/**
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ContextVariable;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.FeatureOfContextVariable;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Default Contain Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.DefaultContainExpressionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.DefaultContainExpressionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.DefaultContainExpressionImpl#getResource <em>Resource</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DefaultContainExpressionImpl extends ConstraintExpressionImpl implements DefaultContainExpression
{
  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected ContextVariable target;

  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
  protected FeatureOfContextVariable source;

  /**
   * The default value of the '{@link #getResource() <em>Resource</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResource()
   * @generated
   * @ordered
   */
  protected static final String RESOURCE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getResource() <em>Resource</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResource()
   * @generated
   * @ordered
   */
  protected String resource = RESOURCE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DefaultContainExpressionImpl()
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
    return MappingLanguagePackage.Literals.DEFAULT_CONTAIN_EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextVariable getTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTarget(ContextVariable newTarget, NotificationChain msgs)
  {
    ContextVariable oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET, oldTarget, newTarget);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(ContextVariable newTarget)
  {
    if (newTarget != target)
    {
      NotificationChain msgs = null;
      if (target != null)
        msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET, null, msgs);
      if (newTarget != null)
        msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET, null, msgs);
      msgs = basicSetTarget(newTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET, newTarget, newTarget));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureOfContextVariable getSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSource(FeatureOfContextVariable newSource, NotificationChain msgs)
  {
    FeatureOfContextVariable oldSource = source;
    source = newSource;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE, oldSource, newSource);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSource(FeatureOfContextVariable newSource)
  {
    if (newSource != source)
    {
      NotificationChain msgs = null;
      if (source != null)
        msgs = ((InternalEObject)source).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE, null, msgs);
      if (newSource != null)
        msgs = ((InternalEObject)newSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE, null, msgs);
      msgs = basicSetSource(newSource, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE, newSource, newSource));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getResource()
  {
    return resource;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setResource(String newResource)
  {
    String oldResource = resource;
    resource = newResource;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__RESOURCE, oldResource, resource));
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
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET:
        return basicSetTarget(null, msgs);
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE:
        return basicSetSource(null, msgs);
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
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET:
        return getTarget();
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE:
        return getSource();
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__RESOURCE:
        return getResource();
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
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET:
        setTarget((ContextVariable)newValue);
        return;
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE:
        setSource((FeatureOfContextVariable)newValue);
        return;
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__RESOURCE:
        setResource((String)newValue);
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
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET:
        setTarget((ContextVariable)null);
        return;
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE:
        setSource((FeatureOfContextVariable)null);
        return;
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__RESOURCE:
        setResource(RESOURCE_EDEFAULT);
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
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__TARGET:
        return target != null;
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__SOURCE:
        return source != null;
      case MappingLanguagePackage.DEFAULT_CONTAIN_EXPRESSION__RESOURCE:
        return RESOURCE_EDEFAULT == null ? resource != null : !RESOURCE_EDEFAULT.equals(resource);
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
    result.append(" (resource: ");
    result.append(resource);
    result.append(')');
    return result.toString();
  }

} //DefaultContainExpressionImpl
