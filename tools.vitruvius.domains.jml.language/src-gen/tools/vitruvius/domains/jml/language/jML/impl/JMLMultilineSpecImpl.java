/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multiline Spec</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMultilineSpecImpl#getModelElement <em>Model Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLMultilineSpecImpl extends JMLSpecifiedElementImpl implements JMLMultilineSpec
{
  /**
   * The cached value of the '{@link #getModelElement() <em>Model Element</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModelElement()
   * @generated
   * @ordered
   */
  protected JMLSpecificationOnlyElementWithModifier modelElement;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLMultilineSpecImpl()
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
    return JMLPackage.Literals.JML_MULTILINE_SPEC;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSpecificationOnlyElementWithModifier getModelElement()
  {
    return modelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetModelElement(JMLSpecificationOnlyElementWithModifier newModelElement, NotificationChain msgs)
  {
    JMLSpecificationOnlyElementWithModifier oldModelElement = modelElement;
    modelElement = newModelElement;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT, oldModelElement, newModelElement);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setModelElement(JMLSpecificationOnlyElementWithModifier newModelElement)
  {
    if (newModelElement != modelElement)
    {
      NotificationChain msgs = null;
      if (modelElement != null)
        msgs = ((InternalEObject)modelElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT, null, msgs);
      if (newModelElement != null)
        msgs = ((InternalEObject)newModelElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT, null, msgs);
      msgs = basicSetModelElement(newModelElement, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT, newModelElement, newModelElement));
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
      case JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT:
        return basicSetModelElement(null, msgs);
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
      case JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT:
        return getModelElement();
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
      case JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT:
        setModelElement((JMLSpecificationOnlyElementWithModifier)newValue);
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
      case JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT:
        setModelElement((JMLSpecificationOnlyElementWithModifier)null);
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
      case JMLPackage.JML_MULTILINE_SPEC__MODEL_ELEMENT:
        return modelElement != null;
    }
    return super.eIsSet(featureID);
  }

} //JMLMultilineSpecImpl
