/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Classifier Declaration With Modifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier#getClassOrInterfaceDeclaration <em>Class Or Interface Declaration</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getClassifierDeclarationWithModifier()
 * @model
 * @generated
 */
public interface ClassifierDeclarationWithModifier extends Modifiable, BlockStatement
{
  /**
   * Returns the value of the '<em><b>Class Or Interface Declaration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Class Or Interface Declaration</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Class Or Interface Declaration</em>' containment reference.
   * @see #setClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getClassifierDeclarationWithModifier_ClassOrInterfaceDeclaration()
   * @model containment="true"
   * @generated
   */
  ClassOrInterfaceDeclaration getClassOrInterfaceDeclaration();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier#getClassOrInterfaceDeclaration <em>Class Or Interface Declaration</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Class Or Interface Declaration</em>' containment reference.
   * @see #getClassOrInterfaceDeclaration()
   * @generated
   */
  void setClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration value);

} // ClassifierDeclarationWithModifier
