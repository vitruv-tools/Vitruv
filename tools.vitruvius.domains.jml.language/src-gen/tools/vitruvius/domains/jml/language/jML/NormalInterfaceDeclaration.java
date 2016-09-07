/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Normal Interface Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getTypeparameters <em>Typeparameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getImplementedTypes <em>Implemented Types</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getBodyDeclarations <em>Body Declarations</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getNormalInterfaceDeclaration()
 * @model
 * @generated
 */
public interface NormalInterfaceDeclaration extends InterfaceDeclaration, AnnotationTypeElementRest
{
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getNormalInterfaceDeclaration_Identifier()
   * @model
   * @generated
   */
  String getIdentifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getIdentifier <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Identifier</em>' attribute.
   * @see #getIdentifier()
   * @generated
   */
  void setIdentifier(String value);

  /**
   * Returns the value of the '<em><b>Typeparameters</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Typeparameters</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Typeparameters</em>' containment reference.
   * @see #setTypeparameters(TypeParameters)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getNormalInterfaceDeclaration_Typeparameters()
   * @model containment="true"
   * @generated
   */
  TypeParameters getTypeparameters();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration#getTypeparameters <em>Typeparameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Typeparameters</em>' containment reference.
   * @see #getTypeparameters()
   * @generated
   */
  void setTypeparameters(TypeParameters value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getNormalInterfaceDeclaration_ImplementedTypes()
   * @model containment="true"
   * @generated
   */
  EList<Type> getImplementedTypes();

  /**
   * Returns the value of the '<em><b>Body Declarations</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body Declarations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body Declarations</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getNormalInterfaceDeclaration_BodyDeclarations()
   * @model containment="true"
   * @generated
   */
  EList<ClassBodyDeclaration> getBodyDeclarations();

} // NormalInterfaceDeclaration
