/**
 */
package uml_mockup;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UPackage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uml_mockup.UPackage#getInterfaces <em>Interfaces</em>}</li>
 *   <li>{@link uml_mockup.UPackage#getClasses <em>Classes</em>}</li>
 * </ul>
 *
 * @see uml_mockup.Uml_mockupPackage#getUPackage()
 * @model
 * @generated
 */
public interface UPackage extends Identified, UNamedElement {
	/**
	 * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list.
	 * The list contents are of type {@link uml_mockup.UInterface}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interfaces</em>' containment reference list.
	 * @see uml_mockup.Uml_mockupPackage#getUPackage_Interfaces()
	 * @model containment="true"
	 * @generated
	 */
	EList<UInterface> getInterfaces();

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link uml_mockup.UClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see uml_mockup.Uml_mockupPackage#getUPackage_Classes()
	 * @model containment="true"
	 * @generated
	 */
	EList<UClass> getClasses();

} // UPackage
