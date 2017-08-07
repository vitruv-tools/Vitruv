package tools.vitruv.framework.versioning.impl

import java.util.List

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject

import tools.vitruv.framework.versioning.JSONDeserializer
import tools.vitruv.framework.versioning.JSONSerializable
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.impl.CommitImpl
import tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl
import tools.vitruv.framework.versioning.commit.impl.MergeCommitImpl
import tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl
import tools.vitruv.framework.versioning.extensions.EChangeSerializer

class JSONDeserializerImpl implements JSONDeserializer {
	static extension Gson = new GsonBuilder().create
	static extension EChangeSerializer = EChangeSerializer::instance

	static def JSONDeserializer init() {
		new JSONDeserializerImpl
	}

	private new() {
	}

	private static def List<String> getIdentifiers(JsonObject jsonObject, String key) {
		jsonObject.get(key).asJsonArray.map[asString].toList
	}

	private static def CommitMessage createCommitMessageDeserialization(JsonElement jsonObject) {
		val returnValue = fromJson(jsonObject, CommitMessageImpl)
		return returnValue
	}

	override <T extends JSONSerializable> createDeserialization(JsonObject jsonObject) {
		val type = jsonObject.get(JSONSerializable::TYPE_KEY).asString
		var T object = null
		if (type == SimpleCommitImpl.name || type == MergeCommitImpl.name) {
			val changesJSON = jsonObject.get(CommitImpl::CHANGES_KEY).asJsonArray
			val commitmessageJSON = jsonObject.get(CommitImpl::COMMITMESSAGE_KEY).asJsonObject
			val identifier = jsonObject.get(CommitImpl::IDENTIFIER_KEY).asString
			val numberOfChanges = jsonObject.get(CommitImpl::NUMBEROFCHANGES_KEY).asInt

			val changes = changesJSON.deserializeAll
			val commitMessage = createCommitMessageDeserialization(commitmessageJSON)
			if (type == SimpleCommitImpl.name) {
				val parent = jsonObject.get(SimpleCommitImpl::PARENT_KEY).asString
				object = new SimpleCommitImpl(
					changes,
					numberOfChanges,
					commitMessage,
					newArrayList,
					newArrayList,
					identifier,
					parent
				) as T
			} else {
				val sourceCommits = getIdentifiers(jsonObject, MergeCommitImpl::SOURCE_COMMIT_KEY)
				val targetCommits = getIdentifiers(jsonObject, MergeCommitImpl::TARGET_COMMIT_KEY)
				object = new MergeCommitImpl(
					changes,
					numberOfChanges,
					commitMessage,
					newArrayList,
					newArrayList,
					identifier,
					sourceCommits,
					targetCommits
				) as T
			}

		}
		return object
	}

}
