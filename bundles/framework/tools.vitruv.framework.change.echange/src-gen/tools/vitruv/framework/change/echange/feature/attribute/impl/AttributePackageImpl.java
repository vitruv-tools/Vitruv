/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange;
import tools.vitruv.framework.change.echange.feature.attribute.AttributeFactory;
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue;
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;
import tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AttributePackageImpl extends EPackageImpl implements AttributePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateAttributeEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass additiveAttributeEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subtractiveAttributeEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass insertEAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeEAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceSingleValuedEAttributeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AttributePackageImpl() {
		super(eNS_URI, AttributeFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link AttributePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AttributePackage init() {
		if (isInited) return (AttributePackage)EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI);

		// Obtain or create and register package
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AttributePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ListPackage.eINSTANCE.eClass();
		SinglePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAttributePackage.createPackageContents();

		// Initialize created meta-data
		theAttributePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAttributePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AttributePackage.eNS_URI, theAttributePackage);
		return theAttributePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpdateAttributeEChange() {
		return updateAttributeEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdditiveAttributeEChange() {
		return additiveAttributeEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdditiveAttributeEChange_NewValue() {
		return (EAttribute)additiveAttributeEChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubtractiveAttributeEChange() {
		return subtractiveAttributeEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubtractiveAttributeEChange_OldValue() {
		return (EAttribute)subtractiveAttributeEChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInsertEAttributeValue() {
		return insertEAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveEAttributeValue() {
		return removeEAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceSingleValuedEAttribute() {
		return replaceSingleValuedEAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeFactory getAttributeFactory() {
		return (AttributeFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		updateAttributeEChangeEClass = createEClass(UPDATE_ATTRIBUTE_ECHANGE);

		additiveAttributeEChangeEClass = createEClass(ADDITIVE_ATTRIBUTE_ECHANGE);
		createEAttribute(additiveAttributeEChangeEClass, ADDITIVE_ATTRIBUTE_ECHANGE__NEW_VALUE);

		subtractiveAttributeEChangeEClass = createEClass(SUBTRACTIVE_ATTRIBUTE_ECHANGE);
		createEAttribute(subtractiveAttributeEChangeEClass, SUBTRACTIVE_ATTRIBUTE_ECHANGE__OLD_VALUE);

		insertEAttributeValueEClass = createEClass(INSERT_EATTRIBUTE_VALUE);

		removeEAttributeValueEClass = createEClass(REMOVE_EATTRIBUTE_VALUE);

		replaceSingleValuedEAttributeEClass = createEClass(REPLACE_SINGLE_VALUED_EATTRIBUTE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
		SinglePackage theSinglePackage = (SinglePackage)EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI);

		// Create type parameters
		ETypeParameter updateAttributeEChangeEClass_A = addETypeParameter(updateAttributeEChangeEClass, "A");
		ETypeParameter additiveAttributeEChangeEClass_A = addETypeParameter(additiveAttributeEChangeEClass, "A");
		ETypeParameter additiveAttributeEChangeEClass_T = addETypeParameter(additiveAttributeEChangeEClass, "T");
		ETypeParameter subtractiveAttributeEChangeEClass_A = addETypeParameter(subtractiveAttributeEChangeEClass, "A");
		ETypeParameter subtractiveAttributeEChangeEClass_T = addETypeParameter(subtractiveAttributeEChangeEClass, "T");
		ETypeParameter insertEAttributeValueEClass_A = addETypeParameter(insertEAttributeValueEClass, "A");
		ETypeParameter insertEAttributeValueEClass_T = addETypeParameter(insertEAttributeValueEClass, "T");
		ETypeParameter removeEAttributeValueEClass_A = addETypeParameter(removeEAttributeValueEClass, "A");
		ETypeParameter removeEAttributeValueEClass_T = addETypeParameter(removeEAttributeValueEClass, "T");
		ETypeParameter replaceSingleValuedEAttributeEClass_A = addETypeParameter(replaceSingleValuedEAttributeEClass, "A");
		ETypeParameter replaceSingleValuedEAttributeEClass_T = addETypeParameter(replaceSingleValuedEAttributeEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theEcorePackage.getEObject());
		updateAttributeEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		additiveAttributeEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		additiveAttributeEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		subtractiveAttributeEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		subtractiveAttributeEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		insertEAttributeValueEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		insertEAttributeValueEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeEAttributeValueEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		removeEAttributeValueEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceSingleValuedEAttributeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		replaceSingleValuedEAttributeEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
		EGenericType g2 = createEGenericType(updateAttributeEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEAttribute());
		g1.getETypeArguments().add(g2);
		updateAttributeEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType(additiveAttributeEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		additiveAttributeEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateAttributeEChange());
		g2 = createEGenericType(additiveAttributeEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		additiveAttributeEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		g2 = createEGenericType(subtractiveAttributeEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		subtractiveAttributeEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateAttributeEChange());
		g2 = createEGenericType(subtractiveAttributeEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		subtractiveAttributeEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getInsertInListEChange());
		g2 = createEGenericType(insertEAttributeValueEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEAttribute());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(insertEAttributeValueEClass_T);
		g1.getETypeArguments().add(g2);
		insertEAttributeValueEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAdditiveAttributeEChange());
		g2 = createEGenericType(insertEAttributeValueEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(insertEAttributeValueEClass_T);
		g1.getETypeArguments().add(g2);
		insertEAttributeValueEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getRemoveFromListEChange());
		g2 = createEGenericType(removeEAttributeValueEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEAttribute());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(removeEAttributeValueEClass_T);
		g1.getETypeArguments().add(g2);
		removeEAttributeValueEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSubtractiveAttributeEChange());
		g2 = createEGenericType(removeEAttributeValueEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(removeEAttributeValueEClass_T);
		g1.getETypeArguments().add(g2);
		removeEAttributeValueEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAdditiveAttributeEChange());
		g2 = createEGenericType(replaceSingleValuedEAttributeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedEAttributeEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedEAttributeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSubtractiveAttributeEChange());
		g2 = createEGenericType(replaceSingleValuedEAttributeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedEAttributeEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedEAttributeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theSinglePackage.getReplaceSingleValuedFeatureEChange());
		g2 = createEGenericType(replaceSingleValuedEAttributeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEAttribute());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedEAttributeEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedEAttributeEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(updateAttributeEChangeEClass, UpdateAttributeEChange.class, "UpdateAttributeEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(additiveAttributeEChangeEClass, AdditiveAttributeEChange.class, "AdditiveAttributeEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(additiveAttributeEChangeEClass_T);
		initEAttribute(getAdditiveAttributeEChange_NewValue(), g1, "newValue", null, 1, 1, AdditiveAttributeEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subtractiveAttributeEChangeEClass, SubtractiveAttributeEChange.class, "SubtractiveAttributeEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(subtractiveAttributeEChangeEClass_T);
		initEAttribute(getSubtractiveAttributeEChange_OldValue(), g1, "oldValue", null, 1, 1, SubtractiveAttributeEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(insertEAttributeValueEClass, InsertEAttributeValue.class, "InsertEAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removeEAttributeValueEClass, RemoveEAttributeValue.class, "RemoveEAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(replaceSingleValuedEAttributeEClass, ReplaceSingleValuedEAttribute.class, "ReplaceSingleValuedEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //AttributePackageImpl
