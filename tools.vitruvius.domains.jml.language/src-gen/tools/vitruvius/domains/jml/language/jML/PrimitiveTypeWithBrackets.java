/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primitive Type With Brackets</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets#getPrimitivetype <em>Primitivetype</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getPrimitiveTypeWithBrackets()
 * @model
 * @generated
 */
public interface PrimitiveTypeWithBrackets extends Type
{
  /**
   * Returns the value of the '<em><b>Primitivetype</b></em>' attribute.
   * The literals are from the enumeration {@link tools.vitruvius.domains.jml.language.jML.PrimitiveType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Primitivetype</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Primitivetype</em>' attribute.
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveType
   * @see #setPrimitivetype(PrimitiveType)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getPrimitiveTypeWithBrackets_Primitivetype()
   * @model
   * @generated
   */
  PrimitiveType getPrimitivetype();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets#getPrimitivetype <em>Primitivetype</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Primitivetype</em>' attribute.
   * @see tools.vitruvius.domains.jml.language.jML.PrimitiveType
   * @see #getPrimitivetype()
   * @generated
   */
  void setPrimitivetype(PrimitiveType value);

} // PrimitiveTypeWithBrackets
