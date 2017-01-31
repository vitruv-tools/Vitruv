package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import java.util.List
import org.eclipse.xtend2.lib.StringConcatenationClient
import java.util.Map

public abstract class ChangeTypeRepresentation {
	
	protected static def boolean isPrimitiveType(Class<?> type) {
		return primitveTypeNamesMap.containsKey(type.name);
	}

	protected static def String getPrimitiveType(Class<?> type) {
		return primitveTypeNamesMap.get(type.name);
	}
	
	private static Map<String, String> primitveTypeNamesMap = #{
		"short" -> Short.name,
		"int" -> Integer.name,
		"long" -> Long.name,
		"double" -> Double.name,
		"float" -> Float.name,
		"boolean" -> Boolean.name,
		"char" -> Character.name,
		"byte" -> Byte.name,
		"void" -> Void.name
	}

	public def Class<?> getChangeType();

	public def List<Class<?>> getGenericTypeParameters();

	public def StringConcatenationClient getUntypedChangeTypeRepresentation() {
		return '''«changeType»'''
	}

	/**
	 * Returns the relevant change type representation that is used by the consistency preservation.
	 * Is one of the atomic changes if dealing with a compound type representation 
	 */
	public def AtomicChangeTypeRepresentation getRelevantChangeTypeRepresentation();

	public def StringConcatenationClient getTypedChangeTypeRepresentation() {
		return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«
				IF param.isPrimitiveType»«primitveTypeNamesMap.get(param)»«ELSE»«param»«ENDIF»«ENDFOR»'''
	}

	public def StringConcatenationClient getRelevantChangeAssignmentCode(String originalChangeVariableName,
		boolean isOriginalVariableUntyped, String typedChangeVariableName);
}
