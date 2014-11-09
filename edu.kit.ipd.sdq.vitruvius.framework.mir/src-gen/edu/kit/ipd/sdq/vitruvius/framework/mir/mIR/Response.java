/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getAction <em>Action</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getContext <em>Context</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getInv <em>Inv</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getRestoreAction <em>Restore Action</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getResponse()
 * @model
 * @generated
 */
public interface Response extends EObject
{
  /**
   * Returns the value of the '<em><b>Action</b></em>' attribute.
   * The literals are from the enumeration {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Action</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Action</em>' attribute.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction
   * @see #setAction(ResponseAction)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getResponse_Action()
   * @model
   * @generated
   */
  ResponseAction getAction();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getAction <em>Action</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Action</em>' attribute.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction
   * @see #getAction()
   * @generated
   */
  void setAction(ResponseAction value);

  /**
   * Returns the value of the '<em><b>Context</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Context</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Context</em>' containment reference.
   * @see #setContext(MappableElement)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getResponse_Context()
   * @model containment="true"
   * @generated
   */
  MappableElement getContext();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getContext <em>Context</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Context</em>' containment reference.
   * @see #getContext()
   * @generated
   */
  void setContext(MappableElement value);

  /**
   * Returns the value of the '<em><b>Inv</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Inv</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Inv</em>' reference.
   * @see #setInv(Invariant)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getResponse_Inv()
   * @model
   * @generated
   */
  Invariant getInv();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getInv <em>Inv</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Inv</em>' reference.
   * @see #getInv()
   * @generated
   */
  void setInv(Invariant value);

  /**
   * Returns the value of the '<em><b>Restore Action</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Restore Action</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Restore Action</em>' containment reference.
   * @see #setRestoreAction(XExpression)
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getResponse_RestoreAction()
   * @model containment="true"
   * @generated
   */
  XExpression getRestoreAction();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response#getRestoreAction <em>Restore Action</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Restore Action</em>' containment reference.
   * @see #getRestoreAction()
   * @generated
   */
  void setRestoreAction(XExpression value);

} // Response
