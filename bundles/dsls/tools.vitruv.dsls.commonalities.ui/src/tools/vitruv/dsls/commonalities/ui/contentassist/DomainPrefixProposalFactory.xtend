package tools.vitruv.dsls.commonalities.ui.contentassist

import org.eclipse.xtext.resource.IEObjectDescription
import static tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter.DOMAIN_METACLASS_SEPARATOR
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher
import com.google.inject.Inject

class DomainPrefixProposalFactory extends CommonalitiesLanguageProposalFactory {
	
	@Inject PrefixMatcher.IgnoreCase ignoreCase

	override apply(IEObjectDescription description) {
		completionProposal(description.name.firstSegment + DOMAIN_METACLASS_SEPARATOR + '(')
			.appendText(description.name.firstSegment)
			.withImageOf(description.EObjectOrProxy)
			.usePrefixMatcher(ignoreCase)
			.propose()
	}
}
