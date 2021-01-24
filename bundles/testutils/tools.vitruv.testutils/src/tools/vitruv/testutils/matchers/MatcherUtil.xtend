package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection

@Utility
package class MatcherUtil {
	def package static a(String string) {
		switch (string.substring(0, 1).toLowerCase) {
			case 'a',
			case 'e',
			case 'i',
			case 'o': 'an '
			default: 'a '
		} + string
	}
	
	def package static plural(String string) {
		if (string.endsWith('s')) string + 'es'
		else string + 's'
	}

	def package static plural(Collection<?> reference, String string) {
		if (reference.size != 1) plural(string) else string
	}
	
	def static package <T> joinSemantic(StringBuilder builder, Collection<? extends T> collection, String word,
		(T)=>void mapper) {
		builder.joinSemantic(collection, word, ',', mapper)
	}

	def static package <T> joinSemantic(StringBuilder builder, Collection<? extends T> collection, String word,
		String separator, (T)=>void mapper) {
		for (var i = 0, var iterator = collection.iterator; iterator.hasNext; i += 1) {
			val element = iterator.next
			if (i > 0) {
				builder.append(separator).append(' ')
				if (i == collection.size - 2) {
					builder.append(word).append(' ')
				}
			}
			mapper.apply(element)
		}
		return builder
	}
	
	def static package <T> String joinSemantic(Collection<? extends T> collection, String word, String separator, 
		(StringBuilder, T)=>void mapper) {
		val result = new StringBuilder()
		result.joinSemantic(collection, word, separator)[mapper.apply(result, it)]
		result.toString()
	}
	
	def static package <T> String joinSemantic(Iterable<? extends T> iterable, String word, String separator, 
		(StringBuilder, T)=>void mapper) {
		iterable.toList.joinSemantic(word, separator, mapper)
	}
}
