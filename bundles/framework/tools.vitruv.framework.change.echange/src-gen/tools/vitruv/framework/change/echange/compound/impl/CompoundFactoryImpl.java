/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.compound.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompoundFactoryImpl extends EFactoryImpl implements CompoundFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompoundFactory init() {
		try {
			CompoundFactory theCompoundFactory = (CompoundFactory)EPackage.Registry.INSTANCE.getEFactory(CompoundPackage.eNS_URI);
			if (theCompoundFactory != null) {
				return theCompoundFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompoundFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundFactoryImpl() {
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
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE: return createExplicitUnsetEAttribute();
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE: return createExplicitUnsetEReference();
			case CompoundPackage.CREATE_AND_INSERT_ROOT: return createCreateAndInsertRoot();
			case CompoundPackage.REMOVE_AND_DELETE_ROOT: return createRemoveAndDeleteRoot();
			case CompoundPackage.CREATE_AND_INSERT_NON_ROOT: return createCreateAndInsertNonRoot();
			case CompoundPackage.REMOVE_AND_DELETE_NON_ROOT: return createRemoveAndDeleteNonRoot();
			case CompoundPackage.CREATE_AND_REPLACE_NON_ROOT: return createCreateAndReplaceNonRoot();
			case CompoundPackage.REPLACE_AND_DELETE_NON_ROOT: return createReplaceAndDeleteNonRoot();
			case CompoundPackage.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT: return createCreateAndReplaceAndDeleteNonRoot();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CompoundPackage.COMMAND:
				return createCommandFromString(eDataType, initialValue);
			case CompoundPackage.RESOURCE_SET:
				return createResourceSetFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CompoundPackage.COMMAND:
				return convertCommandToString(eDataType, instanceValue);
			case CompoundPackage.RESOURCE_SET:
				return convertResourceSetToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttribute() {
		ExplicitUnsetEAttributeImpl<A, T> explicitUnsetEAttribute = new ExplicitUnsetEAttributeImpl<A, T>();
		return explicitUnsetEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReference() {
		ExplicitUnsetEReferenceImpl<A> explicitUnsetEReference = new ExplicitUnsetEReferenceImpl<A>();
		return explicitUnsetEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRoot() {
		CreateAndInsertRootImpl<T> createAndInsertRoot = new CreateAndInsertRootImpl<T>();
		return createAndInsertRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRoot() {
		RemoveAndDeleteRootImpl<T> removeAndDeleteRoot = new RemoveAndDeleteRootImpl<T>();
		return removeAndDeleteRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A, T> createCreateAndInsertNonRoot() {
		CreateAndInsertNonRootImpl<A, T> createAndInsertNonRoot = new CreateAndInsertNonRootImpl<A, T>();
		return createAndInsertNonRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A, T> createRemoveAndDeleteNonRoot() {
		RemoveAndDeleteNonRootImpl<A, T> removeAndDeleteNonRoot = new RemoveAndDeleteNonRootImpl<A, T>();
		return removeAndDeleteNonRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> CreateAndReplaceNonRoot<A, T> createCreateAndReplaceNonRoot() {
		CreateAndReplaceNonRootImpl<A, T> createAndReplaceNonRoot = new CreateAndReplaceNonRootImpl<A, T>();
		return createAndReplaceNonRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> ReplaceAndDeleteNonRoot<A, T> createReplaceAndDeleteNonRoot() {
		ReplaceAndDeleteNonRootImpl<A, T> replaceAndDeleteNonRoot = new ReplaceAndDeleteNonRootImpl<A, T>();
		return replaceAndDeleteNonRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A, T> createCreateAndReplaceAndDeleteNonRoot() {
		CreateAndReplaceAndDeleteNonRootImpl<A, T> createAndReplaceAndDeleteNonRoot = new CreateAndReplaceAndDeleteNonRootImpl<A, T>();
		return createAndReplaceAndDeleteNonRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Command createCommandFromString(EDataType eDataType, String initialValue) {
		return (Command)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCommandToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceSet createResourceSetFromString(EDataType eDataType, String initialValue) {
		return (ResourceSet)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResourceSetToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundPackage getCompoundPackage() {
		return (CompoundPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CompoundPackage getPackage() {
		return CompoundPackage.eINSTANCE;
	}

} //CompoundFactoryImpl
