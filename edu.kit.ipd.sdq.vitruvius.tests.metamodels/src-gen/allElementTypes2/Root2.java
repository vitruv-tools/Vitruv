/**
 */
package allElementTypes2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root2</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes2.Root2#getSingleValuedEAttribute2 <em>Single Valued EAttribute2</em>}</li>
 *   <li>{@link allElementTypes2.Root2#getSingleValuedNonContainmentEReference2 <em>Single Valued Non Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.Root2#getSingleValuedContainmentEReference2 <em>Single Valued Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.Root2#getMultiValuedEAttribute2 <em>Multi Valued EAttribute2</em>}</li>
 *   <li>{@link allElementTypes2.Root2#getMultiValuedNonContainmentEReference2 <em>Multi Valued Non Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.Root2#getMultiValuedContainmentEReference2 <em>Multi Valued Containment EReference2</em>}</li>
 *   <li>{@link allElementTypes2.Root2#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}</li>
 * </ul>
 *
 * @see allElementTypes2.AllElementTypes2Package#getRoot2()
 * @model
 * @generated
 */
public interface Root2 extends Identified2 {
	/**
	 * Returns the value of the '<em><b>Single Valued EAttribute2</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued EAttribute2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued EAttribute2</em>' attribute.
	 * @see #setSingleValuedEAttribute2(Integer)
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_SingleValuedEAttribute2()
	 * @model default="0"
	 * @generated
	 */
	Integer getSingleValuedEAttribute2();

	/**
	 * Sets the value of the '{@link allElementTypes2.Root2#getSingleValuedEAttribute2 <em>Single Valued EAttribute2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued EAttribute2</em>' attribute.
	 * @see #getSingleValuedEAttribute2()
	 * @generated
	 */
	void setSingleValuedEAttribute2(Integer value);

	/**
	 * Returns the value of the '<em><b>Single Valued Non Containment EReference2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued Non Containment EReference2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued Non Containment EReference2</em>' reference.
	 * @see #setSingleValuedNonContainmentEReference2(NonRoot2)
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_SingleValuedNonContainmentEReference2()
	 * @model
	 * @generated
	 */
	NonRoot2 getSingleValuedNonContainmentEReference2();

	/**
	 * Sets the value of the '{@link allElementTypes2.Root2#getSingleValuedNonContainmentEReference2 <em>Single Valued Non Containment EReference2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued Non Containment EReference2</em>' reference.
	 * @see #getSingleValuedNonContainmentEReference2()
	 * @generated
	 */
	void setSingleValuedNonContainmentEReference2(NonRoot2 value);

	/**
	 * Returns the value of the '<em><b>Single Valued Containment EReference2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued Containment EReference2</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued Containment EReference2</em>' containment reference.
	 * @see #setSingleValuedContainmentEReference2(NonRoot2)
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_SingleValuedContainmentEReference2()
	 * @model containment="true"
	 * @generated
	 */
	NonRoot2 getSingleValuedContainmentEReference2();

	/**
	 * Sets the value of the '{@link allElementTypes2.Root2#getSingleValuedContainmentEReference2 <em>Single Valued Containment EReference2</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued Containment EReference2</em>' containment reference.
	 * @see #getSingleValuedContainmentEReference2()
	 * @generated
	 */
	void setSingleValuedContainmentEReference2(NonRoot2 value);

	/**
	 * Returns the value of the '<em><b>Multi Valued EAttribute2</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued EAttribute2</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued EAttribute2</em>' attribute list.
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_MultiValuedEAttribute2()
	 * @model
	 * @generated
	 */
	EList<Integer> getMultiValuedEAttribute2();

	/**
	 * Returns the value of the '<em><b>Multi Valued Non Containment EReference2</b></em>' reference list.
	 * The list contents are of type {@link allElementTypes2.NonRoot2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued Non Containment EReference2</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued Non Containment EReference2</em>' reference list.
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_MultiValuedNonContainmentEReference2()
	 * @model
	 * @generated
	 */
	EList<NonRoot2> getMultiValuedNonContainmentEReference2();

	/**
	 * Returns the value of the '<em><b>Multi Valued Containment EReference2</b></em>' containment reference list.
	 * The list contents are of type {@link allElementTypes2.NonRoot2}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued Containment EReference2</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued Containment EReference2</em>' containment reference list.
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_MultiValuedContainmentEReference2()
	 * @model containment="true"
	 * @generated
	 */
	EList<NonRoot2> getMultiValuedContainmentEReference2();

	/**
	 * Returns the value of the '<em><b>Non Root Object Container Helper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Root Object Container Helper</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Root Object Container Helper</em>' containment reference.
	 * @see #setNonRootObjectContainerHelper(NonRootObjectContainerHelper2)
	 * @see allElementTypes2.AllElementTypes2Package#getRoot2_NonRootObjectContainerHelper()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NonRootObjectContainerHelper2 getNonRootObjectContainerHelper();

	/**
	 * Sets the value of the '{@link allElementTypes2.Root2#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Root Object Container Helper</em>' containment reference.
	 * @see #getNonRootObjectContainerHelper()
	 * @generated
	 */
	void setNonRootObjectContainerHelper(NonRootObjectContainerHelper2 value);

} // Root2
