/*
 * generated by Xtext 2.10.0
 */
package tools.vitruv.dsls.reactions.ui.contentassist

import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import com.google.inject.Inject
import tools.vitruv.dsls.reactions.services.ReactionsLanguageGrammarAccess
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.xtext.Keyword
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementCompoundChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.Matcher
import tools.vitruv.dsls.reactions.reactionsLanguage.ActionStatement

/**
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#content-assist
 * on how to customize the content assistant.
 */
class ReactionsLanguageProposalProvider extends AbstractReactionsLanguageProposalProvider {

	@Inject
	ReactionsLanguageGrammarAccess grammarAccess;

	override completeKeyword(Keyword keyword, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		var matchedKeyword = false;
		matchedKeyword = keyword.matchInReactionToChangeIn(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchExecuteActionsIn(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchInsertedIn(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchRemovedFrom(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchReplacedAt(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchInsertedAsRoot(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchRemovedAsRoot(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchRetrieve(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchRequireAbsenceOf(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchCorrespondingTo(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchTaggedWith(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchAndInitialize(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchAddCorrespondenceBetween(context, acceptor) || matchedKeyword;
		matchedKeyword = keyword.matchRemoveCorrespondenceBetween(context, acceptor) || matchedKeyword;
		if(!matchedKeyword) super.completeKeyword(keyword, context, acceptor);
	}

	private def boolean matchInReactionToChangeIn(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "in reaction to changes in";
		val firstKeywordSegment = "in";
		val Iterable<Class<?>> relevantContexts = #[ReactionsSegment];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchExecuteActionsIn(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "execute actions in";
		val firstKeywordSegment = "execute";
		val Iterable<Class<?>> relevantContexts = #[ReactionsSegment];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchInsertedIn(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "inserted in";
		val firstKeywordSegment = "inserted";
		val Iterable<Class<?>> relevantContexts = #[ModelAttributeChange, ModelElementChange,
			ElementCompoundChangeType];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchRemovedFrom(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "removed from";
		val firstKeywordSegment = "removed";
		val Iterable<Class<?>> relevantContexts = #[ModelAttributeChange, ModelElementChange,
			ElementCompoundChangeType];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchReplacedAt(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "replaced at";
		val firstKeywordSegment = "replaced";
		val Iterable<Class<?>> relevantContexts = #[ModelAttributeChange, ModelElementChange,
			ElementCompoundChangeType];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchRemovedAsRoot(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "removed as root";
		val firstKeywordSegment = "removed";
		val Iterable<Class<?>> relevantContexts = #[ModelElementChange, ElementCompoundChangeType];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchInsertedAsRoot(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "inserted as root";
		val firstKeywordSegment = "inserted";
		val Iterable<Class<?>> relevantContexts = #[ModelElementChange, ElementCompoundChangeType];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchRetrieve(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeywords = #["retrieve", "retrieve optional", "retrieve asserted", "retrieve many"];
		val firstKeywordSegment = "retrieve";
		val Iterable<Class<?>> relevantContexts = #[Matcher];
		return realKeywords.forall[replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, it, relevantContexts)];
	}
	
	private def boolean matchRequireAbsenceOf(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "require absence of";
		val firstKeywordSegment = "require";
		val Iterable<Class<?>> relevantContexts = #[Matcher];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts, true);
	}

	private def boolean matchCorrespondingTo(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "corresponding to";
		val firstKeywordSegment = "corresponding";
		val Iterable<Class<?>> relevantContexts = #[Matcher];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchTaggedWith(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "tagged with";
		val firstKeywordSegment = "tagged";
		val Iterable<Class<?>> relevantContexts = #[Matcher, ActionStatement];
		// Before "tagged value" an Xtend code block can occur, such that the context can be
		// a node deep in the Xtend block AST -> recursive resolution necessary
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts, true);
	}
	
	private def boolean matchAndInitialize(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "and initialize";
		val firstKeywordSegment = "and";
		val Iterable<Class<?>> relevantContexts = #[ActionStatement];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}
	
	private def boolean matchAddCorrespondenceBetween(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "add correspondence between";
		val firstKeywordSegment = "add";
		val Iterable<Class<?>> relevantContexts = #[ActionStatement];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}

	private def boolean matchRemoveCorrespondenceBetween(Keyword keyword, ContentAssistContext context,
		ICompletionProposalAcceptor acceptor) {
		val realKeyword = "remove correspondence between";
		val firstKeywordSegment = "remove";
		val Iterable<Class<?>> relevantContexts = #[ActionStatement];
		return replaceKeywordIfMatched(keyword, context, acceptor, firstKeywordSegment, realKeyword, relevantContexts);
	}
	
	private def boolean replaceKeywordIfMatched(
		Keyword actualKeyword,
		ContentAssistContext context,
		ICompletionProposalAcceptor acceptor,
		String keywordToMatch,
		String replacementKeyword,
		Iterable<Class<?>> contextsToMatch
	) {
		replaceKeywordIfMatched(actualKeyword, context, acceptor, keywordToMatch, replacementKeyword, contextsToMatch,
			false);
	}

	private def boolean replaceKeywordIfMatched(
		Keyword actualKeyword,
		ContentAssistContext context,
		ICompletionProposalAcceptor acceptor,
		String keywordToMatch,
		String replacementKeyword,
		Iterable<Class<?>> contextsToMatch,
		boolean recursiveContext
	) {
		if (actualKeyword.value != keywordToMatch) {
			return false;
		}

		val actualContexts = newArrayList;
		actualContexts += context.currentModel;
		if (recursiveContext) {
			while (actualContexts.last.eContainer !== null) {
				actualContexts += actualContexts.last.eContainer;
			}
		}
		if (!contextsToMatch.exists[matchedContext|actualContexts.exists[matchedContext.isInstance(it)]]) {
			return false;
		}

		val ICompletionProposal proposal = createCompletionProposal(replacementKeyword, replacementKeyword,
			getImage(grammarAccess.reactionsFileRule), context);
		getPriorityHelper().adjustKeywordPriority(proposal, "top");
		acceptor.accept(proposal)
		return true;
	}

	override completeReactionsImport_UseQualifiedNames(EObject model, Assignment assignment,
		ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		val realKeyword = "using qualified names";
		val ICompletionProposal proposal = createCompletionProposal(realKeyword, realKeyword,
			getImage(grammarAccess.reactionsImportRule), context);
		getPriorityHelper().adjustKeywordPriority(proposal, context.getPrefix());
		acceptor.accept(proposal)
	}

}
