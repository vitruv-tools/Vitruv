package tools.vitruv.framework.tests.versioning

import org.junit.Test
import tools.vitruv.framework.versioning.emfstore.VVWorkspaceProvider
import tools.vitruv.framework.versioning.emfstore.VVFactory
import org.eclipse.emf.emfstore.bowling.BowlingFactory
import org.eclipse.emf.emfstore.bowling.League

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class EMFStoreBaseline {
	@Test
	def void test() {
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

		val league = BowlingFactory.eINSTANCE.createLeague
		league.name = "Superbowling League"
		demoProject.modelElements += league
		val player1 = BowlingFactory.eINSTANCE.createPlayer
		player1.name = "Maximilian"

		val player2 = BowlingFactory.eINSTANCE.createPlayer
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
}
