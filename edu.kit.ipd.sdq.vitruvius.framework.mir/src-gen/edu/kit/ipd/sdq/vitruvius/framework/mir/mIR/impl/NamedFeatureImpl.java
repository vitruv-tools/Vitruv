/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl#getContainingNamedEClass <em>Containing Named EClass</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl#getRepresentedFeature <em>Represented Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.NamedFeatureImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamedFeatureImpl extends MinimalEObjectImpl.Container implements NamedFeature
{
  /**
   * The cached value of the '{@link #getContainingNamedEClass() <em>Containing Named EClass</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContainingNamedEClass()
   * @generated
   * @ordered
   */
  protected NamedEClass containingNamedEClass;

  /**
   * The cached value of the '{@link #getRepresentedFeature() <em>Represented Feature</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRepresentedFeature()
   * @generated
   * @ordered
   */
  protected EStructuralFeature representedFeature;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected NamedFeatureImpl()
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
    return MIRPackage.Literals.NAMED_FEATURE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedEClass getContainingNamedEClass()
  {
    if (containingNamedEClass != null && containingNamedEClass.eIsProxy())
    {
      InternalEObject oldContainingNamedEClass = (InternalEObject)containingNamedEClass;
      containingNamedEClass = (NamedEClass)eResolveProxy(oldContainingNamedEClass);
      if (containingNamedEClass != oldContainingNamedEClass)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRPackage.NAMED_FEATURE__CONTAINING_NAMED_ECLASS, oldContainingNamedEClass, containingNamedEClass));
      }
    }
    return containingNamedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedEClass basicGetContainingNamedEClass()
  {
    return containingNamedEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setContainingNamedEClass(NamedEClass newContainingNamedEClass)
  {
    NamedEClass oldContainingNamedEClass = containingNamedEClass;
    containingNamedEClass = newContainingNamedEClass;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.NAMED_FEATURE__CONTAINING_NAMED_ECLASS, oldContainingNamedEClass, containingNamedEClass));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EStructuralFeature getRepresentedFeature()
  {
    if (representedFeature != null && representedFeature.eIsProxy())
    {
      InternalEObject oldRepresentedFeature = (InternalEObject)representedFeature;
      representedFeature = (EStructuralFeature)eResolveProxy(oldRepresentedFeature);
      if (representedFeature != oldRepresentedFeature)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRPackage.NAMED_FEATURE__REPRESENTED_FEATURE, oldRepresentedFeature, representedFeature));
      }
    }
    return representedFeature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EStructuralFeature basicGetRepresentedFeature()
  {
    return representedFeature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRepresentedFeature(EStructuralFeature newRepresentedFeature)
  {
    EStructuralFeature oldRepresentedFeature = representedFeature;
    representedFeature = newRepresentedFeature;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.NAMED_FEATURE__REPRESENTED_FEATURE, oldRepresentedFeature, representedFeature));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.NAMED_FEATURE__NAME, oldName, name));
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
      case MIRPackage.NAMED_FEATURE__CONTAINING_NAMED_ECLASS:
        if (resolve) return getContainingNamedEClass();
        return basicGetContainingNamedEClass();
      case MIRPackage.NAMED_FEATURE__REPRESENTED_FEATURE:
        if (resolve) return getRepresentedFeature();
        return basicGetRepresentedFeature();
      case MIRPackage.NAMED_FEATURE__NAME:
        return getName();
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
      case MIRPackage.NAMED_FEATURE__CONTAINING_NAMED_ECLASS:
        setContainingNamedEClass((NamedEClass)newValue);
        return;
      case MIRPackage.NAMED_FEATURE__REPRESENTED_FEATURE:
        setRepresentedFeature((EStructuralFeature)newValue);
        return;
      case MIRPackage.NAMED_FEATURE__NAME:
        setName((String)newValue);
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
      case MIRPackage.NAMED_FEATURE__CONTAINING_NAMED_ECLASS:
        setContainingNamedEClass((NamedEClass)null);
        return;
      case MIRPackage.NAMED_FEATURE__REPRESENTED_FEATURE:
        setRepresentedFeature((EStructuralFeature)null);
        return;
      case MIRPackage.NAMED_FEATURE__NAME:
        setName(NAME_EDEFAULT);
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
      case MIRPackage.NAMED_FEATURE__CONTAINING_NAMED_ECLASS:
        return containingNamedEClass != null;
      case MIRPackage.NAMED_FEATURE__REPRESENTED_FEATURE:
        return representedFeature != null;
      case MIRPackage.NAMED_FEATURE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //NamedFeatureImpl
