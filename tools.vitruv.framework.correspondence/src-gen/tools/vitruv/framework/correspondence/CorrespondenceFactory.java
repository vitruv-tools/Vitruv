/**
 */
package tools.vitruv.framework.correspondence;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.correspondence.CorrespondencePackage
 * @generated
 */
public interface CorrespondenceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CorrespondenceFactory eINSTANCE = tools.vitruv.framework.correspondence.impl.CorrespondenceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Correspondences</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Correspondences</em>'.
	 * @generated
	 */
	Correspondences createCorrespondences();

	/**
	 * Returns a new object of class '<em>Manual Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Manual Correspondence</em>'.
	 * @generated
	 */
	ManualCorrespondence createManualCorrespondence();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorrespondencePackage getCorrespondencePackage();

} //CorrespondenceFactory
