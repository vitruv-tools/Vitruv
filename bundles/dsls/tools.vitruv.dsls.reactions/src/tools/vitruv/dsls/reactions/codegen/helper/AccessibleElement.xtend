package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.xtext.common.types.JvmTypeReference

class AccessibleElement {
	private val String name;
	private val JvmTypeReference type;
	
	public new(String name, JvmTypeReference type) {
		this.name = name;
		this.type = type;
	}
	
	public def String getName() {
		return name;
	}
	
	public def JvmTypeReference getType() {
		return type;
	}
}