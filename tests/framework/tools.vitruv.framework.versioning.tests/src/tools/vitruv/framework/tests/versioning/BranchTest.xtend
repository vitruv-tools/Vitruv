package tools.vitruv.framework.tests.versioning

import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.versioning.Author
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl
import tools.vitruv.framework.versioning.impl.AuthorImpl

class BranchTest {
	@Test
	def public branchTest() {
		val authorA = new AuthorImpl("a", "AuthorA") as Author
		val authorB = new AuthorImpl("b", "AuthorB") as Author
		
		val initialCommit = new InitialCommitImpl(authorA) as InitialCommit
		Assert.assertEquals(initialCommit.changes.length, 0)
		
		
	}
}