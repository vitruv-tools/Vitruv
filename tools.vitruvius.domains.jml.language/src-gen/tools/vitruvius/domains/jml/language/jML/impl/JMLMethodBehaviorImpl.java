/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior;
import tools.vitruvius.domains.jml.language.jML.JMLMethodExpression;
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
 * An implementation of the model object '<em><b>Method Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.JMLMethodBehaviorImpl#getSpecifications <em>Specifications</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JMLMethodBehaviorImpl extends JMLMethodSpecificationImpl implements JMLMethodBehavior
{
  /**
   * The cached value of the '{@link #getSpecifications() <em>Specifications</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpecifications()
   * @generated
   * @ordered
   */
  protected EList<JMLMethodExpression> specifications;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JMLMethodBehaviorImpl()
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
    return JMLPackage.Literals.JML_METHOD_BEHAVIOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<JMLMethodExpression> getSpecifications()
  {
    if (specifications == null)
    {
      specifications = new EObjectContainmentEList<JMLMethodExpression>(JMLMethodExpression.class, this, JMLPackage.JML_METHOD_BEHAVIOR__SPECIFICATIONS);
    }
    return specifications;
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
      case JMLPackage.JML_METHOD_BEHAVIOR__SPECIFICATIONS:
        return ((InternalEList<?>)getSpecifications()).basicRemove(otherEnd, msgs);
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
      case JMLPackage.JML_METHOD_BEHAVIOR__SPECIFICATIONS:
        return getSpecifications();
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
      case JMLPackage.JML_METHOD_BEHAVIOR__SPECIFICATIONS:
        getSpecifications().clear();
        getSpecifications().addAll((Collection<? extends JMLMethodExpression>)newValue);
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
      case JMLPackage.JML_METHOD_BEHAVIOR__SPECIFICATIONS:
        getSpecifications().clear();
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
      case JMLPackage.JML_METHOD_BEHAVIOR__SPECIFICATIONS:
        return specifications != null && !specifications.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //JMLMethodBehaviorImpl
