/**
 */
package tools.vitruv.framework.versioning.commit.tests;

import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.tests.SignedTest;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.commit.Commit#getChecksum() <em>Checksum</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public abstract class CommitTest extends SignedTest {

	/**
	 * Constructs a new Commit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Commit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Commit getFixture() {
		return (Commit)fixture;
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.commit.Commit#getChecksum() <em>Checksum</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.Commit#getChecksum()
	 */
	public void testGetChecksum() {
		assertThat(getFixture().getChecksum(), equalTo(1000L));
	}

} //CommitTest
