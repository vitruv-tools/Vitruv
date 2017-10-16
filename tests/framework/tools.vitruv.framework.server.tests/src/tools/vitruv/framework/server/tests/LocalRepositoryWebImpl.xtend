package tools.vitruv.framework.server.tests

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

import com.google.gson.JsonParser

import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.impl.RemoteBranchImpl
import tools.vitruv.framework.versioning.mococo.PushState
import tools.vitruv.framework.versioning.mococo.impl.AbstractLocalRepository
import tools.vitruv.framework.versioning.exceptions.LocalBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.RemoteBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.RepositoryNotFoundException
import tools.vitruv.framework.versioning.extensions.CommitSerializer

class LocalRepositoryWebImpl extends AbstractLocalRepository<String> {
	static extension CommitSerializer = CommitSerializer::instance
	static extension JsonParser = new JsonParser
	val Client client

	new() {
		super()
		client = ClientBuilder::newClient
	}

	static def void resetBranch(String branchName, String remoteURL) {
		val newClient = ClientBuilder::newClient
		val target = newClient.target(remoteURL).path('''commit/«branchName»/reset''')
		target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity::entity("", MediaType::APPLICATION_JSON_TYPE))
	}

	private static def Iterable<String> getIdentifiers(String remoRepo, String branchName) {
		val client = ClientBuilder::newClient
		val target = client.target(remoRepo).path('''commit/«branchName»''').queryParam("idsonly", "true")
		val response = target.request(MediaType::APPLICATION_JSON_TYPE).get(String)
		if (response === null || response == "")
			return #[]
		val jsonObject = parse(response)
		if (!jsonObject.jsonArray)
			return #[]
		val ids = jsonObject.asJsonArray.map[asString]
		return ids
	}

	override addOrigin(LocalBranch<String> branch, String remoteURL) {
		if (!localBranches.exists[it === branch])
			throw new LocalBranchNotFoundException
		if (!remoteRepositories.exists[it === remoteURL])
			throw new RepositoryNotFoundException
		val branchName = branch.name
		val target = client.target(remoteURL).path('''branch/«branchName»''')
		val response = target.request(MediaType::APPLICATION_JSON_TYPE).get(String)
		if (response === null || response == "")
			throw new RemoteBranchNotFoundException
		val jelement = parse(response)
		if (!jelement.jsonObject)
			throw new RemoteBranchNotFoundException
		val jsonObject = jelement.asJsonObject
		val remoteBranchName = jsonObject.get("name").asString
		val newBranch = createRemoteBranch(remoteBranchName, remoteURL)
		branch.remoteBranch = newBranch
	}

	override createRemoteBranch(String name, String remoteURL) {
		val remoteName = remoteURL
		val newBranchName = '''«remoteName»/«name»'''
		val newBranch = new RemoteBranchImpl(newBranchName)
		addCommit(initialCommit, newBranch)
		newBranch.remoteRepository = remoteURL
		remoteBranches += newBranch
		return newBranch
	}

	override push(LocalBranch<String> localBranch) {
		val branchName = localBranch.name
		val remoteBranch = localBranch.remoteBranch
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val remoteRepo = remoteBranch.remoteRepository
		if (null === remoteRepo)
			throw new RepositoryNotFoundException
		val ids = remoteRepo.getIdentifiers(branchName)
		val currentCommits = localBranch.commits
		if (ids.length > currentCommits.length)
			return PushState::COMMIT_NOT_ACCEPTED
		val x = 0 ..< ids.length
		val serverHasNewerCommits = x.map[currentCommits.get(it).identifier -> ids.get(it)].exists[key != value]
		if (serverHasNewerCommits)
			return PushState::COMMIT_NOT_ACCEPTED

		val commitsToPush = currentCommits.drop(ids.length)
		for (commit : commitsToPush) {
			val commitString = commit.serialization
			val newTarget = client.target(remoteRepo).path('''commit/«branchName»''')
			val newResponse = newTarget.request(MediaType::APPLICATION_JSON_TYPE).post(
				Entity::entity(commitString, MediaType::APPLICATION_JSON_TYPE), String)
			if (newResponse === null || newResponse == "")
				return PushState::COMMIT_NOT_ACCEPTED
			val newJsonObject = parse(newResponse)
			if (!newJsonObject.jsonObject)
				return PushState::COMMIT_NOT_ACCEPTED
			val state = newJsonObject.asJsonObject.get("state").asString
			if (state !== null && state == "success")
				addCommit(commit, remoteBranch)
			else
				return PushState::COMMIT_NOT_ACCEPTED
		}
		val localCommits = localBranch.commits.toList
		val remoteCommits = remoteBranch.commits.toList
		val localIds = localCommits.map[identifier]
		val remoteIds = remoteCommits.map[identifier]
		localIds.forEach [ localId, i |
			val remoteId = remoteIds.get(i)
			if (remoteId != localId)
				throw new IllegalStateException('''Id at «i» should be «localId» but was «remoteId»''')
		]
		return PushState::SUCCESS
	}

	override pull(LocalBranch<String> branch) {
		val branchName = branch.name
		val remoteBranch = branch.remoteBranch
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val remoteRepo = remoteBranch.remoteRepository
		if (null === remoteRepo)
			throw new RepositoryNotFoundException
		val toLocal = branch.commits.length === remoteBranch.commits.length
		val ids = remoteRepo.getIdentifiers(branch.name)
		val localIds = remoteBranch.commits.map[identifier]
		if (localIds.length > ids.length)
			throw new IllegalStateException
		localIds.forEach [ localId, i |
			val remoteId = ids.get(i)
			if (remoteId != localId)
				throw new IllegalStateException
		]
		val idsToPull = newArrayList
		ids.drop(localIds.length).forEach[idsToPull += it]
		for (id : idsToPull) {
			val newTarget = client.target(remoteRepo).path('''commit/«branchName»/«id»''')
			val newResponse = newTarget.request(MediaType::APPLICATION_JSON_TYPE).get(String)
			if (newResponse === null || newResponse == "")
				return;
			val newJsonObject = parse(newResponse)
			if (!newJsonObject.jsonObject)
				return;
			val commit = deserialize(newJsonObject.asJsonObject)
			addCommit(commit, remoteBranch)
			if (toLocal)
				addCommit(commit, branch)
		}
	}

	override void createBranchOnServer(String name, String remoteURL) {
		val target = client.target(remoteURL).path('''branch''')
		val response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity::entity(
			'''
				{
					"name": "«name»"
				}
			''',
			MediaType::APPLICATION_JSON_TYPE
		), String)
		val jelement = parse(response)
		if (jelement === null || !jelement.jsonObject)
			throw new IllegalStateException
		val state = jelement.asJsonObject.get("state")?.asString
		if (state === null)
			throw new IllegalStateException
	}
}
