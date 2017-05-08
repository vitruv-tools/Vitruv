/**
 */
package tools.vitruv.framework.versioning.author.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.Assert;

import junit.textui.TestRunner;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.Root;
import tools.vitruv.framework.versioning.VersioningFactory;
import tools.vitruv.framework.versioning.author.Author;
import tools.vitruv.framework.versioning.branch.Branch;
import tools.vitruv.framework.versioning.branch.UserBranch;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.InitialCommit;
import tools.vitruv.framework.versioning.commit.SimpleCommit;
import tools.vitruv.framework.versioning.repository.Repository;
import tools.vitruv.framework.versioning.tests.NamedTest;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.author.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.Author#createBranch(java.lang.String) <em>Create Branch</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.Author#switchToBranch(tools.vitruv.framework.versioning.branch.Branch) <em>Switch To Branch</em>}</li>
 *   <li>{@link tools.vitruv.framework.versioning.author.Author#switchToRepository(tools.vitruv.framework.versioning.repository.Repository) <em>Switch To Repository</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class AuthorTest extends NamedTest {
	private Root root;
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
		this.root = VersioningFactory.eINSTANCE.createRoot();
		final Author author = this.root.createAuthor("Name", "email");
		final Repository repository = this.root.createRepository();
		author.switchToRepository(repository);
		setFixture(author);
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
	 * Tests the '{@link tools.vitruv.framework.versioning.author.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList) <em>Create Simple Commit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.Author#createSimpleCommit(java.lang.String, tools.vitruv.framework.versioning.commit.Commit, org.eclipse.emf.common.util.EList)
	 */
	public void testCreateSimpleCommit__String_Commit_EList() {
		final Author author = getFixture();
		final String commitMessage = "Test message";
		final InitialCommit parent = author.getCurrentRepository().getInitialCommit();
		final EList<EChange> changes = new BasicEList<>();
		final SimpleCommit commit = author.createSimpleCommit(commitMessage, parent, changes);
		testCommit(commit, commitMessage, 0);
		assertThat(commit.getParent(), equalTo(parent));
		assertThat(parent.getCommitsBranchedFromThis(), hasItem(commit));
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.author.Author#createBranch(java.lang.String) <em>Create Branch</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.Author#createBranch(java.lang.String)
	 */
	@SuppressWarnings("boxing")
	public void testCreateBranch__String() {
		final Author author = getFixture();
		final Repository repo = author.getCurrentRepository();
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
		assertThat(branch.getCurrentHeadCommit(), equalTo(masterBranch.getCurrentHeadCommit()));
		Assert.assertNotNull(branch.getCurrentHeadCommit());
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.author.Author#switchToBranch(tools.vitruv.framework.versioning.branch.Branch) <em>Switch To Branch</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.Author#switchToBranch(tools.vitruv.framework.versioning.branch.Branch)
	 */
	public void testSwitchToBranch__Branch() {
		final Author author = getFixture();
		final Branch branch = author.createBranch("Test");
		author.switchToBranch(branch);
		assertThat(author.getCurrentBranch(), equalTo(branch));
		assertThat(author.getContributedBranches(), hasItem(branch));
	}

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.author.Author#switchToRepository(tools.vitruv.framework.versioning.repository.Repository) <em>Switch To Repository</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.author.Author#switchToRepository(tools.vitruv.framework.versioning.repository.Repository)
	 */
	public void testSwitchToRepository__Repository() {
		final Author author = getFixture();
		final Repository repository = this.root.createRepository();
		author.switchToRepository(repository);
		assertThat(author.getCurrentRepository(), equalTo(repository));
		assertThat(author.getCurrentBranch(), equalTo(repository.getMaster()));
	}
	
	@SuppressWarnings("boxing")
	private void testCommit(final Commit commit, final String message, final int changesLength) {
		final Author author = getFixture();
		final Repository repo = author.getCurrentRepository();
		assertThat(commit.getChanges().size(), equalTo(changesLength));
		assertThat(commit.getCommitmessage().getAuthor(), equalTo(author));
		assertThat(commit.getCommitmessage().getMessage(), equalTo(message));
		assertThat(author.getCommits(), hasItem(commit));
		assertThat(repo.getCommits(), hasItem(commit));
	}

} //AuthorTest
