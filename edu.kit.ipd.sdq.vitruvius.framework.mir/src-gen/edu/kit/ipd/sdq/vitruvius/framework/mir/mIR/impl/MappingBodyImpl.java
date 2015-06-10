/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody;

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

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl#getWhenwhere <em>Whenwhere</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl#getWithBlocks <em>With Blocks</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.impl.MappingBodyImpl#getWiths <em>Withs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingBodyImpl extends MinimalEObjectImpl.Container implements MappingBody
{
  /**
   * The cached value of the '{@link #getWhenwhere() <em>Whenwhere</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWhenwhere()
   * @generated
   * @ordered
   */
  protected XExpression whenwhere;

  /**
   * The cached value of the '{@link #getWithBlocks() <em>With Blocks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWithBlocks()
   * @generated
   * @ordered
   */
  protected EList<XExpression> withBlocks;

  /**
   * The cached value of the '{@link #getWiths() <em>Withs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWiths()
   * @generated
   * @ordered
   */
  protected EList<Mapping> withs;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MappingBodyImpl()
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
    return MIRPackage.Literals.MAPPING_BODY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XExpression getWhenwhere()
  {
    return whenwhere;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWhenwhere(XExpression newWhenwhere, NotificationChain msgs)
  {
    XExpression oldWhenwhere = whenwhere;
    whenwhere = newWhenwhere;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING_BODY__WHENWHERE, oldWhenwhere, newWhenwhere);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWhenwhere(XExpression newWhenwhere)
  {
    if (newWhenwhere != whenwhere)
    {
      NotificationChain msgs = null;
      if (whenwhere != null)
        msgs = ((InternalEObject)whenwhere).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRPackage.MAPPING_BODY__WHENWHERE, null, msgs);
      if (newWhenwhere != null)
        msgs = ((InternalEObject)newWhenwhere).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRPackage.MAPPING_BODY__WHENWHERE, null, msgs);
      msgs = basicSetWhenwhere(newWhenwhere, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MIRPackage.MAPPING_BODY__WHENWHERE, newWhenwhere, newWhenwhere));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<XExpression> getWithBlocks()
  {
    if (withBlocks == null)
    {
      withBlocks = new EObjectContainmentEList<XExpression>(XExpression.class, this, MIRPackage.MAPPING_BODY__WITH_BLOCKS);
    }
    return withBlocks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mapping> getWiths()
  {
    if (withs == null)
    {
      withs = new EObjectContainmentEList<Mapping>(Mapping.class, this, MIRPackage.MAPPING_BODY__WITHS);
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
      case MIRPackage.MAPPING_BODY__WHENWHERE:
        return basicSetWhenwhere(null, msgs);
      case MIRPackage.MAPPING_BODY__WITH_BLOCKS:
        return ((InternalEList<?>)getWithBlocks()).basicRemove(otherEnd, msgs);
      case MIRPackage.MAPPING_BODY__WITHS:
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
      case MIRPackage.MAPPING_BODY__WHENWHERE:
        return getWhenwhere();
      case MIRPackage.MAPPING_BODY__WITH_BLOCKS:
        return getWithBlocks();
      case MIRPackage.MAPPING_BODY__WITHS:
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
      case MIRPackage.MAPPING_BODY__WHENWHERE:
        setWhenwhere((XExpression)newValue);
        return;
      case MIRPackage.MAPPING_BODY__WITH_BLOCKS:
        getWithBlocks().clear();
        getWithBlocks().addAll((Collection<? extends XExpression>)newValue);
        return;
      case MIRPackage.MAPPING_BODY__WITHS:
        getWiths().clear();
        getWiths().addAll((Collection<? extends Mapping>)newValue);
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
      case MIRPackage.MAPPING_BODY__WHENWHERE:
        setWhenwhere((XExpression)null);
        return;
      case MIRPackage.MAPPING_BODY__WITH_BLOCKS:
        getWithBlocks().clear();
        return;
      case MIRPackage.MAPPING_BODY__WITHS:
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
      case MIRPackage.MAPPING_BODY__WHENWHERE:
        return whenwhere != null;
      case MIRPackage.MAPPING_BODY__WITH_BLOCKS:
        return withBlocks != null && !withBlocks.isEmpty();
      case MIRPackage.MAPPING_BODY__WITHS:
        return withs != null && !withs.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MappingBodyImpl
