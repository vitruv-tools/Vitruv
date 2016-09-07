/**
 */
package uml_mockup;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uml_mockup.UClass#getClassCount <em>Class Count</em>}</li>
 * </ul>
 *
 * @see uml_mockup.Uml_mockupPackage#getUClass()
 * @model
 * @generated
 */
public interface UClass extends Identified, UNamedElement {

	/**
	 * Returns the value of the '<em><b>Class Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Count</em>' attribute.
	 * @see #setClassCount(int)
	 * @see uml_mockup.Uml_mockupPackage#getUClass_ClassCount()
	 * @model required="true"
	 * @generated
	 */
	int getClassCount();

	/**
	 * Sets the value of the '{@link uml_mockup.UClass#getClassCount <em>Class Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Count</em>' attribute.
	 * @see #getClassCount()
	 * @generated
	 */
	void setClassCount(int value);
} // UClass
