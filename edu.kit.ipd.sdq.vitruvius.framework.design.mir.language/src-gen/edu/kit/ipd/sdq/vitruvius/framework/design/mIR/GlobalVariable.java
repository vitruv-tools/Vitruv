/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getJvmTypeReference <em>Jvm Type Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getGlobalVariable()
 * @model
 * @generated
 */
public interface GlobalVariable extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getGlobalVariable_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Jvm Type Reference</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Jvm Type Reference</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Jvm Type Reference</em>' containment reference.
   * @see #setJvmTypeReference(JvmTypeReference)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getGlobalVariable_JvmTypeReference()
   * @model containment="true"
   * @generated
   */
  JvmTypeReference getJvmTypeReference();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.GlobalVariable#getJvmTypeReference <em>Jvm Type Reference</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Jvm Type Reference</em>' containment reference.
   * @see #getJvmTypeReference()
   * @generated
   */
  void setJvmTypeReference(JvmTypeReference value);

} // GlobalVariable
