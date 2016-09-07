/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ImportDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl#isStatic <em>Static</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl#getQualifiedname <em>Qualifiedname</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.ImportDeclarationImpl#isWildcard <em>Wildcard</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportDeclarationImpl extends MinimalEObjectImpl.Container implements ImportDeclaration
{
  /**
   * The default value of the '{@link #isStatic() <em>Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isStatic()
   * @generated
   * @ordered
   */
  protected static final boolean STATIC_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isStatic() <em>Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isStatic()
   * @generated
   * @ordered
   */
  protected boolean static_ = STATIC_EDEFAULT;

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
   * The default value of the '{@link #isWildcard() <em>Wildcard</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isWildcard()
   * @generated
   * @ordered
   */
  protected static final boolean WILDCARD_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isWildcard() <em>Wildcard</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isWildcard()
   * @generated
   * @ordered
   */
  protected boolean wildcard = WILDCARD_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ImportDeclarationImpl()
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
    return JMLPackage.Literals.IMPORT_DECLARATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isStatic()
  {
    return static_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStatic(boolean newStatic)
  {
    boolean oldStatic = static_;
    static_ = newStatic;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.IMPORT_DECLARATION__STATIC, oldStatic, static_));
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
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.IMPORT_DECLARATION__QUALIFIEDNAME, oldQualifiedname, qualifiedname));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isWildcard()
  {
    return wildcard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWildcard(boolean newWildcard)
  {
    boolean oldWildcard = wildcard;
    wildcard = newWildcard;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.IMPORT_DECLARATION__WILDCARD, oldWildcard, wildcard));
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
      case JMLPackage.IMPORT_DECLARATION__STATIC:
        return isStatic();
      case JMLPackage.IMPORT_DECLARATION__QUALIFIEDNAME:
        return getQualifiedname();
      case JMLPackage.IMPORT_DECLARATION__WILDCARD:
        return isWildcard();
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
      case JMLPackage.IMPORT_DECLARATION__STATIC:
        setStatic((Boolean)newValue);
        return;
      case JMLPackage.IMPORT_DECLARATION__QUALIFIEDNAME:
        setQualifiedname((String)newValue);
        return;
      case JMLPackage.IMPORT_DECLARATION__WILDCARD:
        setWildcard((Boolean)newValue);
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
      case JMLPackage.IMPORT_DECLARATION__STATIC:
        setStatic(STATIC_EDEFAULT);
        return;
      case JMLPackage.IMPORT_DECLARATION__QUALIFIEDNAME:
        setQualifiedname(QUALIFIEDNAME_EDEFAULT);
        return;
      case JMLPackage.IMPORT_DECLARATION__WILDCARD:
        setWildcard(WILDCARD_EDEFAULT);
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
      case JMLPackage.IMPORT_DECLARATION__STATIC:
        return static_ != STATIC_EDEFAULT;
      case JMLPackage.IMPORT_DECLARATION__QUALIFIEDNAME:
        return QUALIFIEDNAME_EDEFAULT == null ? qualifiedname != null : !QUALIFIEDNAME_EDEFAULT.equals(qualifiedname);
      case JMLPackage.IMPORT_DECLARATION__WILDCARD:
        return wildcard != WILDCARD_EDEFAULT;
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
    result.append(" (static: ");
    result.append(static_);
    result.append(", qualifiedname: ");
    result.append(qualifiedname);
    result.append(", wildcard: ");
    result.append(wildcard);
    result.append(')');
    return result.toString();
  }

} //ImportDeclarationImpl
