/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement;

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
 * An implementation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl#getMappedElements <em>Mapped Elements</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingImpl#getConstraints <em>Constraints</em>}</li>
 * </ul>
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
  protected EList<TypedElement> mappedElements;

  /**
   * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstraints()
   * @generated
   * @ordered
   */
  protected MappingBody constraints;

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
  public EList<TypedElement> getMappedElements()
  {
    if (mappedElements == null)
    {
      mappedElements = new EObjectContainmentEList<TypedElement>(TypedElement.class, this, MIRPackage.MAPPING__MAPPED_ELEMENTS);
    }
    return mappedElements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MappingBody getConstraints()
  {
    return constraints;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstraints(MappingBody newConstraints, NotificationChain msgs)
  {
    MappingBody oldConstraints = constraints;
    constraints = newConstraints;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING__CONSTRAINTS, oldConstraints, newConstraints);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstraints(MappingBody newConstraints)
  {
    if (newConstraints != constraints)
    {
      NotificationChain msgs = null;
      if (constraints != null)
        msgs = ((InternalEObject)constraints).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.MAPPING__CONSTRAINTS, null, msgs);
      if (newConstraints != null)
        msgs = ((InternalEObject)newConstraints).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.MAPPING__CONSTRAINTS, null, msgs);
      msgs = basicSetConstraints(newConstraints, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING__CONSTRAINTS, newConstraints, newConstraints));
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
      case MIRPackage.MAPPING__CONSTRAINTS:
        return basicSetConstraints(null, msgs);
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
      case MIRPackage.MAPPING__CONSTRAINTS:
        return getConstraints();
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
        getMappedElements().addAll((Collection<? extends TypedElement>)newValue);
        return;
      case MIRPackage.MAPPING__CONSTRAINTS:
        setConstraints((MappingBody)newValue);
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
      case MIRPackage.MAPPING__CONSTRAINTS:
        setConstraints((MappingBody)null);
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
      case MIRPackage.MAPPING__CONSTRAINTS:
        return constraints != null;
    }
    return super.eIsSet(featureID);
  }

} //MappingImpl
