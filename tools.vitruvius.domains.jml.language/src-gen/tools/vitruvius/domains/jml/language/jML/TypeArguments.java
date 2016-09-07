/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Arguments</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.TypeArguments#getTypeargument <em>Typeargument</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArguments()
 * @model
 * @generated
 */
public interface TypeArguments extends EObject
{
  /**
   * Returns the value of the '<em><b>Typeargument</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.TypeArgument}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Typeargument</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Typeargument</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getTypeArguments_Typeargument()
   * @model containment="true"
   * @generated
   */
  EList<TypeArgument> getTypeargument();

} // TypeArguments
