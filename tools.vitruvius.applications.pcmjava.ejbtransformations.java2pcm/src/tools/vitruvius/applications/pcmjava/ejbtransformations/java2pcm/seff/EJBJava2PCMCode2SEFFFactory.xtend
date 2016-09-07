package tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.seff

import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import tools.vitruvius.applications.pcmjava.seffstatements.code2seff.BasicComponentFinding
import tools.vitruvius.applications.pcmjava.seffstatements.code2seff.Code2SEFFFactory
import tools.vitruvius.framework.correspondence.CorrespondenceModel

class EJBJava2PCMCode2SEFFFactory implements Code2SEFFFactory {
	override BasicComponentFinding createBasicComponentFinding() {
		return new EJBBasicComponentFinder()
	}

	override InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
		CorrespondenceModel correspondenceModel, BasicComponent basicComponent) {
		return new InterfaceOfExternalCallFinder4EJB(correspondenceModel, basicComponent)
	}

	override ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
		CorrespondenceModel correspondenceModel) {
		return new ResourceDemandingBehaviourForClassMethodFinder4EJB(correspondenceModel)
	}

	override AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
		BasicComponentFinding basicComponentFinding, CorrespondenceModel correspondenceModel,
		BasicComponent basicComponent) {
		return new EJB2PCMFunctionClassificationStrategy(basicComponentFinding, correspondenceModel,
			basicComponent)
	}
}
