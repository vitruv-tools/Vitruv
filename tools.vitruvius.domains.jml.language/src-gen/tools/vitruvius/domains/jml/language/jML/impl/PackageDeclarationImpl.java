/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.PackageDeclaration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.PackageDeclarationImpl#getQualifiedname <em>Qualifiedname</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageDeclarationImpl extends MinimalEObjectImpl.Container implements PackageDeclaration
{
  /**
   * The default value of the '{@link #getQualifiedname() <em>Qualifiedname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQualifiedname()
   * @generated
   * @ordered
   */
  protected static final String QUALIFIEDNAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getQualifiedname() <em>Qualifiedname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQualifiedname()
   * @generated
   * @ordered
   */
  protected String qualifiedname = QUALIFIEDNAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PackageDeclarationImpl()
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
    return JMLPackage.Literals.PACKAGE_DECLARATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getQualifiedname()
  {
    return qualifiedname;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQualifiedname(String newQualifiedname)
  {
    String oldQualifiedname = qualifiedname;
    qualifiedname = newQualifiedname;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.PACKAGE_DECLARATION__QUALIFIEDNAME, oldQualifiedname, qualifiedname));
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
      case JMLPackage.PACKAGE_DECLARATION__QUALIFIEDNAME:
        return getQualifiedname();
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
      case JMLPackage.PACKAGE_DECLARATION__QUALIFIEDNAME:
        setQualifiedname((String)newValue);
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
      case JMLPackage.PACKAGE_DECLARATION__QUALIFIEDNAME:
        setQualifiedname(QUALIFIEDNAME_EDEFAULT);
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
      case JMLPackage.PACKAGE_DECLARATION__QUALIFIEDNAME:
        return QUALIFIEDNAME_EDEFAULT == null ? qualifiedname != null : !QUALIFIEDNAME_EDEFAULT.equals(qualifiedname);
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
    result.append(" (qualifiedname: ");
    result.append(qualifiedname);
    result.append(')');
    return result.toString();
  }

} //PackageDeclarationImpl
