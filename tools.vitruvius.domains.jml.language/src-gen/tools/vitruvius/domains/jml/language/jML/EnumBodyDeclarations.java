/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Body Declarations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.EnumBodyDeclarations#getClassbodydeclaration <em>Classbodydeclaration</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumBodyDeclarations()
 * @model
 * @generated
 */
public interface EnumBodyDeclarations extends EObject
{
  /**
   * Returns the value of the '<em><b>Classbodydeclaration</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ClassBodyDeclaration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Classbodydeclaration</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Classbodydeclaration</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getEnumBodyDeclarations_Classbodydeclaration()
   * @model containment="true"
   * @generated
   */
  EList<ClassBodyDeclaration> getClassbodydeclaration();

} // EnumBodyDeclarations
