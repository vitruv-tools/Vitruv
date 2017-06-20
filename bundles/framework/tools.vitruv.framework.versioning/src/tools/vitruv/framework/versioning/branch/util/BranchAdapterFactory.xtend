/** 
 */
package tools.vitruv.framework.versioning.branch.util

import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.branch.BranchDiffCreator
import tools.vitruv.framework.versioning.branch.BranchPackage
import tools.vitruv.framework.versioning.branch.MasterBranch
import tools.vitruv.framework.versioning.branch.UserBranch

/** 
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see BranchPackage
 * @generated
 */
class BranchAdapterFactory extends AdapterFactoryImpl {
	/** 
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BranchPackage modelPackage

	/** 
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = BranchPackage::eINSTANCE
		}
	}

	/** 
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	override boolean isFactoryForType(Object object) {
		if (object === modelPackage) {
			return true
		}
		if (object instanceof EObject) {
			return object.eClass().getEPackage() === modelPackage
		}
		return false
	}

	/** 
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchSwitch<Adapter> modelSwitch = new BranchSwitch<Adapter>() {
		override Adapter caseBranchDiffCreator(BranchDiffCreator object) {
			return createBranchDiffCreatorAdapter()
		}

		override Adapter caseBranchDiff(BranchDiff object) {
			return createBranchDiffAdapter()
		}

		override Adapter caseUserBranch(UserBranch object) {
			return createUserBranchAdapter()
		}

		override Adapter caseMasterBranch(MasterBranch object) {
			return createMasterBranchAdapter()
		}

		override Adapter caseBranch(Branch object) {
			return createBranchAdapter()
		}

		override Adapter caseNamed(Named object) {
			return createNamedAdapter()
		}

		override Adapter defaultCase(EObject object) {
			return createEObjectAdapter()
		}
	}

	/** 
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	override Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((target as EObject))
	}

	/** 
	 * Creates a new adapter for an object of class '{@link BranchDiffCreator <em>Diff Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see BranchDiffCreator
	 * @generated
	 */
	def Adapter createBranchDiffCreatorAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link BranchDiff <em>Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see BranchDiff
	 * @generated
	 */
	def Adapter createBranchDiffAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link UserBranch <em>User Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see UserBranch
	 * @generated
	 */
	def Adapter createUserBranchAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link MasterBranch <em>Master Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MasterBranch
	 * @generated
	 */
	def Adapter createMasterBranchAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Branch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Branch
	 * @generated
	 */
	def Adapter createBranchAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Named <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Named
	 * @generated
	 */
	def Adapter createNamedAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	def Adapter createEObjectAdapter() {
		return null
	} // BranchAdapterFactory
}
