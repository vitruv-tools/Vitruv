/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWhenwhere <em>Whenwhere</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWithBlocks <em>With Blocks</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWiths <em>Withs</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMappingBody()
 * @model
 * @generated
 */
public interface MappingBody extends EObject
{
  /**
   * Returns the value of the '<em><b>Whenwhere</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Whenwhere</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Whenwhere</em>' containment reference.
   * @see #setWhenwhere(XExpression)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMappingBody_Whenwhere()
   * @model containment="true"
   * @generated
   */
  XExpression getWhenwhere();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody#getWhenwhere <em>Whenwhere</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Whenwhere</em>' containment reference.
   * @see #getWhenwhere()
   * @generated
   */
  void setWhenwhere(XExpression value);

  /**
   * Returns the value of the '<em><b>With Blocks</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.xtext.xbase.XExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>With Blocks</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>With Blocks</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMappingBody_WithBlocks()
   * @model containment="true"
   * @generated
   */
  EList<XExpression> getWithBlocks();

  /**
   * Returns the value of the '<em><b>Withs</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Withs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Withs</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getMappingBody_Withs()
   * @model containment="true"
   * @generated
   */
  EList<Mapping> getWiths();

} // MappingBody
