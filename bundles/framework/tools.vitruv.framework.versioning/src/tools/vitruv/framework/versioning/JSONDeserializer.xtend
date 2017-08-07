package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.impl.JSONDeserializerImpl
import com.google.gson.JsonObject

interface JSONDeserializer {
	JSONDeserializer instance = JSONDeserializerImpl::init

	def <T extends JSONSerializable> T createDeserialization(JsonObject jsonObject)
}
