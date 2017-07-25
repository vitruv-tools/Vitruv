package tools.vitruv.dsls.reactions.tests.versioning

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.emfstore.bowling.BowlingFactory
import org.eclipse.emf.emfstore.bowling.League
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.BowlingDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.MultiChangeConflict
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.emfstore.impl.LocalRepositoryImpl
import tools.vitruv.framework.versioning.emfstore.impl.RemoteRepositoryImpl
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.versioning.extensions.VirtualModelExtension

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

class EMFStoreBaseline extends VitruviusApplicationTest {
	Author author1
	Author author2
	League league1
	League league2
	LocalRepository localRepository
	LocalRepository newLocalRepository
	RemoteRepository remoteRepository
	VURI newSourceVURI
	VURI sourceVURI
	static extension Logger = Logger::getLogger(EMFStoreBaseline)
	static extension VirtualModelExtension = VirtualModelExtension::instance
	static val MODEL_FILE_EXTENSION = new BowlingDomainProvider().domain.fileExtensions.get(0);
	static val demoProjectCopyName = "DemoProjectCopy"
	static val demoProjectName = "DemoProject"
	static val leagueName = "Superbowling League"

	protected static def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	protected def VURI calculateVURI(String path) {
		VURI::getInstance('''«testName.methodName»/«path.projectModelPath»''')
	}

	protected final override createChangePropagationSpecifications() {
		#[]
	}

	protected override getVitruvDomains() {
		return #[new BowlingDomainProvider().domain]
	}

	override protected setup() {
		localRepository = new LocalRepositoryImpl
		newLocalRepository = new LocalRepositoryImpl
		remoteRepository = new RemoteRepositoryImpl

		localRepository.addRemoteRepository(remoteRepository)
		newLocalRepository.addRemoteRepository(remoteRepository)

		author1 = Author::createAuthor("Author1")
		author2 = Author::createAuthor("Author2")
		localRepository.author = author1
		newLocalRepository.author = author2
	}

	override protected cleanup() {
	}

	override unresolveChanges() {
		true
	}

