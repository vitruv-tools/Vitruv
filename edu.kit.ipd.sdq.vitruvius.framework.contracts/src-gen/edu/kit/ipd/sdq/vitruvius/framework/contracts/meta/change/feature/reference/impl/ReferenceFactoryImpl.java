/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.*;

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
            case ReferencePackage.PERMUTE_EREFERENCE_VALUES: return createPermuteEReferenceValues();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> ReplaceSingleValuedEReference<T> createReplaceSingleValuedEReference() {
        ReplaceSingleValuedEReferenceImpl<T> replaceSingleValuedEReference = new ReplaceSingleValuedEReferenceImpl<T>();
        return replaceSingleValuedEReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> InsertEReference<T> createInsertEReference() {
        InsertEReferenceImpl<T> insertEReference = new InsertEReferenceImpl<T>();
        return insertEReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> RemoveEReference<T> createRemoveEReference() {
        RemoveEReferenceImpl<T> removeEReference = new RemoveEReferenceImpl<T>();
        return removeEReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> PermuteEReferenceValues<T> createPermuteEReferenceValues() {
        PermuteEReferenceValuesImpl<T> permuteEReferenceValues = new PermuteEReferenceValuesImpl<T>();
        return permuteEReferenceValues;
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
