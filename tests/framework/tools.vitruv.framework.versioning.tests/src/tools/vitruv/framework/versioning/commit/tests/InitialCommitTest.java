/**
 */
package tools.vitruv.framework.versioning.commit.tests;

import junit.textui.TestRunner;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.commit.InitialCommit;
import tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl;
import tools.vitruv.framework.versioning.impl.AuthorImpl;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Initial Commit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InitialCommitTest extends CommitTest {
	
	private Author author;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InitialCommitTest.class);
	}

	/**
	 * Constructs a new Initial Commit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialCommitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Initial Commit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InitialCommit getFixture() {
		return (InitialCommit)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		author = new AuthorImpl("email", "name");
		setFixture(new InitialCommitImpl(author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		author = null;
		setFixture(null);
	}
	
	public void testSetup() {
		assertThat(getFixture().getChanges().size(), equalTo(0));
		assertThat(getFixture().getCommitmessage().getAuthor(), equalTo(author));
		assertThat(author.getCommits(), hasItem(getFixture()));
	}

} //InitialCommitTest
