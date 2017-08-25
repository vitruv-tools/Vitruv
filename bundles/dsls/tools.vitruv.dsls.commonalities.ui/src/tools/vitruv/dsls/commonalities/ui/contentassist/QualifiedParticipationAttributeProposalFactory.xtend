package tools.vitruv.dsls.commonalities.ui.contentassist

import org.eclipse.xtext.resource.IEObjectDescription
import static tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter.*
import tools.vitruv.dsls.commonalities.language.elements.ParticipationAttribute
import org.eclipse.emf.ecore.EObject
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import com.google.inject.Singleton

class QualifiedParticipationAttributeProposalFactory extends CommonalitiesLanguageProposalFactory {
	
	@Inject AttributePrefixMatcher attributePrefixMatcher
	
	override apply(IEObjectDescription description) {
		completionProposal(description.name)
			.appendText(description.name.firstSegment)
			.appendText(DOMAIN_METACLASS_SEPARATOR)
			.appendText(description.name.getSegment(1))
			.appendText(METACLASS_ATTRIBUTE_SEPARATOR)
			.appendText(description.name.getSegment(2))
			.appendInfoText(' : ')
			.appendInfoText(description.EObjectOrProxy.type)
			.withImageOf(description.EObjectOrProxy)
			.usePrefixMatcher(attributePrefixMatcher)
			.propose()
	}
	
	def private static getType(EObject attribute) {
		if (attribute instanceof ParticipationAttribute) {
			return attribute.type.name
		}
		return ''
	}
}

@Singleton
package class AttributePrefixMatcher extends LanguageElementPrefixMatcher {
	@Inject extension IQualifiedNameConverter qualifiedNameConverter
	
	override isCandidateMatchingPrefix(String name, String prefix) {
		isCandidateMatchingPrefix(name.toQualifiedName, prefix.toQualifiedName)
	}
	
	def package isCandidateMatchingPrefix(QualifiedName name, QualifiedName prefix) {
		switch (prefix.segmentCount) {
			case 1: prefix.getSegment(0).matchesAnySegmentStartIgnoringCase(name, 0, 1, 2)
			case 2: prefix.matchesQualifiedNamePart(name, 0) && prefix.matchesStartIgnoringCase(name, 1)
			case 3: prefix.matchesQualifiedNamePart(name, 0, 1) && prefix.matchesStartIgnoringCase(name, 2)
		}
	}
}