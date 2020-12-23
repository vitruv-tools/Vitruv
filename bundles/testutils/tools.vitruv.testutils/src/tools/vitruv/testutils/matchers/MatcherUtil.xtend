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

	def package static plural(Collection<?> reference, String string) {
		if (reference.size != 1) string + 's' else string
	}

	def static package <T> joinSemantic(StringBuilder builder, Collection<T> collection, String word,
		(T)=>void mapper) {
		for (var i = 0, var iterator = collection.iterator; iterator.hasNext; i += 1) {
			val element = iterator.next
			if (i > 0) {
				builder.append(', ')
				if (i == collection.size - 2) {
					builder.append('or ')
				}
			}
			mapper.apply(element)
		}
		return builder
	}
}
