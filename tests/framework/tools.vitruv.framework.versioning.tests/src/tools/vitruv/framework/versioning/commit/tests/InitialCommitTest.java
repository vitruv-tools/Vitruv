package tools.vitruv.framework.versioning.commit.tests;


import junit.textui.TestRunner;
import tools.vitruv.framework.versioning.commit.CommitFactory;
import tools.vitruv.framework.versioning.commit.InitialCommit;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Initial Commit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InitialCommitTest extends CommitTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InitialCommitTest.class);
	}

	/**
	 * Constructs a new Initial Commit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialCommitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Initial Commit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InitialCommit getFixture() {
		return (InitialCommit)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CommitFactory.eINSTANCE.createInitialCommit());
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

} //InitialCommitTest