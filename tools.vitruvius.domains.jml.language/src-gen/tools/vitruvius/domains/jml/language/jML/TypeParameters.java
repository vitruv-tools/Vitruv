/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Parameters</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getTypeparameter <em>Typeparameter</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getType <em>Type</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getParameters <em>Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getExceptions <em>Exceptions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getMethodbody <em>Methodbody</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters()
 * @model
 * @generated
 */
public interface TypeParameters extends GenericMethodOrConstructorDeclOld
{
  /**
   * Returns the value of the '<em><b>Typeparameter</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.TypeParameter}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Typeparameter</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Typeparameter</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters_Typeparameter()
   * @model containment="true"
   * @generated
   */
  EList<TypeParameter> getTypeparameter();

  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(Type)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters_Type()
   * @model containment="true"
   * @generated
   */
  Type getType();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(Type value);

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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters_Identifier()
   * @model
   * @generated
   */
  String getIdentifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getIdentifier <em>Identifier</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Identifier</em>' attribute.
   * @see #getIdentifier()
   * @generated
   */
  void setIdentifier(String value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.FormalParameterDecl}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<FormalParameterDecl> getParameters();

  /**
   * Returns the value of the '<em><b>Exceptions</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.DeclaredException}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exceptions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exceptions</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters_Exceptions()
   * @model containment="true"
   * @generated
   */
  EList<DeclaredException> getExceptions();

  /**
   * Returns the value of the '<em><b>Methodbody</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Methodbody</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Methodbody</em>' containment reference.
   * @see #setMethodbody(MethodBody)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeParameters_Methodbody()
   * @model containment="true"
   * @generated
   */
  MethodBody getMethodbody();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.TypeParameters#getMethodbody <em>Methodbody</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Methodbody</em>' containment reference.
   * @see #getMethodbody()
   * @generated
   */
  void setMethodbody(MethodBody value);

} // TypeParameters
