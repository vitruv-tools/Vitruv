/**
 */
package model.branch.tests;

import junit.textui.TestRunner;

import model.branch.Branch;
import model.branch.BranchFactory;

import model.tests.NamedTest;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class BranchTest extends NamedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(BranchTest.class);
	}

	/**
	 * Constructs a new Branch test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Branch test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Branch getFixture() {
		return (Branch)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(BranchFactory.eINSTANCE.createBranch());
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

} //BranchTest
