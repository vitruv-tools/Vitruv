/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where;

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
 * An implementation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl#getMappedElements <em>Mapped Elements</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl#getWhens <em>Whens</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl#getWiths <em>Withs</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl#getWheres <em>Wheres</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingImpl extends MinimalEObjectImpl.Container implements Mapping
{
  /**
   * The cached value of the '{@link #getMappedElements() <em>Mapped Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMappedElements()
   * @generated
   * @ordered
   */
  protected EList<MappableElement> mappedElements;

  /**
   * The cached value of the '{@link #getWhens() <em>Whens</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWhens()
   * @generated
   * @ordered
   */
  protected EList<When> whens;

  /**
   * The cached value of the '{@link #getWiths() <em>Withs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWiths()
   * @generated
   * @ordered
   */
  protected EList<Mapping> withs;

  /**
   * The cached value of the '{@link #getWheres() <em>Wheres</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWheres()
   * @generated
   * @ordered
   */
  protected EList<Where> wheres;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MappingImpl()
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
    return MIRPackage.Literals.MAPPING;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MappableElement> getMappedElements()
  {
    if (mappedElements == null)
    {
      mappedElements = new EObjectContainmentEList<MappableElement>(MappableElement.class, this, MIRPackage.MAPPING__MAPPED_ELEMENTS);
    }
    return mappedElements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<When> getWhens()
  {
    if (whens == null)
    {
      whens = new EObjectContainmentEList<When>(When.class, this, MIRPackage.MAPPING__WHENS);
    }
    return whens;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mapping> getWiths()
  {
    if (withs == null)
    {
      withs = new EObjectContainmentEList<Mapping>(Mapping.class, this, MIRPackage.MAPPING__WITHS);
    }
    return withs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Where> getWheres()
  {
    if (wheres == null)
    {
      wheres = new EObjectContainmentEList<Where>(Where.class, this, MIRPackage.MAPPING__WHERES);
    }
    return wheres;
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
      case MIRPackage.MAPPING__MAPPED_ELEMENTS:
        return ((InternalEList<?>)getMappedElements()).basicRemove(otherEnd, msgs);
      case MIRPackage.MAPPING__WHENS:
        return ((InternalEList<?>)getWhens()).basicRemove(otherEnd, msgs);
      case MIRPackage.MAPPING__WITHS:
        return ((InternalEList<?>)getWiths()).basicRemove(otherEnd, msgs);
      case MIRPackage.MAPPING__WHERES:
        return ((InternalEList<?>)getWheres()).basicRemove(otherEnd, msgs);
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
      case MIRPackage.MAPPING__MAPPED_ELEMENTS:
        return getMappedElements();
      case MIRPackage.MAPPING__WHENS:
        return getWhens();
      case MIRPackage.MAPPING__WITHS:
        return getWiths();
      case MIRPackage.MAPPING__WHERES:
        return getWheres();
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
      case MIRPackage.MAPPING__MAPPED_ELEMENTS:
        getMappedElements().clear();
        getMappedElements().addAll((Collection<? extends MappableElement>)newValue);
        return;
      case MIRPackage.MAPPING__WHENS:
        getWhens().clear();
        getWhens().addAll((Collection<? extends When>)newValue);
        return;
      case MIRPackage.MAPPING__WITHS:
        getWiths().clear();
        getWiths().addAll((Collection<? extends Mapping>)newValue);
        return;
      case MIRPackage.MAPPING__WHERES:
        getWheres().clear();
        getWheres().addAll((Collection<? extends Where>)newValue);
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
      case MIRPackage.MAPPING__MAPPED_ELEMENTS:
        getMappedElements().clear();
        return;
      case MIRPackage.MAPPING__WHENS:
        getWhens().clear();
        return;
      case MIRPackage.MAPPING__WITHS:
        getWiths().clear();
        return;
      case MIRPackage.MAPPING__WHERES:
        getWheres().clear();
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
      case MIRPackage.MAPPING__MAPPED_ELEMENTS:
        return mappedElements != null && !mappedElements.isEmpty();
      case MIRPackage.MAPPING__WHENS:
        return whens != null && !whens.isEmpty();
      case MIRPackage.MAPPING__WITHS:
        return withs != null && !withs.isEmpty();
      case MIRPackage.MAPPING__WHERES:
        return wheres != null && !wheres.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MappingImpl
