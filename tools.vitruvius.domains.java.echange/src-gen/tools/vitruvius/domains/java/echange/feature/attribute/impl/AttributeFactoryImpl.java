/**
 */
package tools.vitruvius.domains.java.echange.feature.attribute.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruvius.domains.java.echange.feature.attribute.*;

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
			case AttributePackage.JAVA_INSERT_EATTRIBUTE_VALUE: return createJavaInsertEAttributeValue();
			case AttributePackage.JAVA_REMOVE_EATTRIBUTE_VALUE: return createJavaRemoveEAttributeValue();
			case AttributePackage.JAVA_REPLACE_SINGLE_VALUED_EATTRIBUTE: return createJavaReplaceSingleValuedEAttribute();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> JavaInsertEAttributeValue<A, T> createJavaInsertEAttributeValue() {
		JavaInsertEAttributeValueImpl<A, T> javaInsertEAttributeValue = new JavaInsertEAttributeValueImpl<A, T>();
		return javaInsertEAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> JavaRemoveEAttributeValue<A, T> createJavaRemoveEAttributeValue() {
		JavaRemoveEAttributeValueImpl<A, T> javaRemoveEAttributeValue = new JavaRemoveEAttributeValueImpl<A, T>();
		return javaRemoveEAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> JavaReplaceSingleValuedEAttribute<A, T> createJavaReplaceSingleValuedEAttribute() {
		JavaReplaceSingleValuedEAttributeImpl<A, T> javaReplaceSingleValuedEAttribute = new JavaReplaceSingleValuedEAttributeImpl<A, T>();
		return javaReplaceSingleValuedEAttribute;
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
