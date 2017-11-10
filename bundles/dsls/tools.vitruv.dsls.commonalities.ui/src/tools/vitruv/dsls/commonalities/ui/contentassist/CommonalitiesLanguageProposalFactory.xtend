package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.jface.viewers.StyledString
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.ui.editor.contentassist.AbstractContentProposalProvider
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher
import org.eclipse.xtext.xbase.lib.Functions.Function1

abstract class CommonalitiesLanguageProposalFactory implements Function<IEObjectDescription, ICompletionProposal>, Function1<IEObjectDescription, ICompletionProposal>, com.google.common.base.Function<IEObjectDescription, ICompletionProposal> {

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
	
	/**
	 * Needed because Xtend’s type inference cannot handle any case that’s not
	 * completely obvious.
	 */
	def Function1<IEObjectDescription, ICompletionProposal> fun() {
		return this
	}

	protected static class CompletionProposalBuilder {
		val extension CommonalitiesLanguageProposalFactory factory
		String completion
		StyledString text = new StyledString()
		Image image

		private new(CommonalitiesLanguageProposalFactory factory) {
			this.factory = factory
		}

		def protected forCompletion(String completion) {
			this.completion = completion
			this
		}

		def protected withImage(Image image) {
			this.image = image
			this
		}

		def protected withImageOf(EObject object) {
			withImage(factory.labelProvider.getImage(object))
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
