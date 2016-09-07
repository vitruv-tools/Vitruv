/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constructor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Constructor#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Constructor#getParameters <em>Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Constructor#getExceptions <em>Exceptions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.Constructor#getConstructorbody <em>Constructorbody</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructor()
 * @model
 * @generated
 */
public interface Constructor extends MemberDecl
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructor_Identifier()
   * @model
   * @generated
   */
  String getIdentifier();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Constructor#getIdentifier <em>Identifier</em>}' attribute.
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructor_Parameters()
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructor_Exceptions()
   * @model containment="true"
   * @generated
   */
  EList<DeclaredException> getExceptions();

  /**
   * Returns the value of the '<em><b>Constructorbody</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constructorbody</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constructorbody</em>' containment reference.
   * @see #setConstructorbody(ConstructorBody)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructor_Constructorbody()
   * @model containment="true"
   * @generated
   */
  ConstructorBody getConstructorbody();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.Constructor#getConstructorbody <em>Constructorbody</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constructorbody</em>' containment reference.
   * @see #getConstructorbody()
   * @generated
   */
  void setConstructorbody(ConstructorBody value);

} // Constructor
