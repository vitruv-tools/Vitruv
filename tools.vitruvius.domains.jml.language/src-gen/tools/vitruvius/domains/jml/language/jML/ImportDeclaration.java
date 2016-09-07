/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isStatic <em>Static</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#getQualifiedname <em>Qualifiedname</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isWildcard <em>Wildcard</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getImportDeclaration()
 * @model
 * @generated
 */
public interface ImportDeclaration extends EObject
{
  /**
   * Returns the value of the '<em><b>Static</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Static</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Static</em>' attribute.
   * @see #setStatic(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getImportDeclaration_Static()
   * @model
   * @generated
   */
  boolean isStatic();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isStatic <em>Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Static</em>' attribute.
   * @see #isStatic()
   * @generated
   */
  void setStatic(boolean value);

  /**
   * Returns the value of the '<em><b>Qualifiedname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Qualifiedname</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Qualifiedname</em>' attribute.
   * @see #setQualifiedname(String)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getImportDeclaration_Qualifiedname()
   * @model
   * @generated
   */
  String getQualifiedname();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#getQualifiedname <em>Qualifiedname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Qualifiedname</em>' attribute.
   * @see #getQualifiedname()
   * @generated
   */
  void setQualifiedname(String value);

  /**
   * Returns the value of the '<em><b>Wildcard</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Wildcard</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Wildcard</em>' attribute.
   * @see #setWildcard(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getImportDeclaration_Wildcard()
   * @model
   * @generated
   */
  boolean isWildcard();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration#isWildcard <em>Wildcard</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Wildcard</em>' attribute.
   * @see #isWildcard()
   * @generated
   */
  void setWildcard(boolean value);

} // ImportDeclaration
