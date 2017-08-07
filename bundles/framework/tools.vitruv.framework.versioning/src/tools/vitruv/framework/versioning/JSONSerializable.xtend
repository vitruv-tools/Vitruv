package tools.vitruv.framework.versioning

interface JSONSerializable {
	static val TYPE_KEY = "type"

	def String getSerialization()

}
