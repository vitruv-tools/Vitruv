/**
 */
package tools.vitruvius.domains.jml.language.jML.impl;

import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.PrimitiveType;
import tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primitive Type With Brackets</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.impl.PrimitiveTypeWithBracketsImpl#getPrimitivetype <em>Primitivetype</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrimitiveTypeWithBracketsImpl extends TypeImpl implements PrimitiveTypeWithBrackets
{
  /**
   * The default value of the '{@link #getPrimitivetype() <em>Primitivetype</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPrimitivetype()
   * @generated
   * @ordered
   */
  protected static final PrimitiveType PRIMITIVETYPE_EDEFAULT = PrimitiveType.BOOLEAN;

  /**
   * The cached value of the '{@link #getPrimitivetype() <em>Primitivetype</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPrimitivetype()
   * @generated
   * @ordered
   */
  protected PrimitiveType primitivetype = PRIMITIVETYPE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PrimitiveTypeWithBracketsImpl()
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
    return JMLPackage.Literals.PRIMITIVE_TYPE_WITH_BRACKETS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PrimitiveType getPrimitivetype()
  {
    return primitivetype;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPrimitivetype(PrimitiveType newPrimitivetype)
  {
    PrimitiveType oldPrimitivetype = primitivetype;
    primitivetype = newPrimitivetype == null ? PRIMITIVETYPE_EDEFAULT : newPrimitivetype;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE, oldPrimitivetype, primitivetype));
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
      case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE:
        return getPrimitivetype();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE:
        setPrimitivetype((PrimitiveType)newValue);
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
      case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE:
        setPrimitivetype(PRIMITIVETYPE_EDEFAULT);
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
      case JMLPackage.PRIMITIVE_TYPE_WITH_BRACKETS__PRIMITIVETYPE:
        return primitivetype != PRIMITIVETYPE_EDEFAULT;
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
    result.append(" (primitivetype: ");
    result.append(primitivetype);
    result.append(')');
    return result.toString();
  }

} //PrimitiveTypeWithBracketsImpl
