/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.change.echange.feature.attribute.*;

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
			case AttributePackage.INSERT_EATTRIBUTE_VALUE: return createInsertEAttributeValue();
			case AttributePackage.REMOVE_EATTRIBUTE_VALUE: return createRemoveEAttributeValue();
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE: return createReplaceSingleValuedEAttribute();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> InsertEAttributeValue<A, T> createInsertEAttributeValue() {
		InsertEAttributeValueImpl<A, T> insertEAttributeValue = new InsertEAttributeValueImpl<A, T>();
		return insertEAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> RemoveEAttributeValue<A, T> createRemoveEAttributeValue() {
		RemoveEAttributeValueImpl<A, T> removeEAttributeValue = new RemoveEAttributeValueImpl<A, T>();
		return removeEAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> ReplaceSingleValuedEAttribute<A, T> createReplaceSingleValuedEAttribute() {
		ReplaceSingleValuedEAttributeImpl<A, T> replaceSingleValuedEAttribute = new ReplaceSingleValuedEAttributeImpl<A, T>();
		return replaceSingleValuedEAttribute;
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
