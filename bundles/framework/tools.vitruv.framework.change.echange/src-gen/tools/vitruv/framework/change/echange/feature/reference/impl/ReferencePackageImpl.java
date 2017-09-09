/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;

import tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;

import tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl;

import tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

import tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl;

import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

import tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl;

import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;

import tools.vitruv.framework.change.echange.root.RootPackage;

import tools.vitruv.framework.change.echange.root.impl.RootPackageImpl;

import tools.vitruv.framework.change.uuid.UuidPackage;

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
	private EClass updateReferenceEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass additiveReferenceEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subtractiveReferenceEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass insertEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replaceSingleValuedEReferenceEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#eNS_URI
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

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		UuidPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
		SinglePackageImpl theSinglePackage = (SinglePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) instanceof SinglePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) : SinglePackage.eINSTANCE);
		CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);
		EobjectPackageImpl theEobjectPackage = (EobjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI) instanceof EobjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI) : EobjectPackage.eINSTANCE);
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);

		// Create package meta-data objects
		theReferencePackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theEChangePackage.createPackageContents();
		theListPackage.createPackageContents();
		theSinglePackage.createPackageContents();
		theCompoundPackage.createPackageContents();
		theEobjectPackage.createPackageContents();
		theRootPackage.createPackageContents();

		// Initialize created meta-data
		theReferencePackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theSinglePackage.initializePackageContents();
		theCompoundPackage.initializePackageContents();
		theEobjectPackage.initializePackageContents();
		theRootPackage.initializePackageContents();

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
	public EClass getUpdateReferenceEChange() {
		return updateReferenceEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getUpdateReferenceEChange__IsContainment() {
		return updateReferenceEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdditiveReferenceEChange() {
		return additiveReferenceEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubtractiveReferenceEChange() {
		return subtractiveReferenceEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInsertEReference() {
		return insertEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveEReference() {
		return removeEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplaceSingleValuedEReference() {
		return replaceSingleValuedEReferenceEClass;
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
		updateReferenceEChangeEClass = createEClass(UPDATE_REFERENCE_ECHANGE);
		createEOperation(updateReferenceEChangeEClass, UPDATE_REFERENCE_ECHANGE___IS_CONTAINMENT);

		additiveReferenceEChangeEClass = createEClass(ADDITIVE_REFERENCE_ECHANGE);

		subtractiveReferenceEChangeEClass = createEClass(SUBTRACTIVE_REFERENCE_ECHANGE);

		insertEReferenceEClass = createEClass(INSERT_EREFERENCE);

		removeEReferenceEClass = createEClass(REMOVE_EREFERENCE);

		replaceSingleValuedEReferenceEClass = createEClass(REPLACE_SINGLE_VALUED_EREFERENCE);
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
		EobjectPackage theEobjectPackage = (EobjectPackage)EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
		SinglePackage theSinglePackage = (SinglePackage)EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI);

		// Create type parameters
		ETypeParameter updateReferenceEChangeEClass_A = addETypeParameter(updateReferenceEChangeEClass, "A");
		ETypeParameter additiveReferenceEChangeEClass_A = addETypeParameter(additiveReferenceEChangeEClass, "A");
		ETypeParameter additiveReferenceEChangeEClass_T = addETypeParameter(additiveReferenceEChangeEClass, "T");
		ETypeParameter subtractiveReferenceEChangeEClass_A = addETypeParameter(subtractiveReferenceEChangeEClass, "A");
		ETypeParameter subtractiveReferenceEChangeEClass_T = addETypeParameter(subtractiveReferenceEChangeEClass, "T");
		ETypeParameter insertEReferenceEClass_A = addETypeParameter(insertEReferenceEClass, "A");
		ETypeParameter insertEReferenceEClass_T = addETypeParameter(insertEReferenceEClass, "T");
		ETypeParameter removeEReferenceEClass_A = addETypeParameter(removeEReferenceEClass, "A");
		ETypeParameter removeEReferenceEClass_T = addETypeParameter(removeEReferenceEClass, "T");
		ETypeParameter replaceSingleValuedEReferenceEClass_A = addETypeParameter(replaceSingleValuedEReferenceEClass, "A");
		ETypeParameter replaceSingleValuedEReferenceEClass_T = addETypeParameter(replaceSingleValuedEReferenceEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theEcorePackage.getEObject());
		updateReferenceEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		additiveReferenceEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		additiveReferenceEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		subtractiveReferenceEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		subtractiveReferenceEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		insertEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		insertEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		removeEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceSingleValuedEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		replaceSingleValuedEReferenceEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
		EGenericType g2 = createEGenericType(updateReferenceEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		updateReferenceEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateReferenceEChange());
		g2 = createEGenericType(additiveReferenceEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		additiveReferenceEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEobjectPackage.getEObjectAddedEChange());
		g2 = createEGenericType(additiveReferenceEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		additiveReferenceEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getUpdateReferenceEChange());
		g2 = createEGenericType(subtractiveReferenceEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		subtractiveReferenceEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theEobjectPackage.getEObjectSubtractedEChange());
		g2 = createEGenericType(subtractiveReferenceEChangeEClass_T);
		g1.getETypeArguments().add(g2);
		subtractiveReferenceEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getInsertInListEChange());
		g2 = createEGenericType(insertEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(insertEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		insertEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAdditiveReferenceEChange());
		g2 = createEGenericType(insertEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(insertEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		insertEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theListPackage.getRemoveFromListEChange());
		g2 = createEGenericType(removeEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(removeEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		removeEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSubtractiveReferenceEChange());
		g2 = createEGenericType(removeEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(removeEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		removeEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theSinglePackage.getReplaceSingleValuedFeatureEChange());
		g2 = createEGenericType(replaceSingleValuedEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getAdditiveReferenceEChange());
		g2 = createEGenericType(replaceSingleValuedEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedEReferenceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSubtractiveReferenceEChange());
		g2 = createEGenericType(replaceSingleValuedEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(replaceSingleValuedEReferenceEClass_T);
		g1.getETypeArguments().add(g2);
		replaceSingleValuedEReferenceEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(updateReferenceEChangeEClass, UpdateReferenceEChange.class, "UpdateReferenceEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getUpdateReferenceEChange__IsContainment(), theEcorePackage.getEBoolean(), "isContainment", 1, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(additiveReferenceEChangeEClass, AdditiveReferenceEChange.class, "AdditiveReferenceEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(subtractiveReferenceEChangeEClass, SubtractiveReferenceEChange.class, "SubtractiveReferenceEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(insertEReferenceEClass, InsertEReference.class, "InsertEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removeEReferenceEClass, RemoveEReference.class, "RemoveEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(replaceSingleValuedEReferenceEClass, ReplaceSingleValuedEReference.class, "ReplaceSingleValuedEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ReferencePackageImpl
