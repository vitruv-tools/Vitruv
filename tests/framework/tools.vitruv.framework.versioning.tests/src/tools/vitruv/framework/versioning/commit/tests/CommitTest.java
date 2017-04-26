/**
 */
package tools.vitruv.framework.versioning.commit.tests;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.InitialCommit;
import tools.vitruv.framework.versioning.commit.SimpleCommit;
import tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl;
import tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl;
import tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl;
import tools.vitruv.framework.versioning.impl.AuthorImpl;
import tools.vitruv.framework.versioning.tests.SignedTest;

import static org.junit.Assert.assertThat;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

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
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.commit.Commit#addNextCommit(tools.vitruv.framework.versioning.commit.SimpleCommit) <em>Add Next Commit</em>}</li>
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

	/**
	 * Tests the '{@link tools.vitruv.framework.versioning.commit.Commit#addNextCommit(tools.vitruv.framework.versioning.commit.SimpleCommit) <em>Add Next Commit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruv.framework.versioning.commit.Commit#addNextCommit(tools.vitruv.framework.versioning.commit.SimpleCommit)
	 */
	public void testAddNextCommit__SimpleCommit() {
		final Author author = new AuthorImpl("test", "name");
		final InitialCommit parentCommit = new InitialCommitImpl(author);
		assertThat(parentCommit.getCommitsBranchedFromThis().size(), equalTo(0));
		final EList<EChange> changes = new BasicEList<EChange>();
		new SimpleCommitImpl(changes, new CommitMessageImpl("test", author), parentCommit);
		assertThat(parentCommit.getCommitsBranchedFromThis().size(), equalTo(1));
	}

} //CommitTest
