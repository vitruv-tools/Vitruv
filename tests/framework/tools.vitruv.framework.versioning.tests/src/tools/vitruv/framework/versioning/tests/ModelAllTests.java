/**
 */
package tools.vitruv.framework.versioning.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import org.eclipse.emf.ecore.tests.EcoreTests;

import tools.vitruv.framework.change.echange.tests.EChangeTests;

import tools.vitruv.framework.versioning.branch.tests.BranchTests;

import tools.vitruv.framework.versioning.conflict.tests.ConflictTests;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>Model</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelAllTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ModelAllTests("Model Tests");
		suite.addTest(ConflictTests.suite());
		suite.addTest(BranchTests.suite());
		suite.addTest(EcoreTests.suite());
		suite.addTest(EChangeTests.suite());
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelAllTests(String name) {
		super(name);
	}

} //ModelAllTests
