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
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.DependencyGraphCreator
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.VVWorkspaceProvider
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.versioning.extensions.GraphExtension
import tools.vitruv.framework.versioning.repository.impl.RepositoryImpl

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.repository.Repository

class EMFStoreBaseline extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = new BowlingDomainProvider().domain.fileExtensions.get(0);
	protected static extension ConflictDetector conflictDetector = ConflictDetector::instance
	protected static extension DependencyGraphCreator = DependencyGraphCreator::instance
	protected static extension GraphExtension = GraphExtension::instance
	protected SourceTargetRecorder stRecorder
	protected VURI newSourceVURI
	protected VURI sourceVURI
	static extension Logger = Logger::getLogger(EMFStoreBaseline)

	protected static def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	protected def VURI calculateVURI(String path) {
		VURI::getInstance('''«currentTestProjectFolder.name»/«path.projectModelPath»''')
	}

	protected final override createChangePropagationSpecifications() {
		#[]
	}

	protected override getVitruvDomains() {
		return #[new BowlingDomainProvider().domain]
	}

	override protected setup() {
	}

	override protected cleanup() {
	}

	override unresolveChanges() {
		true
	}

	@Test
	def void emfHelloWorldExample() {
		val Repository repo = new RepositoryImpl
		val author = Author::createAuthor("Author1")
		val demoProjectName = "DemoProject"
		val demoProjectCopyName = "DemoProjectCopy"
		sourceVURI = demoProjectName.calculateVURI
		newSourceVURI = demoProjectCopyName.calculateVURI
		val rootToRootMap = #{
			sourceVURI.EMFUri.toPlatformString(false) -> newSourceVURI.EMFUri.toPlatformString(false)
		}
		addMap(rootToRootMap)
		stRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		stRecorder.registerObserver
		val league = BowlingFactory::eINSTANCE.createLeague
		league.name = "Superbowling League"
		demoProjectName.projectModelPath.createAndSynchronizeModel(league)
		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[])

		val leagueCopy1 = BowlingFactory::eINSTANCE.createLeague
		leagueCopy1.name = "Superbowling League"
		demoProjectCopyName.projectModelPath.createAndSynchronizeModel(leagueCopy1)

		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"
		league.players += player1
		val player2 = BowlingFactory::eINSTANCE.createPlayer
		player2.name = "Ottgar"
		league.players += player2
		league.saveAndSynchronizeChanges

		val changeMathes = stRecorder.getChangeMatches(sourceVURI)
		val echanges = changeMathes.map[allEChanges].flatten.toList

		val commit = author.createSimpleCommit("My message", repo.initialCommit, echanges)
		repo.add(commit)
		assertThat(repo.head, is(commit))
		val pair = new Pair(sourceVURI.EMFUri.toString, newSourceVURI.EMFUri.toString)
		val copiedChanges = repo.getCopiedEChanges(newSourceVURI, pair)

		copiedChanges.forEach[virtualModel.propagateChange(it)]
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
		val league = demoProject.modelElements.get(0) as League
		val demoProjectCopy = workspace.localProjects.get(1)
		val leagueCopy = demoProjectCopy.modelElements.get(0) as League
		league.name = "Euro-League"
		val newPlayer = BowlingFactory.eINSTANCE.createPlayer
		newPlayer.name = "Eugene"
		league.players += newPlayer
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
