/**
 */
package edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationPackage
 * @generated
 */
public interface IntegrationFactory extends EFactory {
	/**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	IntegrationFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.impl.IntegrationFactoryImpl.init();

	/**
     * Returns a new object of class '<em>Correspondence</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Correspondence</em>'.
     * @generated
     */
	IntegrationCorrespondence createIntegrationCorrespondence();

	/**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
	IntegrationPackage getIntegrationPackage();

} //IntegrationFactory
