package tools.vitruv.dsls.commonalities.ui.contentassist

import org.eclipse.xtext.resource.IEObjectDescription
import static tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter.*
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher
import com.google.inject.Inject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.naming.IQualifiedNameConverter
import com.google.inject.Singleton

class QualifiedMetaclassProposalFactory extends CommonailitiesLanguageProposalFactory {

	@Inject MetaclassPrefixMatcher metaclassPrefixMatcher

	override apply(IEObjectDescription description) {
		completionProposal(description.name).appendInfoText(description.name.firstSegment).appendInfoText(
			DOMAIN_METACLASS_SEPARATOR).appendText(description.name.getSegment(1)).withImageOf(
			description.EObjectOrProxy.eClass).usePrefixMatcher(metaclassPrefixMatcher).propose()
	}

}

@Singleton
package class MetaclassPrefixMatcher extends PrefixMatcher {
	@Inject
	private PrefixMatcher.IgnoreCase ignoreCase;

	@Inject IQualifiedNameConverter qualifiedNameConverter;

	override isCandidateMatchingPrefix(String name, String prefix) {
		val QualifiedName qName = qualifiedNameConverter.toQualifiedName(name)
		if (prefix.contains(DOMAIN_METACLASS_SEPARATOR)) {
			// we already have a domain!
			val prefixQ = qualifiedNameConverter.toQualifiedName(prefix)
			return qName.firstSegment == prefixQ.firstSegment &&
				ignoreCase.isCandidateMatchingPrefix(qName.segment(1), prefixQ.segment(1))
		}
		ignoreCase.isCandidateMatchingPrefix(qName.firstSegment, prefix) ||
			ignoreCase.isCandidateMatchingPrefix(qName.segment(1), prefix)
	}

	def private static segment(QualifiedName name, int index) {
		if (name.segmentCount > index) name.getSegment(index) else ""
	}
}
