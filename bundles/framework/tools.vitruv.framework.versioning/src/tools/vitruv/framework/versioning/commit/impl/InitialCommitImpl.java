/**
 */
package tools.vitruv.framework.versioning.commit.impl;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.commit.CommitPackage;
import tools.vitruv.framework.versioning.commit.InitialCommit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initial Commit</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class InitialCommitImpl extends CommitImpl implements InitialCommit {
	private static final String INITIAL_COMMIT_MESSAGE = "Initial commit";
	/**
	 * @param changes
	 * @param commitmessage
	 */
	public InitialCommitImpl(final Author author) {
		super(new BasicEList<EChange>(), new CommitMessageImpl(INITIAL_COMMIT_MESSAGE, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitialCommitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CommitPackage.Literals.INITIAL_COMMIT;
	}

} //InitialCommitImpl
