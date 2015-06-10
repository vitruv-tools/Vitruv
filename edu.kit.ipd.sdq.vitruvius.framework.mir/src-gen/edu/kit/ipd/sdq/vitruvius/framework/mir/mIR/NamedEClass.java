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
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedEClass()
 * @model
 * @generated
 */
public interface NamedEClass extends TypedElement
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' reference.
   * @see #setType(EClass)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedEClass_Type()
   * @model
   * @generated
   */
  EClass getType();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getType <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' reference.
   * @see #getType()
   * @generated
   */
  void setType(EClass value);

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
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedEClass_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // NamedEClass
