/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specification Only Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementImpl#isInstance <em>Instance</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementImpl#getElement <em>Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLSpecificationOnlyElementImpl extends MinimalEObjectImpl.Container implements JMLSpecificationOnlyElement
{
  /**
   * The default value of the '{@link #isInstance() <em>Instance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInstance()
   * @generated
   * @ordered
   */
  protected static final boolean INSTANCE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isInstance() <em>Instance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInstance()
   * @generated
   * @ordered
   */
  protected boolean instance = INSTANCE_EDEFAULT;

  /**
   * The cached value of the '{@link #getElement() <em>Element</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected MemberDeclWithModifierSpec element;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLSpecificationOnlyElementImpl()
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
    return JMLPackage.Literals.JML_SPECIFICATION_ONLY_ELEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isInstance()
  {
    return instance;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInstance(boolean newInstance)
  {
    boolean oldInstance = instance;
    instance = newInstance;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE, oldInstance, instance));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDeclWithModifierSpec getElement()
  {
    return element;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElement(MemberDeclWithModifierSpec newElement, NotificationChain msgs)
  {
    MemberDeclWithModifierSpec oldElement = element;
    element = newElement;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT, oldElement, newElement);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElement(MemberDeclWithModifierSpec newElement)
  {
    if (newElement != element)
    {
      NotificationChain msgs = null;
      if (element != null)
        msgs = ((InternalEObject)element).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT, null, msgs);
      if (newElement != null)
        msgs = ((InternalEObject)newElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT, null, msgs);
      msgs = basicSetElement(newElement, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT, newElement, newElement));
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT:
        return basicSetElement(null, msgs);
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE:
        return isInstance();
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT:
        return getElement();
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE:
        setInstance((Boolean)newValue);
        return;
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT:
        setElement((MemberDeclWithModifierSpec)newValue);
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE:
        setInstance(INSTANCE_EDEFAULT);
        return;
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT:
        setElement((MemberDeclWithModifierSpec)null);
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__INSTANCE:
        return instance != INSTANCE_EDEFAULT;
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT__ELEMENT:
        return element != null;
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
    result.append(" (instance: ");
    result.append(instance);
    result.append(')');
    return result.toString();
  }

} //JMLSpecificationOnlyElementImpl
