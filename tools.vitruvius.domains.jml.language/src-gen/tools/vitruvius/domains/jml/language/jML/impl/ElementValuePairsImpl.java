/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ElementValuePair;
import tools.vitruvius.domains.jml.language.jML.ElementValuePairs;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Value Pairs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ElementValuePairsImpl#getElementvaluepair <em>Elementvaluepair</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementValuePairsImpl extends MinimalEObjectImpl.Container implements ElementValuePairs
{
  /**
   * The cached value of the '{@link #getElementvaluepair() <em>Elementvaluepair</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElementvaluepair()
   * @generated
   * @ordered
   */
  protected EList<ElementValuePair> elementvaluepair;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ElementValuePairsImpl()
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
    return JMLPackage.Literals.ELEMENT_VALUE_PAIRS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ElementValuePair> getElementvaluepair()
  {
    if (elementvaluepair == null)
    {
      elementvaluepair = new EObjectContainmentEList<ElementValuePair>(ElementValuePair.class, this, JMLPackage.ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR);
    }
    return elementvaluepair;
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
      case JMLPackage.ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR:
        return ((InternalEList<?>)getElementvaluepair()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR:
        return getElementvaluepair();
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
      case JMLPackage.ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR:
        getElementvaluepair().clear();
        getElementvaluepair().addAll((Collection<? extends ElementValuePair>)newValue);
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
      case JMLPackage.ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR:
        getElementvaluepair().clear();
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
      case JMLPackage.ELEMENT_VALUE_PAIRS__ELEMENTVALUEPAIR:
        return elementvaluepair != null && !elementvaluepair.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ElementValuePairsImpl
