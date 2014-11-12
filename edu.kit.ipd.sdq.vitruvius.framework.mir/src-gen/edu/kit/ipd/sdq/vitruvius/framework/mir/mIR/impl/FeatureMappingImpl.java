/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl#getMappedElements <em>Mapped Elements</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl#getWhens <em>Whens</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl#getWiths <em>Withs</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.FeatureMappingImpl#getWheres <em>Wheres</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureMappingImpl extends MinimalEObjectImpl.Container implements FeatureMapping
{
  /**
   * The cached value of the '{@link #getMappedElements() <em>Mapped Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMappedElements()
   * @generated
   * @ordered
   */
  protected EList<NamedFeature> mappedElements;

  /**
   * The cached value of the '{@link #getWhens() <em>Whens</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWhens()
   * @generated
   * @ordered
   */
  protected EList<EObject> whens;

  /**
   * The cached value of the '{@link #getWiths() <em>Withs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWiths()
   * @generated
   * @ordered
   */
  protected EList<FeatureMapping> withs;

  /**
   * The cached value of the '{@link #getWheres() <em>Wheres</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWheres()
   * @generated
   * @ordered
   */
  protected EList<EObject> wheres;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FeatureMappingImpl()
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
    return MIRPackage.Literals.FEATURE_MAPPING;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<NamedFeature> getMappedElements()
  {
    if (mappedElements == null)
    {
      mappedElements = new EObjectContainmentEList<NamedFeature>(NamedFeature.class, this, MIRPackage.FEATURE_MAPPING__MAPPED_ELEMENTS);
    }
    return mappedElements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<EObject> getWhens()
  {
    if (whens == null)
    {
      whens = new EObjectContainmentEList<EObject>(EObject.class, this, MIRPackage.FEATURE_MAPPING__WHENS);
    }
    return whens;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FeatureMapping> getWiths()
  {
    if (withs == null)
    {
      withs = new EObjectContainmentEList<FeatureMapping>(FeatureMapping.class, this, MIRPackage.FEATURE_MAPPING__WITHS);
    }
    return withs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<EObject> getWheres()
  {
    if (wheres == null)
    {
      wheres = new EObjectContainmentEList<EObject>(EObject.class, this, MIRPackage.FEATURE_MAPPING__WHERES);
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
      case MIRPackage.FEATURE_MAPPING__MAPPED_ELEMENTS:
        return ((InternalEList<?>)getMappedElements()).basicRemove(otherEnd, msgs);
      case MIRPackage.FEATURE_MAPPING__WHENS:
        return ((InternalEList<?>)getWhens()).basicRemove(otherEnd, msgs);
      case MIRPackage.FEATURE_MAPPING__WITHS:
        return ((InternalEList<?>)getWiths()).basicRemove(otherEnd, msgs);
      case MIRPackage.FEATURE_MAPPING__WHERES:
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
      case MIRPackage.FEATURE_MAPPING__MAPPED_ELEMENTS:
        return getMappedElements();
      case MIRPackage.FEATURE_MAPPING__WHENS:
        return getWhens();
      case MIRPackage.FEATURE_MAPPING__WITHS:
        return getWiths();
      case MIRPackage.FEATURE_MAPPING__WHERES:
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
      case MIRPackage.FEATURE_MAPPING__MAPPED_ELEMENTS:
        getMappedElements().clear();
        getMappedElements().addAll((Collection<? extends NamedFeature>)newValue);
        return;
      case MIRPackage.FEATURE_MAPPING__WHENS:
        getWhens().clear();
        getWhens().addAll((Collection<? extends EObject>)newValue);
        return;
      case MIRPackage.FEATURE_MAPPING__WITHS:
        getWiths().clear();
        getWiths().addAll((Collection<? extends FeatureMapping>)newValue);
        return;
      case MIRPackage.FEATURE_MAPPING__WHERES:
        getWheres().clear();
        getWheres().addAll((Collection<? extends EObject>)newValue);
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
      case MIRPackage.FEATURE_MAPPING__MAPPED_ELEMENTS:
        getMappedElements().clear();
        return;
      case MIRPackage.FEATURE_MAPPING__WHENS:
        getWhens().clear();
        return;
      case MIRPackage.FEATURE_MAPPING__WITHS:
        getWiths().clear();
        return;
      case MIRPackage.FEATURE_MAPPING__WHERES:
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
      case MIRPackage.FEATURE_MAPPING__MAPPED_ELEMENTS:
        return mappedElements != null && !mappedElements.isEmpty();
      case MIRPackage.FEATURE_MAPPING__WHENS:
        return whens != null && !whens.isEmpty();
      case MIRPackage.FEATURE_MAPPING__WITHS:
        return withs != null && !withs.isEmpty();
      case MIRPackage.FEATURE_MAPPING__WHERES:
        return wheres != null && !wheres.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //FeatureMappingImpl
