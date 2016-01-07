/**
 */
package allElementTypes;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes.Root#getSingleValuedEAttribute <em>Single Valued EAttribute</em>}</li>
 *   <li>{@link allElementTypes.Root#getSingleValuedNonContainmentEReference <em>Single Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getSingleValuedContainmentEReference <em>Single Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedEAttribute <em>Multi Valued EAttribute</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedNonContainmentEReference <em>Multi Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedContainmentEReference <em>Multi Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}</li>
 *   <li>{@link allElementTypes.Root#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see allElementTypes.AllElementTypesPackage#getRoot()
 * @model
 * @generated
 */
public interface Root extends EObject {
	/**
	 * Returns the value of the '<em><b>Single Valued EAttribute</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued EAttribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued EAttribute</em>' attribute.
	 * @see #setSingleValuedEAttribute(int)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_SingleValuedEAttribute()
	 * @model default="0"
	 * @generated
	 */
	int getSingleValuedEAttribute();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getSingleValuedEAttribute <em>Single Valued EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued EAttribute</em>' attribute.
	 * @see #getSingleValuedEAttribute()
	 * @generated
	 */
	void setSingleValuedEAttribute(int value);

	/**
	 * Returns the value of the '<em><b>Single Valued Non Containment EReference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued Non Containment EReference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued Non Containment EReference</em>' reference.
	 * @see #setSingleValuedNonContainmentEReference(NonRoot)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_SingleValuedNonContainmentEReference()
	 * @model
	 * @generated
	 */
	NonRoot getSingleValuedNonContainmentEReference();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getSingleValuedNonContainmentEReference <em>Single Valued Non Containment EReference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued Non Containment EReference</em>' reference.
	 * @see #getSingleValuedNonContainmentEReference()
	 * @generated
	 */
	void setSingleValuedNonContainmentEReference(NonRoot value);

	/**
	 * Returns the value of the '<em><b>Single Valued Containment EReference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued Containment EReference</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued Containment EReference</em>' containment reference.
	 * @see #setSingleValuedContainmentEReference(NonRoot)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_SingleValuedContainmentEReference()
	 * @model containment="true"
	 * @generated
	 */
	NonRoot getSingleValuedContainmentEReference();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getSingleValuedContainmentEReference <em>Single Valued Containment EReference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued Containment EReference</em>' containment reference.
	 * @see #getSingleValuedContainmentEReference()
	 * @generated
	 */
	void setSingleValuedContainmentEReference(NonRoot value);

	/**
	 * Returns the value of the '<em><b>Multi Valued EAttribute</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued EAttribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued EAttribute</em>' attribute list.
	 * @see allElementTypes.AllElementTypesPackage#getRoot_MultiValuedEAttribute()
	 * @model
	 * @generated
	 */
	EList<Integer> getMultiValuedEAttribute();

	/**
	 * Returns the value of the '<em><b>Multi Valued Non Containment EReference</b></em>' reference list.
	 * The list contents are of type {@link allElementTypes.NonRoot}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued Non Containment EReference</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued Non Containment EReference</em>' reference list.
	 * @see allElementTypes.AllElementTypesPackage#getRoot_MultiValuedNonContainmentEReference()
	 * @model
	 * @generated
	 */
	EList<NonRoot> getMultiValuedNonContainmentEReference();

	/**
	 * Returns the value of the '<em><b>Multi Valued Containment EReference</b></em>' containment reference list.
	 * The list contents are of type {@link allElementTypes.NonRoot}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued Containment EReference</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued Containment EReference</em>' containment reference list.
	 * @see allElementTypes.AllElementTypesPackage#getRoot_MultiValuedContainmentEReference()
	 * @model containment="true"
	 * @generated
	 */
	EList<NonRoot> getMultiValuedContainmentEReference();

	/**
	 * Returns the value of the '<em><b>Non Root Object Container Helper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Root Object Container Helper</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Root Object Container Helper</em>' containment reference.
	 * @see #setNonRootObjectContainerHelper(NonRootObjectContainerHelper)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_NonRootObjectContainerHelper()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NonRootObjectContainerHelper getNonRootObjectContainerHelper();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Root Object Container Helper</em>' containment reference.
	 * @see #getNonRootObjectContainerHelper()
	 * @generated
	 */
	void setNonRootObjectContainerHelper(NonRootObjectContainerHelper value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void singleValuedOperation();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<Integer> multiValuedOperation();

} // Root
