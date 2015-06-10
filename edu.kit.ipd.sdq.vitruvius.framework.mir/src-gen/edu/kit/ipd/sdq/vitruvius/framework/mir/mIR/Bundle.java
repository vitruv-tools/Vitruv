/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bundle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle#getBundleFQN <em>Bundle FQN</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getBundle()
 * @model
 * @generated
 */
public interface Bundle extends EObject
{
  /**
   * Returns the value of the '<em><b>Bundle FQN</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bundle FQN</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Bundle FQN</em>' attribute.
   * @see #setBundleFQN(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getBundle_BundleFQN()
   * @model
   * @generated
   */
  String getBundleFQN();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle#getBundleFQN <em>Bundle FQN</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Bundle FQN</em>' attribute.
   * @see #getBundleFQN()
   * @generated
   */
  void setBundleFQN(String value);

} // Bundle
