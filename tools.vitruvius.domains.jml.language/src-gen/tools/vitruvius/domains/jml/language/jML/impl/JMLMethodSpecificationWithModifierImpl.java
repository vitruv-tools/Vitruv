/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecification;
import tools.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.VisiblityModifier;

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
 * An implementation of the model object '<em><b>Method Specification With Modifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierImpl#getModifier <em>Modifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodSpecificationWithModifierImpl#getSpec <em>Spec</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLMethodSpecificationWithModifierImpl extends MinimalEObjectImpl.Container implements JMLMethodSpecificationWithModifier
{
  /**
   * The cached value of the '{@link #getModifier() <em>Modifier</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getModifier()
   * @generated
   * @ordered
   */
  protected EList<VisiblityModifier> modifier;

  /**
   * The cached value of the '{@link #getSpec() <em>Spec</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpec()
   * @generated
   * @ordered
   */
  protected JMLMethodSpecification spec;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLMethodSpecificationWithModifierImpl()
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
    return JMLPackage.Literals.JML_METHOD_SPECIFICATION_WITH_MODIFIER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VisiblityModifier> getModifier()
  {
    if (modifier == null)
    {
      modifier = new EObjectContainmentEList<VisiblityModifier>(VisiblityModifier.class, this, JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER);
    }
    return modifier;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JMLMethodSpecification getSpec()
  {
    return spec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSpec(JMLMethodSpecification newSpec, NotificationChain msgs)
  {
    JMLMethodSpecification oldSpec = spec;
    spec = newSpec;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC, oldSpec, newSpec);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSpec(JMLMethodSpecification newSpec)
  {
    if (newSpec != spec)
    {
      NotificationChain msgs = null;
      if (spec != null)
        msgs = ((InternalEObject)spec).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC, null, msgs);
      if (newSpec != null)
        msgs = ((InternalEObject)newSpec).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC, null, msgs);
      msgs = basicSetSpec(newSpec, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC, newSpec, newSpec));
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
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER:
        return ((InternalEList<?>)getModifier()).basicRemove(otherEnd, msgs);
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC:
        return basicSetSpec(null, msgs);
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
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER:
        return getModifier();
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC:
        return getSpec();
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
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER:
        getModifier().clear();
        getModifier().addAll((Collection<? extends VisiblityModifier>)newValue);
        return;
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC:
        setSpec((JMLMethodSpecification)newValue);
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
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER:
        getModifier().clear();
        return;
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC:
        setSpec((JMLMethodSpecification)null);
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
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__MODIFIER:
        return modifier != null && !modifier.isEmpty();
      case JMLPackage.JML_METHOD_SPECIFICATION_WITH_MODIFIER__SPEC:
        return spec != null;
    }
    return super.eIsSet(featureID);
  }

} //JMLMethodSpecificationWithModifierImpl
