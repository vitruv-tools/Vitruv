package tools.vitruv.framework.tests.versioning

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.emfstore.bowling.BowlingFactory
import org.eclipse.emf.emfstore.bowling.League
import org.junit.Test
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.conflict.SimpleChangeConflict
import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVWorkspaceProvider
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class EMFStoreBaseline {
	static val logger = Logger::getLogger(EMFStoreBaseline)

	@Test
	def void emfHelloWorldExample() {
		val server = VVFactory::instance.createServer
		val workspace = VVWorkspaceProvider::instance.workspace
		workspace.addServer(server)
		workspace.servers.filter[it !== server].forEach [ existingServer |
			workspace.removeServer(existingServer)
		]
		val demoProject = workspace.createLocalProject("DemoProject")
		workspace.localProjects.filter[it !== demoProject].forEach [ existingLocalProject |
			existingLocalProject.delete
		]
		val remoteDemoProject = demoProject.shareProject(server)
		server.remoteProjects.filter[id != remoteDemoProject.id].forEach [ existingRemoteProject |
			existingRemoteProject.delete
		]
		val demoProjectCopy = demoProject.remoteProject.checkout("DemoProject Copy")

		val league = BowlingFactory::eINSTANCE.createLeague
		league.name = "Superbowling League"
		demoProject.modelElements += league
		val player1 = BowlingFactory::eINSTANCE.createPlayer
		player1.name = "Maximilian"

		val player2 = BowlingFactory::eINSTANCE.createPlayer
		player2.name = "Ottgar"
		league.players += #[player1, player2]
		demoProject.commit("My message")
		demoProjectCopy.update
		var leagueCopy = demoProject.modelElements.get(0) as League
		assertThat(league.name, equalTo(leagueCopy.name))
		assertThat(league.players.size, is(leagueCopy.players.size))

		leagueCopy = demoProjectCopy.getModelElement(demoProject.getModelElementId(league)) as League
		league.name = "Superbowling League"
		demoProject.commit("New message")
		demoProjectCopy.update

		assertThat(league.name, equalTo(leagueCopy.name))
		assertThat(league.players.size, is(leagueCopy.players.size))
	}

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
			logger.debug(e)
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
