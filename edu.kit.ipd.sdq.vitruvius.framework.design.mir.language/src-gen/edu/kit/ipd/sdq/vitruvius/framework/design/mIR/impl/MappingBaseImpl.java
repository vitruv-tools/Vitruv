/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MappingBase;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When;
import edu.kit.ipd.sdq.vitruvius.framework.design.mIR.With;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl#getNameA <em>Name A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl#getMetaclassA <em>Metaclass A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl#getNameB <em>Name B</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl#getMetaclassB <em>Metaclass B</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl#getWhens <em>Whens</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.impl.MappingBaseImpl#getWiths <em>Withs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingBaseImpl extends MappingImpl implements MappingBase
{
  /**
   * The default value of the '{@link #getNameA() <em>Name A</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNameA()
   * @generated
   * @ordered
   */
  protected static final String NAME_A_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNameA() <em>Name A</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNameA()
   * @generated
   * @ordered
   */
  protected String nameA = NAME_A_EDEFAULT;

  /**
   * The cached value of the '{@link #getMetaclassA() <em>Metaclass A</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetaclassA()
   * @generated
   * @ordered
   */
  protected EClass metaclassA;

  /**
   * The default value of the '{@link #getNameB() <em>Name B</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNameB()
   * @generated
   * @ordered
   */
  protected static final String NAME_B_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNameB() <em>Name B</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNameB()
   * @generated
   * @ordered
   */
  protected String nameB = NAME_B_EDEFAULT;

  /**
   * The cached value of the '{@link #getMetaclassB() <em>Metaclass B</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetaclassB()
   * @generated
   * @ordered
   */
  protected EClass metaclassB;

  /**
   * The cached value of the '{@link #getWhens() <em>Whens</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWhens()
   * @generated
   * @ordered
   */
  protected EList<When> whens;

  /**
   * The cached value of the '{@link #getWiths() <em>Withs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWiths()
   * @generated
   * @ordered
   */
  protected EList<With> withs;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MappingBaseImpl()
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
    return MIRPackage.Literals.MAPPING_BASE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getNameA()
  {
    return nameA;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNameA(String newNameA)
  {
    String oldNameA = nameA;
    nameA = newNameA;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING_BASE__NAME_A, oldNameA, nameA));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMetaclassA()
  {
    if (metaclassA != null && metaclassA.eIsProxy())
    {
      InternalEObject oldMetaclassA = (InternalEObject)metaclassA;
      metaclassA = (EClass)eResolveProxy(oldMetaclassA);
      if (metaclassA != oldMetaclassA)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRPackage.MAPPING_BASE__METACLASS_A, oldMetaclassA, metaclassA));
      }
    }
    return metaclassA;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass basicGetMetaclassA()
  {
    return metaclassA;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMetaclassA(EClass newMetaclassA)
  {
    EClass oldMetaclassA = metaclassA;
    metaclassA = newMetaclassA;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING_BASE__METACLASS_A, oldMetaclassA, metaclassA));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getNameB()
  {
    return nameB;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNameB(String newNameB)
  {
    String oldNameB = nameB;
    nameB = newNameB;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING_BASE__NAME_B, oldNameB, nameB));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMetaclassB()
  {
    if (metaclassB != null && metaclassB.eIsProxy())
    {
      InternalEObject oldMetaclassB = (InternalEObject)metaclassB;
      metaclassB = (EClass)eResolveProxy(oldMetaclassB);
      if (metaclassB != oldMetaclassB)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRPackage.MAPPING_BASE__METACLASS_B, oldMetaclassB, metaclassB));
      }
    }
    return metaclassB;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass basicGetMetaclassB()
  {
    return metaclassB;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMetaclassB(EClass newMetaclassB)
  {
    EClass oldMetaclassB = metaclassB;
    metaclassB = newMetaclassB;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING_BASE__METACLASS_B, oldMetaclassB, metaclassB));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<When> getWhens()
  {
    if (whens == null)
    {
      whens = new EObjectContainmentEList<When>(When.class, this, MIRPackage.MAPPING_BASE__WHENS);
    }
    return whens;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<With> getWiths()
  {
    if (withs == null)
    {
      withs = new EObjectContainmentEList<With>(With.class, this, MIRPackage.MAPPING_BASE__WITHS);
    }
    return withs;
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
      case MIRPackage.MAPPING_BASE__WHENS:
        return ((InternalEList<?>)getWhens()).basicRemove(otherEnd, msgs);
      case MIRPackage.MAPPING_BASE__WITHS:
        return ((InternalEList<?>)getWiths()).basicRemove(otherEnd, msgs);
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
      case MIRPackage.MAPPING_BASE__NAME_A:
        return getNameA();
      case MIRPackage.MAPPING_BASE__METACLASS_A:
        if (resolve) return getMetaclassA();
        return basicGetMetaclassA();
      case MIRPackage.MAPPING_BASE__NAME_B:
        return getNameB();
      case MIRPackage.MAPPING_BASE__METACLASS_B:
        if (resolve) return getMetaclassB();
        return basicGetMetaclassB();
      case MIRPackage.MAPPING_BASE__WHENS:
        return getWhens();
      case MIRPackage.MAPPING_BASE__WITHS:
        return getWiths();
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
      case MIRPackage.MAPPING_BASE__NAME_A:
        setNameA((String)newValue);
        return;
      case MIRPackage.MAPPING_BASE__METACLASS_A:
        setMetaclassA((EClass)newValue);
        return;
      case MIRPackage.MAPPING_BASE__NAME_B:
        setNameB((String)newValue);
        return;
      case MIRPackage.MAPPING_BASE__METACLASS_B:
        setMetaclassB((EClass)newValue);
        return;
      case MIRPackage.MAPPING_BASE__WHENS:
        getWhens().clear();
        getWhens().addAll((Collection<? extends When>)newValue);
        return;
      case MIRPackage.MAPPING_BASE__WITHS:
        getWiths().clear();
        getWiths().addAll((Collection<? extends With>)newValue);
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
      case MIRPackage.MAPPING_BASE__NAME_A:
        setNameA(NAME_A_EDEFAULT);
        return;
      case MIRPackage.MAPPING_BASE__METACLASS_A:
        setMetaclassA((EClass)null);
        return;
      case MIRPackage.MAPPING_BASE__NAME_B:
        setNameB(NAME_B_EDEFAULT);
        return;
      case MIRPackage.MAPPING_BASE__METACLASS_B:
        setMetaclassB((EClass)null);
        return;
      case MIRPackage.MAPPING_BASE__WHENS:
        getWhens().clear();
        return;
      case MIRPackage.MAPPING_BASE__WITHS:
        getWiths().clear();
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
      case MIRPackage.MAPPING_BASE__NAME_A:
        return NAME_A_EDEFAULT == null ? nameA != null : !NAME_A_EDEFAULT.equals(nameA);
      case MIRPackage.MAPPING_BASE__METACLASS_A:
        return metaclassA != null;
      case MIRPackage.MAPPING_BASE__NAME_B:
        return NAME_B_EDEFAULT == null ? nameB != null : !NAME_B_EDEFAULT.equals(nameB);
      case MIRPackage.MAPPING_BASE__METACLASS_B:
        return metaclassB != null;
      case MIRPackage.MAPPING_BASE__WHENS:
        return whens != null && !whens.isEmpty();
      case MIRPackage.MAPPING_BASE__WITHS:
        return withs != null && !withs.isEmpty();
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
    result.append(" (nameA: ");
    result.append(nameA);
    result.append(", nameB: ");
    result.append(nameB);
    result.append(')');
    return result.toString();
  }

} //MappingBaseImpl
