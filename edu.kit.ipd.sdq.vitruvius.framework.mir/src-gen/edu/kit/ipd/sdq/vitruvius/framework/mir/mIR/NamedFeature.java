/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getContainingNamedEClass <em>Containing Named EClass</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getRepresentedFeature <em>Represented Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedFeature()
 * @model
 * @generated
 */
public interface NamedFeature extends MappableElement
{
  /**
   * Returns the value of the '<em><b>Containing Named EClass</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Containing Named EClass</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Containing Named EClass</em>' reference.
   * @see #setContainingNamedEClass(NamedEClass)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedFeature_ContainingNamedEClass()
   * @model
   * @generated
   */
  NamedEClass getContainingNamedEClass();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getContainingNamedEClass <em>Containing Named EClass</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Containing Named EClass</em>' reference.
   * @see #getContainingNamedEClass()
   * @generated
   */
  void setContainingNamedEClass(NamedEClass value);

  /**
   * Returns the value of the '<em><b>Represented Feature</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Represented Feature</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Represented Feature</em>' reference.
   * @see #setRepresentedFeature(EStructuralFeature)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getNamedFeature_RepresentedFeature()
   * @model
   * @generated
   */
  EStructuralFeature getRepresentedFeature();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature#getRepresentedFeature <em>Represented Feature</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Represented Feature</em>' reference.
   * @see #getRepresentedFeature()
   * @generated
   */
  void setRepresentedFeature(EStructuralFeature value);

} // NamedFeature
