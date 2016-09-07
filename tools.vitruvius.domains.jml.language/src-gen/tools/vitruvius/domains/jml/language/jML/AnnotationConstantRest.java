/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation Constant Rest</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.AnnotationConstantRest#getVariabledeclarator <em>Variabledeclarator</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotationConstantRest()
 * @model
 * @generated
 */
public interface AnnotationConstantRest extends EObject
{
  /**
   * Returns the value of the '<em><b>Variabledeclarator</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.VariableDeclarator}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Variabledeclarator</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variabledeclarator</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getAnnotationConstantRest_Variabledeclarator()
   * @model containment="true"
   * @generated
   */
  EList<VariableDeclarator> getVariabledeclarator();

} // AnnotationConstantRest
