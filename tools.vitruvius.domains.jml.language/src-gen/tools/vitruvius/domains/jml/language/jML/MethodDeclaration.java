/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getParameters <em>Parameters</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getExceptions <em>Exceptions</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getMethodbody <em>Methodbody</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMethodDeclaration()
 * @model
 * @generated
 */
public interface MethodDeclaration extends IdentifierHaving
{
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMethodDeclaration_Parameters()
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMethodDeclaration_Exceptions()
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getMethodDeclaration_Methodbody()
   * @model containment="true"
   * @generated
   */
  MethodBody getMethodbody();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.MethodDeclaration#getMethodbody <em>Methodbody</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Methodbody</em>' containment reference.
   * @see #getMethodbody()
   * @generated
   */
  void setMethodbody(MethodBody value);

} // MethodDeclaration
