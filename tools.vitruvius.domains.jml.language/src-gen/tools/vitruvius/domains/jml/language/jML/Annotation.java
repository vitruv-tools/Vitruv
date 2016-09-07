/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Annotation#getAnnotationname <em>Annotationname</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Annotation#getElementvaluepairs <em>Elementvaluepairs</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Annotation#getElementvalue <em>Elementvalue</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends Modifier, ElementValue
{
  /**
   * Returns the value of the '<em><b>Annotationname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Annotationname</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Annotationname</em>' attribute.
   * @see #setAnnotationname(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotation_Annotationname()
   * @model
   * @generated
   */
  String getAnnotationname();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Annotation#getAnnotationname <em>Annotationname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Annotationname</em>' attribute.
   * @see #getAnnotationname()
   * @generated
   */
  void setAnnotationname(String value);

  /**
   * Returns the value of the '<em><b>Elementvaluepairs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elementvaluepairs</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elementvaluepairs</em>' containment reference.
   * @see #setElementvaluepairs(ElementValuePairs)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotation_Elementvaluepairs()
   * @model containment="true"
   * @generated
   */
  ElementValuePairs getElementvaluepairs();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Annotation#getElementvaluepairs <em>Elementvaluepairs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Elementvaluepairs</em>' containment reference.
   * @see #getElementvaluepairs()
   * @generated
   */
  void setElementvaluepairs(ElementValuePairs value);

  /**
   * Returns the value of the '<em><b>Elementvalue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elementvalue</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elementvalue</em>' containment reference.
   * @see #setElementvalue(ElementValue)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotation_Elementvalue()
   * @model containment="true"
   * @generated
   */
  ElementValue getElementvalue();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Annotation#getElementvalue <em>Elementvalue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Elementvalue</em>' containment reference.
   * @see #getElementvalue()
   * @generated
   */
  void setElementvalue(ElementValue value);

} // Annotation
