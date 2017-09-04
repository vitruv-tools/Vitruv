package tools.vitruv.dsls.commonalities.export

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.CommonalityDeclaration
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

class CommonalityFileResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
	
	@Inject extension IEObjectDescriptionProvider eObjectDescriptionProvider
	
	def dispatch createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		true
	}
	
	def dispatch createEObjectDescriptions(CommonalityDeclaration commonality, IAcceptor<IEObjectDescription> acceptor) {
		acceptor.accept(describe(commonality))
		return true
	}
	
	def dispatch createEObjectDescriptions(ParticipationClass participationClass, IAcceptor<IEObjectDescription> acceptor) {
		acceptor.accept(describe(participationClass))
		return false
	}
	
	def dispatch createEObjectDescriptions(AttributeDeclaration attribute, IAcceptor<IEObjectDescription> acceptor) {
		acceptor.accept(describe(attribute))
		return false
	}
}
