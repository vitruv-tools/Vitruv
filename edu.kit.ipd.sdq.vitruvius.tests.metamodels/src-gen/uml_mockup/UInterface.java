/**
 */
package uml_mockup;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UInterface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uml_mockup.UInterface#getMethods <em>Methods</em>}</li>
 * </ul>
 *
 * @see uml_mockup.Uml_mockupPackage#getUInterface()
 * @model
 * @generated
 */
public interface UInterface extends Identified, UNamedElement {
	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link uml_mockup.UMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methods</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see uml_mockup.Uml_mockupPackage#getUInterface_Methods()
	 * @model containment="true"
	 * @generated
	 */
	EList<UMethod> getMethods();

} // UInterface
