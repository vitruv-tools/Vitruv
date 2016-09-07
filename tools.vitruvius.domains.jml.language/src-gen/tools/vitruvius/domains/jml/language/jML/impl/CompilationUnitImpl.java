/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier;
import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.ImportDeclaration;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.PackageDeclaration;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compilation Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl#getPackagedeclaration <em>Packagedeclaration</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl#getImportdeclaration <em>Importdeclaration</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.CompilationUnitImpl#getTypedeclaration <em>Typedeclaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompilationUnitImpl extends MinimalEObjectImpl.Container implements CompilationUnit
{
  /**
   * The cached value of the '{@link #getPackagedeclaration() <em>Packagedeclaration</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackagedeclaration()
   * @generated
   * @ordered
   */
  protected PackageDeclaration packagedeclaration;

  /**
   * The cached value of the '{@link #getImportdeclaration() <em>Importdeclaration</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImportdeclaration()
   * @generated
   * @ordered
   */
  protected EList<ImportDeclaration> importdeclaration;

  /**
   * The cached value of the '{@link #getTypedeclaration() <em>Typedeclaration</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypedeclaration()
   * @generated
   * @ordered
   */
  protected EList<ClassifierDeclarationWithModifier> typedeclaration;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompilationUnitImpl()
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
    return JMLPackage.Literals.COMPILATION_UNIT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PackageDeclaration getPackagedeclaration()
  {
    return packagedeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPackagedeclaration(PackageDeclaration newPackagedeclaration, NotificationChain msgs)
  {
    PackageDeclaration oldPackagedeclaration = packagedeclaration;
    packagedeclaration = newPackagedeclaration;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION, oldPackagedeclaration, newPackagedeclaration);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPackagedeclaration(PackageDeclaration newPackagedeclaration)
  {
    if (newPackagedeclaration != packagedeclaration)
    {
      NotificationChain msgs = null;
      if (packagedeclaration != null)
        msgs = ((InternalEObject)packagedeclaration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION, null, msgs);
      if (newPackagedeclaration != null)
        msgs = ((InternalEObject)newPackagedeclaration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION, null, msgs);
      msgs = basicSetPackagedeclaration(newPackagedeclaration, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION, newPackagedeclaration, newPackagedeclaration));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ImportDeclaration> getImportdeclaration()
  {
    if (importdeclaration == null)
    {
      importdeclaration = new EObjectContainmentEList<ImportDeclaration>(ImportDeclaration.class, this, JMLPackage.COMPILATION_UNIT__IMPORTDECLARATION);
    }
    return importdeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ClassifierDeclarationWithModifier> getTypedeclaration()
  {
    if (typedeclaration == null)
    {
      typedeclaration = new EObjectContainmentEList<ClassifierDeclarationWithModifier>(ClassifierDeclarationWithModifier.class, this, JMLPackage.COMPILATION_UNIT__TYPEDECLARATION);
    }
    return typedeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    if (getTypedeclaration().size() > 0) {
      EStructuralFeature feature = getTypedeclaration().get(0).getClassOrInterfaceDeclaration().eClass().getEStructuralFeature("identifier");
      String typeName = (String)getTypedeclaration().get(0).getClassOrInterfaceDeclaration().eGet(feature);
      String pkgName = "";
      if (getPackagedeclaration() != null) {
        pkgName = getPackagedeclaration().getQualifiedname() + ".";
      }
      return pkgName + typeName;
    }
    throw new IllegalStateException("There has to be at least one type in the compilation unit in order to construct its name.");
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
      case JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION:
        return basicSetPackagedeclaration(null, msgs);
      case JMLPackage.COMPILATION_UNIT__IMPORTDECLARATION:
        return ((InternalEList<?>)getImportdeclaration()).basicRemove(otherEnd, msgs);
      case JMLPackage.COMPILATION_UNIT__TYPEDECLARATION:
        return ((InternalEList<?>)getTypedeclaration()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION:
        return getPackagedeclaration();
      case JMLPackage.COMPILATION_UNIT__IMPORTDECLARATION:
        return getImportdeclaration();
      case JMLPackage.COMPILATION_UNIT__TYPEDECLARATION:
        return getTypedeclaration();
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
      case JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION:
        setPackagedeclaration((PackageDeclaration)newValue);
        return;
      case JMLPackage.COMPILATION_UNIT__IMPORTDECLARATION:
        getImportdeclaration().clear();
        getImportdeclaration().addAll((Collection<? extends ImportDeclaration>)newValue);
        return;
      case JMLPackage.COMPILATION_UNIT__TYPEDECLARATION:
        getTypedeclaration().clear();
        getTypedeclaration().addAll((Collection<? extends ClassifierDeclarationWithModifier>)newValue);
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
      case JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION:
        setPackagedeclaration((PackageDeclaration)null);
        return;
      case JMLPackage.COMPILATION_UNIT__IMPORTDECLARATION:
        getImportdeclaration().clear();
        return;
      case JMLPackage.COMPILATION_UNIT__TYPEDECLARATION:
        getTypedeclaration().clear();
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
      case JMLPackage.COMPILATION_UNIT__PACKAGEDECLARATION:
        return packagedeclaration != null;
      case JMLPackage.COMPILATION_UNIT__IMPORTDECLARATION:
        return importdeclaration != null && !importdeclaration.isEmpty();
      case JMLPackage.COMPILATION_UNIT__TYPEDECLARATION:
        return typedeclaration != null && !typedeclaration.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //CompilationUnitImpl
