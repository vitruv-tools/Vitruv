/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constructor Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.ConstructorBody#getBlockstatement <em>Blockstatement</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorBody()
 * @model
 * @generated
 */
public interface ConstructorBody extends EObject
{
  /**
   * Returns the value of the '<em><b>Blockstatement</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.BlockStatement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Blockstatement</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Blockstatement</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getConstructorBody_Blockstatement()
   * @model containment="true"
   * @generated
   */
  EList<BlockStatement> getBlockstatement();

} // ConstructorBody
