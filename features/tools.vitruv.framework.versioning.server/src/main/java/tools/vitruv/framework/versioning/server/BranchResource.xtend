package tools.vitruv.framework.versioning.server

import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.Produces

@Path("/branch")
class BranchResource {
	@GET
	@Produces("application/json")
	def String getBranches() {
		
	}
}
