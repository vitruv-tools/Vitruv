/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage
 * @generated
 */
public interface ReferenceFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ReferenceFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferenceFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Replace Single Valued EReference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace Single Valued EReference</em>'.
     * @generated
     */
    <T extends EObject> ReplaceSingleValuedEReference<T> createReplaceSingleValuedEReference();

    /**
     * Returns a new object of class '<em>Insert EReference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Insert EReference</em>'.
     * @generated
     */
    <T extends EObject> InsertEReference<T> createInsertEReference();

    /**
     * Returns a new object of class '<em>Remove EReference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Remove EReference</em>'.
     * @generated
     */
    <T extends EObject> RemoveEReference<T> createRemoveEReference();

    /**
     * Returns a new object of class '<em>Permute EReference Values</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Permute EReference Values</em>'.
     * @generated
     */
    <T extends EObject> PermuteEReferenceValues<T> createPermuteEReferenceValues();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ReferencePackage getReferencePackage();

} //ReferenceFactory
