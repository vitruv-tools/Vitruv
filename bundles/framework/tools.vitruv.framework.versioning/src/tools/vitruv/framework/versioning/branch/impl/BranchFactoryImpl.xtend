/** 
 */
package tools.vitruv.framework.versioning.branch.impl

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EFactoryImpl
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.branch.BranchDiffCreator
import tools.vitruv.framework.versioning.branch.BranchFactory
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.MasterBranch
import tools.vitruv.framework.versioning.branch.UserBranch

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class BranchFactoryImpl extends EFactoryImpl implements BranchFactory {
	/** 
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def static BranchFactory init() {
		try {
			var BranchFactory theBranchFactory = (EPackage.Registry::INSTANCE.getEFactory(
				BranchPackage::eNS_URI) as BranchFactory)
			if (theBranchFactory !== null) {
				return theBranchFactory
			}
		} catch (Exception exception) {
			EcorePlugin::INSTANCE.log(exception)
		}

		return new BranchFactoryImpl()
	}

	/** 
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EObject create(EClass eClass) {

		switch (eClass.getClassifierID()) {
			case BranchPackage::BRANCH_DIFF_CREATOR: {
				return createBranchDiffCreator()
			}
			case BranchPackage::BRANCH_DIFF: {
				return createBranchDiff()
			}
			case BranchPackage::USER_BRANCH: {
				return createUserBranch()
			}
			case BranchPackage::MASTER_BRANCH: {
				return createMasterBranch()
			}
			case BranchPackage::BRANCH: {
				return createBranch()
			}
			default: {
				throw new IllegalArgumentException(
					'''The class '«»«eClass.getName()»' is not a valid classifier'''.toString);
			}
		}
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override BranchDiffCreator createBranchDiffCreator() {
		var BranchDiffCreatorImpl branchDiffCreator = new BranchDiffCreatorImpl()
		return branchDiffCreator
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override BranchDiff createBranchDiff() {
		var BranchDiffImpl branchDiff = new BranchDiffImpl()
		return branchDiff
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override UserBranch createUserBranch() {
		var UserBranchImpl userBranch = new UserBranchImpl()
		return userBranch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override MasterBranch createMasterBranch() {
		var MasterBranchImpl masterBranch = new MasterBranchImpl()
		return masterBranch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Branch createBranch() {
		var BranchImpl branch = new BranchImpl()
		return branch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override BranchPackage getBranchPackage() {
		return (getEPackage() as BranchPackage)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	def static BranchPackage getPackage() {
		return BranchPackage::eINSTANCE
	} // BranchFactoryImpl
}
