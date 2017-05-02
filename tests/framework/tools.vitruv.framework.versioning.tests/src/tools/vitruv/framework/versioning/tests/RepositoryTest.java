/**
 */
package tools.vitruv.framework.versioning.tests;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import junit.textui.TestRunner;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.Repository;
import tools.vitruv.framework.versioning.VersioningFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.Repository#createAuthor(java.lang.String, java.lang.String) <em>Create Author</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class RepositoryTest extends TestCase {

	/**
	 * The fixture for this Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Repository fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RepositoryTest.class);
	}

	/**
	 * Constructs a new Repository test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Repository fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Repository getFixture() {
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
		setFixture(VersioningFactory.eINSTANCE.createRepository());
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
	 * Tests the '{@link tools.vitruv.framework.versioning.Repository#createAuthor(java.lang.String, java.lang.String) <em>Create Author</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Repository#createAuthor(java.lang.String, java.lang.String)
	 */
	public void testCreateAuthor__String_String() {
		final Repository repository = getFixture();
		final String name= "TestName";
		final String email = "TestEmail";
		final Author author = repository.createAuthor(name, email);
		assertThat(author.getCommits().size(), equalTo(0));
		assertThat(author.getContributedBranches().size(), equalTo(0));
		assertThat(author.getEmail(), equalTo(email));
		assertThat(author.getName(), equalTo(name));
		assertThat(author.getRepository(), equalTo(repository));
	}

} //RepositoryTest
