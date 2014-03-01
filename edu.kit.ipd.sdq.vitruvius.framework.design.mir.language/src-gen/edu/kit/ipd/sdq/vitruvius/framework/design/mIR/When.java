/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>When</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When#getXorExpression <em>Xor Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getWhen()
 * @model
 * @generated
 */
public interface When extends EObject
{
  /**
   * Returns the value of the '<em><b>Xor Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Xor Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Xor Expression</em>' containment reference.
   * @see #setXorExpression(XExpression)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getWhen_XorExpression()
   * @model containment="true"
   * @generated
   */
  XExpression getXorExpression();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.When#getXorExpression <em>Xor Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Xor Expression</em>' containment reference.
   * @see #getXorExpression()
   * @generated
   */
  void setXorExpression(XExpression value);

} // When
