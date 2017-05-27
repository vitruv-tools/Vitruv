/**
 */
package tools.vitruv.framework.versioning.conflict.tests;

import junit.textui.TestRunner;

import tools.vitruv.framework.versioning.conflict.ConflictFactory;
import tools.vitruv.framework.versioning.conflict.MultiChangeConflict;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Multi Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MultiChangeConflictTest extends ConflictTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MultiChangeConflictTest.class);
	}

	/**
	 * Constructs a new Multi Change Conflict test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiChangeConflictTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Multi Change Conflict test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected MultiChangeConflict getFixture() {
		return (MultiChangeConflict)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ConflictFactory.eINSTANCE.createMultiChangeConflict());
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

} //MultiChangeConflictTest
