/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named EClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getRepresentedEClass <em>Represented EClass</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedEClass()
 * @model
 * @generated
 */
public interface NamedEClass extends MappableElement
{
  /**
   * Returns the value of the '<em><b>Represented EClass</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Represented EClass</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Represented EClass</em>' reference.
   * @see #setRepresentedEClass(EClass)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedEClass_RepresentedEClass()
   * @model
   * @generated
   */
  EClass getRepresentedEClass();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getRepresentedEClass <em>Represented EClass</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Represented EClass</em>' reference.
   * @see #getRepresentedEClass()
   * @generated
   */
  void setRepresentedEClass(EClass value);

} // NamedEClass
