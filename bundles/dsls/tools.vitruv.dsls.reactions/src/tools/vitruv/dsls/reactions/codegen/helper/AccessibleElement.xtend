package tools.vitruv.dsls.reactions.codegen.helper

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder

class AccessibleElement {
	@Accessors(PUBLIC_GETTER)
	private val String name;
	private val String fullyQualifiedTypeName;
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
	
	public def generateTypeRef(@Extension JvmTypeReferenceBuilder typeReferenceBuilder) {
		typeRef(fullyQualifiedTypeName, typeParameters.map[typeRef])
	}
	
}