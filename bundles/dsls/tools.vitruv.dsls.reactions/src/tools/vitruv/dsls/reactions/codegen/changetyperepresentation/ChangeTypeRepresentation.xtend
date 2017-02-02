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
	
	protected static def Class<?> mapToNonPrimitiveType(Class<?> potentiallyPrimitiveType) {
		return 
			if (primitveToWrapperTypesMap.containsKey(potentiallyPrimitiveType)) 
				primitveToWrapperTypesMap.get(potentiallyPrimitiveType) 
			else 
				potentiallyPrimitiveType;
	}	

	private static Map<Class<?>, Class<?>> primitveToWrapperTypesMap = #{
		short -> Short,
		int-> Integer,
		long -> Long,
		double -> Double,
		float -> Float,
		boolean -> Boolean,
		char -> Character,
		byte -> Byte,
		void -> Void
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
		return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«param»«ENDFOR»'''
	}

	/**
	 * Returns code for extracting the relevant atomic change, according to 
	 * {@link #getRelevantAtomicChangeTypeRepresentation() getRelevantAtomicChangeTypeRepresentation},
	 * from an {@link tools.vitruv.framework.change.echange.EChange EChange} given in the variable specified by <code>untypedChangeVariableName</code> 
	 * and assigning it to the variable with the name <code>typedChangeVariableName</code>.
	 */
	public def StringConcatenationClient getRelevantChangeAssignmentCode(String untypedChangeVariableName, String typedChangeVariableName);
}
