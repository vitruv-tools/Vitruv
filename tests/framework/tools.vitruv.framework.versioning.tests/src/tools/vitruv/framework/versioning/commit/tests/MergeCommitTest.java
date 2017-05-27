/**
 */
package tools.vitruv.framework.versioning.commit.tests;

import junit.textui.TestRunner;

import tools.vitruv.framework.versioning.commit.CommitFactory;
import tools.vitruv.framework.versioning.commit.MergeCommit;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Merge Commit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MergeCommitTest extends CommitTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MergeCommitTest.class);
	}

	/**
	 * Constructs a new Merge Commit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeCommitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Merge Commit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected MergeCommit getFixture() {
		return (MergeCommit)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CommitFactory.eINSTANCE.createMergeCommit());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //MergeCommitTest
