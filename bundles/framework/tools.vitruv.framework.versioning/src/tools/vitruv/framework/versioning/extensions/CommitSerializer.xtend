package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.CommitSerializerImpl
import tools.vitruv.framework.versioning.commit.Commit
import java.util.List
import com.google.gson.JsonObject
import com.google.gson.JsonArray

interface CommitSerializer {
	CommitSerializer instance = CommitSerializerImpl::init

	def Commit deserialize(String commitsString)

	def Commit deserialize(JsonObject jsonObject)

	def List<Commit> deserializeAll(String allCommitsString)

	def List<Commit> deserializeAll(JsonArray allCommitsArray)

	def String serializeAll(List<Commit> commits)
}
