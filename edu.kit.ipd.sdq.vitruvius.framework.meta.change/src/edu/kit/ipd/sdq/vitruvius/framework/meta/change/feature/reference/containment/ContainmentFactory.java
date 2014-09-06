/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage
 * @generated
 */
public interface ContainmentFactory extends EFactory {
	/**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	ContainmentFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentFactoryImpl.init();

	/**
     * Returns a new object of class '<em>Create Non Root EObject Single</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Create Non Root EObject Single</em>'.
     * @generated
     */
	<T extends EObject> CreateNonRootEObjectSingle<T> createCreateNonRootEObjectSingle();

	/**
     * Returns a new object of class '<em>Replace Non Root EObject Single</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace Non Root EObject Single</em>'.
     * @generated
     */
	<T extends EObject> ReplaceNonRootEObjectSingle<T> createReplaceNonRootEObjectSingle();

	/**
     * Returns a new object of class '<em>Delete Non Root EObject Single</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete Non Root EObject Single</em>'.
     * @generated
     */
	<T extends EObject> DeleteNonRootEObjectSingle<T> createDeleteNonRootEObjectSingle();

	/**
     * Returns a new object of class '<em>Create Non Root EObject In List</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Create Non Root EObject In List</em>'.
     * @generated
     */
	<T extends EObject> CreateNonRootEObjectInList<T> createCreateNonRootEObjectInList();

	/**
     * Returns a new object of class '<em>Replace Non Root EObject In List</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace Non Root EObject In List</em>'.
     * @generated
     */
	<T extends EObject> ReplaceNonRootEObjectInList<T> createReplaceNonRootEObjectInList();

	/**
     * Returns a new object of class '<em>Delete Non Root EObject In List</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete Non Root EObject In List</em>'.
     * @generated
     */
	<T extends EObject> DeleteNonRootEObjectInList<T> createDeleteNonRootEObjectInList();

	/**
     * Returns a new object of class '<em>Permute Containment EReference Values</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Permute Containment EReference Values</em>'.
     * @generated
     */
	<T extends EObject> PermuteContainmentEReferenceValues<T> createPermuteContainmentEReferenceValues();

	/**
     * Returns a new object of class '<em>Insert Non Root EObject In Containment List</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Insert Non Root EObject In Containment List</em>'.
     * @generated
     */
    <T extends EObject> InsertNonRootEObjectInContainmentList<T> createInsertNonRootEObjectInContainmentList();

    /**
     * Returns a new object of class '<em>Remove Non Root EObject From Containment List</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Remove Non Root EObject From Containment List</em>'.
     * @generated
     */
    <T extends EObject> RemoveNonRootEObjectFromContainmentList<T> createRemoveNonRootEObjectFromContainmentList();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
	ContainmentPackage getContainmentPackage();

} //ContainmentFactory
