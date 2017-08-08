package tools.vitruv.framework.versioning.server

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.mongodb.MongoClient
import com.mongodb.client.model.Sorts

import org.apache.log4j.Logger
import org.bson.Document

import static com.mongodb.client.model.Filters.and
import static com.mongodb.client.model.Filters.eq
import static com.mongodb.client.model.Projections.exclude
import static com.mongodb.client.model.Projections.excludeId
import static com.mongodb.client.model.Projections.fields
import javax.ws.rs.QueryParam
import tools.vitruv.framework.versioning.common.commit.impl.MergeCommitImpl
import tools.vitruv.framework.versioning.common.commit.impl.SimpleCommitImpl
import tools.vitruv.framework.versioning.common.commit.CommitFactory
import tools.vitruv.framework.versioning.common.commit.impl.CommitMessageImpl

@Path("/commit/{branchName}")
class CommitResource {
	static extension Logger = Logger::getLogger(CommitResource)
	static extension Gson = new GsonBuilder().create
	static extension JsonParser = new JsonParser

	static val mongoClient = new MongoClient
	static val database = mongoClient.getDatabase("vitruv_versioning")
	static val collection = database.getCollection("commits")

	@GET
	@Produces("application/json")
	def String getCommits(
		@PathParam("branchName") String branchName,
		@QueryParam("idsonly") String idsOnly
	) {
		val commits = collection.find(eq("branch", branchName)).projection(fields(excludeId, exclude("branch"))).
			fold("[", [ p, c |
				val currentString = if (idsOnly !== null) '''"«c.getString("identifier")»"''' else toJson(c)
				return p + currentString + ", "
			])
		val trimLast = commits.substring(0, commits.length - 2) + "]"
		return trimLast
	}

	@GET
	@Path("/{identifier}")
	@Produces("application/json")
	def String getSingleCommit(
		@PathParam("branchName") String branchName,
		@PathParam("identifier") String identifier
	) {
		val commit = collection.find(and(eq("branch", branchName), eq("identifier", identifier))).projection(
			fields(excludeId, exclude("branch"))).first
		return toJson(commit)
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	def String postCommit(@PathParam("branchName") String branchName, String commitString) {
		val jsonElement = parse(commitString)
		if (jsonElement.jsonObject) {
			val id = jsonElement.asJsonObject.get("identifier").asString
			if (null === id)
				return createErrorResponse('''«commitString» has no identifier.''')
			val alreadyCommit = collection.find(and(eq("branch", branchName), eq("identifier", id))).first
			if (null !== alreadyCommit)
				return createErrorResponse('''An commit with id «id» has already been commited''')
			val lastCommitId = collection.find(eq("branch", branchName)).sort(Sorts::descending("commitmessage.date")).
				map [
					getString("identifier")
				].first
			val b = fromJson(jsonElement, Document)
			val type = b.getString("type")
			if (type == SimpleCommitImpl.name) {
				val parent = b.getString("parent")
				if ((parent === null || parent == "") && id != CommitFactory::initialCommitHash)
					return createErrorResponse('''The commit has no parent and is not the initial commit''')
				if (parent !== null && parent != "" && parent != lastCommitId)
					return createErrorResponse('''There have been commits since the last push! Please pull before''')
			}
			if (type == MergeCommitImpl.name) {
				val sourceCommit = b.getString("sourceCommit")
				val targetCommit = b.getString("targetCommit")
				if (lastCommitId != sourceCommit && lastCommitId != targetCommit)
					return createErrorResponse('''There have been commits since the last push! Please pull before''')
			}
			val commitMessage = b.get("commitmessage")
			val commitMessageJson = commitMessage.toJson
			val newCommitMessage = fromJson(commitMessageJson, CommitMessageImpl)
			val newCommitMessageBson = new Document("date", newCommitMessage.date).append("message",
				newCommitMessage.message).append("authorName", newCommitMessage.authorName).append("authorName",
				newCommitMessage.authorName)
			b.append("branch", branchName)
			b.put("commitmessage", newCommitMessageBson)
			debug('''Add commit «commitString» to branch «branchName»''')
			collection.insertOne(b)
			return createResponse("success", "")
		}
		return createErrorResponse('''«commitString» was no valid argument. ''')
	}

	private static def String createErrorResponse(String message) {
		error(message)
		createResponse("error", message)
	}

	private static def String createResponse(String state, String message) '''
		{
			"message": "«message»",
			"state": "«state»"
		}
	'''
}
