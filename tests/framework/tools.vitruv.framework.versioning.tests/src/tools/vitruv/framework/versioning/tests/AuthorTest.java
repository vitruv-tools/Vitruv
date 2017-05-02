/**
 */
package tools.vitruv.framework.versioning.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import junit.textui.TestRunner;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.Repository;
import tools.vitruv.framework.versioning.VersioningFactory;
import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.UserBranch;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.InitialCommit;
import tools.vitruv.framework.versioning.commit.SimpleCommit;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.Author#createBranch(java.lang.String) <em>Create Branch</em>}</li>
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
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(VersioningFactory.eINSTANCE.createRepository().createAuthor("Name", "Email"));
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
	 * Tests the '{@link tools.vitruv.framework.versioning.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList)
	 */
	public void testCreateSimpleCommit__String_Commit_EList() {
		final Author author = getFixture();
		final String commitMessage = "Test message";
		final InitialCommit parent = author.getRepository().getInitialCommit();
		final EList<EChange> changes = new BasicEList<EChange>();
		final SimpleCommit commit = author.createSimpleCommit(commitMessage, parent, changes);
		testCommit(commit, commitMessage, 0);
		assertThat(commit.getParent(), equalTo(parent));
		assertThat(parent.getCommitsBranchedFromThis(), hasItem(commit));
	}


	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.Author#createBranch(java.lang.String) <em>Create Branch</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.Author#createBranch(java.lang.String)
	 */
	public void testCreateBranch__String() {
		final Author author = getFixture();
		final Repository repo = author.getRepository();
		final Branch masterBranch = author.getCurrentBranch();
		final String branchName = "branchName";
		final UserBranch branch = author.createBranch(branchName);
		assertThat(branch.getName(), equalTo(branchName));
		assertThat(branch.getOwner(), equalTo(author));
		assertThat(branch.getContributors(), hasItem(author));
		assertThat(branch.getBranchedFrom(), equalTo(masterBranch));
		assertThat(repo.getBranches(), hasItem(branch));
		assertThat(author.getContributedBranches(), hasItem(branch));
		assertThat(author.getOwnedBranches(), hasItem(branch));
		assertThat(masterBranch.getChildBranches(), hasItem(branch));
		assertThat(masterBranch.getChildBranches().size(), equalTo(1));
	}

	private void testCommit(final Commit commit, final String message, final int changesLength) {
		final Author author = getFixture();
		final Repository repo = author.getRepository();
		assertThat(commit.getChanges().size(), equalTo(changesLength));
		assertThat(commit.getCommitmessage().getAuthor(), equalTo(author));
		assertThat(commit.getCommitmessage().getMessage(), equalTo(message));
		assertThat(author.getCommits(), hasItem(commit));
		assertThat(repo.getCommits(), hasItem(commit));
	}
} //AuthorTest
