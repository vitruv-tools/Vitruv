package tools.vitruv.framework.versioning.common

interface JSONSerializable {
	static val TYPE_KEY = "type"

	def String getSerialization()

}
