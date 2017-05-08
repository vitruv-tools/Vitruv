/**
 */
package tools.vitruv.framework.versioning.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
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
 *   <li>{@link tools.vitruv.framework.versioning.Root#createRepository() <em>Create Repository</em>}</li>
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
	@SuppressWarnings("boxing")
	public void testCreateAuthor__String_String() {
		final Root root = getFixture();
		final String name= "TestName";
		final String email = "TestEmail";
		final Author author = root.createAuthor(name, email);
		assertThat(author.getCommits().size(), equalTo(0));
		assertThat(author.getContributedBranches().size(), equalTo(0));
		assertThat(author.getEmail(), equalTo(email));
		assertThat(author.getName(), equalTo(name));
		assertThat(root.getAuthors(), hasItem(author));
		Assert.assertNull(author.getCurrentRepository());
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.Root#createRepository() <em>Create Repository</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Root#createRepository()
	 */
	@SuppressWarnings("boxing")
	public void testCreateRepository() {
		final Root root = getFixture();
		final Repository repository = root.createRepository();
		assertThat(root.getRepositories(), hasItem(repository));
		Assert.assertNotNull(repository.getMaster());
		Assert.assertNotNull(repository.getInitialCommit());
		Assert.assertNotNull(repository.getMaster().getCurrentHeadCommit());
		assertThat(repository.getTags().size(), equalTo(0));
		assertThat(repository.getCommits().size(), equalTo(1));
		assertThat(repository.getCommits(), hasItem(repository.getInitialCommit()));
		assertThat(repository.getBranches().size(), equalTo(1));
		assertThat(repository.getBranches(), hasItem(repository.getMaster()));
		assertThat(repository.getMaster().getCurrentHeadCommit(), equalTo(repository.getInitialCommit()));
	}
} //RootTest
