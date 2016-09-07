/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement;
import tools.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier;
import tools.vitruvius.domains.jml.language.jML.VisiblityModifier;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specification Only Element With Modifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementWithModifierImpl#getModifier <em>Modifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecificationOnlyElementWithModifierImpl#getElement <em>Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLSpecificationOnlyElementWithModifierImpl extends MinimalEObjectImpl.Container implements JMLSpecificationOnlyElementWithModifier
{
  /**
   * The cached value of the '{@link #getModifier() <em>Modifier</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModifier()
   * @generated
   * @ordered
   */
  protected EList<VisiblityModifier> modifier;

  /**
   * The cached value of the '{@link #getElement() <em>Element</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected JMLSpecificationOnlyElement element;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLSpecificationOnlyElementWithModifierImpl()
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
    return JMLPackage.Literals.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VisiblityModifier> getModifier()
  {
    if (modifier == null)
    {
      modifier = new EObjectContainmentEList<VisiblityModifier>(VisiblityModifier.class, this, JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER);
    }
    return modifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLSpecificationOnlyElement getElement()
  {
    return element;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElement(JMLSpecificationOnlyElement newElement, NotificationChain msgs)
  {
    JMLSpecificationOnlyElement oldElement = element;
    element = newElement;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT, oldElement, newElement);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElement(JMLSpecificationOnlyElement newElement)
  {
    if (newElement != element)
    {
      NotificationChain msgs = null;
      if (element != null)
        msgs = ((InternalEObject)element).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT, null, msgs);
      if (newElement != null)
        msgs = ((InternalEObject)newElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT, null, msgs);
      msgs = basicSetElement(newElement, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT, newElement, newElement));
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER:
        return ((InternalEList<?>)getModifier()).basicRemove(otherEnd, msgs);
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT:
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER:
        return getModifier();
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT:
        return getElement();
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER:
        getModifier().clear();
        getModifier().addAll((Collection<? extends VisiblityModifier>)newValue);
        return;
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT:
        setElement((JMLSpecificationOnlyElement)newValue);
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER:
        getModifier().clear();
        return;
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT:
        setElement((JMLSpecificationOnlyElement)null);
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
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__MODIFIER:
        return modifier != null && !modifier.isEmpty();
      case JMLPackage.JML_SPECIFICATION_ONLY_ELEMENT_WITH_MODIFIER__ELEMENT:
        return element != null;
    }
    return super.eIsSet(featureID);
  }

} //JMLSpecificationOnlyElementWithModifierImpl
