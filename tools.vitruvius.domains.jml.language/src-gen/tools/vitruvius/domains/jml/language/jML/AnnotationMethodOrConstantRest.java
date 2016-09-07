/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation Method Or Constant Rest</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getMethod <em>Method</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getConstant <em>Constant</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotationMethodOrConstantRest()
 * @model
 * @generated
 */
public interface AnnotationMethodOrConstantRest extends Typed, AnnotationTypeElementRest
{
  /**
   * Returns the value of the '<em><b>Method</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Method</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Method</em>' containment reference.
   * @see #setMethod(AnnotationMethodRest)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotationMethodOrConstantRest_Method()
   * @model containment="true"
   * @generated
   */
  AnnotationMethodRest getMethod();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getMethod <em>Method</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Method</em>' containment reference.
   * @see #getMethod()
   * @generated
   */
  void setMethod(AnnotationMethodRest value);

  /**
   * Returns the value of the '<em><b>Constant</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constant</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constant</em>' containment reference.
   * @see #setConstant(AnnotationConstantRest)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotationMethodOrConstantRest_Constant()
   * @model containment="true"
   * @generated
   */
  AnnotationConstantRest getConstant();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.AnnotationMethodOrConstantRest#getConstant <em>Constant</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constant</em>' containment reference.
   * @see #getConstant()
   * @generated
   */
  void setConstant(AnnotationConstantRest value);

} // AnnotationMethodOrConstantRest
