/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;
import tools.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular;

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
 * An implementation of the model object '<em><b>Specified Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl#getJmlTypeSpecifications <em>Jml Type Specifications</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl#getJmlSpecifications <em>Jml Specifications</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLSpecifiedElementImpl#getElement <em>Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLSpecifiedElementImpl extends ClassBodyDeclarationImpl implements JMLSpecifiedElement
{
  /**
   * The cached value of the '{@link #getJmlTypeSpecifications() <em>Jml Type Specifications</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJmlTypeSpecifications()
   * @generated
   * @ordered
   */
  protected EList<JMLTypeExpressionWithModifier> jmlTypeSpecifications;

  /**
   * The cached value of the '{@link #getJmlSpecifications() <em>Jml Specifications</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJmlSpecifications()
   * @generated
   * @ordered
   */
  protected EList<JMLMethodSpecificationWithModifier> jmlSpecifications;

  /**
   * The cached value of the '{@link #getElement() <em>Element</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElement()
   * @generated
   * @ordered
   */
  protected MemberDeclWithModifierRegular element;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLSpecifiedElementImpl()
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
    return JMLPackage.Literals.JML_SPECIFIED_ELEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<JMLTypeExpressionWithModifier> getJmlTypeSpecifications()
  {
    if (jmlTypeSpecifications == null)
    {
      jmlTypeSpecifications = new EObjectContainmentEList<JMLTypeExpressionWithModifier>(JMLTypeExpressionWithModifier.class, this, JMLPackage.JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS);
    }
    return jmlTypeSpecifications;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<JMLMethodSpecificationWithModifier> getJmlSpecifications()
  {
    if (jmlSpecifications == null)
    {
      jmlSpecifications = new EObjectContainmentEList<JMLMethodSpecificationWithModifier>(JMLMethodSpecificationWithModifier.class, this, JMLPackage.JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS);
    }
    return jmlSpecifications;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MemberDeclWithModifierRegular getElement()
  {
    return element;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetElement(MemberDeclWithModifierRegular newElement, NotificationChain msgs)
  {
    MemberDeclWithModifierRegular oldElement = element;
    element = newElement;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT, oldElement, newElement);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setElement(MemberDeclWithModifierRegular newElement)
  {
    if (newElement != element)
    {
      NotificationChain msgs = null;
      if (element != null)
        msgs = ((InternalEObject)element).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT, null, msgs);
      if (newElement != null)
        msgs = ((InternalEObject)newElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT, null, msgs);
      msgs = basicSetElement(newElement, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT, newElement, newElement));
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
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS:
        return ((InternalEList<?>)getJmlTypeSpecifications()).basicRemove(otherEnd, msgs);
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS:
        return ((InternalEList<?>)getJmlSpecifications()).basicRemove(otherEnd, msgs);
      case JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT:
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
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS:
        return getJmlTypeSpecifications();
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS:
        return getJmlSpecifications();
      case JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT:
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
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS:
        getJmlTypeSpecifications().clear();
        getJmlTypeSpecifications().addAll((Collection<? extends JMLTypeExpressionWithModifier>)newValue);
        return;
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS:
        getJmlSpecifications().clear();
        getJmlSpecifications().addAll((Collection<? extends JMLMethodSpecificationWithModifier>)newValue);
        return;
      case JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT:
        setElement((MemberDeclWithModifierRegular)newValue);
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
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS:
        getJmlTypeSpecifications().clear();
        return;
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS:
        getJmlSpecifications().clear();
        return;
      case JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT:
        setElement((MemberDeclWithModifierRegular)null);
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
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_TYPE_SPECIFICATIONS:
        return jmlTypeSpecifications != null && !jmlTypeSpecifications.isEmpty();
      case JMLPackage.JML_SPECIFIED_ELEMENT__JML_SPECIFICATIONS:
        return jmlSpecifications != null && !jmlSpecifications.isEmpty();
      case JMLPackage.JML_SPECIFIED_ELEMENT__ELEMENT:
        return element != null;
    }
    return super.eIsSet(featureID);
  }

} //JMLSpecifiedElementImpl
