/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getMappedElements <em>Mapped Elements</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getConstraints <em>Constraints</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMapping()
 * @model
 * @generated
 */
public interface Mapping extends EObject
{
  /**
   * Returns the value of the '<em><b>Mapped Elements</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mapped Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mapped Elements</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMapping_MappedElements()
   * @model containment="true"
   * @generated
   */
  EList<TypedElement> getMappedElements();

  /**
   * Returns the value of the '<em><b>Constraints</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constraints</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constraints</em>' containment reference.
   * @see #setConstraints(MappingBody)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMapping_Constraints()
   * @model containment="true"
   * @generated
   */
  MappingBody getConstraints();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping#getConstraints <em>Constraints</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constraints</em>' containment reference.
   * @see #getConstraints()
   * @generated
   */
  void setConstraints(MappingBody value);

} // Mapping
