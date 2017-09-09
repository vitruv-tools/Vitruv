/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.compound.CompoundAddition;
import tools.vitruv.framework.change.echange.compound.CompoundEChange;
import tools.vitruv.framework.change.echange.compound.CompoundFactory;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.eobject.impl.EobjectPackageImpl;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;

import tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl;

import tools.vitruv.framework.change.echange.feature.impl.FeaturePackageImpl;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

import tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl;

import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;

import tools.vitruv.framework.change.echange.feature.reference.impl.ReferencePackageImpl;

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
public class CompoundPackageImpl extends EPackageImpl implements CompoundPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundEChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass explicitUnsetEFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass explicitUnsetEAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass explicitUnsetEReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundSubtractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compoundAdditionEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CompoundPackageImpl() {
		super(eNS_URI, CompoundFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CompoundPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CompoundPackage init() {
		if (isInited) return (CompoundPackage)EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI);

		// Obtain or create and register package
		CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CompoundPackageImpl());

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
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
		EobjectPackageImpl theEobjectPackage = (EobjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI) instanceof EobjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EobjectPackage.eNS_URI) : EobjectPackage.eINSTANCE);
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);

		// Create package meta-data objects
		theCompoundPackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theFeaturePackage.createPackageContents();
		theEChangePackage.createPackageContents();
		theListPackage.createPackageContents();
		theSinglePackage.createPackageContents();
		theReferencePackage.createPackageContents();
		theEobjectPackage.createPackageContents();
		theRootPackage.createPackageContents();

		// Initialize created meta-data
		theCompoundPackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theFeaturePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theSinglePackage.initializePackageContents();
		theReferencePackage.initializePackageContents();
		theEobjectPackage.initializePackageContents();
		theRootPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCompoundPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CompoundPackage.eNS_URI, theCompoundPackage);
		return theCompoundPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundEChange() {
		return compoundEChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCompoundEChange__GetAtomicChanges() {
		return compoundEChangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExplicitUnsetEFeature() {
		return explicitUnsetEFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExplicitUnsetEFeature_AffectedEObject() {
		return (EReference)explicitUnsetEFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExplicitUnsetEFeature_AffectedFeature() {
		return (EReference)explicitUnsetEFeatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExplicitUnsetEFeature_AffectedEObjectID() {
		return (EAttribute)explicitUnsetEFeatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExplicitUnsetEAttribute() {
		return explicitUnsetEAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExplicitUnsetEAttribute__GetAtomicChanges() {
		return explicitUnsetEAttributeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExplicitUnsetEReference() {
		return explicitUnsetEReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExplicitUnsetEReference_Changes() {
		return (EReference)explicitUnsetEReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExplicitUnsetEReference__GetContainedChanges() {
		return explicitUnsetEReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExplicitUnsetEReference__GetAtomicChanges() {
		return explicitUnsetEReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundSubtraction() {
		return compoundSubtractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompoundSubtraction_SubtractiveChanges() {
		return (EReference)compoundSubtractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCompoundSubtraction__GetAtomicChanges() {
		return compoundSubtractionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompoundAddition() {
		return compoundAdditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompoundAddition_AdditiveChanges() {
		return (EReference)compoundAdditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getCompoundAddition__GetAtomicChanges() {
		return compoundAdditionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundFactory getCompoundFactory() {
		return (CompoundFactory)getEFactoryInstance();
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
		compoundEChangeEClass = createEClass(COMPOUND_ECHANGE);
		createEOperation(compoundEChangeEClass, COMPOUND_ECHANGE___GET_ATOMIC_CHANGES);

		explicitUnsetEFeatureEClass = createEClass(EXPLICIT_UNSET_EFEATURE);
		createEReference(explicitUnsetEFeatureEClass, EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT);
		createEReference(explicitUnsetEFeatureEClass, EXPLICIT_UNSET_EFEATURE__AFFECTED_FEATURE);
		createEAttribute(explicitUnsetEFeatureEClass, EXPLICIT_UNSET_EFEATURE__AFFECTED_EOBJECT_ID);

		explicitUnsetEAttributeEClass = createEClass(EXPLICIT_UNSET_EATTRIBUTE);
		createEOperation(explicitUnsetEAttributeEClass, EXPLICIT_UNSET_EATTRIBUTE___GET_ATOMIC_CHANGES);

		explicitUnsetEReferenceEClass = createEClass(EXPLICIT_UNSET_EREFERENCE);
		createEReference(explicitUnsetEReferenceEClass, EXPLICIT_UNSET_EREFERENCE__CHANGES);
		createEOperation(explicitUnsetEReferenceEClass, EXPLICIT_UNSET_EREFERENCE___GET_CONTAINED_CHANGES);
		createEOperation(explicitUnsetEReferenceEClass, EXPLICIT_UNSET_EREFERENCE___GET_ATOMIC_CHANGES);

		compoundSubtractionEClass = createEClass(COMPOUND_SUBTRACTION);
		createEReference(compoundSubtractionEClass, COMPOUND_SUBTRACTION__SUBTRACTIVE_CHANGES);
		createEOperation(compoundSubtractionEClass, COMPOUND_SUBTRACTION___GET_ATOMIC_CHANGES);

		compoundAdditionEClass = createEClass(COMPOUND_ADDITION);
		createEReference(compoundAdditionEClass, COMPOUND_ADDITION__ADDITIVE_CHANGES);
		createEOperation(compoundAdditionEClass, COMPOUND_ADDITION___GET_ATOMIC_CHANGES);
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
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		AttributePackage theAttributePackage = (AttributePackage)EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI);

		// Create type parameters
		ETypeParameter explicitUnsetEFeatureEClass_A = addETypeParameter(explicitUnsetEFeatureEClass, "A");
		ETypeParameter explicitUnsetEFeatureEClass_F = addETypeParameter(explicitUnsetEFeatureEClass, "F");
		ETypeParameter explicitUnsetEAttributeEClass_A = addETypeParameter(explicitUnsetEAttributeEClass, "A");
		ETypeParameter explicitUnsetEAttributeEClass_T = addETypeParameter(explicitUnsetEAttributeEClass, "T");
		ETypeParameter explicitUnsetEReferenceEClass_A = addETypeParameter(explicitUnsetEReferenceEClass, "A");
		ETypeParameter compoundSubtractionEClass_T = addETypeParameter(compoundSubtractionEClass, "T");
		ETypeParameter compoundSubtractionEClass_S = addETypeParameter(compoundSubtractionEClass, "S");
		ETypeParameter compoundAdditionEClass_T = addETypeParameter(compoundAdditionEClass, "T");
		ETypeParameter compoundAdditionEClass_S = addETypeParameter(compoundAdditionEClass, "S");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theEcorePackage.getEObject());
		explicitUnsetEFeatureEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEStructuralFeature());
		explicitUnsetEFeatureEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		explicitUnsetEAttributeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		explicitUnsetEAttributeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEObject());
		explicitUnsetEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		compoundSubtractionEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEChangePackage.getSubtractiveEChange());
		EGenericType g2 = createEGenericType(compoundSubtractionEClass_T);
		g1.getETypeArguments().add(g2);
		compoundSubtractionEClass_S.getEBounds().add(g1);
		g1 = createEGenericType(theEcorePackage.getEJavaObject());
		compoundAdditionEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(theEChangePackage.getAdditiveEChange());
		g2 = createEGenericType(compoundAdditionEClass_T);
		g1.getETypeArguments().add(g2);
		compoundAdditionEClass_S.getEBounds().add(g1);

		// Add supertypes to classes
		compoundEChangeEClass.getESuperTypes().add(theEChangePackage.getEChange());
		explicitUnsetEFeatureEClass.getESuperTypes().add(this.getCompoundEChange());
		g1 = createEGenericType(this.getCompoundSubtraction());
		g2 = createEGenericType(explicitUnsetEAttributeEClass_T);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theAttributePackage.getSubtractiveAttributeEChange());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(explicitUnsetEAttributeEClass_A);
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType(explicitUnsetEAttributeEClass_T);
		g2.getETypeArguments().add(g3);
		explicitUnsetEAttributeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getExplicitUnsetEFeature());
		g2 = createEGenericType(explicitUnsetEAttributeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEAttribute());
		g1.getETypeArguments().add(g2);
		explicitUnsetEAttributeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getExplicitUnsetEFeature());
		g2 = createEGenericType(explicitUnsetEReferenceEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theEcorePackage.getEReference());
		g1.getETypeArguments().add(g2);
		explicitUnsetEReferenceEClass.getEGenericSuperTypes().add(g1);
		compoundSubtractionEClass.getESuperTypes().add(this.getCompoundEChange());
		compoundAdditionEClass.getESuperTypes().add(this.getCompoundEChange());

		// Initialize classes, features, and operations; add parameters
		initEClass(compoundEChangeEClass, CompoundEChange.class, "CompoundEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getCompoundEChange__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(explicitUnsetEFeatureEClass, ExplicitUnsetEFeature.class, "ExplicitUnsetEFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(explicitUnsetEFeatureEClass_A);
		initEReference(getExplicitUnsetEFeature_AffectedEObject(), g1, null, "affectedEObject", null, 1, 1, ExplicitUnsetEFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(explicitUnsetEFeatureEClass_F);
		initEReference(getExplicitUnsetEFeature_AffectedFeature(), g1, null, "affectedFeature", null, 1, 1, ExplicitUnsetEFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExplicitUnsetEFeature_AffectedEObjectID(), theEcorePackage.getEString(), "affectedEObjectID", null, 0, 1, ExplicitUnsetEFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(explicitUnsetEAttributeEClass, ExplicitUnsetEAttribute.class, "ExplicitUnsetEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getExplicitUnsetEAttribute__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(explicitUnsetEReferenceEClass, ExplicitUnsetEReference.class, "ExplicitUnsetEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExplicitUnsetEReference_Changes(), theEChangePackage.getEChange(), null, "changes", null, 1, -1, ExplicitUnsetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getExplicitUnsetEReference__GetContainedChanges(), theEChangePackage.getEChange(), "getContainedChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getExplicitUnsetEReference__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundSubtractionEClass, CompoundSubtraction.class, "CompoundSubtraction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(compoundSubtractionEClass_S);
		initEReference(getCompoundSubtraction_SubtractiveChanges(), g1, null, "subtractiveChanges", null, 1, -1, CompoundSubtraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCompoundSubtraction__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundAdditionEClass, CompoundAddition.class, "CompoundAddition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(compoundAdditionEClass_S);
		initEReference(getCompoundAddition_AdditiveChanges(), g1, null, "additiveChanges", null, 1, -1, CompoundAddition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getCompoundAddition__GetAtomicChanges(), theEChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CompoundPackageImpl
