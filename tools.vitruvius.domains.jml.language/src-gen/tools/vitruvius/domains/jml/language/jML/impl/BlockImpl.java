/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.Block;
import tools.vitruvius.domains.jml.language.jML.BlockStatement;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.BlockImpl#getBlockstatement <em>Blockstatement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockImpl extends MethodBodyImpl implements Block
{
  /**
   * The cached value of the '{@link #getBlockstatement() <em>Blockstatement</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBlockstatement()
   * @generated
   * @ordered
   */
  protected EList<BlockStatement> blockstatement;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BlockImpl()
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
    return JMLPackage.Literals.BLOCK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<BlockStatement> getBlockstatement()
  {
    if (blockstatement == null)
    {
      blockstatement = new EObjectContainmentEList<BlockStatement>(BlockStatement.class, this, JMLPackage.BLOCK__BLOCKSTATEMENT);
    }
    return blockstatement;
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
      case JMLPackage.BLOCK__BLOCKSTATEMENT:
        return ((InternalEList<?>)getBlockstatement()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.BLOCK__BLOCKSTATEMENT:
        return getBlockstatement();
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
      case JMLPackage.BLOCK__BLOCKSTATEMENT:
        getBlockstatement().clear();
        getBlockstatement().addAll((Collection<? extends BlockStatement>)newValue);
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
      case JMLPackage.BLOCK__BLOCKSTATEMENT:
        getBlockstatement().clear();
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
      case JMLPackage.BLOCK__BLOCKSTATEMENT:
        return blockstatement != null && !blockstatement.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //BlockImpl
