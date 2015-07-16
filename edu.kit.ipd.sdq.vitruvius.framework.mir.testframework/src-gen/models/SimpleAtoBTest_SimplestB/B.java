/**
 */
package SimpleAtoBTest_SimplestB;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SimpleAtoBTest_SimplestB.B#getB_name <em>Bname</em>}</li>
 *   <li>{@link SimpleAtoBTest_SimplestB.B#getB_children <em>Bchildren</em>}</li>
 * </ul>
 *
 * @see SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage#getB()
 * @model
 * @generated
 */
public interface B extends EObject {
	/**
	 * Returns the value of the '<em><b>Bname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bname</em>' attribute.
	 * @see #setB_name(String)
	 * @see SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage#getB_B_name()
	 * @model
	 * @generated
	 */
	String getB_name();

	/**
	 * Sets the value of the '{@link SimpleAtoBTest_SimplestB.B#getB_name <em>Bname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bname</em>' attribute.
	 * @see #getB_name()
	 * @generated
	 */
	void setB_name(String value);

	/**
	 * Returns the value of the '<em><b>Bchildren</b></em>' reference list.
	 * The list contents are of type {@link SimpleAtoBTest_SimplestB.BChild}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bchildren</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bchildren</em>' reference list.
	 * @see SimpleAtoBTest_SimplestB.SimpleAtoBTest_SimplestBPackage#getB_B_children()
	 * @model
	 * @generated
	 */
	EList<BChild> getB_children();

} // B
