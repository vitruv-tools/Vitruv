package tools.vitruv.framework.versioning.extensions.impl

import com.google.gson.JsonParser
import java.util.List
import tools.vitruv.framework.versioning.JSONDeserializer
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.extensions.CommitSerializer
import org.apache.log4j.Logger
import org.apache.log4j.Level

class CommitSerializerImpl implements CommitSerializer {
	static extension Logger = Logger::getLogger(CommitSerializerImpl)
	static extension JSONDeserializer = JSONDeserializer::instance
	static extension JsonParser = new JsonParser

	static def init() {
		// TODO PS remove 
		level = Level::DEBUG
		return new CommitSerializerImpl

	}

	private new() {
	}

	override deserializeAll(String allCommitsString) {
		debug(allCommitsString)
		val jsonArray = parse(allCommitsString).asJsonArray
		return jsonArray.map[createDeserialization(asJsonObject)].toList
	}

	override serializeAll(List<Commit> commits) {
		val strings = commits.map[serialization]
		return '''
			«FOR s : strings BEFORE '[' SEPARATOR ', ' AFTER ']'»«s»«ENDFOR»
		'''
	}

}
