/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.*;

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
            case ReferencePackage.UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE: return createUpdateSingleValuedNonContainmentEReference();
            case ReferencePackage.INSERT_NON_CONTAINMENT_EREFERENCE: return createInsertNonContainmentEReference();
            case ReferencePackage.REPLACE_NON_CONTAINMENT_EREFERENCE: return createReplaceNonContainmentEReference();
            case ReferencePackage.REMOVE_NON_CONTAINMENT_EREFERENCE: return createRemoveNonContainmentEReference();
            case ReferencePackage.PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES: return createPermuteNonContainmentEReferenceValues();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> UpdateSingleValuedNonContainmentEReference<T> createUpdateSingleValuedNonContainmentEReference() {
        UpdateSingleValuedNonContainmentEReferenceImpl<T> updateSingleValuedNonContainmentEReference = new UpdateSingleValuedNonContainmentEReferenceImpl<T>();
        return updateSingleValuedNonContainmentEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> InsertNonContainmentEReference<T> createInsertNonContainmentEReference() {
        InsertNonContainmentEReferenceImpl<T> insertNonContainmentEReference = new InsertNonContainmentEReferenceImpl<T>();
        return insertNonContainmentEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> ReplaceNonContainmentEReference<T> createReplaceNonContainmentEReference() {
        ReplaceNonContainmentEReferenceImpl<T> replaceNonContainmentEReference = new ReplaceNonContainmentEReferenceImpl<T>();
        return replaceNonContainmentEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> RemoveNonContainmentEReference<T> createRemoveNonContainmentEReference() {
        RemoveNonContainmentEReferenceImpl<T> removeNonContainmentEReference = new RemoveNonContainmentEReferenceImpl<T>();
        return removeNonContainmentEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> PermuteNonContainmentEReferenceValues<T> createPermuteNonContainmentEReferenceValues() {
        PermuteNonContainmentEReferenceValuesImpl<T> permuteNonContainmentEReferenceValues = new PermuteNonContainmentEReferenceValuesImpl<T>();
        return permuteNonContainmentEReferenceValues;
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
