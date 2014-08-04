/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.PermuteContainmentEReferenceValues;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.UpdateSingleValuedContainmentEReference;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl;

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
public class ContainmentPackageImpl extends EPackageImpl implements ContainmentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateSingleValuedContainmentEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createNonRootEObjectSingleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceNonRootEObjectSingleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deleteNonRootEObjectSingleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createNonRootEObjectInListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceNonRootEObjectInListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deleteNonRootEObjectInListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass permuteContainmentEReferenceValuesEClass = null;

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
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ContainmentPackageImpl() {
		super(eNS_URI, ContainmentFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ContainmentPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ContainmentPackage init() {
		if (isInited) return (ContainmentPackage)EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI);

		// Obtain or create and register package
		ContainmentPackageImpl theContainmentPackage = (ContainmentPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ContainmentPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ContainmentPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
		ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) : ObjectPackage.eINSTANCE);

		// Create package meta-data objects
		theContainmentPackage.createPackageContents();
		theChangePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theListPackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theReferencePackage.createPackageContents();
		theObjectPackage.createPackageContents();

		// Initialize created meta-data
		theContainmentPackage.initializePackageContents();
		theChangePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theReferencePackage.initializePackageContents();
		theObjectPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theContainmentPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ContainmentPackage.eNS_URI, theContainmentPackage);
		return theContainmentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpdateContainmentEReference() {
		return updateContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUpdateSingleValuedContainmentEReference() {
		return updateSingleValuedContainmentEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateNonRootEObjectSingle() {
		return createNonRootEObjectSingleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceNonRootEObjectSingle() {
		return replaceNonRootEObjectSingleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeleteNonRootEObjectSingle() {
		return deleteNonRootEObjectSingleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateNonRootEObjectInList() {
		return createNonRootEObjectInListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceNonRootEObjectInList() {
		return replaceNonRootEObjectInListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeleteNonRootEObjectInList() {
		return deleteNonRootEObjectInListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPermuteContainmentEReferenceValues() {
		return permuteContainmentEReferenceValuesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainmentFactory getContainmentFactory() {
		return (ContainmentFactory)getEFactoryInstance();
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
		updateContainmentEReferenceEClass = createEClass(UPDATE_CONTAINMENT_EREFERENCE);

		updateSingleValuedContainmentEReferenceEClass = createEClass(UPDATE_SINGLE_VALUED_CONTAINMENT_EREFERENCE);

		createNonRootEObjectSingleEClass = createEClass(CREATE_NON_ROOT_EOBJECT_SINGLE);

		replaceNonRootEObjectSingleEClass = createEClass(REPLACE_NON_ROOT_EOBJECT_SINGLE);

		deleteNonRootEObjectSingleEClass = createEClass(DELETE_NON_ROOT_EOBJECT_SINGLE);

		createNonRootEObjectInListEClass = createEClass(CREATE_NON_ROOT_EOBJECT_IN_LIST);

		replaceNonRootEObjectInListEClass = createEClass(REPLACE_NON_ROOT_EOBJECT_IN_LIST);

		deleteNonRootEObjectInListEClass = createEClass(DELETE_NON_ROOT_EOBJECT_IN_LIST);

		permuteContainmentEReferenceValuesEClass = createEClass(PERMUTE_CONTAINMENT_EREFERENCE_VALUES);
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
		ReferencePackage theReferencePackage = (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);
		FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);
		ObjectPackage theObjectPackage = (ObjectPackage)EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);

		// Create type parameters
		ETypeParameter updateContainmentEReferenceEClass_T = addETypeParameter(updateContainmentEReferenceEClass, "T");
		ETypeParameter updateSingleValuedContainmentEReferenceEClass_T = addETypeParameter(updateSingleValuedContainmentEReferenceEClass, "T");
		ETypeParameter createNonRootEObjectSingleEClass_T = addETypeParameter(createNonRootEObjectSingleEClass, "T");
		ETypeParameter replaceNonRootEObjectSingleEClass_T = addETypeParameter(replaceNonRootEObjectSingleEClass, "T");
		ETypeParameter deleteNonRootEObjectSingleEClass_T = addETypeParameter(deleteNonRootEObjectSingleEClass, "T");
		ETypeParameter createNonRootEObjectInListEClass_T = addETypeParameter(createNonRootEObjectInListEClass, "T");
		ETypeParameter replaceNonRootEObjectInListEClass_T = addETypeParameter(replaceNonRootEObjectInListEClass, "T");
		ETypeParameter deleteNonRootEObjectInListEClass_T = addETypeParameter(deleteNonRootEObjectInListEClass, "T");
		ETypeParameter permuteContainmentEReferenceValuesEClass_T = addETypeParameter(permuteContainmentEReferenceValuesEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEObject());
		updateContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		updateSingleValuedContainmentEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		createNonRootEObjectSingleEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		replaceNonRootEObjectSingleEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		deleteNonRootEObjectSingleEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		createNonRootEObjectInListEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		replaceNonRootEObjectInListEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		deleteNonRootEObjectInListEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		permuteContainmentEReferenceValuesEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theReferencePackage.getUpdateEReference());
		EGenericType g2 = createEGenericType(updateContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		updateContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theFeaturePackage.getUpdateSingleValuedEFeature());
		g2 = createEGenericType(updateSingleValuedContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		updateSingleValuedContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateContainmentEReference());
		g2 = createEGenericType(updateSingleValuedContainmentEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		updateSingleValuedContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theObjectPackage.getCreateEObject());
		g2 = createEGenericType(createNonRootEObjectSingleEClass_T);
		g1.getETypeArguments().add(g2);
		createNonRootEObjectSingleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateSingleValuedContainmentEReference());
		g2 = createEGenericType(createNonRootEObjectSingleEClass_T);
		g1.getETypeArguments().add(g2);
		createNonRootEObjectSingleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theObjectPackage.getReplaceEObject());
		g2 = createEGenericType(replaceNonRootEObjectSingleEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonRootEObjectSingleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateSingleValuedContainmentEReference());
		g2 = createEGenericType(replaceNonRootEObjectSingleEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonRootEObjectSingleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theObjectPackage.getDeleteEObject());
		g2 = createEGenericType(deleteNonRootEObjectSingleEClass_T);
		g1.getETypeArguments().add(g2);
		deleteNonRootEObjectSingleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateSingleValuedContainmentEReference());
		g2 = createEGenericType(deleteNonRootEObjectSingleEClass_T);
		g1.getETypeArguments().add(g2);
		deleteNonRootEObjectSingleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theObjectPackage.getCreateEObject());
		g2 = createEGenericType(createNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		createNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getInsertInEList());
		g2 = createEGenericType(createNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		createNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateContainmentEReference());
		g2 = createEGenericType(createNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		createNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theObjectPackage.getReplaceEObject());
		g2 = createEGenericType(replaceNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getReplaceInEList());
		g2 = createEGenericType(replaceNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateContainmentEReference());
		g2 = createEGenericType(replaceNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		replaceNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theObjectPackage.getDeleteEObject());
		g2 = createEGenericType(deleteNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		deleteNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getRemoveFromEList());
		g2 = createEGenericType(deleteNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		deleteNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateContainmentEReference());
		g2 = createEGenericType(deleteNonRootEObjectInListEClass_T);
		g1.getETypeArguments().add(g2);
		deleteNonRootEObjectInListEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getPermuteEList());
		g2 = createEGenericType(permuteContainmentEReferenceValuesEClass_T);
		g1.getETypeArguments().add(g2);
		permuteContainmentEReferenceValuesEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateContainmentEReference());
		g2 = createEGenericType(permuteContainmentEReferenceValuesEClass_T);
		g1.getETypeArguments().add(g2);
		permuteContainmentEReferenceValuesEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes and features; add operations and parameters
		initEClass(updateContainmentEReferenceEClass, UpdateContainmentEReference.class, "UpdateContainmentEReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(updateSingleValuedContainmentEReferenceEClass, UpdateSingleValuedContainmentEReference.class, "UpdateSingleValuedContainmentEReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createNonRootEObjectSingleEClass, CreateNonRootEObjectSingle.class, "CreateNonRootEObjectSingle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(replaceNonRootEObjectSingleEClass, ReplaceNonRootEObjectSingle.class, "ReplaceNonRootEObjectSingle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deleteNonRootEObjectSingleEClass, DeleteNonRootEObjectSingle.class, "DeleteNonRootEObjectSingle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createNonRootEObjectInListEClass, CreateNonRootEObjectInList.class, "CreateNonRootEObjectInList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(replaceNonRootEObjectInListEClass, ReplaceNonRootEObjectInList.class, "ReplaceNonRootEObjectInList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deleteNonRootEObjectInListEClass, DeleteNonRootEObjectInList.class, "DeleteNonRootEObjectInList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(permuteContainmentEReferenceValuesEClass, PermuteContainmentEReferenceValues.class, "PermuteContainmentEReferenceValues", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //ContainmentPackageImpl
