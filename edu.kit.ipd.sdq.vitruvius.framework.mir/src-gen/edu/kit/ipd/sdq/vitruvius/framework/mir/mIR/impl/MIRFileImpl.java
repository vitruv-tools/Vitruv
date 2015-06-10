/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping;

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
 * An implementation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl#getGeneratedPackage <em>Generated Package</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl#getGeneratedClass <em>Generated Class</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl#getBundles <em>Bundles</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MIRFileImpl#getMappings <em>Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MIRFileImpl extends MinimalEObjectImpl.Container implements MIRFile
{
  /**
   * The default value of the '{@link #getGeneratedPackage() <em>Generated Package</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGeneratedPackage()
   * @generated
   * @ordered
   */
  protected static final String GENERATED_PACKAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGeneratedPackage() <em>Generated Package</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGeneratedPackage()
   * @generated
   * @ordered
   */
  protected String generatedPackage = GENERATED_PACKAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getGeneratedClass() <em>Generated Class</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGeneratedClass()
   * @generated
   * @ordered
   */
  protected static final String GENERATED_CLASS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGeneratedClass() <em>Generated Class</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGeneratedClass()
   * @generated
   * @ordered
   */
  protected String generatedClass = GENERATED_CLASS_EDEFAULT;

  /**
   * The cached value of the '{@link #getBundles() <em>Bundles</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBundles()
   * @generated
   * @ordered
   */
  protected EList<Bundle> bundles;

  /**
   * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImports()
   * @generated
   * @ordered
   */
  protected EList<Import> imports;

  /**
   * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMappings()
   * @generated
   * @ordered
   */
  protected EList<Mapping> mappings;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MIRFileImpl()
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
    return MIRPackage.Literals.MIR_FILE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getGeneratedPackage()
  {
    return generatedPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGeneratedPackage(String newGeneratedPackage)
  {
    String oldGeneratedPackage = generatedPackage;
    generatedPackage = newGeneratedPackage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MIR_FILE__GENERATED_PACKAGE, oldGeneratedPackage, generatedPackage));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getGeneratedClass()
  {
    return generatedClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGeneratedClass(String newGeneratedClass)
  {
    String oldGeneratedClass = generatedClass;
    generatedClass = newGeneratedClass;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MIR_FILE__GENERATED_CLASS, oldGeneratedClass, generatedClass));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Bundle> getBundles()
  {
    if (bundles == null)
    {
      bundles = new EObjectContainmentEList<Bundle>(Bundle.class, this, MIRPackage.MIR_FILE__BUNDLES);
    }
    return bundles;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Import> getImports()
  {
    if (imports == null)
    {
      imports = new EObjectContainmentEList<Import>(Import.class, this, MIRPackage.MIR_FILE__IMPORTS);
    }
    return imports;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mapping> getMappings()
  {
    if (mappings == null)
    {
      mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, MIRPackage.MIR_FILE__MAPPINGS);
    }
    return mappings;
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
      case MIRPackage.MIR_FILE__BUNDLES:
        return ((InternalEList<?>)getBundles()).basicRemove(otherEnd, msgs);
      case MIRPackage.MIR_FILE__IMPORTS:
        return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
      case MIRPackage.MIR_FILE__MAPPINGS:
        return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
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
      case MIRPackage.MIR_FILE__GENERATED_PACKAGE:
        return getGeneratedPackage();
      case MIRPackage.MIR_FILE__GENERATED_CLASS:
        return getGeneratedClass();
      case MIRPackage.MIR_FILE__BUNDLES:
        return getBundles();
      case MIRPackage.MIR_FILE__IMPORTS:
        return getImports();
      case MIRPackage.MIR_FILE__MAPPINGS:
        return getMappings();
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
      case MIRPackage.MIR_FILE__GENERATED_PACKAGE:
        setGeneratedPackage((String)newValue);
        return;
      case MIRPackage.MIR_FILE__GENERATED_CLASS:
        setGeneratedClass((String)newValue);
        return;
      case MIRPackage.MIR_FILE__BUNDLES:
        getBundles().clear();
        getBundles().addAll((Collection<? extends Bundle>)newValue);
        return;
      case MIRPackage.MIR_FILE__IMPORTS:
        getImports().clear();
        getImports().addAll((Collection<? extends Import>)newValue);
        return;
      case MIRPackage.MIR_FILE__MAPPINGS:
        getMappings().clear();
        getMappings().addAll((Collection<? extends Mapping>)newValue);
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
      case MIRPackage.MIR_FILE__GENERATED_PACKAGE:
        setGeneratedPackage(GENERATED_PACKAGE_EDEFAULT);
        return;
      case MIRPackage.MIR_FILE__GENERATED_CLASS:
        setGeneratedClass(GENERATED_CLASS_EDEFAULT);
        return;
      case MIRPackage.MIR_FILE__BUNDLES:
        getBundles().clear();
        return;
      case MIRPackage.MIR_FILE__IMPORTS:
        getImports().clear();
        return;
      case MIRPackage.MIR_FILE__MAPPINGS:
        getMappings().clear();
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
      case MIRPackage.MIR_FILE__GENERATED_PACKAGE:
        return GENERATED_PACKAGE_EDEFAULT == null ? generatedPackage != null : !GENERATED_PACKAGE_EDEFAULT.equals(generatedPackage);
      case MIRPackage.MIR_FILE__GENERATED_CLASS:
        return GENERATED_CLASS_EDEFAULT == null ? generatedClass != null : !GENERATED_CLASS_EDEFAULT.equals(generatedClass);
      case MIRPackage.MIR_FILE__BUNDLES:
        return bundles != null && !bundles.isEmpty();
      case MIRPackage.MIR_FILE__IMPORTS:
        return imports != null && !imports.isEmpty();
      case MIRPackage.MIR_FILE__MAPPINGS:
        return mappings != null && !mappings.isEmpty();
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
    result.append(" (generatedPackage: ");
    result.append(generatedPackage);
    result.append(", generatedClass: ");
    result.append(generatedClass);
    result.append(')');
    return result.toString();
  }

} //MIRFileImpl
