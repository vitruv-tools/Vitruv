/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage
 * @generated
 */
public interface ChangeFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChangeFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl.ChangeFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Create Non Root EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Create Non Root EObject</em>'.
     * @generated
     */
    <T extends EObject, U extends EReference> CreateNonRootEObject<T, U> createCreateNonRootEObject();

    /**
     * Returns a new object of class '<em>Create Root EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Create Root EObject</em>'.
     * @generated
     */
    CreateRootEObject createCreateRootEObject();

    /**
     * Returns a new object of class '<em>Unset EFeature</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Unset EFeature</em>'.
     * @generated
     */
    <T extends EStructuralFeature> UnsetEFeature<T> createUnsetEFeature();

    /**
     * Returns a new object of class '<em>Update EAttribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Update EAttribute</em>'.
     * @generated
     */
    <T extends Object, U extends EAttribute> UpdateEAttribute<T, U> createUpdateEAttribute();

    /**
     * Returns a new object of class '<em>Update EReference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Update EReference</em>'.
     * @generated
     */
    <T extends EObject, U extends EReference> UpdateEReference<T, U> createUpdateEReference();

    /**
     * Returns a new object of class '<em>Update EContainment Reference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Update EContainment Reference</em>'.
     * @generated
     */
    <T extends EObject, U extends EReference> UpdateEContainmentReference<T, U> createUpdateEContainmentReference();

    /**
     * Returns a new object of class '<em>Delete Non Root EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete Non Root EObject</em>'.
     * @generated
     */
    <T extends EObject, U extends EReference> DeleteNonRootEObject<T, U> createDeleteNonRootEObject();

    /**
     * Returns a new object of class '<em>Delete Root EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete Root EObject</em>'.
     * @generated
     */
    DeleteRootEObject createDeleteRootEObject();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ChangePackage getChangePackage();

} //ChangeFactory
