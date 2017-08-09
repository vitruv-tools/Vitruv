package tools.vitruv.framework.versioning.server

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import org.glassfish.grizzly.http.server.HttpServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals

class MyResourceTest {
	HttpServer server
	WebTarget target

	@Before
	def void setUp() throws Exception {
		// start the server
		server = Main::startServer()
		// create the client
		val Client c = ClientBuilder::newClient
		target = c.target(Main::BASE_URI)
	}

	@After
	def void tearDown() throws Exception {
		server.shutdown
	}

	/** 
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	def void testGetIt() {
		val responseMsg = target.path("branch").request.get(String)
		assertEquals("Got it!", responseMsg)
	}
}
