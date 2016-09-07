/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.TypeArgument;
import tools.vitruvius.domains.jml.language.jML.TypeArguments;

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
 * An implementation of the model object '<em><b>Type Arguments</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.TypeArgumentsImpl#getTypeargument <em>Typeargument</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeArgumentsImpl extends MinimalEObjectImpl.Container implements TypeArguments
{
  /**
   * The cached value of the '{@link #getTypeargument() <em>Typeargument</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypeargument()
   * @generated
   * @ordered
   */
  protected EList<TypeArgument> typeargument;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TypeArgumentsImpl()
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
    return JMLPackage.Literals.TYPE_ARGUMENTS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TypeArgument> getTypeargument()
  {
    if (typeargument == null)
    {
      typeargument = new EObjectContainmentEList<TypeArgument>(TypeArgument.class, this, JMLPackage.TYPE_ARGUMENTS__TYPEARGUMENT);
    }
    return typeargument;
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
      case JMLPackage.TYPE_ARGUMENTS__TYPEARGUMENT:
        return ((InternalEList<?>)getTypeargument()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.TYPE_ARGUMENTS__TYPEARGUMENT:
        return getTypeargument();
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
      case JMLPackage.TYPE_ARGUMENTS__TYPEARGUMENT:
        getTypeargument().clear();
        getTypeargument().addAll((Collection<? extends TypeArgument>)newValue);
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
      case JMLPackage.TYPE_ARGUMENTS__TYPEARGUMENT:
        getTypeargument().clear();
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
      case JMLPackage.TYPE_ARGUMENTS__TYPEARGUMENT:
        return typeargument != null && !typeargument.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TypeArgumentsImpl
