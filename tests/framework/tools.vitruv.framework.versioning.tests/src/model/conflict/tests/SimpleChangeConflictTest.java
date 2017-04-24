/**
 */
package model.conflict.tests;

import junit.textui.TestRunner;

import model.conflict.ConflictFactory;
import model.conflict.SimpleChangeConflict;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Simple Change Conflict</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimpleChangeConflictTest extends ConflictTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SimpleChangeConflictTest.class);
	}

	/**
	 * Constructs a new Simple Change Conflict test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleChangeConflictTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Simple Change Conflict test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected SimpleChangeConflict getFixture() {
		return (SimpleChangeConflict)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ConflictFactory.eINSTANCE.createSimpleChangeConflict());
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

} //SimpleChangeConflictTest
