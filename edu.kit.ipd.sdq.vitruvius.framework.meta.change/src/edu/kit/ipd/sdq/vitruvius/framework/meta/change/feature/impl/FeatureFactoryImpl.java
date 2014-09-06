/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.*;

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
public class FeatureFactoryImpl extends EFactoryImpl implements FeatureFactory {
	/**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static FeatureFactory init() {
        try {
            FeatureFactory theFeatureFactory = (FeatureFactory)EPackage.Registry.INSTANCE.getEFactory(FeaturePackage.eNS_URI);
            if (theFeatureFactory != null) {
                return theFeatureFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new FeatureFactoryImpl();
    }

	/**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeatureFactoryImpl() {
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
            case FeaturePackage.UNSET_EATTRIBUTE: return createUnsetEAttribute();
            case FeaturePackage.UNSET_NON_CONTAINMENT_EREFERENCE: return createUnsetNonContainmentEReference();
            case FeaturePackage.UNSET_CONTAINMENT_EREFERENCE: return createUnsetContainmentEReference();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends Object> UnsetEAttribute<T> createUnsetEAttribute() {
        UnsetEAttributeImpl<T> unsetEAttribute = new UnsetEAttributeImpl<T>();
        return unsetEAttribute;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> UnsetNonContainmentEReference<T> createUnsetNonContainmentEReference() {
        UnsetNonContainmentEReferenceImpl<T> unsetNonContainmentEReference = new UnsetNonContainmentEReferenceImpl<T>();
        return unsetNonContainmentEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> UnsetContainmentEReference<T> createUnsetContainmentEReference() {
        UnsetContainmentEReferenceImpl<T> unsetContainmentEReference = new UnsetContainmentEReferenceImpl<T>();
        return unsetContainmentEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeaturePackage getFeaturePackage() {
        return (FeaturePackage)getEPackage();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
	@Deprecated
	public static FeaturePackage getPackage() {
        return FeaturePackage.eINSTANCE;
    }

} //FeatureFactoryImpl
