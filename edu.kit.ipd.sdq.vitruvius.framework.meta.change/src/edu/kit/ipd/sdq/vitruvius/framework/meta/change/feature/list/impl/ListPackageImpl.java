/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.PermuteEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.RemoveFromEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ReplaceInEList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.UpdateSingleEListEntry;

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
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ListPackageImpl extends EPackageImpl implements ListPackage {
	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass updateSingleEListEntryEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass insertInEListEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass replaceInEListEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass removeFromEListEClass = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass permuteEListEClass = null;

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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.list.ListPackage#eNS_URI
     * @see #init()
     * @generated
     */
	private ListPackageImpl() {
        super(eNS_URI, ListFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link ListPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
	public static ListPackage init() {
        if (isInited) return (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);

        // Obtain or create and register package
        ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ListPackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
        FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
        AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
        ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
        ContainmentPackageImpl theContainmentPackage = (ContainmentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI) instanceof ContainmentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ContainmentPackage.eNS_URI) : ContainmentPackage.eINSTANCE);
        ObjectPackageImpl theObjectPackage = (ObjectPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) instanceof ObjectPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ObjectPackage.eNS_URI) : ObjectPackage.eINSTANCE);

        // Create package meta-data objects
        theListPackage.createPackageContents();
        theChangePackage.createPackageContents();
        theFeaturePackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theReferencePackage.createPackageContents();
        theContainmentPackage.createPackageContents();
        theObjectPackage.createPackageContents();

        // Initialize created meta-data
        theListPackage.initializePackageContents();
        theChangePackage.initializePackageContents();
        theFeaturePackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theReferencePackage.initializePackageContents();
        theContainmentPackage.initializePackageContents();
        theObjectPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theListPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ListPackage.eNS_URI, theListPackage);
        return theListPackage;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getUpdateSingleEListEntry() {
        return updateSingleEListEntryEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getUpdateSingleEListEntry_Index() {
        return (EAttribute)updateSingleEListEntryEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getInsertInEList() {
        return insertInEListEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getInsertInEList_NewValue() {
        return (EAttribute)insertInEListEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getReplaceInEList() {
        return replaceInEListEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getReplaceInEList_OldValue() {
        return (EAttribute)replaceInEListEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getReplaceInEList_NewValue() {
        return (EAttribute)replaceInEListEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getRemoveFromEList() {
        return removeFromEListEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getRemoveFromEList_RemovedObjectURIFragment() {
        return (EAttribute)removeFromEListEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getRemoveFromEList_OldValue() {
        return (EAttribute)removeFromEListEClass.getEStructuralFeatures().get(1);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getPermuteEList() {
        return permuteEListEClass;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getPermuteEList_NewIndexForElementAt() {
        return (EAttribute)permuteEListEClass.getEStructuralFeatures().get(0);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ListFactory getListFactory() {
        return (ListFactory)getEFactoryInstance();
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
        updateSingleEListEntryEClass = createEClass(UPDATE_SINGLE_ELIST_ENTRY);
        createEAttribute(updateSingleEListEntryEClass, UPDATE_SINGLE_ELIST_ENTRY__INDEX);

        insertInEListEClass = createEClass(INSERT_IN_ELIST);
        createEAttribute(insertInEListEClass, INSERT_IN_ELIST__NEW_VALUE);

        replaceInEListEClass = createEClass(REPLACE_IN_ELIST);
        createEAttribute(replaceInEListEClass, REPLACE_IN_ELIST__OLD_VALUE);
        createEAttribute(replaceInEListEClass, REPLACE_IN_ELIST__NEW_VALUE);

        removeFromEListEClass = createEClass(REMOVE_FROM_ELIST);
        createEAttribute(removeFromEListEClass, REMOVE_FROM_ELIST__REMOVED_OBJECT_URI_FRAGMENT);
        createEAttribute(removeFromEListEClass, REMOVE_FROM_ELIST__OLD_VALUE);

        permuteEListEClass = createEClass(PERMUTE_ELIST);
        createEAttribute(permuteEListEClass, PERMUTE_ELIST__NEW_INDEX_FOR_ELEMENT_AT);
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
        FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);

        // Create type parameters
        ETypeParameter updateSingleEListEntryEClass_T = addETypeParameter(updateSingleEListEntryEClass, "T");
        ETypeParameter insertInEListEClass_T = addETypeParameter(insertInEListEClass, "T");
        ETypeParameter replaceInEListEClass_T = addETypeParameter(replaceInEListEClass, "T");
        ETypeParameter removeFromEListEClass_T = addETypeParameter(removeFromEListEClass, "T");
        ETypeParameter permuteEListEClass_T = addETypeParameter(permuteEListEClass, "T");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEJavaObject());
        updateSingleEListEntryEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        insertInEListEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        replaceInEListEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        removeFromEListEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        permuteEListEClass_T.getEBounds().add(g1);

        // Add supertypes to classes
        g1 = createEGenericType(theFeaturePackage.getUpdateMultiValuedEFeature());
        EGenericType g2 = createEGenericType(updateSingleEListEntryEClass_T);
        g1.getETypeArguments().add(g2);
        updateSingleEListEntryEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateSingleEListEntry());
        g2 = createEGenericType(insertInEListEClass_T);
        g1.getETypeArguments().add(g2);
        insertInEListEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateSingleEListEntry());
        g2 = createEGenericType(replaceInEListEClass_T);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(this.getUpdateSingleEListEntry());
        g2 = createEGenericType(removeFromEListEClass_T);
        g1.getETypeArguments().add(g2);
        removeFromEListEClass.getEGenericSuperTypes().add(g1);
        g1 = createEGenericType(theFeaturePackage.getUpdateMultiValuedEFeature());
        g2 = createEGenericType(permuteEListEClass_T);
        g1.getETypeArguments().add(g2);
        permuteEListEClass.getEGenericSuperTypes().add(g1);

        // Initialize classes and features; add operations and parameters
        initEClass(updateSingleEListEntryEClass, UpdateSingleEListEntry.class, "UpdateSingleEListEntry", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUpdateSingleEListEntry_Index(), ecorePackage.getEInt(), "index", "0", 1, 1, UpdateSingleEListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(insertInEListEClass, InsertInEList.class, "InsertInEList", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(insertInEListEClass_T);
        initEAttribute(getInsertInEList_NewValue(), g1, "newValue", null, 1, 1, InsertInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(replaceInEListEClass, ReplaceInEList.class, "ReplaceInEList", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(replaceInEListEClass_T);
        initEAttribute(getReplaceInEList_OldValue(), g1, "oldValue", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(replaceInEListEClass_T);
        initEAttribute(getReplaceInEList_NewValue(), g1, "newValue", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(removeFromEListEClass, RemoveFromEList.class, "RemoveFromEList", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRemoveFromEList_RemovedObjectURIFragment(), ecorePackage.getEString(), "removedObjectURIFragment", "0", 1, 1, RemoveFromEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(removeFromEListEClass_T);
        initEAttribute(getRemoveFromEList_OldValue(), g1, "oldValue", null, 1, 1, RemoveFromEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(permuteEListEClass, PermuteEList.class, "PermuteEList", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPermuteEList_NewIndexForElementAt(), ecorePackage.getEIntegerObject(), "newIndexForElementAt", null, 1, -1, PermuteEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    }

} //ListPackageImpl
