/**
 */
package tools.vitruv.framework.versioning.commit.tests;

import junit.textui.TestRunner;

import tools.vitruv.framework.versioning.commit.CommitFactory;
import tools.vitruv.framework.versioning.commit.SimpleCommit;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Simple Commit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimpleCommitTest extends CommitTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SimpleCommitTest.class);
	}

	/**
	 * Constructs a new Simple Commit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleCommitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Simple Commit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected SimpleCommit getFixture() {
		return (SimpleCommit)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CommitFactory.eINSTANCE.createSimpleCommit());
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

} //SimpleCommitTest
