/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.PermuteNonContainmentEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReplaceNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReferencePackageImpl extends EPackageImpl implements ReferencePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateNonContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateSingleValuedNonContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass insertNonContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceNonContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeNonContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass permuteNonContainmentEReferenceValuesEClass = null;

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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ReferencePackageImpl() {
		super(eNS_URI, ReferenceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ReferencePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ReferencePackage init() {
		if (isInited) return (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);

		// Obtain or create and register package
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ReferencePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		ContainmentPackageImpl theContainmentPackage = (ContainmentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI) instanceof ContainmentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI) : ContainmentPackage.eINSTANCE);
		ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) : ObjectPackage.eINSTANCE);

		// Create package meta-data objects
		theReferencePackage.createPackageContents();
		theChangePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theListPackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theContainmentPackage.createPackageContents();
		theObjectPackage.createPackageContents();

		// Initialize created meta-data
		theReferencePackage.initializePackageContents();
		theChangePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theContainmentPackage.initializePackageContents();
		theObjectPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theReferencePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ReferencePackage.eNS_URI, theReferencePackage);
		return theReferencePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpdateEReference() {
		return updateEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpdateNonContainmentEReference() {
		return updateNonContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpdateSingleValuedNonContainmentEReference() {
		return updateSingleValuedNonContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUpdateSingleValuedNonContainmentEReference_OldValue() {
		return (EAttribute)updateSingleValuedNonContainmentEReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUpdateSingleValuedNonContainmentEReference_NewValue() {
		return (EAttribute)updateSingleValuedNonContainmentEReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInsertNonContainmentEReference() {
		return insertNonContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceNonContainmentEReference() {
		return replaceNonContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveNonContainmentEReference() {
		return removeNonContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPermuteNonContainmentEReferenceValues() {
		return permuteNonContainmentEReferenceValuesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceFactory getReferenceFactory() {
		return (ReferenceFactory)getEFactoryInstance();
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
		updateEReferenceEClass = createEClass(UPDATE_EREFERENCE);

		updateNonContainmentEReferenceEClass = createEClass(UPDATE_NON_CONTAINMENT_EREFERENCE);

		updateSingleValuedNonContainmentEReferenceEClass = createEClass(UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE);
		createEAttribute(updateSingleValuedNonContainmentEReferenceEClass, UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__OLD_VALUE);
		createEAttribute(updateSingleValuedNonContainmentEReferenceEClass, UPDATE_SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE__NEW_VALUE);

		insertNonContainmentEReferenceEClass = createEClass(INSERT_NON_CONTAINMENT_EREFERENCE);

		replaceNonContainmentEReferenceEClass = createEClass(REPLACE_NON_CONTAINMENT_EREFERENCE);

		removeNonContainmentEReferenceEClass = createEClass(REMOVE_NON_CONTAINMENT_EREFERENCE);

		permuteNonContainmentEReferenceValuesEClass = createEClass(PERMUTE_NON_CONTAINMENT_EREFERENCE_VALUES);
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
		ContainmentPackage theContainmentPackage = (ContainmentPackage)EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI);
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theContainmentPackage);

		// Create type parameters
		ETypeParameter updateEReferenceEClass_T = addETypeParameter(updateEReferenceEClass, "T");
		ETypeParameter updateNonContainmentEReferenceEClass_T = addETypeParameter(updateNonContainmentEReferenceEClass, "T");
		ETypeParameter updateSingleValuedNonContainmentEReferenceEClass_T = addETypeParameter(updateSingleValuedNonContainmentEReferenceEClass, "T");
		ETypeParameter insertNonContainmentEReferenceEClass_T = addETypeParameter(insertNonContainmentEReferenceEClass, "T");
		ETypeParameter replaceNonContainmentEReferenceEClass_T = addETypeParameter(replaceNonContainmentEReferenceEClass, "T");
		ETypeParameter removeNonContainmentEReferenceEClass_T = addETypeParameter(removeNonContainmentEReferenceEClass, "T");
		ETypeParameter permuteNonContainmentEReferenceValuesEClass_T = addETypeParameter(permuteNonContainmentEReferenceValuesEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEObject());
		updateEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		updateNonContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		updateSingleValuedNonContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		insertNonContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		replaceNonContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		removeNonContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		permuteNonContainmentEReferenceValuesEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theFeaturePackage.getEFeatureChange());
		EGenericType g2 = createEGenericType(ecorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		updateEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateEReference());
		g2 = createEGenericType(updateNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		updateNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theFeaturePackage.getUpdateSingleValuedEFeature());
		g2 = createEGenericType(updateSingleValuedNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		updateSingleValuedNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateNonContainmentEReference());
		g2 = createEGenericType(updateSingleValuedNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		updateSingleValuedNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getInsertInEList());
		g2 = createEGenericType(insertNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		insertNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateNonContainmentEReference());
		g2 = createEGenericType(insertNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		insertNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getReplaceInEList());
		g2 = createEGenericType(replaceNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateNonContainmentEReference());
		g2 = createEGenericType(replaceNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getRemoveFromEList());
		g2 = createEGenericType(removeNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		removeNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateNonContainmentEReference());
		g2 = createEGenericType(removeNonContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		removeNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getPermuteEList());
		g2 = createEGenericType(permuteNonContainmentEReferenceValuesEClass_T);
		g1.getETypeArguments().add(g2);
		permuteNonContainmentEReferenceValuesEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateNonContainmentEReference());
		g2 = createEGenericType(permuteNonContainmentEReferenceValuesEClass_T);
		g1.getETypeArguments().add(g2);
		permuteNonContainmentEReferenceValuesEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes and features; add operations and parameters
		initEClass(updateEReferenceEClass, UpdateEReference.class, "UpdateEReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(updateNonContainmentEReferenceEClass, UpdateNonContainmentEReference.class, "UpdateNonContainmentEReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(updateSingleValuedNonContainmentEReferenceEClass, UpdateSingleValuedNonContainmentEReference.class, "UpdateSingleValuedNonContainmentEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(updateSingleValuedNonContainmentEReferenceEClass_T);
		initEAttribute(getUpdateSingleValuedNonContainmentEReference_OldValue(), g1, "oldValue", null, 1, 1, UpdateSingleValuedNonContainmentEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(updateSingleValuedNonContainmentEReferenceEClass_T);
		initEAttribute(getUpdateSingleValuedNonContainmentEReference_NewValue(), g1, "newValue", null, 1, 1, UpdateSingleValuedNonContainmentEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(insertNonContainmentEReferenceEClass, InsertNonContainmentEReference.class, "InsertNonContainmentEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(replaceNonContainmentEReferenceEClass, ReplaceNonContainmentEReference.class, "ReplaceNonContainmentEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removeNonContainmentEReferenceEClass, RemoveNonContainmentEReference.class, "RemoveNonContainmentEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(permuteNonContainmentEReferenceValuesEClass, PermuteNonContainmentEReferenceValues.class, "PermuteNonContainmentEReferenceValues", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //ReferencePackageImpl
