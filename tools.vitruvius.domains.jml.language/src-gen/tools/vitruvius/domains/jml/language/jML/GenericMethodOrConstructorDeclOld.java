/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Method Or Constructor Decl Old</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld#getConstructor <em>Constructor</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDeclOld()
 * @model
 * @generated
 */
public interface GenericMethodOrConstructorDeclOld extends EObject
{
  /**
   * Returns the value of the '<em><b>Constructor</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constructor</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Constructor</em>' containment reference.
   * @see #setConstructor(Constructor)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getGenericMethodOrConstructorDeclOld_Constructor()
   * @model containment="true"
   * @generated
   */
  Constructor getConstructor();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDeclOld#getConstructor <em>Constructor</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Constructor</em>' containment reference.
   * @see #getConstructor()
   * @generated
   */
  void setConstructor(Constructor value);

} // GenericMethodOrConstructorDeclOld
