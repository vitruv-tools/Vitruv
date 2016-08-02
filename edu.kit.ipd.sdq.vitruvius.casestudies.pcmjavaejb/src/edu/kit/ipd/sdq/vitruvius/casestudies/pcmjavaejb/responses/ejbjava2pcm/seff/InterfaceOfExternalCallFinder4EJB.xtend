package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm.seff

import org.emftext.language.java.members.Method
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class InterfaceOfExternalCallFinder4EJB implements InterfaceOfExternalCallFinding {
	final CorrespondenceInstance<Correspondence> correspondenceInstance
	final BasicComponent basicComponent

	new(CorrespondenceInstance<Correspondence> correspondenceInstance, BasicComponent basicComponent) {
		this.correspondenceInstance = correspondenceInstance
		this.basicComponent = basicComponent
	}

	override InterfacePortOperationTuple getCalledInterfacePort(Method calledMethod) {
		// TODO Auto-generated method stub
		return null
	}
}
