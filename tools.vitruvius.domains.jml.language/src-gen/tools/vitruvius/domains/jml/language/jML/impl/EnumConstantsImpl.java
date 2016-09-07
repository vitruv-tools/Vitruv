/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.EnumConstant;
import tools.vitruvius.domains.jml.language.jML.EnumConstants;
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
 * An implementation of the model object '<em><b>Enum Constants</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumConstantsImpl#getEnumconstant <em>Enumconstant</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumConstantsImpl extends MinimalEObjectImpl.Container implements EnumConstants
{
  /**
   * The cached value of the '{@link #getEnumconstant() <em>Enumconstant</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnumconstant()
   * @generated
   * @ordered
   */
  protected EList<EnumConstant> enumconstant;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnumConstantsImpl()
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
    return JMLPackage.Literals.ENUM_CONSTANTS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<EnumConstant> getEnumconstant()
  {
    if (enumconstant == null)
    {
      enumconstant = new EObjectContainmentEList<EnumConstant>(EnumConstant.class, this, JMLPackage.ENUM_CONSTANTS__ENUMCONSTANT);
    }
    return enumconstant;
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
      case JMLPackage.ENUM_CONSTANTS__ENUMCONSTANT:
        return ((InternalEList<?>)getEnumconstant()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.ENUM_CONSTANTS__ENUMCONSTANT:
        return getEnumconstant();
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
      case JMLPackage.ENUM_CONSTANTS__ENUMCONSTANT:
        getEnumconstant().clear();
        getEnumconstant().addAll((Collection<? extends EnumConstant>)newValue);
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
      case JMLPackage.ENUM_CONSTANTS__ENUMCONSTANT:
        getEnumconstant().clear();
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
      case JMLPackage.ENUM_CONSTANTS__ENUMCONSTANT:
        return enumconstant != null && !enumconstant.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //EnumConstantsImpl
