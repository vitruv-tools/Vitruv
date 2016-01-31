/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage
 * @generated
 */
public interface CompoundFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    CompoundFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Create EObject And Add</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Create EObject And Add</em>'.
     * @generated
     */
    <T extends EObject> CreateEObjectAndAdd<T> createCreateEObjectAndAdd();

    /**
     * Returns a new object of class '<em>Delete EObject And Subtract</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete EObject And Subtract</em>'.
     * @generated
     */
    <T extends EObject> DeleteEObjectAndSubtract<T> createDeleteEObjectAndSubtract();

    /**
     * Returns a new object of class '<em>Delete EObject Create EObject And Replace Single</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete EObject Create EObject And Replace Single</em>'.
     * @generated
     */
    <T extends EObject> DeleteEObjectCreateEObjectAndReplaceSingle<T> createDeleteEObjectCreateEObjectAndReplaceSingle();

    /**
     * Returns a new object of class '<em>Delete EObject Create EObject And Replace In List</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete EObject Create EObject And Replace In List</em>'.
     * @generated
     */
    <T extends EObject> DeleteEObjectCreateEObjectAndReplaceInList<T> createDeleteEObjectCreateEObjectAndReplaceInList();

    /**
     * Returns a new object of class '<em>Move EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Move EObject</em>'.
     * @generated
     */
    <T extends EObject> MoveEObject<T> createMoveEObject();

    /**
     * Returns a new object of class '<em>Replace In EList</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace In EList</em>'.
     * @generated
     */
    <T extends Object> ReplaceInEList<T> createReplaceInEList();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    CompoundPackage getCompoundPackage();

} //CompoundFactory
