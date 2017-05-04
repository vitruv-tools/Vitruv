/**
 */
package tools.vitruv.framework.versioning.branch.tests;

import junit.textui.TestRunner;

import tools.vitruv.framework.versioning.branch.BranchFactory;
import tools.vitruv.framework.versioning.branch.MasterBranch;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Master Branch</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MasterBranchTest extends BranchTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MasterBranchTest.class);
	}

	/**
	 * Constructs a new Master Branch test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterBranchTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Master Branch test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected MasterBranch getFixture() {
		return (MasterBranch)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(BranchFactory.eINSTANCE.createMasterBranch());
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

} //MasterBranchTest
