/**
 */
package tools.vitruv.framework.versioning.commit.tests;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import junit.textui.TestRunner;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.commit.CommitFactory;
import tools.vitruv.framework.versioning.commit.InitialCommit;
import tools.vitruv.framework.versioning.commit.SimpleCommit;
import tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl;
import tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl;
import tools.vitruv.framework.versioning.impl.AuthorImpl;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Simple Commit</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimpleCommitTest extends CommitTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SimpleCommitTest.class);
	}

	/**
	 * Constructs a new Simple Commit test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleCommitTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Simple Commit test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected SimpleCommit getFixture() {
		return (SimpleCommit)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CommitFactory.eINSTANCE.createSimpleCommit());
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

	public void testParentCommitAdding() {
		final Author author = new AuthorImpl("test", "name");
		final InitialCommit parentCommit = new InitialCommitImpl(author);
		assertThat(parentCommit.getCommitsBranchedFromThis().size(), equalTo(0));
		final EList<EChange> changes = new BasicEList<EChange>();
		final SimpleCommit sCommit = new SimpleCommitImpl(changes, "test", author, parentCommit);
		assertThat(parentCommit.getCommitsBranchedFromThis().size(), equalTo(1));
		assertThat(sCommit.getParent(), equalTo(parentCommit));
		
	}
	
} //SimpleCommitTest
