/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Import;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Model;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response;

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

import org.eclipse.xtext.xtype.XImportSection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl#getMmImports <em>Mm Imports</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl#getJavaImportSection <em>Java Import Section</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl#getInvariants <em>Invariants</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.ModelImpl#getRespones <em>Respones</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model
{
  /**
   * The cached value of the '{@link #getMmImports() <em>Mm Imports</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMmImports()
   * @generated
   * @ordered
   */
  protected EList<Import> mmImports;

  /**
   * The cached value of the '{@link #getJavaImportSection() <em>Java Import Section</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJavaImportSection()
   * @generated
   * @ordered
   */
  protected XImportSection javaImportSection;

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
   * The cached value of the '{@link #getInvariants() <em>Invariants</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvariants()
   * @generated
   * @ordered
   */
  protected EList<Invariant> invariants;

  /**
   * The cached value of the '{@link #getGlobalVariables() <em>Global Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGlobalVariables()
   * @generated
   * @ordered
   */
  protected EList<GlobalVariable> globalVariables;

  /**
   * The cached value of the '{@link #getRespones() <em>Respones</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRespones()
   * @generated
   * @ordered
   */
  protected EList<Response> respones;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ModelImpl()
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
    return MIRPackage.Literals.MODEL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Import> getMmImports()
  {
    if (mmImports == null)
    {
      mmImports = new EObjectContainmentEList<Import>(Import.class, this, MIRPackage.MODEL__MM_IMPORTS);
    }
    return mmImports;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XImportSection getJavaImportSection()
  {
    return javaImportSection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetJavaImportSection(XImportSection newJavaImportSection, NotificationChain msgs)
  {
    XImportSection oldJavaImportSection = javaImportSection;
    javaImportSection = newJavaImportSection;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.MODEL__JAVA_IMPORT_SECTION, oldJavaImportSection, newJavaImportSection);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJavaImportSection(XImportSection newJavaImportSection)
  {
    if (newJavaImportSection != javaImportSection)
    {
      NotificationChain msgs = null;
      if (javaImportSection != null)
        msgs = ((InternalEObject)javaImportSection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.MODEL__JAVA_IMPORT_SECTION, null, msgs);
      if (newJavaImportSection != null)
        msgs = ((InternalEObject)newJavaImportSection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.MODEL__JAVA_IMPORT_SECTION, null, msgs);
      msgs = basicSetJavaImportSection(newJavaImportSection, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MODEL__JAVA_IMPORT_SECTION, newJavaImportSection, newJavaImportSection));
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
      mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, MIRPackage.MODEL__MAPPINGS);
    }
    return mappings;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Invariant> getInvariants()
  {
    if (invariants == null)
    {
      invariants = new EObjectContainmentEList<Invariant>(Invariant.class, this, MIRPackage.MODEL__INVARIANTS);
    }
    return invariants;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<GlobalVariable> getGlobalVariables()
  {
    if (globalVariables == null)
    {
      globalVariables = new EObjectContainmentEList<GlobalVariable>(GlobalVariable.class, this, MIRPackage.MODEL__GLOBAL_VARIABLES);
    }
    return globalVariables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Response> getRespones()
  {
    if (respones == null)
    {
      respones = new EObjectContainmentEList<Response>(Response.class, this, MIRPackage.MODEL__RESPONES);
    }
    return respones;
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
      case MIRPackage.MODEL__MM_IMPORTS:
        return ((InternalEList<?>)getMmImports()).basicRemove(otherEnd, msgs);
      case MIRPackage.MODEL__JAVA_IMPORT_SECTION:
        return basicSetJavaImportSection(null, msgs);
      case MIRPackage.MODEL__MAPPINGS:
        return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
      case MIRPackage.MODEL__INVARIANTS:
        return ((InternalEList<?>)getInvariants()).basicRemove(otherEnd, msgs);
      case MIRPackage.MODEL__GLOBAL_VARIABLES:
        return ((InternalEList<?>)getGlobalVariables()).basicRemove(otherEnd, msgs);
      case MIRPackage.MODEL__RESPONES:
        return ((InternalEList<?>)getRespones()).basicRemove(otherEnd, msgs);
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
      case MIRPackage.MODEL__MM_IMPORTS:
        return getMmImports();
      case MIRPackage.MODEL__JAVA_IMPORT_SECTION:
        return getJavaImportSection();
      case MIRPackage.MODEL__MAPPINGS:
        return getMappings();
      case MIRPackage.MODEL__INVARIANTS:
        return getInvariants();
      case MIRPackage.MODEL__GLOBAL_VARIABLES:
        return getGlobalVariables();
      case MIRPackage.MODEL__RESPONES:
        return getRespones();
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
      case MIRPackage.MODEL__MM_IMPORTS:
        getMmImports().clear();
        getMmImports().addAll((Collection<? extends Import>)newValue);
        return;
      case MIRPackage.MODEL__JAVA_IMPORT_SECTION:
        setJavaImportSection((XImportSection)newValue);
        return;
      case MIRPackage.MODEL__MAPPINGS:
        getMappings().clear();
        getMappings().addAll((Collection<? extends Mapping>)newValue);
        return;
      case MIRPackage.MODEL__INVARIANTS:
        getInvariants().clear();
        getInvariants().addAll((Collection<? extends Invariant>)newValue);
        return;
      case MIRPackage.MODEL__GLOBAL_VARIABLES:
        getGlobalVariables().clear();
        getGlobalVariables().addAll((Collection<? extends GlobalVariable>)newValue);
        return;
      case MIRPackage.MODEL__RESPONES:
        getRespones().clear();
        getRespones().addAll((Collection<? extends Response>)newValue);
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
      case MIRPackage.MODEL__MM_IMPORTS:
        getMmImports().clear();
        return;
      case MIRPackage.MODEL__JAVA_IMPORT_SECTION:
        setJavaImportSection((XImportSection)null);
        return;
      case MIRPackage.MODEL__MAPPINGS:
        getMappings().clear();
        return;
      case MIRPackage.MODEL__INVARIANTS:
        getInvariants().clear();
        return;
      case MIRPackage.MODEL__GLOBAL_VARIABLES:
        getGlobalVariables().clear();
        return;
      case MIRPackage.MODEL__RESPONES:
        getRespones().clear();
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
      case MIRPackage.MODEL__MM_IMPORTS:
        return mmImports != null && !mmImports.isEmpty();
      case MIRPackage.MODEL__JAVA_IMPORT_SECTION:
        return javaImportSection != null;
      case MIRPackage.MODEL__MAPPINGS:
        return mappings != null && !mappings.isEmpty();
      case MIRPackage.MODEL__INVARIANTS:
        return invariants != null && !invariants.isEmpty();
      case MIRPackage.MODEL__GLOBAL_VARIABLES:
        return globalVariables != null && !globalVariables.isEmpty();
      case MIRPackage.MODEL__RESPONES:
        return respones != null && !respones.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ModelImpl