	@Test
	def void commitTest() {
		val demoProjectName = "DemoProject"
		val league = BowlingFactory::eINSTANCE.createLeague
		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league.players += player1
		league.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league.eResource)

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits.length, is(1))
		val changeMatches = virtualModel.getChangeMatches(sourceVURI)
		val commit = localRepository.commit("My message", changeMatches)
		assertThat(localRepository.commits.length, is(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes.empty, is(false))
	}

	@Test
	def void twoCommitsTest() {
		val demoProjectName = "DemoProject"
		val league = BowlingFactory::eINSTANCE.createLeague
		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league.players += player1
		league.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league.eResource)

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits.length, is(1))
		val commit = localRepository.commit("My message", virtualModel, sourceVURI)
		assertThat(localRepository.commits.length, is(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes.empty, is(false))

		league.name = "Champions League"
		league.saveAndSynchronizeChanges
		val secondCommit = localRepository.commit("My message2", virtualModel, sourceVURI)
		assertThat(localRepository.commits.length, is(3))
		assertThat(localRepository.head, is(secondCommit))
		assertThat(secondCommit.parent, is(commit.identifier))
		assertThat(secondCommit.changes.empty, is(false))

	}

	@Test
	def void commitLastChangesTest() {
		val demoProjectName = "DemoProject"
		league1 = BowlingFactory::eINSTANCE.createLeague
		league1.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league1)
		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league1.players += player1
		league1.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league1.eResource)
		assertThat(localRepository.currentBranch, not(equalTo(null)))
		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits.length, is(1))
		val changeMatchesBeforeCommit = virtualModel.getUnresolvedPropagatedChangesSinceLastCommit(sourceVURI)
		assertThat(changeMatchesBeforeCommit.length, is(1))
		val commit = localRepository.commit("My message", virtualModel, sourceVURI)
		val changeMatchesAfterCommit = virtualModel.getUnresolvedPropagatedChangesSinceLastCommit(sourceVURI)
		assertThat(changeMatchesAfterCommit.length, is(0))
		assertThat(localRepository.commits.length, is(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes.empty, is(false))
	}

	@Test
	def void commitAndCheckout() {
		val demoProjectName = "DemoProject"
		val demoProjectCopyName = "DemoProjectCopy"
		val league = BowlingFactory::eINSTANCE.createLeague
		val league2 = BowlingFactory::eINSTANCE.createLeague
		val leagueName = "Superbowling League"

		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		demoProjectCopyName.projectModelPath.createAndSynchronizeModel(league2)

		league.name = leagueName
		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league.players += player1
		sourceVURI = VURI::getInstance(league.eResource)
		newSourceVURI = VURI::getInstance(league2.eResource)
		league.saveAndSynchronizeChanges
		val changeMatches = virtualModel.getChangeMatches(sourceVURI)

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits.length, is(1))
		val commit = localRepository.commit("My message", changeMatches)
		assertThat(localRepository.commits.length, is(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))

		localRepository.checkout(virtualModel, newSourceVURI)
		val leagueCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(leagueCopy.name, equalTo(leagueName))
		assertThat(leagueCopy.players.get(0).name, equalTo("Maximilian"))
	}

	@Test
	def void commitHashTest() {
		val demoProjectName = "DemoProject"
		val league = BowlingFactory::eINSTANCE.createLeague
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		val leagueName = "Superbowling League"
		sourceVURI = VURI::getInstance(league.eResource)
		for (i : 0 .. 100) {
			league.name = leagueName
			league.saveAndSynchronizeChanges
			localRepository.commit('''Commit «i» a''', virtualModel, sourceVURI)
			league.name = null
			league.saveAndSynchronizeChanges
			localRepository.commit('''Commit «i» b''', virtualModel, sourceVURI)
		}
		val commits = localRepository.commits
		level = Level::DEBUG
		commits.forEach [ commit |
			val otherCommits = commits.filter[it !== commit]
			otherCommits.forEach[assertThat(commit.identifier, not(equalTo(identifier)))]
		]

	}

	@Test
	def void commitAndPushAndPullTest() {
		val demoProjectName = "DemoProject"
		val demoProjectCopyName = "DemoProjectCopy"
		val league = BowlingFactory::eINSTANCE.createLeague
		val league2 = BowlingFactory::eINSTANCE.createLeague
		val leagueName = "Superbowling League"
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		demoProjectCopyName.projectModelPath.createAndSynchronizeModel(league2)

		league.name = leagueName
		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league.players += player1
		sourceVURI = VURI::getInstance(league.eResource)
		newSourceVURI = VURI::getInstance(league2.eResource)
		league.saveAndSynchronizeChanges

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits.length, is(1))
		val commit = localRepository.commit("My message", virtualModel, sourceVURI)

		assertThat(localRepository.commits.length, is(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		val currentBranch = localRepository.currentBranch
		localRepository.addOrigin(currentBranch, remoteRepository)
		assertThat(remoteRepository.commits.length, is(1))
		localRepository.push
		assertThat(remoteRepository.commits.length, is(2))

		val newMasterBranch = newLocalRepository.currentBranch
		newLocalRepository.addOrigin(newMasterBranch, remoteRepository)
		assertThat(newLocalRepository.commits.length, is(1))
		newLocalRepository.pull
		assertThat(newLocalRepository.commits.length, is(2))

		newLocalRepository.checkout(virtualModel, newSourceVURI)
		val leagueCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(leagueCopy.name, equalTo(leagueName))
		assertThat(leagueCopy.players.get(0).name, equalTo("Maximilian"))
	}

	@Test
	def void emfHelloWorldExample() {

		league1 = BowlingFactory::eINSTANCE.createLeague
		league2 = BowlingFactory::eINSTANCE.createLeague
		demoProjectName.projectModelPath.createAndSynchronizeModel(league1)
		demoProjectCopyName.projectModelPath.createAndSynchronizeModel(league2)

		league1.name = leagueName

		sourceVURI = VURI::getInstance(league1.eResource)
		newSourceVURI = VURI::getInstance(league2.eResource)

		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league1.players += player1
		val player2 = BowlingFactory::eINSTANCE.createPlayer
		player2.name = "Ottgar"
		league1.players += player2

		league1.saveAndSynchronizeChanges

		val commit = localRepository.commit("My message", virtualModel, sourceVURI)
		assertThat(commit.changes.length, is(3))
		localRepository.checkout(virtualModel, newSourceVURI)
		val leagueCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(league1.name, equalTo(leagueCopy.name))
		assertThat(league1.players.size, is(leagueCopy.players.size))
		league1.players.forEach [ p1 |
			assertThat(leagueCopy.players.exists[p2|EcoreUtil::equals(p1, p2)], is(true))
		]
	}

	@Test
	def void emfStoreMergingExample() {
		emfHelloWorldExample
		val currentBranch1 = localRepository.currentBranch
		val currentBranch2 = newLocalRepository.currentBranch
		localRepository.addOrigin(currentBranch1, remoteRepository)
		newLocalRepository.addOrigin(currentBranch2, remoteRepository)

		localRepository.push
		newLocalRepository.pull

		assertThat(localRepository.commits.length, is(newLocalRepository.commits.length))
		assertThat(localRepository.commits.length, is(2))

		league1.name = "Euro-League"
//		val newPlayer = BowlingFactory.eINSTANCE.createPlayer
//		newPlayer.name = "Eugene"
//		league1.players += newPlayer
		league1.saveAndSynchronizeChanges

		val myCommit = localRepository.commit("Commit1", virtualModel, sourceVURI)
		assertThat(myCommit.changes.length, is(1))
		assertThat(localRepository.commits.length, is(3))
		localRepository.push

		newLocalRepository.checkout(virtualModel, newSourceVURI)
		val modelInstance = virtualModel.getModelInstance(newSourceVURI)

		val newLeague1 = modelInstance.resource.contents.get(0) as League
		modelInstance.resource.save(#{})
		assertThat(newLeague1.name, equalTo(leagueName))
		assertThat(newLeague1.players.get(0).name, equalTo("Maximilian"))

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(newLeague1.eResource.URI, true)
		val newLeague = sourceModel.contents.get(0) as League
		newLeague.startRecordingChanges
		// Changing the name again value without calling update() on the copy first will cause a conflict on commit.
		// We also add one change which is non-conflicting, setting the name of the first player.
		newLeague.name = "EU-League"
//		newLeague.players.get(0).name = "Johannes"
		newLeague.saveAndSynchronizeChanges
		val testVuri = VURI::getInstance(newLeague.eResource)
		assertThat(newLocalRepository.commits.length, is(2))
		val newCommit = newLocalRepository.commit("Commit2", virtualModel, testVuri)
		assertThat(newLocalRepository.commits.length, is(3))
		assertThat(newCommit.changes.length, is(1))
		try {
			newLocalRepository.push
		} catch (CommitNotExceptedException e) {
			val remoteBranch = newLocalRepository.currentBranch.remoteBranch
			assertThat(newLocalRepository.getCommits(remoteBranch).length, is(2))
			newLocalRepository.pull
			assertThat(newLocalRepository.getCommits(remoteBranch).length, is(3))
			val lastRemoteCommit = newLocalRepository.getCommits(remoteBranch).last
			val lastLocalCommit = newLocalRepository.getCommits(newLocalRepository.currentBranch).last
			assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))
			val originalCallback = [ Conflict c |
				if (c instanceof MultiChangeConflict) {
					return c.targetChanges
				} else if (c instanceof SimpleChangeConflict) {
					return #[c.targetChange]
				} else
					#[]
			]
			val triggeredCallback = [ Conflict c |
				if (c instanceof MultiChangeConflict) {
					return c.triggeredTargetChanges
				} else
					#[]
			]
			val testLeague1 = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
			assertThat(testLeague1.name, equalTo("Euro-League"))
			val mergeCommit = newLocalRepository.merge(remoteBranch, newLocalRepository.currentBranch, originalCallback,
				triggeredCallback, virtualModel)
			assertThat(mergeCommit.changes.length, is(1))
			val testLeague2 = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
			assertThat(testLeague2.name, equalTo("EU-League"))
			assertThat(remoteRepository.commits.length, is(3))
			newLocalRepository.push
			assertThat(remoteRepository.commits.length, is(5))
		}
	}
}
