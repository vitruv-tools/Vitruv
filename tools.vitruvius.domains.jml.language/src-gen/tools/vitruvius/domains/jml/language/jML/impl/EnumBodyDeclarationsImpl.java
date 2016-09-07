/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration;
import tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations;
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
 * An implementation of the model object '<em><b>Enum Body Declarations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.EnumBodyDeclarationsImpl#getClassbodydeclaration <em>Classbodydeclaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumBodyDeclarationsImpl extends MinimalEObjectImpl.Container implements EnumBodyDeclarations
{
  /**
   * The cached value of the '{@link #getClassbodydeclaration() <em>Classbodydeclaration</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClassbodydeclaration()
   * @generated
   * @ordered
   */
  protected EList<ClassBodyDeclaration> classbodydeclaration;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnumBodyDeclarationsImpl()
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
    return JMLPackage.Literals.ENUM_BODY_DECLARATIONS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ClassBodyDeclaration> getClassbodydeclaration()
  {
    if (classbodydeclaration == null)
    {
      classbodydeclaration = new EObjectContainmentEList<ClassBodyDeclaration>(ClassBodyDeclaration.class, this, JMLPackage.ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION);
    }
    return classbodydeclaration;
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
      case JMLPackage.ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION:
        return ((InternalEList<?>)getClassbodydeclaration()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION:
        return getClassbodydeclaration();
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
      case JMLPackage.ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION:
        getClassbodydeclaration().clear();
        getClassbodydeclaration().addAll((Collection<? extends ClassBodyDeclaration>)newValue);
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
      case JMLPackage.ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION:
        getClassbodydeclaration().clear();
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
      case JMLPackage.ENUM_BODY_DECLARATIONS__CLASSBODYDECLARATION:
        return classbodydeclaration != null && !classbodydeclaration.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //EnumBodyDeclarationsImpl
