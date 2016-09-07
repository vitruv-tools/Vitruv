/**
 */
package tools.vitruvius.dsls.response.meta.correspondence.response.impl;

import tools.vitruvius.dsls.response.meta.correspondence.response.*;

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
public class ResponseFactoryImpl extends EFactoryImpl implements ResponseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResponseFactory init() {
		try {
			ResponseFactory theResponseFactory = (ResponseFactory)EPackage.Registry.INSTANCE.getEFactory(ResponsePackage.eNS_URI);
			if (theResponseFactory != null) {
				return theResponseFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ResponseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponseFactoryImpl() {
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
			case ResponsePackage.RESPONSE_CORRESPONDENCE: return createResponseCorrespondence();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponseCorrespondence createResponseCorrespondence() {
		ResponseCorrespondenceImpl responseCorrespondence = new ResponseCorrespondenceImpl();
		return responseCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponsePackage getResponsePackage() {
		return (ResponsePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ResponsePackage getPackage() {
		return ResponsePackage.eINSTANCE;
	}

} //ResponseFactoryImpl
