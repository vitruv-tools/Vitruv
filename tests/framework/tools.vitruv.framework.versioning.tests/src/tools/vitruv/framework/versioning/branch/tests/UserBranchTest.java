/**
 */
package tools.vitruv.framework.versioning.branch.tests;

import junit.textui.TestRunner;

import tools.vitruv.framework.versioning.branch.BranchFactory;
import tools.vitruv.framework.versioning.branch.UserBranch;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>User Branch</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class UserBranchTest extends BranchTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UserBranchTest.class);
	}

	/**
	 * Constructs a new User Branch test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserBranchTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this User Branch test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected UserBranch getFixture() {
		return (UserBranch)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(BranchFactory.eINSTANCE.createUserBranch());
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

} //UserBranchTest
