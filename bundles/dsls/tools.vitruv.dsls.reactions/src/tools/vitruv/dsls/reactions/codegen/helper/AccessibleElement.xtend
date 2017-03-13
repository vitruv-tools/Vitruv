package tools.vitruv.dsls.reactions.codegen.helper

class AccessibleElement {
	private val String name;
	private val String fullyQualifiedTypeName;
	
	public new(String name, String fullyQualifiedTypeName) {
		this.name = name;
		this.fullyQualifiedTypeName = fullyQualifiedTypeName;
	}
	
	public new(String name, Class<?> type) {
		this.name = name;
		this.fullyQualifiedTypeName = type.name;
	}
	
	public def String getName() {
		return name;
	}
	
	public def String getFullyQualifiedType() {
		return fullyQualifiedTypeName;
	}
}