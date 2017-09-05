package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher

package abstract class LanguageElementPrefixMatcher extends PrefixMatcher {
	@Inject PrefixMatcher.IgnoreCase ignoreCase

	def protected static segment(QualifiedName name, int index) {
		if (name.segmentCount > index) name.getSegment(index) else ""
	}

	def protected matchesAnySegmentStartIgnoringCase(String prefix, QualifiedName qualifiedName,
		int... segmentIndices) {
		for (index : segmentIndices) {
			if (ignoreCase.isCandidateMatchingPrefix(qualifiedName.segment(index), prefix)) {
				return true
			}
		}
		return false
	}

	def protected matchesQualifiedNamePart(QualifiedName prefix, QualifiedName qualifiedNamePart,
		int... segmentIndices) {
		for (index : segmentIndices) {
			val perfixPart = prefix.getSegment(index)
			val namePart = prefix.getSegment(index)
			if (!perfixPart.equalsIgnoreCase(namePart) && namePart.length !== 0) {
				return false
			}
		}
		return true
	}

	def protected matchesStartIgnoringCase(QualifiedName prefix, QualifiedName name, int index) {
		ignoreCase.isCandidateMatchingPrefix(name.getSegment(index), prefix.getSegment(index))
	}
}
