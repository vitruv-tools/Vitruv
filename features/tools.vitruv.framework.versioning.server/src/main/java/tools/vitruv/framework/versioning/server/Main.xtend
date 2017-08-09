package tools.vitruv.framework.versioning.server

import java.io.IOException
import java.net.URI
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

/** 
 * Main class.
 */
class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/myapp/"

	/** 
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 */
	def static HttpServer startServer() {
		// create a resource config that scans for JAX-RS resources and providers
		// in tools.vitruv.framework.versioning.server package
		val ResourceConfig rc = new ResourceConfig().packages("tools.vitruv.framework.versioning.server")
		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory::createHttpServer(URI::create(BASE_URI), rc)
	}

	/** 
	 * Main method.
	 * @param args
	 * @throws IOException
	 */
	def static void main(String[] args) throws IOException {
		val HttpServer server = startServer
		System.out.println(String.format('''
			Jersey app started with WADL available at %sapplication.wadl
			Hit enter to stop it...
		''', BASE_URI))
		System.in.read
		server.shutdownNow
	}
}
