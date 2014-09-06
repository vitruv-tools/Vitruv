/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeatureFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateMultiValuedEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UpdateSingleValuedEFeature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl.ListPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectPackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
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
	private EClass eFeatureChangeEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass updateEFeatureEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass updateMultiValuedEFeatureEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass updateSingleValuedEFeatureEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass unsetEFeatureEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass unsetEAttributeEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass unsetEReferenceEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass unsetNonContainmentEReferenceEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass unsetContainmentEReferenceEClass = null;

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#eNS_URI
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
        ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
        ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
        AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
        ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
        ContainmentPackageImpl theContainmentPackage = (ContainmentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI) instanceof ContainmentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI) : ContainmentPackage.eINSTANCE);
        ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) : ObjectPackage.eINSTANCE);

        // Create package meta-data objects
        theFeaturePackage.createPackageContents();
        theChangePackage.createPackageContents();
        theListPackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theReferencePackage.createPackageContents();
        theContainmentPackage.createPackageContents();
        theObjectPackage.createPackageContents();

        // Initialize created meta-data
        theFeaturePackage.initializePackageContents();
        theChangePackage.initializePackageContents();
        theListPackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theReferencePackage.initializePackageContents();
        theContainmentPackage.initializePackageContents();
        theObjectPackage.initializePackageContents();

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
	public EClass getEFeatureChange() {
        return eFeatureChangeEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getEFeatureChange_AffectedFeature() {
        return (EReference)eFeatureChangeEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getEFeatureChange_OldAffectedEObject() {
        return (EReference)eFeatureChangeEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getEFeatureChange_NewAffectedEObject() {
        return (EReference)eFeatureChangeEClass.getEStructuralFeatures().get(2);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUpdateEFeature() {
        return updateEFeatureEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUpdateMultiValuedEFeature() {
        return updateMultiValuedEFeatureEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUpdateSingleValuedEFeature() {
        return updateSingleValuedEFeatureEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUnsetEFeature() {
        return unsetEFeatureEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUnsetEAttribute() {
        return unsetEAttributeEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getUnsetEAttribute_OldValue() {
        return (EAttribute)unsetEAttributeEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUnsetEReference() {
        return unsetEReferenceEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getUnsetEReference_OldValue() {
        return (EAttribute)unsetEReferenceEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUnsetNonContainmentEReference() {
        return unsetNonContainmentEReferenceEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUnsetContainmentEReference() {
        return unsetContainmentEReferenceEClass;
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
        eFeatureChangeEClass = createEClass(EFEATURE_CHANGE);
        createEReference(eFeatureChangeEClass, EFEATURE_CHANGE__AFFECTED_FEATURE);
        createEReference(eFeatureChangeEClass, EFEATURE_CHANGE__OLD_AFFECTED_EOBJECT);
        createEReference(eFeatureChangeEClass, EFEATURE_CHANGE__NEW_AFFECTED_EOBJECT);

        updateEFeatureEClass = createEClass(UPDATE_EFEATURE);

        updateMultiValuedEFeatureEClass = createEClass(UPDATE_MULTI_VALUED_EFEATURE);

        updateSingleValuedEFeatureEClass = createEClass(UPDATE_SINGLE_VALUED_EFEATURE);

        unsetEFeatureEClass = createEClass(UNSET_EFEATURE);

        unsetEAttributeEClass = createEClass(UNSET_EATTRIBUTE);
        createEAttribute(unsetEAttributeEClass, UNSET_EATTRIBUTE__OLD_VALUE);

        unsetEReferenceEClass = createEClass(UNSET_EREFERENCE);
        createEAttribute(unsetEReferenceEClass, UNSET_EREFERENCE__OLD_VALUE);

        unsetNonContainmentEReferenceEClass = createEClass(UNSET_NON_CONTAINMENT_EREFERENCE);

        unsetContainmentEReferenceEClass = createEClass(UNSET_CONTAINMENT_EREFERENCE);
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
        ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
        AttributePackage theAttributePackage = (AttributePackage)EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI);
        ReferencePackage theReferencePackage = (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);
        ChangePackage theChangePackage = (ChangePackage)EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI);

        // Add subpackages
        getESubpackages().add(theListPackage);
        getESubpackages().add(theAttributePackage);
        getESubpackages().add(theReferencePackage);

        // Create type parameters
        ETypeParameter eFeatureChangeEClass_T = addETypeParameter(eFeatureChangeEClass, "T");
        ETypeParameter updateEFeatureEClass_T = addETypeParameter(updateEFeatureEClass, "T");
        ETypeParameter updateMultiValuedEFeatureEClass_T = addETypeParameter(updateMultiValuedEFeatureEClass, "T");
        ETypeParameter updateSingleValuedEFeatureEClass_T = addETypeParameter(updateSingleValuedEFeatureEClass, "T");
        ETypeParameter unsetEFeatureEClass_T = addETypeParameter(unsetEFeatureEClass, "T");
        ETypeParameter unsetEAttributeEClass_T = addETypeParameter(unsetEAttributeEClass, "T");
        ETypeParameter unsetEReferenceEClass_T = addETypeParameter(unsetEReferenceEClass, "T");
        ETypeParameter unsetNonContainmentEReferenceEClass_T = addETypeParameter(unsetNonContainmentEReferenceEClass, "T");
        ETypeParameter unsetContainmentEReferenceEClass_T = addETypeParameter(unsetContainmentEReferenceEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEStructuralFeature());
        eFeatureChangeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        updateEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        updateMultiValuedEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        updateSingleValuedEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEStructuralFeature());
        unsetEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        unsetEAttributeEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        unsetEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        unsetNonContainmentEReferenceEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        unsetContainmentEReferenceEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        eFeatureChangeEClass.getESuperTypes().add(theChangePackage.getEChange());
        g1 = createEGenericType(this.getUpdateEFeature());
        EGenericType g2 = createEGenericType(updateMultiValuedEFeatureEClass_T);
        g1.getETypeArguments().add(g2);
        updateMultiValuedEFeatureEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateEFeature());
        g2 = createEGenericType(updateSingleValuedEFeatureEClass_T);
        g1.getETypeArguments().add(g2);
        updateSingleValuedEFeatureEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getEFeatureChange());
        g2 = createEGenericType(unsetEFeatureEClass_T);
        g1.getETypeArguments().add(g2);
        unsetEFeatureEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUnsetEFeature());
        g2 = createEGenericType(ecorePackage.getEAttribute());
        g1.getETypeArguments().add(g2);
        unsetEAttributeEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUnsetEFeature());
        g2 = createEGenericType(ecorePackage.getEReference());
        g1.getETypeArguments().add(g2);
        unsetEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUnsetEReference());
        g2 = createEGenericType(unsetNonContainmentEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        unsetNonContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUnsetEReference());
        g2 = createEGenericType(unsetContainmentEReferenceEClass_T);
        g1.getETypeArguments().add(g2);
        unsetContainmentEReferenceEClass.getEGenericSuperTypes().add(g1);

        // Initialize classes and features; add operations and parameters
        initEClass(eFeatureChangeEClass, EFeatureChange.class, "EFeatureChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(eFeatureChangeEClass_T);
        initEReference(getEFeatureChange_AffectedFeature(), g1, null, "affectedFeature", null, 1, 1, EFeatureChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEFeatureChange_OldAffectedEObject(), ecorePackage.getEObject(), null, "oldAffectedEObject", null, 1, 1, EFeatureChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEFeatureChange_NewAffectedEObject(), ecorePackage.getEObject(), null, "newAffectedEObject", null, 1, 1, EFeatureChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(updateEFeatureEClass, UpdateEFeature.class, "UpdateEFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(updateMultiValuedEFeatureEClass, UpdateMultiValuedEFeature.class, "UpdateMultiValuedEFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(updateSingleValuedEFeatureEClass, UpdateSingleValuedEFeature.class, "UpdateSingleValuedEFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(unsetEFeatureEClass, UnsetEFeature.class, "UnsetEFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(unsetEAttributeEClass, UnsetEAttribute.class, "UnsetEAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(unsetEAttributeEClass_T);
        initEAttribute(getUnsetEAttribute_OldValue(), g1, "oldValue", null, 1, 1, UnsetEAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unsetEReferenceEClass, UnsetEReference.class, "UnsetEReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(unsetEReferenceEClass_T);
        initEAttribute(getUnsetEReference_OldValue(), g1, "oldValue", null, 1, 1, UnsetEReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unsetNonContainmentEReferenceEClass, UnsetNonContainmentEReference.class, "UnsetNonContainmentEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(unsetContainmentEReferenceEClass, UnsetContainmentEReference.class, "UnsetContainmentEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    }

} //FeaturePackageImpl
