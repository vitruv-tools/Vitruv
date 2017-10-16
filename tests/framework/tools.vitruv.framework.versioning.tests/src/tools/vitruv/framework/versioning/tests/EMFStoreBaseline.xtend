package tools.vitruv.framework.versioning.tests

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.emfstore.bowling.BowlingFactory
import org.eclipse.emf.emfstore.bowling.League

import org.junit.Test

import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.MultiChangeConflict
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.versioning.mococo.InternalTestLocalRepository
import tools.vitruv.framework.versioning.mococo.PushState
import tools.vitruv.framework.versioning.mococo.RemoteRepository
import tools.vitruv.framework.versioning.mococo.impl.LocalRepositoryImpl
import tools.vitruv.framework.versioning.mococo.impl.RemoteRepositoryImpl
import tools.vitruv.framework.versioning.extensions.CommitSerializer
import tools.vitruv.framework.versioning.extensions.URIRemapper
import tools.vitruv.framework.vsum.InternalTestVersioningVirtualModel

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsEmptyCollection.empty

import static org.junit.Assert.assertThat

class EMFStoreBaseline extends VitruviusApplicationTest {
	static extension BowlingFactory = BowlingFactory::eINSTANCE
	static extension CommitSerializer = CommitSerializer::instance
	static extension Logger = Logger::getLogger(EMFStoreBaseline)
	static extension URIRemapper = URIRemapper::instance
	static val MODEL_FILE_EXTENSION = new BowlingDomainProvider().domain.fileExtensions.get(0)
	static val demoProjectCopyName = "DemoProjectCopy"
	static val demoProjectName = "DemoProject"
	static val leagueName = "Superbowling League"
	static val newName1 = "EU-League"
	static val newName2 = "Euro-League"
	static val acceptTheirChangesCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.targetChanges
		} else if (c instanceof SimpleChangeConflict) {
			return #[c.targetChange]
		} else
			#[]
	]
	static val triggeredCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.triggeredTargetChanges
		} else
			#[]
	]

	Author author1
	Author author2
	League league1
	InternalTestLocalRepository<RemoteRepository> localRepository
	InternalTestLocalRepository<RemoteRepository> newLocalRepository
	RemoteRepository remoteRepository
	VURI newSourceVURI
	VURI sourceVURI
	InternalTestVersioningVirtualModel newVirtualModel

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

	override setup() {
		localRepository = new LocalRepositoryImpl
		newLocalRepository = new LocalRepositoryImpl
		remoteRepository = new RemoteRepositoryImpl

		localRepository.addRemoteRepository(remoteRepository)
		newLocalRepository.addRemoteRepository(remoteRepository)

		author1 = Author::createAuthor("Author1")
		author2 = Author::createAuthor("Author2")
		localRepository.author = author1
		newLocalRepository.author = author2
		newVirtualModel = TestUtil::createVirtualModel("newVMname", true, vitruvDomains,
			createChangePropagationSpecifications, userInteractor) as InternalTestVersioningVirtualModel

		localRepository.virtualModel = virtualModel
		newLocalRepository.virtualModel = newVirtualModel
		val currentBranch1 = localRepository.currentBranch
		val currentBranch2 = newLocalRepository.currentBranch
		localRepository.addOrigin(currentBranch1, remoteRepository)
		newLocalRepository.addOrigin(currentBranch2, remoteRepository)
	}

	override cleanup() {
	}

	override unresolveChanges() {
		true
	}

	@Test
	def void commitTest() {
		val demoProjectName = "DemoProject"
		val league = createLeague
		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		val player1 = createPlayer
		player1.name = "Maximilian"
		league.players += player1
		league.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league.eResource)

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits, hasSize(1))
		val commit = localRepository.commit("My message")
		assertThat(localRepository.commits, hasSize(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes, not(empty))
	}

	@Test
	def void commitSerializeTest() {
		val demoProjectName = "DemoProject"
		val league = createLeague
		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		val player1 = createPlayer
		player1.name = "Maximilian"
		league.players += player1
		league.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league.eResource)

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits, hasSize(1))
		val message = "My message"
		val commit = localRepository.commit(message)
		assertThat(localRepository.commits, hasSize(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes, not(empty))
		val commits = localRepository.commits
		val serialized = serializeAll(commits)
		val deserialized = deserializeAll(serialized)
		assertThat(deserialized, hasSize(2))
		val firstCommit = deserialized.get(0) as SimpleCommit
		val secondCommit = deserialized.get(1) as SimpleCommit
		assertThat(firstCommit.changes, empty)
		assertThat(secondCommit.commitmessage.message, equalTo(message))
		assertThat(secondCommit.commitmessage.authorName, equalTo(author1.name))
		assertThat(secondCommit.changes, hasSize(2))
	}

	@Test
	def void twoCommitsTest() {
		val demoProjectName = "DemoProject"
		val league = createLeague
		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		val player1 = createPlayer
		player1.name = "Maximilian"
		league.players += player1
		league.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league.eResource)

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits, hasSize(1))
		val commit = localRepository.commit("My message", virtualModel, sourceVURI)
		assertThat(localRepository.commits, hasSize(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes, not(empty))

		league.name = "Champions League"
		league.saveAndSynchronizeChanges
		val secondCommit = localRepository.commit("My message2", virtualModel, sourceVURI)
		assertThat(localRepository.commits, hasSize(3))
		assertThat(localRepository.head, is(secondCommit))
		assertThat(secondCommit.parent, is(commit.identifier))
		assertThat(secondCommit.changes, not(empty))

	}

	@Test
	def void commitLastChangesTest() {
		val demoProjectName = "DemoProject"
		league1 = createLeague
		league1.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league1)
		val player1 = createPlayer
		player1.name = "Maximilian"
		league1.players += player1
		league1.saveAndSynchronizeChanges
		sourceVURI = VURI::getInstance(league1.eResource)
		assertThat(localRepository.currentBranch, not(equalTo(null)))
		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits, hasSize(1))
		val changeMatchesBeforeCommit = (virtualModel as InternalTestVersioningVirtualModel).
			getUnresolvedPropagatedChangesSinceLastCommit(sourceVURI)
		assertThat(changeMatchesBeforeCommit, hasSize(2))
		val commit = localRepository.commit("My message", virtualModel, sourceVURI)
		val changeMatchesAfterCommit = (virtualModel as InternalTestVersioningVirtualModel).
			getUnresolvedPropagatedChangesSinceLastCommit(sourceVURI)
		assertThat(changeMatchesAfterCommit, hasSize(0))
		assertThat(localRepository.commits, hasSize(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		assertThat(commit.changes, not(empty))
	}

	@Test
	def void commitAndCheckout() {
		val demoProjectName = "DemoProject"
		val demoProjectCopyName = "DemoProjectCopy"
		val league = createLeague
		val league2 = createLeague
		val leagueName = "Superbowling League"
		league.name = leagueName
		league2.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)

		val player1 = createPlayer
		player1.name = "Maximilian"
		league.players += player1
		sourceVURI = VURI::getInstance(league.eResource)
		newSourceVURI = createNewVURI(sourceVURI, demoProjectName -> demoProjectCopyName)
		league.saveAndSynchronizeChanges

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits, hasSize(1))
		val commit = localRepository.commit("My message")
		assertThat(localRepository.commits, hasSize(2))
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
		val league = createLeague
		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		sourceVURI = VURI::getInstance(league.eResource)
		for (i : 0 .. 100) {
			league.name = null
			league.saveAndSynchronizeChanges
			localRepository.commit('''Commit «i» a''', virtualModel, sourceVURI)
			league.name = leagueName
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
		val league = createLeague
		val leagueName = "Superbowling League"

		league.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)

		val player1 = createPlayer
		player1.name = "Maximilian"
		league.players += player1
		sourceVURI = VURI::getInstance(league.eResource)
		newSourceVURI = createNewVURI(sourceVURI, demoProjectName -> demoProjectCopyName)
		league.saveAndSynchronizeChanges

		assertThat(localRepository.head, is(localRepository.initialCommit))
		assertThat(localRepository.commits, hasSize(1))
		val commit = localRepository.commit("My message", virtualModel, sourceVURI)

		assertThat(localRepository.commits, hasSize(2))
		assertThat(localRepository.head, is(commit))
		assertThat(commit.parent, is(localRepository.initialCommit.identifier))
		val currentBranch = localRepository.currentBranch
		localRepository.addOrigin(currentBranch, remoteRepository)
		assertThat(remoteRepository.commits, hasSize(1))
		val pushCommit1 = localRepository.push
		assertThat(pushCommit1, is(PushState::SUCCESS))
		assertThat(remoteRepository.commits, hasSize(2))

		val newMasterBranch = newLocalRepository.currentBranch
		newLocalRepository.addOrigin(newMasterBranch, remoteRepository)
		assertThat(newLocalRepository.commits, hasSize(1))
		newLocalRepository.pull
		assertThat(newLocalRepository.commits, hasSize(2))

		newLocalRepository.checkout(virtualModel, newSourceVURI)
		val leagueCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(leagueCopy.name, equalTo(leagueName))
		assertThat(leagueCopy.players.get(0).name, equalTo("Maximilian"))
	}

	@Test
	def void emfHelloWorldExample() {

		league1 = createLeague
		league1.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league1)

		league1.name = leagueName

		sourceVURI = VURI::getInstance(league1.eResource)
		newSourceVURI = createNewVURI(sourceVURI, demoProjectName -> demoProjectCopyName)

		val player1 = createPlayer
		player1.name = "Maximilian"
		league1.players += player1
		val player2 = createPlayer
		player2.name = "Ottgar"
		league1.players += player2

		league1.saveAndSynchronizeChanges

		val commit = localRepository.commit("My message", virtualModel, sourceVURI)
		assertThat(commit.changes, hasSize(3))
		assertThat(localRepository.push, is(PushState::SUCCESS))

		newLocalRepository.pull
		newLocalRepository.checkout(newSourceVURI)

		val leagueCopy = newLocalRepository.virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(league1.name, equalTo(leagueCopy.name))
		assertThat(league1.players, hasSize(leagueCopy.players.size))
		league1.players.forEach [ p1 |
			assertThat(leagueCopy.players.exists[p2|EcoreUtil::equals(p1, p2)], is(true))
		]
	}

	@Test
	def void emfStoreMergingExample() {
		emfHelloWorldExample

		assertThat(localRepository.commits.length, is(newLocalRepository.commits.length))
		assertThat(localRepository.commits, hasSize(2))

		league1.name = newName2
		league1.saveAndSynchronizeChanges

		val myCommit = localRepository.commit("Commit1", virtualModel, sourceVURI)
		assertThat(myCommit.changes, hasSize(1))
		assertThat(localRepository.commits, hasSize(3))
		assertThat(localRepository.push, is(PushState::SUCCESS))

		newLocalRepository.checkout(newSourceVURI)
		val modelInstance = newLocalRepository.virtualModel.getModelInstance(newSourceVURI)

		val newLeague1 = modelInstance.resource.contents.get(0) as League
		modelInstance.resource.save(#{})
		assertThat(newLeague1.name, equalTo(leagueName))
		assertThat(newLeague1.players.get(0).name, equalTo("Maximilian"))

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(newLeague1.eResource.URI, true)
		val newLeague = sourceModel.contents.get(0) as League
		newLeague.startRecordingChanges
		newLeague.name = newName1
		newLeague.saveAndSynchronizeChanges
		val testVuri = VURI::getInstance(newLeague.eResource)
		assertThat(newLocalRepository.commits, hasSize(2))
		val newCommit = newLocalRepository.commit("Commit2", virtualModel, testVuri)
		assertThat(newLocalRepository.commits, hasSize(3))
		assertThat(newCommit.changes, hasSize(1))
		val commitAccepted = newLocalRepository.push
		assertThat(commitAccepted, is(PushState::COMMIT_NOT_ACCEPTED))

		val remoteBranch = newLocalRepository.currentBranch.remoteBranch
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(2))
		newLocalRepository.pull
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(3))
		val lastRemoteCommit = newLocalRepository.getCommits(remoteBranch).last
		val lastLocalCommit = newLocalRepository.getCommits(newLocalRepository.currentBranch).last
		assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))
		(virtualModel as InternalTestVersioningVirtualModel).addMappedVURIs(sourceVURI, newSourceVURI)
		val mergeCommit = newLocalRepository.merge(
			remoteBranch,
			newLocalRepository.currentBranch,
			acceptTheirChangesCallback,
			triggeredCallback,
			virtualModel
		)
		assertThat(mergeCommit.changes, hasSize(1))
		val testLeague2 = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		assertThat(testLeague2.name, equalTo(newName2))
		assertThat(remoteRepository.commits, hasSize(3))
		val newCommitAccepted = newLocalRepository.push
		assertThat(newCommitAccepted, is(PushState::SUCCESS))
		assertThat(remoteRepository.commits, hasSize(5))
	}
	
	@Test
	def void emfStoreChangeAndDeleteRootExample() {
		emfHelloWorldExample

		assertThat(localRepository.commits.length, is(newLocalRepository.commits.length))
		assertThat(localRepository.commits, hasSize(2))

		league1.name = newName1
		league1.saveAndSynchronizeChanges

		val myCommit = localRepository.commit("Commit1", virtualModel, sourceVURI)
		assertThat(myCommit.changes, hasSize(1))
		assertThat(localRepository.commits, hasSize(3))
		assertThat(localRepository.push, is(PushState::SUCCESS))

		newLocalRepository.checkout(newSourceVURI)
		val modelInstance = newLocalRepository.virtualModel.getModelInstance(newSourceVURI)

		val newLeague1 = modelInstance.resource.contents.get(0) as League
		modelInstance.resource.save(#{})
		assertThat(newLeague1.name, equalTo(leagueName))
		assertThat(newLeague1.players.get(0).name, equalTo("Maximilian"))

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(newLeague1.eResource.URI, true)
		val newLeague = sourceModel.contents.get(0) as League
		newLeague.startRecordingChanges
		sourceModel.contents.remove(0)
		newLeague.saveAndSynchronizeChanges
		val testVuri = VURI::getInstance(newLeague.eResource)
		assertThat(newLocalRepository.commits, hasSize(2))
		val newCommit = newLocalRepository.commit("Commit2", virtualModel, testVuri)
		assertThat(newLocalRepository.commits, hasSize(3))
		assertThat(newCommit.changes, hasSize(1))
		val commitAccepted = newLocalRepository.push
		assertThat(commitAccepted, is(PushState::COMMIT_NOT_ACCEPTED))

		val remoteBranch = newLocalRepository.currentBranch.remoteBranch
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(2))
		newLocalRepository.pull
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(3))
		val lastRemoteCommit = newLocalRepository.getCommits(remoteBranch).last
		val lastLocalCommit = newLocalRepository.getCommits(newLocalRepository.currentBranch).last
		assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))
		(virtualModel as InternalTestVersioningVirtualModel).addMappedVURIs(sourceVURI, newSourceVURI)
		val mergeCommit = newLocalRepository.merge(
			remoteBranch,
			newLocalRepository.currentBranch,
			acceptTheirChangesCallback,
			triggeredCallback,
			virtualModel
		)
		assertThat(mergeCommit.changes, hasSize(1))
		val testLeague2 = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		assertThat(testLeague2.name, equalTo(newName2))
		assertThat(remoteRepository.commits, hasSize(3))
		val newCommitAccepted = newLocalRepository.push
		assertThat(newCommitAccepted, is(PushState::SUCCESS))
		assertThat(remoteRepository.commits, hasSize(5))
	}
	@Test
	def void emfStoreChangeAndDeleteExample() {
		emfHelloWorldExample

		assertThat(localRepository.commits.length, is(newLocalRepository.commits.length))
		assertThat(localRepository.commits, hasSize(2))

		league1.players.get(1).name = "Hans"
		league1.saveAndSynchronizeChanges

		val myCommit = localRepository.commit("Commit1", virtualModel, sourceVURI)
		assertThat(myCommit.changes, hasSize(1))
		assertThat(localRepository.commits, hasSize(3))
		assertThat(localRepository.push, is(PushState::SUCCESS))

		newLocalRepository.checkout(newSourceVURI)
		val modelInstance = newLocalRepository.virtualModel.getModelInstance(newSourceVURI)

		val newLeague1 = modelInstance.resource.contents.get(0) as League
		modelInstance.resource.save(#{})
		assertThat(newLeague1.name, equalTo(leagueName))
		assertThat(newLeague1.players.get(0).name, equalTo("Maximilian"))

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(newLeague1.eResource.URI, true)
		val newLeague = sourceModel.contents.get(0) as League
		newLeague.startRecordingChanges
		newLeague.players.remove(1)
		newLeague.saveAndSynchronizeChanges
		val testVuri = VURI::getInstance(newLeague.eResource)
		assertThat(newLocalRepository.commits, hasSize(2))
		val newCommit = newLocalRepository.commit("Commit2", virtualModel, testVuri)
		assertThat(newLocalRepository.commits, hasSize(3))
		assertThat(newCommit.changes, hasSize(1))
		val commitAccepted = newLocalRepository.push
		assertThat(commitAccepted, is(PushState::COMMIT_NOT_ACCEPTED))

		val remoteBranch = newLocalRepository.currentBranch.remoteBranch
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(2))
		newLocalRepository.pull
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(3))
		val lastRemoteCommit = newLocalRepository.getCommits(remoteBranch).last
		val lastLocalCommit = newLocalRepository.getCommits(newLocalRepository.currentBranch).last
		assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))
		(virtualModel as InternalTestVersioningVirtualModel).addMappedVURIs(sourceVURI, newSourceVURI)
		val mergeCommit = newLocalRepository.merge(
			remoteBranch,
			newLocalRepository.currentBranch,
			acceptTheirChangesCallback,
			triggeredCallback,
			virtualModel
		)
		assertThat(mergeCommit.changes, hasSize(1))
		val testLeague2 = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		assertThat(testLeague2.name, equalTo(newName2))
		assertThat(remoteRepository.commits, hasSize(3))
		val newCommitAccepted = newLocalRepository.push
		assertThat(newCommitAccepted, is(PushState::SUCCESS))
		assertThat(remoteRepository.commits, hasSize(5))
	}

	@Test
	def void emfHelloWorldExample2VirtualModels() {

		league1 = createLeague
		league1.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league1)

		league1.name = leagueName

		sourceVURI = VURI::getInstance(league1.eResource)
		newSourceVURI = createNewVURI(sourceVURI, demoProjectName -> demoProjectCopyName)

		val player1 = createPlayer
		player1.name = "Maximilian"
		league1.players += player1
		val player2 = createPlayer
		player2.name = "Ottgar"
		league1.players += player2

		league1.saveAndSynchronizeChanges

		val commit = localRepository.commit("My message", virtualModel)
		assertThat(commit.changes, hasSize(3))
		localRepository.checkout(newVirtualModel)
		val leagueCopy = newVirtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		assertThat(league1.name, equalTo(leagueCopy.name))
		assertThat(league1.players.size, is(leagueCopy.players.size))
		league1.players.forEach [ p1 |
			assertThat(leagueCopy.players.exists[p2|EcoreUtil::equals(p1, p2)], is(true))
		]
	}

	@Test
	def void emfHelloWorldExampleWithoutVMpassing() {

		league1 = createLeague
		league1.name = leagueName
		demoProjectName.projectModelPath.createAndSynchronizeModel(league1)

		league1.name = leagueName

		sourceVURI = VURI::getInstance(league1.eResource)
		newSourceVURI = createNewVURI(sourceVURI, demoProjectName -> demoProjectCopyName)

		val player1 = createPlayer
		player1.name = "Maximilian"
		league1.players += player1
		val player2 = createPlayer
		player2.name = "Ottgar"
		league1.players += player2

		league1.saveAndSynchronizeChanges

		val commit = localRepository.commit("My message")
		assertThat(commit.changes, hasSize(3))
		val pushState = localRepository.push
		assertThat(pushState, is(PushState::SUCCESS))
		newLocalRepository.pull
		assertThat(newLocalRepository.commits, hasSize(2))
		newLocalRepository.checkout

		val leagueCopy = newVirtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		assertThat(league1.name, equalTo(leagueCopy.name))
		assertThat(league1.players.size, is(leagueCopy.players.size))
		league1.players.forEach [ p1 |
			assertThat(leagueCopy.players.exists[p2|EcoreUtil::equals(p1, p2)], is(true))
		]

		assertThat(localRepository.commits.length, is(newLocalRepository.commits.length))
		assertThat(localRepository.commits, hasSize(2))
	}

	@Test
	def void emfStoreMergingWithoutVMPassing() {
		emfHelloWorldExampleWithoutVMpassing

		league1.name = newName2
		league1.saveAndSynchronizeChanges

		val myCommit = localRepository.commit("Commit1")
		assertThat(myCommit.changes, hasSize(1))
		assertThat(localRepository.commits, hasSize(3))
		localRepository.push

		newLocalRepository.checkout
		val modelInstance = newVirtualModel.getModelInstance(sourceVURI)

		val newLeague1 = modelInstance.resource.contents.get(0) as League
		modelInstance.resource.save(#{})
		assertThat(newLeague1.name, equalTo(leagueName))
		assertThat(newLeague1.players.get(0).name, equalTo("Maximilian"))

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(newLeague1.eResource.URI, true)
		val newLeague = sourceModel.contents.get(0) as League
		newLeague.startRecordingChanges
		newLeague.name = newName1

		saveAndSynchronizeChanges(newVirtualModel, newLeague)
		assertThat(newLocalRepository.commits, hasSize(2))
		val newCommit = newLocalRepository.commit("Commit2")
		assertThat(newLocalRepository.commits, hasSize(3))
		assertThat(newCommit.changes, hasSize(1))
		val commitAccepted = newLocalRepository.push
		assertThat(commitAccepted, is(PushState::COMMIT_NOT_ACCEPTED))
		val remoteBranch = newLocalRepository.currentBranch.remoteBranch
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(2))
		newLocalRepository.pull
		assertThat(newLocalRepository.getCommits(remoteBranch), hasSize(3))
		val lastRemoteCommit = newLocalRepository.getCommits(remoteBranch).last
		val lastLocalCommit = newLocalRepository.getCommits(newLocalRepository.currentBranch).last
		assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))
		val mergeCommit = newLocalRepository.merge(
			remoteBranch,
			newLocalRepository.currentBranch,
			acceptTheirChangesCallback,
			triggeredCallback
		)
		assertThat(mergeCommit.changes, hasSize(1))
		val testLeague2 = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		assertThat(testLeague2.name, equalTo(newName2))
		assertThat(remoteRepository.commits, hasSize(3))
		val newCommitAccepted = newLocalRepository.push
		assertThat(newCommitAccepted, is(PushState::SUCCESS))
		assertThat(remoteRepository.commits, hasSize(5))
	}
	
}
