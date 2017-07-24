package tools.vitruv.dsls.reactions.tests.versioning

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.emfstore.bowling.BowlingFactory
import org.eclipse.emf.emfstore.bowling.League
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.BowlingDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.VersioningXtendFactory
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.VVWorkspaceProvider
import tools.vitruv.framework.versioning.emfstore.impl.LocalRepositoryImpl
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.versioning.extensions.GraphExtension

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.extensions.VirtualModelExtension
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class EMFStoreBaseline extends VitruviusApplicationTest {
	protected static extension VirtualModelExtension = VirtualModelExtension::instance
	protected LocalRepository localRepository
	protected Author author
	protected static val MODEL_FILE_EXTENSION = new BowlingDomainProvider().domain.fileExtensions.get(0);
	protected VURI newSourceVURI
	protected VURI sourceVURI
	static extension Logger = Logger::getLogger(EMFStoreBaseline)

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
		author = Author::createAuthor("Author1")
		localRepository.author = author
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
		league.name = "Superbowling League"
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
		assertThat(commit.parent, is(localRepository.initialCommit))
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
		assertThat(commit.parent, is(localRepository.initialCommit))

		localRepository.checkout(virtualModel, newSourceVURI)
		val leagueCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(leagueCopy.name, equalTo(leagueName))
		assertThat(leagueCopy.players.get(0).name, equalTo("Maximilian"))
	}

	@Test
	def void emfHelloWorldExample() {
		val demoProjectName = "DemoProject"
		val demoProjectCopyName = "DemoProjectCopy"
		val league = BowlingFactory::eINSTANCE.createLeague
		val leagueCopy1 = BowlingFactory::eINSTANCE.createLeague
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		demoProjectCopyName.projectModelPath.createAndSynchronizeModel(leagueCopy1)

		league.name = "Superbowling League"

		sourceVURI = VURI::getInstance(league.eResource)
		newSourceVURI = VURI::getInstance(leagueCopy1.eResource)

		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league.players += player1
		val player2 = BowlingFactory::eINSTANCE.createPlayer
		player2.name = "Ottgar"
		league.players += player2

		league.saveAndSynchronizeChanges

		val changeMatches = virtualModel.getChangeMatches(sourceVURI)
		localRepository.commit("My message", changeMatches)
		localRepository.checkout(virtualModel, newSourceVURI)
		val leagueCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as League
		assertThat(league.name, equalTo(leagueCopy.name))
		assertThat(league.players.size, is(leagueCopy.players.size))
		league.players.forEach [ p1 |
			assertThat(leagueCopy.players.exists[p2|EcoreUtil::equals(p1, p2)], is(true))
		]
	}

	@Ignore
	@Test
	def void emfStoreMergingExample() {
		emfHelloWorldExample
		val workspace = VVWorkspaceProvider::instance.workspace
		val demoProject = workspace.localProjects.get(0)
		val lastChange = virtualModel.getChangeMatches()
		val league = virtualModel.getModelInstance(sourceVURI).firstRootEObject as League
		
		league.name = "Euro-League"
		val newPlayer = BowlingFactory.eINSTANCE.createPlayer
		newPlayer.name = "Eugene"
		league.players += newPlayer
		
		
		val demoProjectCopy = workspace.localProjects.get(1)
		val leagueCopy = demoProjectCopy.modelElements.get(0) as League

		demoProject.commit("Commit1")

		// Changing the name again value without calling update() on the copy first will cause a conflict on commit.
		// We also add one change which is non-conflicting, setting the name of the first player.
		leagueCopy.name = "EU-League"
		leagueCopy.players.get(0).name = "Johannes"

		try {
			demoProjectCopy.commit("Commit2")
		} catch (CommitNotExceptedException e) {
			debug(e)
			demoProject.update [ conflicts |
				val conflict = conflicts.get(0) as SimpleChangeConflict
				val EList<ChangeMatch> accepted = new BasicEList
				accepted += conflict.sourceChange
				val EList<ChangeMatch> rejected = new BasicEList
				rejected += conflict.targetChange
				conflict.resolveConflict(accepted, rejected)
				return true
			]
			demoProjectCopy.commit("Commit 3");

			// After having merged the two projects update local project 1
			demoProject.update();

			assertThat(league.name, equalTo(leagueCopy.name))
			assertThat(league.players.size, is(leagueCopy.players.size))

			System.out.println("\nLeague name in demoProject  is now: " + league.getName()); // $NON-NLS-1$
			System.out.println("\nLeague name in demoProjectCopy  is now: " + leagueCopy.getName()); // $NON-NLS-1$
			System.out.println("\nPlayer name in demoProject is now: " + league.getPlayers().get(0).getName()); // $NON-NLS-1$
			System.out.println("\nPlayer name in demoProjectCopy is now: " + leagueCopy.getPlayers().get(0).getName()); // $NON-NLS-1$
		}
	}

}
