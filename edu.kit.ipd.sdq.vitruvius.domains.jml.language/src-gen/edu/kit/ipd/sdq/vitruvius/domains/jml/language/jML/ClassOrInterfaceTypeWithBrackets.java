/**
 */
package edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Or Interface Type With Brackets</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getClassOrInterfaceTypeWithBrackets()
 * @model
 * @generated
 */
public interface ClassOrInterfaceTypeWithBrackets extends Type
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassifierType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage#getClassOrInterfaceTypeWithBrackets_Type()
   * @model containment="true"
   * @generated
   */
  EList<ClassifierType> getType();

} // ClassOrInterfaceTypeWithBrackets
