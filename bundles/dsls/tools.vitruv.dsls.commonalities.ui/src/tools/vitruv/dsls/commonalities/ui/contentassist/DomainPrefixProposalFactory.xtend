package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher

import static tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter.DOMAIN_METACLASS_SEPARATOR

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
