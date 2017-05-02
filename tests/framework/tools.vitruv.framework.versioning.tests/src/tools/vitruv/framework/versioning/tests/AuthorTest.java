/**
 */
package tools.vitruv.framework.versioning.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import junit.textui.TestRunner;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.VersioningFactory;
import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.commit.InitialCommit;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.Author#createInitialCommit() <em>Create Initial Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#createBranch(java.lang.String, tools.vitruv.framework.versioning.branch.Branch) <em>Create Branch</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class AuthorTest extends NamedTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AuthorTest.class);
	}

	/**
	 * Constructs a new Author test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthorTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Author test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Author getFixture() {
		return (Author)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(VersioningFactory.eINSTANCE.createAuthor());
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
	 * Tests the '{@link tools.vitruv.framework.versioning.Author#createInitialCommit() <em>Create Initial Commit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Author#createInitialCommit()
	 */
	public void testCreateInitialCommit() {
		final Author author = getFixture();
		final InitialCommit initialCommit = author.createInitialCommit();
		assertThat(initialCommit.getChanges().size(), equalTo(0));
		assertThat(initialCommit.getCommitmessage().getAuthor(), equalTo(author));
		assertThat(initialCommit.getCommitmessage().getMessage(), equalTo("Initial commit"));
		assertThat(author.getCommits(), hasItem(initialCommit));
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList)
	 */
	public void testCreateSimpleCommit__String_Commit_EList() {
		final Author author = getFixture();
		final String branchName = "branchName";
		final Branch branch = author.createBranch(branchName);
		assertThat(branch.getName(), equalTo(branchName));
		assertThat(branch.getOwner(), equalTo(author));
		assertThat(branch.getContributors(), hasItem(author));
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.Author#createBranch(java.lang.String, tools.vitruv.framework.versioning.branch.Branch) <em>Create Branch</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Author#createBranch(java.lang.String, tools.vitruv.framework.versioning.branch.Branch)
	 * @generated
	 */
	public void testCreateBranch__String_Branch() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		fail();
	}

} //AuthorTest
