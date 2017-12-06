package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription

import static tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter.*

class QualifiedMetaclassProposalFactory extends CommonalitiesLanguageProposalFactory {

	@Inject MetaclassPrefixMatcher metaclassPrefixMatcher

	override apply(IEObjectDescription description) {
		completionProposal(description.name)
			.appendText(description.name.firstSegment)
			.appendText(DOMAIN_METACLASS_SEPARATOR)
			.appendText(description.name.getSegment(1))
			.withImageOf(description.EObjectOrProxy)
			.usePrefixMatcher(metaclassPrefixMatcher)
			.propose()
	}

}

@Singleton
package class MetaclassPrefixMatcher extends LanguageElementPrefixMatcher {
	@Inject extension IQualifiedNameConverter qualifiedNameConverter

	override isCandidateMatchingPrefix(String name, String prefix) {
		isCandidateMatchingPrefix(name.toQualifiedName, prefix.toQualifiedName)
	}

	def package isCandidateMatchingPrefix(QualifiedName name, QualifiedName prefix) {
		if (prefix.segmentCount > 1) {
			return prefix.matchesQualifiedNamePart(name, 0) &&	prefix.matchesStartIgnoringCase(name, 1)
		}
		return prefix.getSegment(0).matchesAnySegmentStartIgnoringCase(name, 0, 1)
	}
}
