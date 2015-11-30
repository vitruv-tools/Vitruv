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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#isDefault <em>Default</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getSignatures <em>Signatures</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getRequires <em>Requires</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getConstraintBlocks <em>Constraint Blocks</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getConstraintsBody <em>Constraints Body</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.impl.MappingImpl#getSubmappings <em>Submappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingImpl extends MinimalEObjectImpl.Container implements Mapping
{
  /**
   * The default value of the '{@link #isDefault() <em>Default</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDefault()
   * @generated
   * @ordered
   */
  protected static final boolean DEFAULT_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isDefault() <em>Default</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDefault()
   * @generated
   * @ordered
   */
  protected boolean default_ = DEFAULT_EDEFAULT;

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
   * The cached value of the '{@link #getSignatures() <em>Signatures</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSignatures()
   * @generated
   * @ordered
   */
  protected EList<Signature> signatures;

  /**
   * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstraints()
   * @generated
   * @ordered
   */
  protected EList<SignatureConstraintBlock> constraints;

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
   * The cached value of the '{@link #getConstraintBlocks() <em>Constraint Blocks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConstraintBlocks()
   * @generated
   * @ordered
   */
  protected EList<SignatureConstraintBlock> constraintBlocks;

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
  public boolean isDefault()
  {
    return default_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDefault(boolean newDefault)
  {
    boolean oldDefault = default_;
    default_ = newDefault;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MappingLanguagePackage.MAPPING__DEFAULT, oldDefault, default_));
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
  public EList<Signature> getSignatures()
  {
    if (signatures == null)
    {
      signatures = new EObjectContainmentEList<Signature>(Signature.class, this, MappingLanguagePackage.MAPPING__SIGNATURES);
    }
    return signatures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<SignatureConstraintBlock> getConstraints()
  {
    if (constraints == null)
    {
      constraints = new EObjectContainmentEList<SignatureConstraintBlock>(SignatureConstraintBlock.class, this, MappingLanguagePackage.MAPPING__CONSTRAINTS);
    }
    return constraints;
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
  public EList<SignatureConstraintBlock> getConstraintBlocks()
  {
    if (constraintBlocks == null)
    {
      constraintBlocks = new EObjectContainmentEList<SignatureConstraintBlock>(SignatureConstraintBlock.class, this, MappingLanguagePackage.MAPPING__CONSTRAINT_BLOCKS);
    }
    return constraintBlocks;
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
      case MappingLanguagePackage.MAPPING__SIGNATURES:
        return ((InternalEList<?>)getSignatures()).basicRemove(otherEnd, msgs);
      case MappingLanguagePackage.MAPPING__CONSTRAINTS:
        return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
      case MappingLanguagePackage.MAPPING__REQUIRES:
        return ((InternalEList<?>)getRequires()).basicRemove(otherEnd, msgs);
      case MappingLanguagePackage.MAPPING__CONSTRAINT_BLOCKS:
        return ((InternalEList<?>)getConstraintBlocks()).basicRemove(otherEnd, msgs);
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
      case MappingLanguagePackage.MAPPING__DEFAULT:
        return isDefault();
      case MappingLanguagePackage.MAPPING__NAME:
        return getName();
      case MappingLanguagePackage.MAPPING__SIGNATURES:
        return getSignatures();
      case MappingLanguagePackage.MAPPING__CONSTRAINTS:
        return getConstraints();
      case MappingLanguagePackage.MAPPING__REQUIRES:
        return getRequires();
      case MappingLanguagePackage.MAPPING__CONSTRAINT_BLOCKS:
        return getConstraintBlocks();
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
      case MappingLanguagePackage.MAPPING__DEFAULT:
        setDefault((Boolean)newValue);
        return;
      case MappingLanguagePackage.MAPPING__NAME:
        setName((String)newValue);
        return;
      case MappingLanguagePackage.MAPPING__SIGNATURES:
        getSignatures().clear();
        getSignatures().addAll((Collection<? extends Signature>)newValue);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS:
        getConstraints().clear();
        getConstraints().addAll((Collection<? extends SignatureConstraintBlock>)newValue);
        return;
      case MappingLanguagePackage.MAPPING__REQUIRES:
        getRequires().clear();
        getRequires().addAll((Collection<? extends RequiredMapping>)newValue);
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINT_BLOCKS:
        getConstraintBlocks().clear();
        getConstraintBlocks().addAll((Collection<? extends SignatureConstraintBlock>)newValue);
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
      case MappingLanguagePackage.MAPPING__DEFAULT:
        setDefault(DEFAULT_EDEFAULT);
        return;
      case MappingLanguagePackage.MAPPING__NAME:
        setName(NAME_EDEFAULT);
        return;
      case MappingLanguagePackage.MAPPING__SIGNATURES:
        getSignatures().clear();
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINTS:
        getConstraints().clear();
        return;
      case MappingLanguagePackage.MAPPING__REQUIRES:
        getRequires().clear();
        return;
      case MappingLanguagePackage.MAPPING__CONSTRAINT_BLOCKS:
        getConstraintBlocks().clear();
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
      case MappingLanguagePackage.MAPPING__DEFAULT:
        return default_ != DEFAULT_EDEFAULT;
      case MappingLanguagePackage.MAPPING__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case MappingLanguagePackage.MAPPING__SIGNATURES:
        return signatures != null && !signatures.isEmpty();
      case MappingLanguagePackage.MAPPING__CONSTRAINTS:
        return constraints != null && !constraints.isEmpty();
      case MappingLanguagePackage.MAPPING__REQUIRES:
        return requires != null && !requires.isEmpty();
      case MappingLanguagePackage.MAPPING__CONSTRAINT_BLOCKS:
        return constraintBlocks != null && !constraintBlocks.isEmpty();
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
    result.append(" (default: ");
    result.append(default_);
    result.append(", name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //MappingImpl
