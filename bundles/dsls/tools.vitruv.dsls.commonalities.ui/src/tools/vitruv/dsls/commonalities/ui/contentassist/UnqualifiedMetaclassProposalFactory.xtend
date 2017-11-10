package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher

import static tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter.*

class UnqualifiedMetaclassProposalFactory extends CommonalitiesLanguageProposalFactory {
	
	@Inject PrefixMatcher.IgnoreCase ignoreCase
	
	override apply(IEObjectDescription description) {
		completionProposal(description.name.getSegment(1))
			.appendInfoText(description.name.firstSegment)
			.appendInfoText(DOMAIN_METACLASS_SEPARATOR)
			.appendText(description.name.getSegment(1))
			.withImageOf(description.EObjectOrProxy)
			.usePrefixMatcher(ignoreCase)
			.propose()
	}
	
}