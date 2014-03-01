/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.design.mIR;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getOperationRestriction <em>Operation Restriction</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getInvariantRestriction <em>Invariant Restriction</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getXtendStmt <em>Xtend Stmt</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getResponse()
 * @model
 * @generated
 */
public interface Response extends EObject
{
  /**
   * Returns the value of the '<em><b>Operation Restriction</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operation Restriction</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operation Restriction</em>' containment reference.
   * @see #setOperationRestriction(OperationRestriction)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getResponse_OperationRestriction()
   * @model containment="true"
   * @generated
   */
  OperationRestriction getOperationRestriction();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getOperationRestriction <em>Operation Restriction</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operation Restriction</em>' containment reference.
   * @see #getOperationRestriction()
   * @generated
   */
  void setOperationRestriction(OperationRestriction value);

  /**
   * Returns the value of the '<em><b>Invariant Restriction</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Invariant Restriction</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invariant Restriction</em>' containment reference.
   * @see #setInvariantRestriction(InvariantRestriction)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getResponse_InvariantRestriction()
   * @model containment="true"
   * @generated
   */
  InvariantRestriction getInvariantRestriction();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getInvariantRestriction <em>Invariant Restriction</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Invariant Restriction</em>' containment reference.
   * @see #getInvariantRestriction()
   * @generated
   */
  void setInvariantRestriction(InvariantRestriction value);

  /**
   * Returns the value of the '<em><b>Xtend Stmt</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Xtend Stmt</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Xtend Stmt</em>' attribute.
   * @see #setXtendStmt(String)
   * @see edu.kit.ipd.sdq.vitruvius.framework.design.mIR.MIRPackage#getResponse_XtendStmt()
   * @model
   * @generated
   */
  String getXtendStmt();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.design.mIR.Response#getXtendStmt <em>Xtend Stmt</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Xtend Stmt</em>' attribute.
   * @see #getXtendStmt()
   * @generated
   */
  void setXtendStmt(String value);

} // Response
