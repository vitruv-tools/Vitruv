/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Formal Parameter Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#isVarargs <em>Varargs</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFormalParameterDecl()
 * @model
 * @generated
 */
public interface FormalParameterDecl extends Modifiable, Typed
{
  /**
   * Returns the value of the '<em><b>Varargs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Varargs</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Varargs</em>' attribute.
   * @see #setVarargs(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFormalParameterDecl_Varargs()
   * @model
   * @generated
   */
  boolean isVarargs();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#isVarargs <em>Varargs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Varargs</em>' attribute.
   * @see #isVarargs()
   * @generated
   */
  void setVarargs(boolean value);

  /**
   * Returns the value of the '<em><b>Identifier</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Identifier</em>' attribute.
   * @see #setIdentifier(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getFormalParameterDecl_Identifier()
   * @model
   * @generated
   */
  String getIdentifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl#getIdentifier <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Identifier</em>' attribute.
   * @see #getIdentifier()
   * @generated
   */
  void setIdentifier(String value);

} // FormalParameterDecl
