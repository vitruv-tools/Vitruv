/**
 */
package tools.vitruv.framework.change.echange.feature.reference.impl;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

import tools.vitruv.framework.change.echange.feature.FeaturePackage;

import tools.vitruv.framework.change.echange.feature.list.ListPackage;

import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory;
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange;
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange;

import tools.vitruv.framework.change.echange.feature.single.SinglePackage;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eObjEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resourceSetEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType commandEDataType = null;

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
		EobjectPackage.eINSTANCE.eClass();
		ListPackage.eINSTANCE.eClass();
		SinglePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theReferencePackage.createPackageContents();

		// Initialize created meta-data
		theReferencePackage.initializePackageContents();

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
	public EOperation getInsertEReference__IsResolved() {
		return insertEReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInsertEReference__ResolveApply__ResourceSet() {
		return insertEReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInsertEReference__ResolveRevert__ResourceSet() {
		return insertEReferenceEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInsertEReference__Resolve__ResourceSet_boolean() {
		return insertEReferenceEClass.getEOperations().get(3);
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
	public EOperation getRemoveEReference__IsResolved() {
		return removeEReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRemoveEReference__ResolveApply__ResourceSet() {
		return removeEReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRemoveEReference__ResolveRevert__ResourceSet() {
		return removeEReferenceEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRemoveEReference__Resolve__ResourceSet_boolean() {
		return removeEReferenceEClass.getEOperations().get(3);
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
	public EOperation getReplaceSingleValuedEReference__IsResolved() {
		return replaceSingleValuedEReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getReplaceSingleValuedEReference__ResolveApply__ResourceSet() {
		return replaceSingleValuedEReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getReplaceSingleValuedEReference__ResolveRevert__ResourceSet() {
		return replaceSingleValuedEReferenceEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getReplaceSingleValuedEReference__Resolve__ResourceSet_boolean() {
		return replaceSingleValuedEReferenceEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEObj() {
		return eObjEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getResourceSet() {
		return resourceSetEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getCommand() {
		return commandEDataType;
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
		createEOperation(insertEReferenceEClass, INSERT_EREFERENCE___IS_RESOLVED);
		createEOperation(insertEReferenceEClass, INSERT_EREFERENCE___RESOLVE_APPLY__RESOURCESET);
		createEOperation(insertEReferenceEClass, INSERT_EREFERENCE___RESOLVE_REVERT__RESOURCESET);
		createEOperation(insertEReferenceEClass, INSERT_EREFERENCE___RESOLVE__RESOURCESET_BOOLEAN);

		removeEReferenceEClass = createEClass(REMOVE_EREFERENCE);
		createEOperation(removeEReferenceEClass, REMOVE_EREFERENCE___IS_RESOLVED);
		createEOperation(removeEReferenceEClass, REMOVE_EREFERENCE___RESOLVE_APPLY__RESOURCESET);
		createEOperation(removeEReferenceEClass, REMOVE_EREFERENCE___RESOLVE_REVERT__RESOURCESET);
		createEOperation(removeEReferenceEClass, REMOVE_EREFERENCE___RESOLVE__RESOURCESET_BOOLEAN);

		replaceSingleValuedEReferenceEClass = createEClass(REPLACE_SINGLE_VALUED_EREFERENCE);
		createEOperation(replaceSingleValuedEReferenceEClass, REPLACE_SINGLE_VALUED_EREFERENCE___IS_RESOLVED);
		createEOperation(replaceSingleValuedEReferenceEClass, REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE_APPLY__RESOURCESET);
		createEOperation(replaceSingleValuedEReferenceEClass, REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE_REVERT__RESOURCESET);
		createEOperation(replaceSingleValuedEReferenceEClass, REPLACE_SINGLE_VALUED_EREFERENCE___RESOLVE__RESOURCESET_BOOLEAN);

		// Create data types
		eObjEDataType = createEDataType(EOBJ);
		resourceSetEDataType = createEDataType(RESOURCE_SET);
		commandEDataType = createEDataType(COMMAND);
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
		EChangePackage theEChangePackage = (EChangePackage)EPackage.Registry.INSTANCE.getEPackage(EChangePackage.eNS_URI);
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
		g1 = createEGenericType(this.getEObj());
		additiveReferenceEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		additiveReferenceEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		subtractiveReferenceEChangeEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		subtractiveReferenceEChangeEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		insertEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		insertEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		removeEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		removeEReferenceEClass_T.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
		replaceSingleValuedEReferenceEClass_A.getEBounds().add(g1);
		g1 = createEGenericType(this.getEObj());
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

		initEOperation(getInsertEReference__IsResolved(), theEcorePackage.getEBoolean(), "isResolved", 0, 1, !IS_UNIQUE, IS_ORDERED);

		EOperation op = initEOperation(getInsertEReference__ResolveApply__ResourceSet(), theEChangePackage.getEChange(), "resolveApply", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getInsertEReference__ResolveRevert__ResourceSet(), theEChangePackage.getEChange(), "resolveRevert", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getInsertEReference__Resolve__ResourceSet_boolean(), theEChangePackage.getEChange(), "resolve", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEBoolean(), "applyChange", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(removeEReferenceEClass, RemoveEReference.class, "RemoveEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getRemoveEReference__IsResolved(), theEcorePackage.getEBoolean(), "isResolved", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRemoveEReference__ResolveApply__ResourceSet(), theEChangePackage.getEChange(), "resolveApply", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRemoveEReference__ResolveRevert__ResourceSet(), theEChangePackage.getEChange(), "resolveRevert", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRemoveEReference__Resolve__ResourceSet_boolean(), theEChangePackage.getEChange(), "resolve", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEBoolean(), "applyChange", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(replaceSingleValuedEReferenceEClass, ReplaceSingleValuedEReference.class, "ReplaceSingleValuedEReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getReplaceSingleValuedEReference__IsResolved(), theEcorePackage.getEBoolean(), "isResolved", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getReplaceSingleValuedEReference__ResolveApply__ResourceSet(), theEChangePackage.getEChange(), "resolveApply", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getReplaceSingleValuedEReference__ResolveRevert__ResourceSet(), theEChangePackage.getEChange(), "resolveRevert", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getReplaceSingleValuedEReference__Resolve__ResourceSet_boolean(), theEChangePackage.getEChange(), "resolve", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceSet(), "resourceSet", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEBoolean(), "applyChange", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize data types
		initEDataType(eObjEDataType, EObject.class, "EObj", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(resourceSetEDataType, ResourceSet.class, "ResourceSet", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(commandEDataType, Command.class, "Command", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ReferencePackageImpl
