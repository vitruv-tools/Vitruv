package tools.vitruv.dsls.commonalities.ui.contentassist

import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.jface.text.contentassist.ICompletionProposal
import java.util.function.Function
import org.eclipse.xtext.ui.editor.contentassist.AbstractContentProposalProvider
import org.eclipse.swt.graphics.Image
import com.google.inject.Inject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.jface.viewers.StyledString
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher

abstract class CommonailitiesLanguageProposalFactory implements Function<IEObjectDescription, ICompletionProposal>, com.google.common.base.Function<IEObjectDescription, ICompletionProposal> {

	@Inject IQualifiedNameConverter descriptionConverter
	@Inject ILabelProvider labelProvider
	protected ContentAssistContext contentAssistContext
	protected AbstractContentProposalProvider proposalProvider

	def setProposalProvider(AbstractContentProposalProvider proposalProvider) {
		this.proposalProvider = proposalProvider
	}

	def setContext(ContentAssistContext context) {
		this.contentAssistContext = context
	}

	def protected getContext() {
		return contentAssistContext
	}

	def protected completionProposal(QualifiedName completion) {
		completionProposal(descriptionConverter.toString(completion));
	}

	def protected completionProposal(String completion) {
		new CompletionProposalBuilder(this).forCompletion(completion)
	}

	protected static class CompletionProposalBuilder {
		val CommonailitiesLanguageProposalFactory factory
		String completion
		StyledString text = new StyledString()
		Image image
		ContentAssistContext context

		private new(CommonailitiesLanguageProposalFactory factory) {
			this.factory = factory
			this.context = factory.contentAssistContext
		}
		
		def protected forCompletion(String completion) {
			this.completion = completion
			this
		}
		
		def protected withImage(Image image) {
			this.image = image
			this
		} 
		
		def protected withImageOf(EClass clazz) {
			withImage(factory.labelProvider.getImage(clazz))
		}
		
		def protected appendText(String text) {
			this.text.append(text)
			this
		}
		
		def protected usePrefixMatcher(PrefixMatcher prefixMatcher) {
			context = (context.copy => [
				matcher = prefixMatcher
			]).toContext		
			this
		}
		
		def protected appendInfoText(String text) {
			this.text.append(text, StyledString.QUALIFIER_STYLER)
			this
		}
		
		def protected propose() {
			factory.proposalProvider.createCompletionProposal(completion, text, image, context)
		}
	}

}
