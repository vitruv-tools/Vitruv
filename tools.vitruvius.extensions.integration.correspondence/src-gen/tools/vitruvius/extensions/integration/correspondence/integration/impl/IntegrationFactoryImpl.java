/**
 */
package tools.vitruvius.extensions.integration.correspondence.integration.impl;

import tools.vitruvius.extensions.integration.correspondence.integration.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IntegrationFactoryImpl extends EFactoryImpl implements IntegrationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IntegrationFactory init() {
		try {
			IntegrationFactory theIntegrationFactory = (IntegrationFactory)EPackage.Registry.INSTANCE.getEFactory(IntegrationPackage.eNS_URI);
			if (theIntegrationFactory != null) {
				return theIntegrationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IntegrationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegrationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IntegrationPackage.INTEGRATION_CORRESPONDENCE: return createIntegrationCorrespondence();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegrationCorrespondence createIntegrationCorrespondence() {
		IntegrationCorrespondenceImpl integrationCorrespondence = new IntegrationCorrespondenceImpl();
		return integrationCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegrationPackage getIntegrationPackage() {
		return (IntegrationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IntegrationPackage getPackage() {
		return IntegrationPackage.eINSTANCE;
	}

} //IntegrationFactoryImpl
