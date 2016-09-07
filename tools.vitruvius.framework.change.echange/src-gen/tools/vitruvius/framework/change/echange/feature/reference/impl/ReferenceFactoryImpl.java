/**
 */
package tools.vitruvius.framework.change.echange.feature.reference.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruvius.framework.change.echange.feature.reference.*;

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
			case ReferencePackage.REPLACE_SINGLE_VALUED_EREFERENCE: return createReplaceSingleValuedEReference();
			case ReferencePackage.INSERT_EREFERENCE: return createInsertEReference();
			case ReferencePackage.REMOVE_EREFERENCE: return createRemoveEReference();
			case ReferencePackage.PERMUTE_EREFERENCES: return createPermuteEReferences();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject, T extends EObject> ReplaceSingleValuedEReference<A, T> createReplaceSingleValuedEReference() {
		tools.vitruvius.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl<A, T> replaceSingleValuedEReference = new tools.vitruvius.framework.change.echange.feature.reference.impl.ReplaceSingleValuedEReferenceImpl<A, T>();
		return replaceSingleValuedEReference;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject, T extends EObject> InsertEReference<A, T> createInsertEReference() {
		tools.vitruvius.framework.change.echange.feature.reference.impl.InsertEReferenceImpl<A, T> insertEReference = new tools.vitruvius.framework.change.echange.feature.reference.impl.InsertEReferenceImpl<A, T>();
		return insertEReference;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject, T extends EObject> RemoveEReference<A, T> createRemoveEReference() {
		tools.vitruvius.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl<A, T> removeEReference = new tools.vitruvius.framework.change.echange.feature.reference.impl.RemoveEReferenceImpl<A, T>();
		return removeEReference;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public <A extends EObject> PermuteEReferences<A> createPermuteEReferences() {
		tools.vitruvius.framework.change.echange.feature.reference.impl.PermuteEReferencesImpl<A> permuteEReferences = new tools.vitruvius.framework.change.echange.feature.reference.impl.PermuteEReferencesImpl<A>();
		return permuteEReferences;
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
