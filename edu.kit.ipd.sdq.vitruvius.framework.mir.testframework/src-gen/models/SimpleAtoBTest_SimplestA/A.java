/**
 */
package SimpleAtoBTest_SimplestA;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>A</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SimpleAtoBTest_SimplestA.A#getA_name <em>Aname</em>}</li>
 *   <li>{@link SimpleAtoBTest_SimplestA.A#getA_children <em>Achildren</em>}</li>
 * </ul>
 *
 * @see SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage#getA()
 * @model
 * @generated
 */
public interface A extends EObject {
	/**
	 * Returns the value of the '<em><b>Aname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aname</em>' attribute.
	 * @see #setA_name(String)
	 * @see SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage#getA_A_name()
	 * @model
	 * @generated
	 */
	String getA_name();

	/**
	 * Sets the value of the '{@link SimpleAtoBTest_SimplestA.A#getA_name <em>Aname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Aname</em>' attribute.
	 * @see #getA_name()
	 * @generated
	 */
	void setA_name(String value);

	/**
	 * Returns the value of the '<em><b>Achildren</b></em>' containment reference list.
	 * The list contents are of type {@link SimpleAtoBTest_SimplestA.AChild}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Achildren</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Achildren</em>' containment reference list.
	 * @see SimpleAtoBTest_SimplestA.SimpleAtoBTest_SimplestAPackage#getA_A_children()
	 * @model containment="true"
	 * @generated
	 */
	EList<AChild> getA_children();

} // A
