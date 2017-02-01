package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import java.util.List
import org.eclipse.xtend2.lib.StringConcatenationClient
import java.util.Map

/**
 * This class is responsible for representing the relevant change information for generating reactions
 * code, both for atomic (see {@link AtomicChangeTypeRepresentation}) and compound (see {@link CompoundChangeTypeRepresentation})
 * changes. The information for the changes are extracted by the {@link ChangeTypeRepresentationExtractor} from 
 * a {@link Trigger} of the reactions language.
 */
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
	 * Is one of the atomic changes if dealing with a compound type representation , or otherwise the change itself
	 */
	public def AtomicChangeTypeRepresentation getRelevantAtomicChangeTypeRepresentation();

	public def StringConcatenationClient getTypedChangeTypeRepresentation() {
		return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«
				IF param.isPrimitiveType»«primitveTypeNamesMap.get(param)»«ELSE»«param»«ENDIF»«ENDFOR»'''
	}

	/**
	 * Returns code for extracting the relevant atomic change, according to 
	 * {@link #getRelevantAtomicChangeTypeRepresentation() getRelevantAtomicChangeTypeRepresentation},
	 * from an {@link tools.vitruv.framework.change.echange.EChange EChange} given in the variable specified by <code>untypedChangeVariableName</code> 
	 * and assigning it to the variable with the name <code>typedChangeVariableName</code>.
	 */
	public def StringConcatenationClient getRelevantChangeAssignmentCode(String untypedChangeVariableName, String typedChangeVariableName);
}
