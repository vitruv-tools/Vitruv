package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.xtend2.lib.StringConcatenationClient

/**
 * This class is responsible for representing the relevant change information for generating reactions
 * code, both for atomic (see {@link AtomicChangeTypeRepresentation}) and compound (see {@link CompoundChangeTypeRepresentation})
 * changes. The information for the changes are extracted by the {@link ChangeTypeRepresentationExtractor} from 
 * a {@link Trigger} of the reactions language.
 */
public abstract class ChangeTypeRepresentation {
	
	protected static def mapToNonPrimitiveType(String potentiallyPrimitiveTypeCName) {
		return primitveToWrapperTypesMap.getOrDefault(potentiallyPrimitiveTypeCName, potentiallyPrimitiveTypeCName)
	}	

	static val primitveToWrapperTypesMap = newHashMap(#[
		short -> Short,
		int-> Integer,
		long -> Long,
		double -> Double,
		float -> Float,
		boolean -> Boolean,
		char -> Character,
		byte -> Byte,
		void -> Void
	].map [key.canonicalName -> value.canonicalName])

	public def Class<?> getChangeType();

	public def Iterable<String> getGenericTypeParameters()

	public def StringConcatenationClient getUntypedChangeTypeRepresentation() {
		return '''«changeType»'''
	}

	public def StringConcatenationClient getTypedChangeTypeRepresentation() {
		return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«param»«ENDFOR»'''
	}
	
	public def StringConcatenationClient getChangeTypeRepresentationWithWildcards() {
		return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»?«ENDFOR»'''
	}

}
