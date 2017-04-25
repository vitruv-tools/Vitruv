/**
 */
package tools.vitruv.framework.versioning.branch.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.versioning.branch.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BranchFactoryImpl extends EFactoryImpl implements BranchFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BranchFactory init() {
		try {
			BranchFactory theBranchFactory = (BranchFactory)EPackage.Registry.INSTANCE.getEFactory(BranchPackage.eNS_URI);
			if (theBranchFactory != null) {
				return theBranchFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BranchFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchFactoryImpl() {
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
			case BranchPackage.BRANCH_DIFF_CREATOR: return createBranchDiffCreator();
			case BranchPackage.BRANCH_DIFF: return createBranchDiff();
			case BranchPackage.BRANCH: return createBranch();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchDiffCreator createBranchDiffCreator() {
		BranchDiffCreatorImpl branchDiffCreator = new BranchDiffCreatorImpl();
		return branchDiffCreator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchDiff createBranchDiff() {
		BranchDiffImpl branchDiff = new BranchDiffImpl();
		return branchDiff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branch createBranch() {
		BranchImpl branch = new BranchImpl();
		return branch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchPackage getBranchPackage() {
		return (BranchPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BranchPackage getPackage() {
		return BranchPackage.eINSTANCE;
	}

} //BranchFactoryImpl
