/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage
 * @generated
 */
public interface AttributeFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AttributeFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributeFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Replace Single Valued EAttribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace Single Valued EAttribute</em>'.
     * @generated
     */
    <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A, T> createReplaceSingleValuedEAttribute();

    /**
     * Returns a new object of class '<em>Insert EAttribute Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Insert EAttribute Value</em>'.
     * @generated
     */
    <A extends EObject, T extends Object> InsertEAttributeValue<A, T> createInsertEAttributeValue();

    /**
     * Returns a new object of class '<em>Remove EAttribute Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Remove EAttribute Value</em>'.
     * @generated
     */
    <A extends EObject, T extends Object> RemoveEAttributeValue<A, T> createRemoveEAttributeValue();

    /**
     * Returns a new object of class '<em>Permute EAttribute Values</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Permute EAttribute Values</em>'.
     * @generated
     */
    <A extends EObject> PermuteEAttributeValues<A> createPermuteEAttributeValues();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    AttributePackage getAttributePackage();

} //AttributeFactory
