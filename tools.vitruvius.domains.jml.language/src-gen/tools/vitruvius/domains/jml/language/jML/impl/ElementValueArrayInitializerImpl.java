/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ElementValue;
import tools.vitruvius.domains.jml.language.jML.ElementValueArrayInitializer;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Value Array Initializer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValueArrayInitializerImpl#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementValueArrayInitializerImpl extends ElementValueImpl implements ElementValueArrayInitializer
{
  /**
   * The cached value of the '{@link #getElementvalue() <em>Elementvalue</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElementvalue()
   * @generated
   * @ordered
   */
  protected EList<ElementValue> elementvalue;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ElementValueArrayInitializerImpl()
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
    return JMLPackage.Literals.ELEMENT_VALUE_ARRAY_INITIALIZER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ElementValue> getElementvalue()
  {
    if (elementvalue == null)
    {
      elementvalue = new EObjectContainmentEList<ElementValue>(ElementValue.class, this, JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE);
    }
    return elementvalue;
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
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE:
        return ((InternalEList<?>)getElementvalue()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE:
        return getElementvalue();
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
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE:
        getElementvalue().clear();
        getElementvalue().addAll((Collection<? extends ElementValue>)newValue);
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
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE:
        getElementvalue().clear();
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
      case JMLPackage.ELEMENT_VALUE_ARRAY_INITIALIZER__ELEMENTVALUE:
        return elementvalue != null && !elementvalue.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ElementValueArrayInitializerImpl
