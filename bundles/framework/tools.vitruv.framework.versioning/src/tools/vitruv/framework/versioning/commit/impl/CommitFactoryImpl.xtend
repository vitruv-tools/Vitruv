/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EFactoryImpl
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.CommitPackage
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class CommitFactoryImpl extends EFactoryImpl implements CommitFactory {
	/** 
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def static CommitFactory init() {
		try {
			var CommitFactory theCommitFactory = (EPackage.Registry::INSTANCE.getEFactory(
				CommitPackage::eNS_URI) as CommitFactory)
			if (theCommitFactory !== null) {
				return theCommitFactory
			}
		} catch (Exception exception) {
			EcorePlugin::INSTANCE.log(exception)
		}

		return new CommitFactoryImpl()
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
			case CommitPackage::MERGE_COMMIT: {
				return createMergeCommit()
			}
			case CommitPackage::SIMPLE_COMMIT: {
				return createSimpleCommit()
			}
			case CommitPackage::COMMIT_MESSAGE: {
				return createCommitMessage()
			}
			case CommitPackage::INITIAL_COMMIT: {
				return createInitialCommit()
			}
			case CommitPackage::CHANGE_MATCH: {
				return createChangeMatch()
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
	override MergeCommit createMergeCommit() {
		var MergeCommitImpl mergeCommit = new MergeCommitImpl()
		return mergeCommit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override SimpleCommit createSimpleCommit() {
		var SimpleCommitImpl simpleCommit = new SimpleCommitImpl()
		return simpleCommit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override CommitMessage createCommitMessage() {
		var CommitMessageImpl commitMessage = new CommitMessageImpl()
		return commitMessage
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override InitialCommit createInitialCommit() {
		var InitialCommitImpl initialCommit = new InitialCommitImpl()
		return initialCommit
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ChangeMatch createChangeMatch() {
		var ChangeMatchImpl changeMatch = new ChangeMatchImpl()
		return changeMatch
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override CommitPackage getCommitPackage() {
		return (getEPackage() as CommitPackage)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	def static CommitPackage getPackage() {
		return CommitPackage::eINSTANCE
	} // CommitFactoryImpl
}
