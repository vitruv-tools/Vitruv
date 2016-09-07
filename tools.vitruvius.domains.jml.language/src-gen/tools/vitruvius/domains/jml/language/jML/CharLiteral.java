/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Char Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CharLiteral#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCharLiteral()
 * @model
 * @generated
 */
public interface CharLiteral extends Expression
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCharLiteral_Value()
   * @model
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CharLiteral#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // CharLiteral
