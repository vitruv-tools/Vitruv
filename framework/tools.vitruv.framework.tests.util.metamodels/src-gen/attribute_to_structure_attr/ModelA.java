/**
 */
package attribute_to_structure_attr;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model A</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link attribute_to_structure_attr.ModelA#getContent <em>Content</em>}</li>
 * </ul>
 *
 * @see attribute_to_structure_attr.Attribute_to_structure_attrPackage#getModelA()
 * @model
 * @generated
 */
public interface ModelA extends Identified {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference list.
	 * The list contents are of type {@link attribute_to_structure_attr.Attributed}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference list.
	 * @see attribute_to_structure_attr.Attribute_to_structure_attrPackage#getModelA_Content()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attributed> getContent();

} // ModelA
