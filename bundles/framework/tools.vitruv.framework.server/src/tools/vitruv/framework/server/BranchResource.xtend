package tools.vitruv.framework.server

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import org.bson.Document

import static com.mongodb.client.model.Filters.eq
import static com.mongodb.client.model.Projections.excludeId
import static com.mongodb.client.model.Projections.fields

@Path("/branch")
class BranchResource extends MongoResource {

	override protected getCollectionName() { "branches" }

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	def String sayPlainTextHello() {
		return "Hello";
	}

	@GET
	@Path("/{name}")
	@Produces("application/json")
	def String getSingleCommit(
		@PathParam("name") String name
	) {
		val commit = collection.find(eq("name", name)).projection(fields(excludeId)).first
		if (commit !== null)
			return toJson(commit)
		return ""
	}

	@GET
	@Produces("application/json")
	def String getBranches() {
		val commits = collection.find().projection(fields(excludeId)).fold("[", [ p, c |
			val currentString = toJson(c)
			return p + currentString + ", "
		])
		val trimLast = commits.substring(0, commits.length - 2) + "]"
		return trimLast
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	def String postBranch(String commitString) {
		val jsonElement = parse(commitString)
		if (jsonElement.jsonObject) {
			val name = jsonElement.asJsonObject.get("name").asString
			if (name === null)
				return createErrorResponse('''No name given''')
			val branch = collection.find(eq("name", name)).first
			if (null !== branch)
				return createErrorResponse('''There is already a branch with name '«name»''')
			val document = fromJson(jsonElement, Document)
			collection.insertOne(document)
			return createResponse("success", "")
		}
		return createErrorResponse('''«commitString» was no valid argument. ''')
	}

	private static def String createErrorResponse(String message) {
		createResponse("error", message)
	}

	private static def String createResponse(String state, String message) '''
		{
			"message": "«message»",
			"state": "«state»"
		}
	'''

}
