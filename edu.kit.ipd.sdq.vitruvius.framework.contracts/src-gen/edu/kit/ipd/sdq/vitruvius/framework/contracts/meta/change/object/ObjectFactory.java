/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.ObjectPackage
 * @generated
 */
public interface ObjectFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ObjectFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl.ObjectFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Create EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Create EObject</em>'.
     * @generated
     */
    <T extends EObject> CreateEObject<T> createCreateEObject();

    /**
     * Returns a new object of class '<em>Delete EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete EObject</em>'.
     * @generated
     */
    <T extends EObject> DeleteEObject<T> createDeleteEObject();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ObjectPackage getObjectPackage();

} //ObjectFactory
