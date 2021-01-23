package tools.vitruv.dsls.reactions.codegen.helper

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.xtext.common.types.JvmUnknownTypeReference

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
		val typeParameterReferences = typeParameters.mapFixed [typeRef]
		// TODO this is a hack to make Xtext print parameterized types even if one of
		// the parameter classes are not currently on the classpath. While Xtext prints
		// a direct type reference to some unknown class normally, it does not print type
		// references with unknown type parameters. For example, ‘Known<Unknown>’ becomes 
		// ‘/* Known<Unknown> */ Object’ in the code.
		// This hack is bad because a) it misuses the API of `typeRef` and b) it 
		// leads to a lookup of the java code (e.g. Known<Unknown>), which must fail.
		// I (Joshua Gleitze) found no other way, but a better way would be highly 
		// appreciated!
		if (typeParameterReferences.exists [it instanceof JvmUnknownTypeReference]) {
			typeRef(toJavaCode())
		} else {
			typeRef(fullyQualifiedTypeName, typeParameterReferences)
		}
	}
	
	private def toJavaCode() {
		if (typeParameters.isEmpty) fullyQualifiedTypeName
		else fullyQualifiedTypeName + typeParameters.join('<', ', ', '>') [it]
	}
	
	override toString() {
		toJavaCode()
	}
}