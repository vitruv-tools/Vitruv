package tools.vitruv.framework.tests.versioning

import org.junit.Test
import org.junit.Assert
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.hasItem
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.repository.RepositoryFactory

class RepositoryTest {
	@Test
	def public void repositoryCreationTest() {
		val repo = RepositoryFactory.eINSTANCE.createRepository
		Assert.assertNotNull(repo.master)
		Assert.assertNotNull(repo.initialCommit)
		assertThat(repo.tags.length, equalTo(0))
		assertThat(repo.commits.length, equalTo(1))
		assertThat(repo.commits, hasItem(repo.initialCommit))
		assertThat(repo.branches.length, equalTo(1))
		assertThat(repo.branches, hasItem(repo.master))
	}
	
	@Test
	def public void repositoryStandardTest() {

	}
}