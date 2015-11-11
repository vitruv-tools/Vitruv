/**
 */
package edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.BodyConstraintBlock;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Signature;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.SignatureConstraintBlock;

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
 * An implementation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getRequires <em>Requires</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getSignature0 <em>Signature0</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getConstraints0 <em>Constraints0</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getSignature1 <em>Signature1</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getConstraints1 <em>Constraints1</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getConstraintsBody <em>Constraints Body</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getSubmappings <em>Submappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingImpl extends MinimalEObjectImpl.Container implements Mapping
{
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
   * The cached value of the '{@link #getRequires() <em>Requires</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRequires()
   * @generated
   * @ordered
   */
  protected EList<RequiredMapping> requires;

  /**
   * The cached value of the '{@link #getSignature0() <em>Signature0</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSignature0()
   * @generated
   * @ordered
   */
  protected Signature signature0;

  /**
   * The cached value of the '{@link #getConstraints0() <em>Constraints0</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstraints0()
   * @generated
   * @ordered
   */
  protected SignatureConstraintBlock constraints0;

  /**
   * The cached value of the '{@link #getSignature1() <em>Signature1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSignature1()
   * @generated
   * @ordered
   */
  protected Signature signature1;

  /**
   * The cached value of the '{@link #getConstraints1() <em>Constraints1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstraints1()
   * @generated
   * @ordered
   */
  protected SignatureConstraintBlock constraints1;

  /**
   * The cached value of the '{@link #getConstraintsBody() <em>Constraints Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstraintsBody()
   * @generated
   * @ordered
   */
  protected BodyConstraintBlock constraintsBody;

  /**
   * The cached value of the '{@link #getSubmappings() <em>Submappings</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubmappings()
   * @generated
   * @ordered
   */
  protected EList<Mapping> submappings;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MappingImpl()
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
    return MappingLanguagePackage.Literals.MAPPING;
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
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RequiredMapping> getRequires()
  {
    if (requires == null)
    {
      requires = new EObjectContainmentEList<RequiredMapping>(RequiredMapping.class, this, MappingLanguagePackage.MAPPING__REQUIRES);
    }
    return requires;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Signature getSignature0()
  {
    return signature0;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSignature0(Signature newSignature0, NotificationChain msgs)
  {
    Signature oldSignature0 = signature0;
    signature0 = newSignature0;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__SIGNATURE0, oldSignature0, newSignature0);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSignature0(Signature newSignature0)
  {
    if (newSignature0 != signature0)
    {
      NotificationChain msgs = null;
      if (signature0 != null)
        msgs = ((InternalEObject)signature0).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__SIGNATURE0, null, msgs);
      if (newSignature0 != null)
        msgs = ((InternalEObject)newSignature0).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__SIGNATURE0, null, msgs);
      msgs = basicSetSignature0(newSignature0, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__SIGNATURE0, newSignature0, newSignature0));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SignatureConstraintBlock getConstraints0()
  {
    return constraints0;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstraints0(SignatureConstraintBlock newConstraints0, NotificationChain msgs)
  {
    SignatureConstraintBlock oldConstraints0 = constraints0;
    constraints0 = newConstraints0;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__CONSTRAINTS0, oldConstraints0, newConstraints0);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstraints0(SignatureConstraintBlock newConstraints0)
  {
    if (newConstraints0 != constraints0)
    {
      NotificationChain msgs = null;
      if (constraints0 != null)
        msgs = ((InternalEObject)constraints0).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__CONSTRAINTS0, null, msgs);
      if (newConstraints0 != null)
        msgs = ((InternalEObject)newConstraints0).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__CONSTRAINTS0, null, msgs);
      msgs = basicSetConstraints0(newConstraints0, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__CONSTRAINTS0, newConstraints0, newConstraints0));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Signature getSignature1()
  {
    return signature1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSignature1(Signature newSignature1, NotificationChain msgs)
  {
    Signature oldSignature1 = signature1;
    signature1 = newSignature1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__SIGNATURE1, oldSignature1, newSignature1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSignature1(Signature newSignature1)
  {
    if (newSignature1 != signature1)
    {
      NotificationChain msgs = null;
      if (signature1 != null)
        msgs = ((InternalEObject)signature1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__SIGNATURE1, null, msgs);
      if (newSignature1 != null)
        msgs = ((InternalEObject)newSignature1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__SIGNATURE1, null, msgs);
      msgs = basicSetSignature1(newSignature1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__SIGNATURE1, newSignature1, newSignature1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SignatureConstraintBlock getConstraints1()
  {
    return constraints1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstraints1(SignatureConstraintBlock newConstraints1, NotificationChain msgs)
  {
    SignatureConstraintBlock oldConstraints1 = constraints1;
    constraints1 = newConstraints1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__CONSTRAINTS1, oldConstraints1, newConstraints1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstraints1(SignatureConstraintBlock newConstraints1)
  {
    if (newConstraints1 != constraints1)
    {
      NotificationChain msgs = null;
      if (constraints1 != null)
        msgs = ((InternalEObject)constraints1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__CONSTRAINTS1, null, msgs);
      if (newConstraints1 != null)
        msgs = ((InternalEObject)newConstraints1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__CONSTRAINTS1, null, msgs);
      msgs = basicSetConstraints1(newConstraints1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__CONSTRAINTS1, newConstraints1, newConstraints1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BodyConstraintBlock getConstraintsBody()
  {
    return constraintsBody;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConstraintsBody(BodyConstraintBlock newConstraintsBody, NotificationChain msgs)
  {
    BodyConstraintBlock oldConstraintsBody = constraintsBody;
    constraintsBody = newConstraintsBody;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY, oldConstraintsBody, newConstraintsBody);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConstraintsBody(BodyConstraintBlock newConstraintsBody)
  {
    if (newConstraintsBody != constraintsBody)
    {
      NotificationChain msgs = null;
      if (constraintsBody != null)
        msgs = ((InternalEObject)constraintsBody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY, null, msgs);
      if (newConstraintsBody != null)
        msgs = ((InternalEObject)newConstraintsBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY, null, msgs);
      msgs = basicSetConstraintsBody(newConstraintsBody, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY, newConstraintsBody, newConstraintsBody));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mapping> getSubmappings()
  {
    if (submappings == null)
    {
      submappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, MappingLanguagePackage.MAPPING__SUBMAPPINGS);
    }
    return submappings;
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
      case MappingLanguagePackage.MAPPING__REQUIRES:
        return ((InternalEList<?>)getRequires()).basicRemove(otherEnd, msgs);
      case MappingLanguagePackage.MAPPING__SIGNATURE0:
        return basicSetSignature0(null, msgs);
      case MappingLanguagePackage.MAPPING__CONSTRAINTS0:
        return basicSetConstraints0(null, msgs);
      case MappingLanguagePackage.MAPPING__SIGNATURE1:
        return basicSetSignature1(null, msgs);
      case MappingLanguagePackage.MAPPING__CONSTRAINTS1:
        return basicSetConstraints1(null, msgs);
      case MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY:
        return basicSetConstraintsBody(null, msgs);
      case MappingLanguagePackage.MAPPING__SUBMAPPINGS:
        return ((InternalEList<?>)getSubmappings()).basicRemove(otherEnd, msgs);
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
      case MappingLanguagePackage.MAPPING__NAME:
        return getName();
      case MappingLanguagePackage.MAPPING__REQUIRES:
        return getRequires();
      case MappingLanguagePackage.MAPPING__SIGNATURE0:
        return getSignature0();
      case MappingLanguagePackage.MAPPING__CONSTRAINTS0:
        return getConstraints0();
      case MappingLanguagePackage.MAPPING__SIGNATURE1:
        return getSignature1();
      case MappingLanguagePackage.MAPPING__CONSTRAINTS1:
        return getConstraints1();
      case MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY:
        return getConstraintsBody();
      case MappingLanguagePackage.MAPPING__SUBMAPPINGS:
        return getSubmappings();
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
      case MappingLanguagePackage.MAPPING__NAME:
        setName((String)newValue);
        return;
      case MappingLanguagePackage.MAPPING__REQUIRES:
        getRequires().clear();
        getRequires().addAll((Collection<? extends RequiredMapping>)newValue);
        return;
      case MappingLanguagePackage.MAPPING__SIGNATURE0:
        setSignature0((Signature)newValue);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS0:
        setConstraints0((SignatureConstraintBlock)newValue);
        return;
      case MappingLanguagePackage.MAPPING__SIGNATURE1:
        setSignature1((Signature)newValue);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS1:
        setConstraints1((SignatureConstraintBlock)newValue);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY:
        setConstraintsBody((BodyConstraintBlock)newValue);
        return;
      case MappingLanguagePackage.MAPPING__SUBMAPPINGS:
        getSubmappings().clear();
        getSubmappings().addAll((Collection<? extends Mapping>)newValue);
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
      case MappingLanguagePackage.MAPPING__NAME:
        setName(NAME_EDEFAULT);
        return;
      case MappingLanguagePackage.MAPPING__REQUIRES:
        getRequires().clear();
        return;
      case MappingLanguagePackage.MAPPING__SIGNATURE0:
        setSignature0((Signature)null);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS0:
        setConstraints0((SignatureConstraintBlock)null);
        return;
      case MappingLanguagePackage.MAPPING__SIGNATURE1:
        setSignature1((Signature)null);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS1:
        setConstraints1((SignatureConstraintBlock)null);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY:
        setConstraintsBody((BodyConstraintBlock)null);
        return;
      case MappingLanguagePackage.MAPPING__SUBMAPPINGS:
        getSubmappings().clear();
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
      case MappingLanguagePackage.MAPPING__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case MappingLanguagePackage.MAPPING__REQUIRES:
        return requires != null && !requires.isEmpty();
      case MappingLanguagePackage.MAPPING__SIGNATURE0:
        return signature0 != null;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS0:
        return constraints0 != null;
      case MappingLanguagePackage.MAPPING__SIGNATURE1:
        return signature1 != null;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS1:
        return constraints1 != null;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS_BODY:
        return constraintsBody != null;
      case MappingLanguagePackage.MAPPING__SUBMAPPINGS:
        return submappings != null && !submappings.isEmpty();
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

} //MappingImpl
