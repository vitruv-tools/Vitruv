package tools.vitruv.dsls.reactions.codegen.helper

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

class AccessibleElement {
	@Accessors(PUBLIC_GETTER)
	private val String name;
	@Accessors(PUBLIC_GETTER)
	private val String fullyQualifiedTypeName;
	@Accessors(PUBLIC_GETTER)
	private val List<String> typeParameters;
	
	public new(String name, String fullyQualifiedTypeName) {
		this.name = name;
		this.fullyQualifiedTypeName = fullyQualifiedTypeName;
		this.typeParameters = newArrayList
	}
	
	public new(String name, String fullyQualifiedTypeName, String... typeParameters) {
		this(name, fullyQualifiedTypeName);
		this.typeParameters += typeParameters;
	}
	
	public new(String name, Class<?> type) {
		this(name, type.name)
	}
	
}