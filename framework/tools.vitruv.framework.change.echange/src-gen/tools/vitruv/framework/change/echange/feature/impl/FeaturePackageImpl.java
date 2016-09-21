/**
 */
package tools.vitruv.framework.change.echange.feature.impl;

import tools.vitruv.framework.change.echange.EChangePackage;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.impl.CompoundPackageImpl;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.FeatureFactory;
import tools.vitruv.framework.change.echange.feature.FeaturePackage;
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage;
import tools.vitruv.framework.change.echange.feature.attribute.impl.AttributePackageImpl;
import tools.vitruv.framework.change.echange.feature.list.ListPackage;
import tools.vitruv.framework.change.echange.feature.list.impl.ListPackageImpl;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.impl.ReferencePackageImpl;
import tools.vitruv.framework.change.echange.feature.single.SinglePackage;
import tools.vitruv.framework.change.echange.feature.single.impl.SinglePackageImpl;
import tools.vitruv.framework.change.echange.impl.EChangePackageImpl;
import tools.vitruv.framework.change.echange.root.RootPackage;
import tools.vitruv.framework.change.echange.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeaturePackageImpl extends EPackageImpl implements FeaturePackage {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass featureEChangeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass updateMultiValuedFeatureEChangeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass updateSingleValuedFeatureEChangeEClass = null;

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
	 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
    private FeaturePackageImpl() {
		super(eNS_URI, FeatureFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link FeaturePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static FeaturePackage init() {
		if (isInited) return (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);

		// Obtain or create and register package
		FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new FeaturePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		EChangePackageImpl theEChangePackage = (EChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) instanceof EChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI) : EChangePackage.eINSTANCE);
		SinglePackageImpl theSinglePackage = (SinglePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) instanceof SinglePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI) : SinglePackage.eINSTANCE);
		ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
		AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
		ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
		CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI) : CompoundPackage.eINSTANCE);

		// Create package meta-data objects
		theFeaturePackage.createPackageContents();
		theEChangePackage.createPackageContents();
		theSinglePackage.createPackageContents();
		theListPackage.createPackageContents();
		theAttributePackage.createPackageContents();
		theReferencePackage.createPackageContents();
		theRootPackage.createPackageContents();
		theCompoundPackage.createPackageContents();

		// Initialize created meta-data
		theFeaturePackage.initializePackageContents();
		theEChangePackage.initializePackageContents();
		theSinglePackage.initializePackageContents();
		theListPackage.initializePackageContents();
		theAttributePackage.initializePackageContents();
		theReferencePackage.initializePackageContents();
		theRootPackage.initializePackageContents();
		theCompoundPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFeaturePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FeaturePackage.eNS_URI, theFeaturePackage);
		return theFeaturePackage;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getFeatureEChange() {
		return featureEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getFeatureEChange_AffectedFeature() {
		return (EReference)featureEChangeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getFeatureEChange_AffectedEObject() {
		return (EReference)featureEChangeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUpdateMultiValuedFeatureEChange() {
		return updateMultiValuedFeatureEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUpdateSingleValuedFeatureEChange() {
		return updateSingleValuedFeatureEChangeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureFactory getFeatureFactory() {
		return (FeatureFactory)getEFactoryInstance();
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
		featureEChangeEClass = createEClass(FEATURE_ECHANGE);
		createEReference(featureEChangeEClass, FEATURE_ECHANGE__AFFECTED_FEATURE);
		createEReference(featureEChangeEClass, FEATURE_ECHANGE__AFFECTED_EOBJECT);

		updateMultiValuedFeatureEChangeEClass = createEClass(UPDATE_MULTI_VALUED_FEATURE_ECHANGE);

		updateSingleValuedFeatureEChangeEClass = createEClass(UPDATE_SINGLE_VALUED_FEATURE_ECHANGE);
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
		SinglePackage theSinglePackage = (SinglePackage)EPackage.Registry.INSTANCE.getEPackage(SinglePackage.eNS_URI);
		ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
		AttributePackage theAttributePackage = (AttributePackage)EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI);
		ReferencePackage theReferencePackage = (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theSinglePackage);
		getESubpackages().add(theListPackage);
		getESubpackages().add(theAttributePackage);
		getESubpackages().add(theReferencePackage);

		// Create type parameters
		ETypeParameter featureEChangeEClass_A = addETypeParameter(featureEChangeEClass, "A");
		ETypeParameter featureEChangeEClass_F = addETypeParameter(featureEChangeEClass, "F");
		ETypeParameter updateMultiValuedFeatureEChangeEClass_A = addETypeParameter(updateMultiValuedFeatureEChangeEClass, "A");
		ETypeParameter updateMultiValuedFeatureEChangeEClass_F = addETypeParameter(updateMultiValuedFeatureEChangeEClass, "F");
		ETypeParameter updateSingleValuedFeatureEChangeEClass_A = addETypeParameter(updateSingleValuedFeatureEChangeEClass, "A");
		ETypeParameter updateSingleValuedFeatureEChangeEClass_F = addETypeParameter(updateSingleValuedFeatureEChangeEClass, "F");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(ecorePackage.getEObject());
		featureEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		featureEChangeEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		updateMultiValuedFeatureEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		updateMultiValuedFeatureEChangeEClass_F.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEObject());
		updateSingleValuedFeatureEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(ecorePackage.getEStructuralFeature());
		updateSingleValuedFeatureEChangeEClass_F.getEBounds().add(g1);

		// Add supertypes to classes
		featureEChangeEClass.getESuperTypes().add(theEChangePackage.getAtomicEChange());
		g1 = createEGenericType(this.getFeatureEChange());
		EGenericType g2 = createEGenericType(updateMultiValuedFeatureEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(updateMultiValuedFeatureEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		updateMultiValuedFeatureEChangeEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getFeatureEChange());
		g2 = createEGenericType(updateSingleValuedFeatureEChangeEClass_A);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(updateSingleValuedFeatureEChangeEClass_F);
		g1.getETypeArguments().add(g2);
		updateSingleValuedFeatureEChangeEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(featureEChangeEClass, FeatureEChange.class, "FeatureEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(featureEChangeEClass_F);
		initEReference(getFeatureEChange_AffectedFeature(), g1, null, "affectedFeature", null, 1, 1, FeatureEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(featureEChangeEClass_A);
		initEReference(getFeatureEChange_AffectedEObject(), g1, null, "affectedEObject", null, 1, 1, FeatureEChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(updateMultiValuedFeatureEChangeEClass, UpdateMultiValuedFeatureEChange.class, "UpdateMultiValuedFeatureEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(updateSingleValuedFeatureEChangeEClass, UpdateSingleValuedFeatureEChange.class, "UpdateSingleValuedFeatureEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //FeaturePackageImpl
