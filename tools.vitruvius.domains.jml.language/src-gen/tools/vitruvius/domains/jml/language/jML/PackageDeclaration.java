/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.PackageDeclaration#getQualifiedname <em>Qualifiedname</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getPackageDeclaration()
 * @model
 * @generated
 */
public interface PackageDeclaration extends EObject
{
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
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getPackageDeclaration_Qualifiedname()
   * @model
   * @generated
   */
  String getQualifiedname();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.PackageDeclaration#getQualifiedname <em>Qualifiedname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Qualifiedname</em>' attribute.
   * @see #getQualifiedname()
   * @generated
   */
  void setQualifiedname(String value);

} // PackageDeclaration
