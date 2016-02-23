/**
 */
package attribute_to_structure_struct_1;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link attribute_to_structure_struct_1.ModelB#getContent <em>Content</em>}</li>
 * </ul>
 *
 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getModelB()
 * @model
 * @generated
 */
public interface ModelB extends Identified {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference list.
	 * The list contents are of type {@link attribute_to_structure_struct_1.Structured}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference list.
	 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getModelB_Content()
	 * @model containment="true"
	 * @generated
	 */
	EList<Structured> getContent();

} // ModelB
