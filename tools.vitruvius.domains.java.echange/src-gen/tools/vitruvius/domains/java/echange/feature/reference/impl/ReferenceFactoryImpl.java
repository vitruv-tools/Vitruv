/**
 */
package tools.vitruvius.domains.java.echange.feature.reference.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruvius.domains.java.echange.feature.reference.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReferenceFactoryImpl extends EFactoryImpl implements ReferenceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ReferenceFactory init() {
		try {
			ReferenceFactory theReferenceFactory = (ReferenceFactory)EPackage.Registry.INSTANCE.getEFactory(ReferencePackage.eNS_URI);
			if (theReferenceFactory != null) {
				return theReferenceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ReferenceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceFactoryImpl() {
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
			case ReferencePackage.JAVA_INSERT_EREFERENCE: return createJavaInsertEReference();
			case ReferencePackage.JAVA_REMOVE_EREFERENCE: return createJavaRemoveEReference();
			case ReferencePackage.JAVA_REPLACE_SINGLE_VALUED_EREFERENCE: return createJavaReplaceSingleValuedEReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> JavaInsertEReference<A, T> createJavaInsertEReference() {
		JavaInsertEReferenceImpl<A, T> javaInsertEReference = new JavaInsertEReferenceImpl<A, T>();
		return javaInsertEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> JavaRemoveEReference<A, T> createJavaRemoveEReference() {
		JavaRemoveEReferenceImpl<A, T> javaRemoveEReference = new JavaRemoveEReferenceImpl<A, T>();
		return javaRemoveEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> JavaReplaceSingleValuedEReference<A, T> createJavaReplaceSingleValuedEReference() {
		JavaReplaceSingleValuedEReferenceImpl<A, T> javaReplaceSingleValuedEReference = new JavaReplaceSingleValuedEReferenceImpl<A, T>();
		return javaReplaceSingleValuedEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferencePackage getReferencePackage() {
		return (ReferencePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ReferencePackage getPackage() {
		return ReferencePackage.eINSTANCE;
	}

} //ReferenceFactoryImpl
