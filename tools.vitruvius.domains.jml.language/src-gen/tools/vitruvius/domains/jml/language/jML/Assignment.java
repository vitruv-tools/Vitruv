/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Assignment#getTypeForVariableDeclaration <em>Type For Variable Declaration</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Assignment#getFeature <em>Feature</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Assignment#getValue <em>Value</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Assignment#getAssignable <em>Assignable</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAssignment()
 * @model
 * @generated
 */
public interface Assignment extends Expression
{
  /**
   * Returns the value of the '<em><b>Type For Variable Declaration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type For Variable Declaration</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type For Variable Declaration</em>' attribute.
   * @see #setTypeForVariableDeclaration(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAssignment_TypeForVariableDeclaration()
   * @model
   * @generated
   */
  String getTypeForVariableDeclaration();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getTypeForVariableDeclaration <em>Type For Variable Declaration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type For Variable Declaration</em>' attribute.
   * @see #getTypeForVariableDeclaration()
   * @generated
   */
  void setTypeForVariableDeclaration(String value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAssignment_Feature()
   * @model
   * @generated
   */
  String getFeature();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getFeature <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Feature</em>' attribute.
   * @see #getFeature()
   * @generated
   */
  void setFeature(String value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAssignment_Value()
   * @model containment="true"
   * @generated
   */
  Expression getValue();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Expression value);

  /**
   * Returns the value of the '<em><b>Assignable</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assignable</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assignable</em>' containment reference.
   * @see #setAssignable(Expression)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAssignment_Assignable()
   * @model containment="true"
   * @generated
   */
  Expression getAssignable();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Assignment#getAssignable <em>Assignable</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assignable</em>' containment reference.
   * @see #getAssignable()
   * @generated
   */
  void setAssignable(Expression value);

} // Assignment
