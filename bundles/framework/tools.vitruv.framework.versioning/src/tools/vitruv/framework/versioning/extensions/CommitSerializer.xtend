package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.CommitSerializerImpl
import java.util.List
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import tools.vitruv.framework.versioning.common.commit.Commit

interface CommitSerializer {
	CommitSerializer instance = CommitSerializerImpl::init

	def Commit deserialize(String commitsString)

	def Commit deserialize(JsonObject jsonObject)

	def List<Commit> deserializeAll(String allCommitsString)

	def List<Commit> deserializeAll(JsonArray allCommitsArray)

	def String serializeAll(List<Commit> commits)
}
