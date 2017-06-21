/** 
 */
package tools.vitruv.framework.versioning.commit.util

import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.author.Signed
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit

/** 
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see CommitPackage
 * @generated
 */
class CommitAdapterFactory extends AdapterFactoryImpl {
	/** 
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CommitPackage modelPackage

	/** 
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		if (modelPackage === null) {
			modelPackage = CommitPackage::eINSTANCE
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
	protected CommitSwitch<Adapter> modelSwitch = new CommitSwitch<Adapter>() {
		override Adapter caseMergeCommit(MergeCommit object) {
			return createMergeCommitAdapter()
		}

		override Adapter caseSimpleCommit(SimpleCommit object) {
			return createSimpleCommitAdapter()
		}

		override Adapter caseCommit(Commit object) {
			return createCommitAdapter()
		}

		override Adapter caseCommitMessage(CommitMessage object) {
			return createCommitMessageAdapter()
		}

		override Adapter caseInitialCommit(InitialCommit object) {
			return createInitialCommitAdapter()
		}

		override Adapter caseChangeMatch(ChangeMatch object) {
			return createChangeMatchAdapter()
		}

		override Adapter caseSigned(Signed object) {
			return createSignedAdapter()
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
	 * Creates a new adapter for an object of class '{@link MergeCommit <em>Merge Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see MergeCommit
	 * @generated
	 */
	def Adapter createMergeCommitAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link SimpleCommit <em>Simple Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SimpleCommit
	 * @generated
	 */
	def Adapter createSimpleCommitAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Commit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Commit
	 * @generated
	 */
	def Adapter createCommitAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link CommitMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see CommitMessage
	 * @generated
	 */
	def Adapter createCommitMessageAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link InitialCommit <em>Initial Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see InitialCommit
	 * @generated
	 */
	def Adapter createInitialCommitAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link ChangeMatch <em>Change Match</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see ChangeMatch
	 * @generated
	 */
	def Adapter createChangeMatchAdapter() {
		return null
	}

	/** 
	 * Creates a new adapter for an object of class '{@link Signed <em>Signed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see Signed
	 * @generated
	 */
	def Adapter createSignedAdapter() {
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
	} // CommitAdapterFactory
}
