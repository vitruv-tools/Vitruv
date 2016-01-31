/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.*;

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
public class AttributeFactoryImpl extends EFactoryImpl implements AttributeFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeFactory init() {
        try {
            AttributeFactory theAttributeFactory = (AttributeFactory)EPackage.Registry.INSTANCE.getEFactory(AttributePackage.eNS_URI);
            if (theAttributeFactory != null) {
                return theAttributeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new AttributeFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeFactoryImpl() {
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
            case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE: return createReplaceSingleValuedEAttribute();
            case AttributePackage.INSERT_EATTRIBUTE_VALUE: return createInsertEAttributeValue();
            case AttributePackage.REMOVE_EATTRIBUTE_VALUE: return createRemoveEAttributeValue();
            case AttributePackage.PERMUTE_EATTRIBUTE_VALUES: return createPermuteEAttributeValues();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends Object> ReplaceSingleValuedEAttribute<T> createReplaceSingleValuedEAttribute() {
        ReplaceSingleValuedEAttributeImpl<T> replaceSingleValuedEAttribute = new ReplaceSingleValuedEAttributeImpl<T>();
        return replaceSingleValuedEAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends Object> InsertEAttributeValue<T> createInsertEAttributeValue() {
        InsertEAttributeValueImpl<T> insertEAttributeValue = new InsertEAttributeValueImpl<T>();
        return insertEAttributeValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends Object> RemoveEAttributeValue<T> createRemoveEAttributeValue() {
        RemoveEAttributeValueImpl<T> removeEAttributeValue = new RemoveEAttributeValueImpl<T>();
        return removeEAttributeValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends Object> PermuteEAttributeValues<T> createPermuteEAttributeValues() {
        PermuteEAttributeValuesImpl<T> permuteEAttributeValues = new PermuteEAttributeValuesImpl<T>();
        return permuteEAttributeValues;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributePackage getAttributePackage() {
        return (AttributePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static AttributePackage getPackage() {
        return AttributePackage.eINSTANCE;
    }

} //AttributeFactoryImpl
