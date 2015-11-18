/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage
 * @generated
 */
public interface CorrespondenceFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    CorrespondenceFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.impl.CorrespondenceFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Correspondences</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Correspondences</em>'.
     * @generated
     */
    Correspondences createCorrespondences();

    /**
     * Returns a new object of class '<em>Correspondence</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Correspondence</em>'.
     * @generated
     */
    Correspondence createCorrespondence();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    CorrespondencePackage getCorrespondencePackage();

} //CorrespondenceFactory
