/**
 */
package model.commit.impl;

import model.commit.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CommitFactoryImpl extends EFactoryImpl implements CommitFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CommitFactory init() {
		try {
			CommitFactory theCommitFactory = (CommitFactory)EPackage.Registry.INSTANCE.getEFactory(CommitPackage.eNS_URI);
			if (theCommitFactory != null) {
				return theCommitFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CommitFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommitFactoryImpl() {
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
			case CommitPackage.MERGE_COMMIT: return createMergeCommit();
			case CommitPackage.SIMPLE_COMMIT: return createSimpleCommit();
			case CommitPackage.COMMIT_MESSAGE: return createCommitMessage();
			case CommitPackage.INITIAL_COMMIT: return createInitialCommit();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeCommit createMergeCommit() {
		MergeCommitImpl mergeCommit = new MergeCommitImpl();
		return mergeCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleCommit createSimpleCommit() {
		SimpleCommitImpl simpleCommit = new SimpleCommitImpl();
		return simpleCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommitMessage createCommitMessage() {
		CommitMessageImpl commitMessage = new CommitMessageImpl();
		return commitMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialCommit createInitialCommit() {
		InitialCommitImpl initialCommit = new InitialCommitImpl();
		return initialCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommitPackage getCommitPackage() {
		return (CommitPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CommitPackage getPackage() {
		return CommitPackage.eINSTANCE;
	}

} //CommitFactoryImpl
