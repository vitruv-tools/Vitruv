/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getImplementedTypes <em>Implemented Types</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getEnumconstants <em>Enumconstants</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getBodyDeclarations <em>Body Declarations</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumDeclaration()
 * @model
 * @generated
 */
public interface EnumDeclaration extends ClassDeclaration, AnnotationTypeElementRest, IdentifierHaving
{
  /**
   * Returns the value of the '<em><b>Implemented Types</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.Type}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Implemented Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Implemented Types</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumDeclaration_ImplementedTypes()
   * @model containment="true"
   * @generated
   */
  EList<Type> getImplementedTypes();

  /**
   * Returns the value of the '<em><b>Enumconstants</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Enumconstants</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Enumconstants</em>' containment reference.
   * @see #setEnumconstants(EnumConstants)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumDeclaration_Enumconstants()
   * @model containment="true"
   * @generated
   */
  EnumConstants getEnumconstants();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getEnumconstants <em>Enumconstants</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Enumconstants</em>' containment reference.
   * @see #getEnumconstants()
   * @generated
   */
  void setEnumconstants(EnumConstants value);

  /**
   * Returns the value of the '<em><b>Body Declarations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body Declarations</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body Declarations</em>' containment reference.
   * @see #setBodyDeclarations(EnumBodyDeclarations)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumDeclaration_BodyDeclarations()
   * @model containment="true"
   * @generated
   */
  EnumBodyDeclarations getBodyDeclarations();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.EnumDeclaration#getBodyDeclarations <em>Body Declarations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Body Declarations</em>' containment reference.
   * @see #getBodyDeclarations()
   * @generated
   */
  void setBodyDeclarations(EnumBodyDeclarations value);

} // EnumDeclaration
