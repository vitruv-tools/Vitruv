package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm.seff

import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Code2SEFFFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class EJBJava2PCMCode2SEFFFactory implements Code2SEFFFactory {
	override BasicComponentFinding createBasicComponentFinding() {
		return new EJBBasicComponentFinder()
	}

	override InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
		CorrespondenceInstance<Correspondence> correspondenceInstance, BasicComponent basicComponent) {
		return new InterfaceOfExternalCallFinder4EJB(correspondenceInstance, basicComponent)
	}

	override ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
		CorrespondenceInstance<Correspondence> correspondenceInstance) {
		return new ResourceDemandingBehaviourForClassMethodFinder4EJB(correspondenceInstance)
	}

	override AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
		BasicComponentFinding basicComponentFinding, CorrespondenceInstance<Correspondence> correspondenceInstance,
		BasicComponent basicComponent) {
		return new EJB2PCMFunctionClassificationStrategy(basicComponentFinding, correspondenceInstance,
			basicComponent)
	}
}
