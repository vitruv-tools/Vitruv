/**
 */
package tools.vitruv.framework.versioning.branch.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import tools.vitruv.framework.versioning.branch.BranchDiff;
import tools.vitruv.framework.versioning.branch.BranchFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class BranchDiffTest extends TestCase {

	/**
	 * The fixture for this Diff test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchDiff fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(BranchDiffTest.class);
	}

	/**
	 * Constructs a new Diff test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchDiffTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Diff test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(BranchDiff fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Diff test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchDiff getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(BranchFactory.eINSTANCE.createBranchDiff());
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

} //BranchDiffTest
