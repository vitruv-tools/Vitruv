/**
 */
package tools.vitruv.framework.versioning.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import junit.framework.TestCase;

import junit.textui.TestRunner;
import tools.vitruv.framework.versioning.author.Author;
import tools.vitruv.framework.versioning.repository.Repository;
import tools.vitruv.framework.versioning.Root;
import tools.vitruv.framework.versioning.VersioningFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.Root#createAuthor(java.lang.String, java.lang.String) <em>Create Author</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RootTest extends TestCase {

	/**
	 * The fixture for this Root test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Root fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RootTest.class);
	}

	/**
	 * Constructs a new Root test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Root test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Root fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Root test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Root getFixture() {
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
		setFixture(VersioningFactory.eINSTANCE.createRoot());
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

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.Root#createAuthor(java.lang.String, java.lang.String) <em>Create Author</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Root#createAuthor(java.lang.String, java.lang.String)
	 */
	public void testCreateAuthor__String_String() {
		final Root root = getFixture();
		final String name= "TestName";
		final String email = "TestEmail";
		final Author author = root.createAuthor(name, email);
		assertThat(author.getCommits().size(), equalTo(0));
		assertThat(author.getContributedBranches().size(), equalTo(0));
		assertThat(author.getEmail(), equalTo(email));
		assertThat(author.getName(), equalTo(name));
		Assert.assertNull(author.getCurrentRepository());
	}
} //RootTest
