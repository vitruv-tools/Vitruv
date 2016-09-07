/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.AnnotationMethodRest;
import tools.vitruvius.domains.jml.language.jML.DefaultValue;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation Method Rest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodRestImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.AnnotationMethodRestImpl#getDefaultvalue <em>Defaultvalue</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationMethodRestImpl extends MinimalEObjectImpl.Container implements AnnotationMethodRest
{
  /**
   * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIdentifier()
   * @generated
   * @ordered
   */
  protected static final String IDENTIFIER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIdentifier()
   * @generated
   * @ordered
   */
  protected String identifier = IDENTIFIER_EDEFAULT;

  /**
   * The cached value of the '{@link #getDefaultvalue() <em>Defaultvalue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaultvalue()
   * @generated
   * @ordered
   */
  protected DefaultValue defaultvalue;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AnnotationMethodRestImpl()
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
    return JMLPackage.Literals.ANNOTATION_METHOD_REST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getIdentifier()
  {
    return identifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIdentifier(String newIdentifier)
  {
    String oldIdentifier = identifier;
    identifier = newIdentifier;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_REST__IDENTIFIER, oldIdentifier, identifier));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DefaultValue getDefaultvalue()
  {
    return defaultvalue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDefaultvalue(DefaultValue newDefaultvalue, NotificationChain msgs)
  {
    DefaultValue oldDefaultvalue = defaultvalue;
    defaultvalue = newDefaultvalue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE, oldDefaultvalue, newDefaultvalue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDefaultvalue(DefaultValue newDefaultvalue)
  {
    if (newDefaultvalue != defaultvalue)
    {
      NotificationChain msgs = null;
      if (defaultvalue != null)
        msgs = ((InternalEObject)defaultvalue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE, null, msgs);
      if (newDefaultvalue != null)
        msgs = ((InternalEObject)newDefaultvalue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE, null, msgs);
      msgs = basicSetDefaultvalue(newDefaultvalue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE, newDefaultvalue, newDefaultvalue));
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
      case JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE:
        return basicSetDefaultvalue(null, msgs);
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
      case JMLPackage.ANNOTATION_METHOD_REST__IDENTIFIER:
        return getIdentifier();
      case JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE:
        return getDefaultvalue();
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
      case JMLPackage.ANNOTATION_METHOD_REST__IDENTIFIER:
        setIdentifier((String)newValue);
        return;
      case JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE:
        setDefaultvalue((DefaultValue)newValue);
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
      case JMLPackage.ANNOTATION_METHOD_REST__IDENTIFIER:
        setIdentifier(IDENTIFIER_EDEFAULT);
        return;
      case JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE:
        setDefaultvalue((DefaultValue)null);
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
      case JMLPackage.ANNOTATION_METHOD_REST__IDENTIFIER:
        return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
      case JMLPackage.ANNOTATION_METHOD_REST__DEFAULTVALUE:
        return defaultvalue != null;
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
    result.append(" (identifier: ");
    result.append(identifier);
    result.append(')');
    return result.toString();
  }

} //AnnotationMethodRestImpl
