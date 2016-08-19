package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff

import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Code2SEFFFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

class EJBJava2PCMCode2SEFFFactory implements Code2SEFFFactory {
	override BasicComponentFinding createBasicComponentFinding() {
		return new EJBBasicComponentFinder()
	}

	override InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
		CorrespondenceModel correspondenceInstance, BasicComponent basicComponent) {
		return new InterfaceOfExternalCallFinder4EJB(correspondenceInstance, basicComponent)
	}

	override ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
		CorrespondenceModel correspondenceInstance) {
		return new ResourceDemandingBehaviourForClassMethodFinder4EJB(correspondenceInstance)
	}

	override AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
		BasicComponentFinding basicComponentFinding, CorrespondenceModel correspondenceInstance,
		BasicComponent basicComponent) {
		return new EJB2PCMFunctionClassificationStrategy(basicComponentFinding, correspondenceInstance,
			basicComponent)
	}
}
