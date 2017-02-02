/**
 */
package allElementTypes;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link allElementTypes.Root#getSingleValuedPrimitiveTypeEAttribute <em>Single Valued Primitive Type EAttribute</em>}</li>
 *   <li>{@link allElementTypes.Root#getSingleValuedUnsettableEAttribute <em>Single Valued Unsettable EAttribute</em>}</li>
 *   <li>{@link allElementTypes.Root#getSingleValuedNonContainmentEReference <em>Single Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getSingleValuedContainmentEReference <em>Single Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedEAttribute <em>Multi Valued EAttribute</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedUnsettableEAttribute <em>Multi Valued Unsettable EAttribute</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedNonContainmentEReference <em>Multi Valued Non Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getMultiValuedContainmentEReference <em>Multi Valued Containment EReference</em>}</li>
 *   <li>{@link allElementTypes.Root#getNonRootObjectContainerHelper <em>Non Root Object Container Helper</em>}</li>
 *   <li>{@link allElementTypes.Root#getRecursiveRoot <em>Recursive Root</em>}</li>
 * </ul>
 *
 * @see allElementTypes.AllElementTypesPackage#getRoot()
 * @model
 * @generated
 */
public interface Root extends Identified {
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
	 * @see #setSingleValuedEAttribute(Integer)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_SingleValuedEAttribute()
	 * @model default="0"
	 * @generated
	 */
	Integer getSingleValuedEAttribute();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getSingleValuedEAttribute <em>Single Valued EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued EAttribute</em>' attribute.
	 * @see #getSingleValuedEAttribute()
	 * @generated
	 */
	void setSingleValuedEAttribute(Integer value);

	/**
	 * Returns the value of the '<em><b>Single Valued Primitive Type EAttribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued Primitive Type EAttribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued Primitive Type EAttribute</em>' attribute.
	 * @see #setSingleValuedPrimitiveTypeEAttribute(int)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_SingleValuedPrimitiveTypeEAttribute()
	 * @model
	 * @generated
	 */
	int getSingleValuedPrimitiveTypeEAttribute();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getSingleValuedPrimitiveTypeEAttribute <em>Single Valued Primitive Type EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued Primitive Type EAttribute</em>' attribute.
	 * @see #getSingleValuedPrimitiveTypeEAttribute()
	 * @generated
	 */
	void setSingleValuedPrimitiveTypeEAttribute(int value);

	/**
	 * Returns the value of the '<em><b>Single Valued Unsettable EAttribute</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Valued Unsettable EAttribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Valued Unsettable EAttribute</em>' attribute.
	 * @see #isSetSingleValuedUnsettableEAttribute()
	 * @see #unsetSingleValuedUnsettableEAttribute()
	 * @see #setSingleValuedUnsettableEAttribute(Integer)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_SingleValuedUnsettableEAttribute()
	 * @model default="0" unsettable="true"
	 * @generated
	 */
	Integer getSingleValuedUnsettableEAttribute();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getSingleValuedUnsettableEAttribute <em>Single Valued Unsettable EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Valued Unsettable EAttribute</em>' attribute.
	 * @see #isSetSingleValuedUnsettableEAttribute()
	 * @see #unsetSingleValuedUnsettableEAttribute()
	 * @see #getSingleValuedUnsettableEAttribute()
	 * @generated
	 */
	void setSingleValuedUnsettableEAttribute(Integer value);

	/**
	 * Unsets the value of the '{@link allElementTypes.Root#getSingleValuedUnsettableEAttribute <em>Single Valued Unsettable EAttribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSingleValuedUnsettableEAttribute()
	 * @see #getSingleValuedUnsettableEAttribute()
	 * @see #setSingleValuedUnsettableEAttribute(Integer)
	 * @generated
	 */
	void unsetSingleValuedUnsettableEAttribute();

	/**
	 * Returns whether the value of the '{@link allElementTypes.Root#getSingleValuedUnsettableEAttribute <em>Single Valued Unsettable EAttribute</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Single Valued Unsettable EAttribute</em>' attribute is set.
	 * @see #unsetSingleValuedUnsettableEAttribute()
	 * @see #getSingleValuedUnsettableEAttribute()
	 * @see #setSingleValuedUnsettableEAttribute(Integer)
	 * @generated
	 */
	boolean isSetSingleValuedUnsettableEAttribute();

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
	 * Returns the value of the '<em><b>Multi Valued Unsettable EAttribute</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Valued Unsettable EAttribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Valued Unsettable EAttribute</em>' attribute list.
	 * @see #isSetMultiValuedUnsettableEAttribute()
	 * @see #unsetMultiValuedUnsettableEAttribute()
	 * @see allElementTypes.AllElementTypesPackage#getRoot_MultiValuedUnsettableEAttribute()
	 * @model unsettable="true"
	 * @generated
	 */
	EList<Integer> getMultiValuedUnsettableEAttribute();

	/**
	 * Unsets the value of the '{@link allElementTypes.Root#getMultiValuedUnsettableEAttribute <em>Multi Valued Unsettable EAttribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMultiValuedUnsettableEAttribute()
	 * @see #getMultiValuedUnsettableEAttribute()
	 * @generated
	 */
	void unsetMultiValuedUnsettableEAttribute();

	/**
	 * Returns whether the value of the '{@link allElementTypes.Root#getMultiValuedUnsettableEAttribute <em>Multi Valued Unsettable EAttribute</em>}' attribute list is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Multi Valued Unsettable EAttribute</em>' attribute list is set.
	 * @see #unsetMultiValuedUnsettableEAttribute()
	 * @see #getMultiValuedUnsettableEAttribute()
	 * @generated
	 */
	boolean isSetMultiValuedUnsettableEAttribute();

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
	 * Returns the value of the '<em><b>Recursive Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recursive Root</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recursive Root</em>' containment reference.
	 * @see #setRecursiveRoot(Root)
	 * @see allElementTypes.AllElementTypesPackage#getRoot_RecursiveRoot()
	 * @model containment="true"
	 * @generated
	 */
	Root getRecursiveRoot();

	/**
	 * Sets the value of the '{@link allElementTypes.Root#getRecursiveRoot <em>Recursive Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recursive Root</em>' containment reference.
	 * @see #getRecursiveRoot()
	 * @generated
	 */
	void setRecursiveRoot(Root value);

} // Root
