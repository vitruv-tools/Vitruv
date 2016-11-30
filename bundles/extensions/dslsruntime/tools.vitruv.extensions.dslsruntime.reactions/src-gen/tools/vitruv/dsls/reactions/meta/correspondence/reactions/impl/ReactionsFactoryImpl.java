/**
 */
package tools.vitruv.dsls.reactions.meta.correspondence.reactions.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.dsls.reactions.meta.correspondence.reactions.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReactionsFactoryImpl extends EFactoryImpl implements ReactionsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ReactionsFactory init() {
		try {
			ReactionsFactory theReactionsFactory = (ReactionsFactory)EPackage.Registry.INSTANCE.getEFactory(ReactionsPackage.eNS_URI);
			if (theReactionsFactory != null) {
				return theReactionsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ReactionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReactionsFactoryImpl() {
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
			case ReactionsPackage.REACTIONS_CORRESPONDENCE: return createReactionsCorrespondence();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReactionsCorrespondence createReactionsCorrespondence() {
		ReactionsCorrespondenceImpl reactionsCorrespondence = new ReactionsCorrespondenceImpl();
		return reactionsCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReactionsPackage getReactionsPackage() {
		return (ReactionsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ReactionsPackage getPackage() {
		return ReactionsPackage.eINSTANCE;
	}

} //ReactionsFactoryImpl
