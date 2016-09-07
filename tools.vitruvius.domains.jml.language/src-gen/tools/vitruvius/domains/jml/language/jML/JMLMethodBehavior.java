/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.JMLMethodBehavior#getSpecifications <em>Specifications</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMethodBehavior()
 * @model
 * @generated
 */
public interface JMLMethodBehavior extends JMLMethodSpecification
{
  /**
   * Returns the value of the '<em><b>Specifications</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.JMLMethodExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Specifications</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Specifications</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLMethodBehavior_Specifications()
   * @model containment="true"
   * @generated
   */
  EList<JMLMethodExpression> getSpecifications();

} // JMLMethodBehavior
