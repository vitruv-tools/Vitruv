package tools.vitruv.dsls.reactions.codegen.helper

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder

class AccessibleElement {
	@Accessors(PUBLIC_GETTER)
	val String name;
	val String fullyQualifiedTypeName;
	val List<String> typeParameters;
	
	new(String name, String fullyQualifiedTypeName) {
		this.name = name;
		this.fullyQualifiedTypeName = fullyQualifiedTypeName;
		this.typeParameters = newArrayList
	}
	
	new(String name, String fullyQualifiedTypeName, String... typeParameters) {
		this(name, fullyQualifiedTypeName);
		this.typeParameters += typeParameters;
	}
	
	new(String name, Class<?> type) {
		this(name, type.name)
	}
	
	def generateTypeRef(@Extension JvmTypeReferenceBuilder typeReferenceBuilder) {
		typeRef(fullyQualifiedTypeName, typeParameters.map[typeRef])
	}
	
}