/**
 */
package edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Postfix Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PostfixOperation#getOperand <em>Operand</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PostfixOperation#getFeature <em>Feature</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getPostfixOperation()
 * @model
 * @generated
 */
public interface PostfixOperation extends Expression
{
  /**
   * Returns the value of the '<em><b>Operand</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operand</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operand</em>' containment reference.
   * @see #setOperand(Expression)
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getPostfixOperation_Operand()
   * @model containment="true"
   * @generated
   */
  Expression getOperand();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PostfixOperation#getOperand <em>Operand</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operand</em>' containment reference.
   * @see #getOperand()
   * @generated
   */
  void setOperand(Expression value);

  /**
   * Returns the value of the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Feature</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Feature</em>' attribute.
   * @see #setFeature(String)
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getPostfixOperation_Feature()
   * @model
   * @generated
   */
  String getFeature();

  /**
   * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PostfixOperation#getFeature <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Feature</em>' attribute.
   * @see #getFeature()
   * @generated
   */
  void setFeature(String value);

} // PostfixOperation
