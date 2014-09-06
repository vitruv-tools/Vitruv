/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage
 * @generated
 */
public interface AttributeFactory extends EFactory {
	/**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	AttributeFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributeFactoryImpl.init();

	/**
     * Returns a new object of class '<em>Update Single Valued EAttribute</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Update Single Valued EAttribute</em>'.
     * @generated
     */
	<T extends Object> UpdateSingleValuedEAttribute<T> createUpdateSingleValuedEAttribute();

	/**
     * Returns a new object of class '<em>Insert EAttribute Value</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Insert EAttribute Value</em>'.
     * @generated
     */
	<T extends Object> InsertEAttributeValue<T> createInsertEAttributeValue();

	/**
     * Returns a new object of class '<em>Replace EAttribute Value</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace EAttribute Value</em>'.
     * @generated
     */
	<T extends Object> ReplaceEAttributeValue<T> createReplaceEAttributeValue();

	/**
     * Returns a new object of class '<em>Remove EAttribute Value</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Remove EAttribute Value</em>'.
     * @generated
     */
	<T extends Object> RemoveEAttributeValue<T> createRemoveEAttributeValue();

	/**
     * Returns a new object of class '<em>Permute EAttribute Values</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Permute EAttribute Values</em>'.
     * @generated
     */
	<T extends Object> PermuteEAttributeValues<T> createPermuteEAttributeValues();

	/**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
	AttributePackage getAttributePackage();

} //AttributeFactory
