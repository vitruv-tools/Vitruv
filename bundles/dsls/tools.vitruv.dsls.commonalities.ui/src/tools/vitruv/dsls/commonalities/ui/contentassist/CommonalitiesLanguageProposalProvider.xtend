package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext

/**
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#content-assist
 * on how to customize the content assistant.
 */
class CommonalitiesLanguageProposalProvider extends AbstractCommonalitiesLanguageProposalProvider {
	
	@Inject Provider<QualifiedMetaclassProposalFactory> qMetaclassProposalFactory
	@Inject Provider<UnqualifiedMetaclassProposalFactory> uMetaclassProposalFactory
	@Inject Provider<DomainPrefixProposalFactory> domainPrefixProposalFactory
	
	override getProposalFactory(String ruleName, ContentAssistContext contentAssistContext) {
		switch(ruleName) {
			case "QualifiedMetaclass":
				qMetaclassProposalFactory.init(contentAssistContext)
				
			case "UnqualifiedMetaclass":
				uMetaclassProposalFactory.init(contentAssistContext)
			
			case "DomainReference":
				domainPrefixProposalFactory.init(contentAssistContext)
			
			default:
				super.getProposalFactory(ruleName, contentAssistContext)
		}
	}
	
	def private <T extends CommonalitiesLanguageProposalFactory> init(Provider<T> factory, ContentAssistContext contentAssistContext) {
		factory.get() => [
			context = contentAssistContext
			proposalProvider = this
		]
	}
}
